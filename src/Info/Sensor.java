package Info;

import java.util.ArrayList;
import javafx.util.Pair;

public class Sensor {
	private Point center;
	private Point after;
	private Point before;
	
	private int dir;
//	private double radius;
	
	private double[] vec = new double[2];
	private double alpCos;
	
	public Point getAfter() {
		return after;
	}


	public void setAfter(Point after) {
		this.after = after;
	}


	public Point getBefore() {
		return before;
	}


	public void setBefore(Point before) {
		this.before = before;
	}

	private int numPos;
	public ArrayList<Point> pL = new ArrayList<Point>();

	public Sensor() {
		dir = 1;
	}


	public Sensor(int W, int H, int numPos, double R) {
		Point p1 = new Point((int) (Math.random() * W), (int) (Math.random() * H));
		Point p3 = new Point((int) (Math.random() * W), (int) (Math.random() * H));
		Point p2 = new Point(p1.getX(), p3.getY());
		Point p4 = new Point(p3.getX(), p1.getY());
		pL.add(p1);
		pL.add(p2);
		pL.add(p3);
		pL.add(p4);
		this.numPos = numPos;
	}
	public void setNumPos(int numPos) {
		this.numPos = numPos;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}
	
	public void setPos(Point p) {
		pL.add(p);
	}
	public static double d(Point p1, Point p2) {

		double i = Math.abs(p1.getX() - p2.getX() + p1.getY() - p2.getY());
		return i;
	}

	private double calDis(Point p1, Point p2) {
		double i = (p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY());
		return Math.sqrt(i);
	}
	
	private void calVector() {
		this.vec[0] = this.after.getX() - this.before.getX();
		this.vec[1] = this.after.getY() - this.before.getY();
	}
	private void calAngle() {
		calVector();
		this.alpCos = this.vec[0] / calDis(this.before, this.after);
	}
	private void upDatePos(double s) {
		this.calAngle();
		int sig = 1;
		if (this.after.getY() > this.before.getY()) {
			sig = 1;
		} else if (this.after.getY() < this.before.getY()) {
			sig = -1;
		} else {
			sig = 0;
		}
		this.center.setX(this.center.getX() + s * alpCos);
		double alpSin = Math.sqrt(1 - alpCos * alpCos);
		this.center.setY(this.center.getY() + s * sig * alpSin);	
	}
	public void move(double s) {
		if (this.calDis(center, after) <= s) {
			this.center.setX(this.after.getX());
			this.center.setY(this.after.getY());
			
			this.before.setX(this.after.getX());
			this.before.setY(this.after.getY());
			
			dir = (dir + 1) % numPos;
			this.after.setX(pL.get(dir).getX());
			this.after.setY(pL.get(dir).getY());
			
		} else {
			upDatePos(s);
		}
//		if (d(direct, center) > s) {
//			if (direct.getX() == center.getX()) {
//				if (direct.getY() > center.getY()) {
//					center.setY(center.getY() + s);
//				}
//				else {
//					center.setY(center.getY() - s);
//				}
//			} else {
//				if (direct.getX() > center.getX()) {
//					center.setX(center.getX() + s);
//				}
//				else {
//					center.setX(center.getX() - s);
//				}
//			}
//		}
//		else {
//			double tmp = s - d(direct, center);
//			center.setX(direct.getX());
//			center.setY(direct.getY());
//			dir = (dir + 1) % 4;
//			direct.setX(pL.get(dir).getX());
//			direct.setY(pL.get(dir).getY());
//			move(tmp);
//		}
	}

	public void setDefault() {
		center.setX(pL.get(0).getX());
		center.setY(pL.get(0).getY());
		dir = 1;
		
		this.before.setX(pL.get(dir - 1).getX());
		this.before.setY(pL.get(dir - 1).getY());
		
		this.after.setX(pL.get(dir).getX());
		this.after.setY(pL.get(dir).getY());
	}

	public double MEx(double x, double y) {
		double distance = Math.sqrt((center.getX() - x) * (center.getX() - x) + (center.getY() - y) * (center.getY() - y));
//		if (distance < this.radius)
			return Config.LAMDA / Math.pow(distance, Config.ALPHA);
//			return 1.0;
//		else
//			return 0;
	}
}
