package PSO_GA;

//package tools;

import java.util.ArrayList;
import java.util.Random;

//import model.Config;
import Info.*;

/**
* Contains static methods for initializing Individual.
* @author <strong>Vinsmoke Sanji</strong>
*
*/
public class Initializer {
	
	private static final int LOWER_BOUND_SPLIT_POINTS = 2;
	private static final int UPPER_BOUND_SPLIT_POINTS = 20;
//	private static final double H = 40;
//	private static final double W = 100;
//	final static double X0 = 0.0;
//	final static double Y0 = 10.0;
//	final static double DS = 0.2;
//	private static final 
	
	/**
	 * Create random x-points to drive Individual into different directions.
	 * @param numSplit Number of parts to be splitted.
	 * @return A set of x-points.
	 */
	private static double[] splitPoint(int numSplit) {
		Random rd       = new Random();
		
		double[] result = new double[numSplit];
		double  segment = Config.W / (numSplit - 1);
		
		for (int count = 0; count < numSplit - 1; count++) {
			double ranPoint = rd.nextDouble() * segment + (count * segment);
			result[count] = ranPoint;
		}
		
		result[numSplit - 1] = Config.W;
		
		return result;
	}
	
	/**
	 * Create a random valid genes.
	 * @return A random valid genes.
	 */
	public static ArrayList<Double> initGenes() {

		Random rd = new Random();

		int numSplit = rd.nextInt(UPPER_BOUND_SPLIT_POINTS - LOWER_BOUND_SPLIT_POINTS + 1) + LOWER_BOUND_SPLIT_POINTS;

		double[] xPoints = splitPoint(numSplit);
		double[] yPoints = new double[numSplit];

		for (int i = 0; i < numSplit; i++) {
			yPoints[i] = rd.nextDouble() * Config.H;
		}

		ArrayList<Double> genes = new ArrayList<Double>();

		Point startPoint = new Point(Config.X0, Config.Y0);
		for (int i = 0; i < numSplit; i++) {
			initSubGenes(genes, startPoint, new Point(xPoints[i], yPoints[i]));
		}

		return genes;
	}
	
	/**
	 * Connect <code>startPoint</code> toward <code>endPoint</code>
	 * and make it be part of <code>genes</code>. 
	 * @param genes A gene segment that need to be prolonged.
	 * @param startPoint The end point of <code>genes</code>.
	 * @param endPoint The point toward what <code>genes</code> need to prolonged.
	 */
	protected static void initSubGenes(ArrayList<Double> genes, Point startPoint, Point endPoint) {
		Random rd = new Random();

		double xCur = startPoint.getX();
		double yCur = startPoint.getY();

		double xEnd = endPoint.getX();
		double yEnd = endPoint.getY();

		double xDistance = xEnd - xCur;
		double yDistance = yEnd - yCur;

		double phi, dx, dy;

		while (xDistance > Config.DS) {
			double deltaProb = Math.sqrt(1.0 - (yDistance - Config.H) * (yDistance - Config.H) / (Config.H * Config.H));
			if (yDistance < 0)
				deltaProb = -deltaProb;
			double goUpProb = (1 + deltaProb) / 2;

			do {
				if (rd.nextDouble() < goUpProb) {
					phi = rd.nextDouble() * Math.PI / 2;
				} else {
					phi = -rd.nextDouble() * Math.PI / 2;
				}

				dx = Config.DS * Math.cos(phi);
				dy = Config.DS * Math.sin(phi);
			} while (yCur + dy > Config.H || yCur + dy < 0);

			genes.add(phi);

			xCur += dx;
			yCur += dy;
			xDistance = xEnd - xCur;
			yDistance = yEnd - yCur;
		}

		if (yDistance < 0) {
			phi = -Math.acos(xDistance / Config.DS);
		} else {
			phi = Math.acos(xDistance / Config.DS);
		}

		genes.add(phi);

		xCur = xEnd;
		yCur += Math.sin(phi) * Config.DS;
		startPoint.setX(xCur);
		startPoint.setY(yCur);
	}

}
