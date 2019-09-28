package Info;

import java.util.ArrayList;

public class Sensor {
	final double LAMDA = 1.0; // sua
	final int ALPHA = 2;
	private Point center;
	private Point direct;
	private int dir;
	private double radius;
	public ArrayList<Point> pL = new ArrayList<Point>();

	public Sensor(int p1x, int p1y, int p2x, int p2y, int p3x, int p3y, int p4x, int p4y, double r) {
		pL.add(new Point(p1x, p1y));
		pL.add(new Point(p2x, p2y));
		pL.add(new Point(p3x, p3y));
		pL.add(new Point(p4x, p4y));
		radius = r;
		center = new Point(p1x, p1y);
		direct = new Point(pL.get(1).getX(), pL.get(1).getY());
	
		dir = 1;
	}

	public Sensor(int W, int H, double R) {
		Point p1 = new Point((int) (Math.random() * W), (int) (Math.random() * H));
		Point p3 = new Point((int) (Math.random() * W), (int) (Math.random() * H));
		Point p2 = new Point(p1.getX(), p3.getY());
		Point p4 = new Point(p3.getX(), p1.getY());
		pL.add(p1);
		pL.add(p2);
		pL.add(p3);
		pL.add(p4);
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public static double d(Point p1, Point p2) {

		double i = Math.abs(p1.getX() - p2.getX() + p1.getY() - p2.getY());
		return i;
	}

	public void move(double s) {
		if (d(direct, center) > s) {
			if (direct.getX() == center.getX()) {
				if (direct.getY() > center.getY()) {
					center.setY(center.getY() + s);
				}
				else {
					center.setY(center.getY() - s);
				}
			} else {
				if (direct.getX() > center.getX()) {
					center.setX(center.getX() + s);
				}
				else {
					center.setX(center.getX() - s);
				}
			}
		}
		else {
			double tmp = s - d(direct, center);
			center.setX(direct.getX());
			center.setY(direct.getY());
			dir = (dir + 1) % 4;
			direct.setX(pL.get(dir).getX());
			direct.setY(pL.get(dir).getY());
			move(tmp);
		}
	}

	public void setDefault() {
		center.setX(pL.get(0).getX());
		center.setY(pL.get(0).getY());
		direct.setX(pL.get(1).getX());
		direct.setY(pL.get(1).getY());
		dir = 1;
	}

	public double MEx(double x, double y) {
		double distance = Math.sqrt((center.getX() - x) * (center.getX() - x) + (center.getY() - y) * (center.getY() - y));
//		if (distance < this.radius)
			return LAMDA / Math.pow(distance, ALPHA);
//			return 1.0;
//		else
//			return 0;
	}
}
