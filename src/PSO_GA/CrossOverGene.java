package PSO_GA;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Info.Config;
import Info.Point;
import Info.Sensor;

public class CrossOverGene {
	private Point src;
	private Point des;
	private int nGene;
	double resArrX[], resArrY[];

	public CrossOverGene(Point src, Point des, int nGene) {
		super();
		this.src = src;
		this.des = des;
		this.nGene = nGene;
	}

	/*
	 * Calculate distance
	 */
	private double calDis(Point p1, Point p2) {
		double i = (p1.getX() - p2.getX()) * (p1.getX() - p2.getX())
				+ (p1.getY() - p2.getY()) * (p1.getY() - p2.getY());
		return Math.sqrt(i);
	}

	/*
	 * Check Cross-able or not
	 */
	private boolean checkGeneMakeable() {
		boolean res = true;

		if (des.getX() < src.getX()) {
			res = false;
		}

		if (nGene * Config.DS <= calDis(this.src, this.des)) {
			res = false;
//			System.out.println("sai roi");
		}

		return res;
	}

	/*
	 * Get a list of X Divide sub gene to a set equal gene X nGene - 1 length
	 */
	private double[] getListX() {
		double dis = des.getX() - src.getX();
		double piv[] = new double[this.nGene];
		double dx = dis / this.nGene;

		piv[0] = src.getX();
		for (int i = 1; i < this.nGene; i++) {
			piv[i] = piv[i - 1] + dx;
//			System.out.println(piv[i - 1] + " ");
		}
		return piv;
	}

	private double[] makeGeneY(double[] resArrX) {
		double[] resArrY = new double[this.nGene];
		double[] dyArr = new double[this.nGene];
		resArrY[0] = src.getY();
		double dy, dx, sumY = 0;
		double deltaY = des.getY() - src.getY();
		for (int i = 1; i < this.nGene - 1; i++) {
			dx = resArrX[i] - resArrX[i - 1];
			dy = Math.sqrt(Config.DS * Config.DS - dx * dx);
			sumY += dy;
			dyArr[i - 1] = dy;
		}
		int index = 0;
		while (Math.abs(deltaY - sumY) >= Config.DS && index <= this.nGene - 1) {
			sumY -= 2 * dyArr[index];
			dyArr[index] *= -1;
			index += 1;
		}
		Random rd = new Random();

		for (int i = 0; i < dyArr.length - 2; i++) {
			int randomIndexToSwap = rd.nextInt(dyArr.length - 2);
			double temp = dyArr[randomIndexToSwap];
			dyArr[randomIndexToSwap] = dyArr[i];
			dyArr[i] = temp;
		}

		for (int i = 1; i < this.nGene - 1; i++) {
			resArrY[i] = resArrY[i - 1] + dyArr[i];
		}

		return resArrY;
	}

	private Point findLastPoint(Point p) {
		/*
		 * https://math.stackexchange.com/questions/256100/how-can-i-find-the-points-at-
		 * which-two-circles-intersect
		 */
		Point resP = new Point();
		double R = calDis(p, this.des);
		double r = Config.DS;
		resP.x = (p.x + des.x) / 2 + Math.sqrt(4 * r * r / (R * R) - 1) * (des.y - p.y) / 2;
		resP.y = (p.y + des.y) / 2 + Math.sqrt(4 * r * r / (R * R) - 1) * (p.x - des.x) / 2;
		return resP;
	}

	public List<Double> subGene() {
		List<Double> res = new ArrayList<Double>();
		if (!PSO_Search.checkGeneMakeAble(src, des, nGene)) {
			System.out.println("Can't make subgene");
			return res;
		}

		double dx = (des.getX() - src.getX()) / this.nGene;
		double r = Math.min(dx / 2, (Config.DS - dx) / 2);

		this.resArrX = makeGeneX(getListX(), r);
		this.resArrY = makeGeneY(resArrX);

		Point lastP = findLastPoint(new Point(resArrX[this.nGene - 2], resArrY[this.nGene - 2]));
		resArrX[this.nGene - 1] = lastP.x;
		resArrY[this.nGene - 1] = lastP.y;
		double _dx, _dy;
		int i;
		for (i = 0; i < nGene - 1; i++) {
			_dx = resArrX[i + 1] - resArrX[i];
			_dy = resArrY[i + 1] - resArrY[i];
			res.add(Math.atan(_dy / _dx));
		}
		_dx = des.x - resArrX[i];
		_dy = des.y - resArrY[i];
		res.add(Math.atan(_dy / _dx));
		return res;

	}

	/*
	 * random value for x pivot and r neibor (Lân cận)
	 */
	private double uniformRan(double x, double r) {
		Random rd = new Random();
		return (x - r + 2 * r * rd.nextDouble());
	}

	private double[] makeGeneX(double[] piv, double r) {
		double pivX[] = new double[nGene];
		pivX[0] = src.getX();

		for (int i = 1; i < nGene - 1; i++) {
			pivX[i] = uniformRan(piv[i], r);
		}
		return pivX;
	}

	private void drawGene() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				for (int i = 0; i < nGene - 1; i++) {
					g2.draw(new Line2D.Double(resArrX[i] * 10, Config.H * 10 - resArrY[i] * 10, resArrX[i + 1] * 10,
							Config.H * 10 - resArrY[i + 1] * 10));
				}
				g2.draw(new Line2D.Double(resArrX[nGene - 1] * 10, Config.H * 10 - resArrY[nGene - 1] * 10, des.x * 10,
						Config.H * 10 - des.y * 10));
			}
		};
		frame.add(panel);
		frame.setSize((int) (Config.W) * 10, (int) (Config.H) * 10);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		CrossOverGene crossOG = new CrossOverGene(new Point(0, 10), new Point(10, 20), 100);
		crossOG.subGene();
		crossOG.drawGene();
	}
}
