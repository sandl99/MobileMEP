//package GWO;
//
//import java.io.*;
//import java.util.*;
//import Info.*;
//import PSO_GA.Initializer;
//
//public class Grey_wolf_optimizer {
//	double r1;
//	double r2;
//	int N;
//	int D;
//	int maxiter;
//	public double alfa[];
//	double beta[];
//	double delta[];
//	double Lower[];
//	double Upper[];
//	public double XX[][];
//	double X1[][];
//	double X2[][];
//	double X3[][];
//	double fitness[];
//	double BESTVAL[];
//	double iterdep[];
//	double a[];
//	double A1[];
//	double C1[];
//	double A2[];
//	double C2[];
//	double A3[];
//	double C3[];
//
//	int n; // so Sensor
//	double r;
//	int k;
//	public ArrayList<Sensor> list;
//
//	public static double calDistance(Point p1, Point p2) {
//		return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
//	}
//
//	public void readData(String filename) {
//		String input;
//		this.list = new ArrayList<Sensor>();
//		try {
//			FileReader fileReader = new FileReader(filename);
//			BufferedReader br = new BufferedReader(fileReader);
//			input = br.readLine();
//			String[] split = input.split(" ");
//			this.n = Integer.parseInt(split[0]);
//			this.r = Double.parseDouble(split[1]);
//			for (int i = 0; i < n; i++) {
//				input = br.readLine();
//				split = input.split(" ");
//				list.add(new Sensor((int) Double.parseDouble(split[0]), (int) Double.parseDouble(split[1]),
//						(int) Double.parseDouble(split[2]), (int) Double.parseDouble(split[3]),
//						(int) Double.parseDouble(split[4]), (int) Double.parseDouble(split[5]),
//						(int) Double.parseDouble(split[6]), (int) Double.parseDouble(split[7]), r));
//
//			}
//			br.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public double calFitness(double[] indi) {
//		for (Sensor s : list) {
//			s.setDefault();
//		}
//		double result = 0;
////		Point tmp = new Point(Config.X0, Config.Y0);
//		double tmp_x = Config.X0, tmp_y = Config.Y0;
//		for (int i = 1; i <= Config.MAX_LEN; i++) {
//			tmp_x += Config.DT * Config.VI * Math.cos(indi[i - 1]);
//			tmp_y += Config.DT * Config.VI * Math.sin(indi[i - 1]);
//			if (tmp_x > Config.W)
//				break;
//			if (tmp_y > Config.H || tmp_y < 0)
//				return Double.MAX_VALUE;
//			for (Sensor s : list) {
//				s.move(Config.DT * Config.VS);
//				result += s.MEx(tmp_x, tmp_y) * Config.DT;
////				s.setDefault();
//			}
////			for (int it = 0; it < s.; it) {
////				s.get(it).move()
////			}
//		}
//		// Code for intruder travel to target
//		int len = (int) Math.abs(((Config.YN - tmp_y * 1.0) / Config.DS));
//		double sign = 1.0;
//		if (Config.YN < tmp_y)
//			sign = -1.0;
//		while (Math.abs(tmp_y - Config.YN) >= 0.2) {
//			tmp_y += sign * Config.DS;
//			for (Sensor s : list) {
//				s.move(Config.DT * Config.VS);
//				result += s.MEx(tmp_x, tmp_y) * Config.DT;
////				s.setDefault();
//			}
//		}
//
//		return result;
//	}
//
//	public Grey_wolf_optimizer(double iLower[], double iUpper[], int imaxiter, int iN) {
//		maxiter = imaxiter;
//		Lower = iLower;
//		Upper = iUpper;
//		N = iN;
//		D = Upper.length;
//		a = new double[D];
//		XX = new double[N][D];
//		alfa = new double[D];
//		beta = new double[D];
//		delta = new double[D];
//		A1 = new double[D];
//		C1 = new double[D];
//		A2 = new double[D];
//		C2 = new double[D];
//		A3 = new double[D];
//		C3 = new double[D];
//		BESTVAL = new double[maxiter];
//		iterdep = new double[maxiter];
//		X1 = new double[N][D];
//		X2 = new double[N][D];
//		X3 = new double[N][D];
//
//	}
//
//	double[][] sort_and_index(double[][] XXX) {
//		double[] yval = new double[N];
//		for (int i = 0; i < N; i++) {
//			yval[i] = calFitness(XXX[i]);
//		}
//		ArrayList<Double> nfit = new ArrayList<Double>();
//		for (int i = 0; i < N; i++) {
//			nfit.add(yval[i]);
//		}
//		ArrayList<Double> nstore = new ArrayList<Double>(nfit);
//		Collections.sort(nfit);
//		double[] ret = new double[nfit.size()];
//		Iterator<Double> iterator = nfit.iterator();
//		int ii = 0;
//		while (iterator.hasNext()) {
//			ret[ii] = iterator.next().doubleValue();
//			ii++;
//		}
//		int[] indexes = new int[nfit.size()];
//		for (int n = 0; n < nfit.size(); n++) {
//			indexes[n] = nstore.indexOf(nfit.get(n));
//		}
//		double[][] B = new double[N][D];
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < D; j++) {
//				B[i][j] = XXX[indexes[i]][j];
//			}
//		}
//
//		return B;
//	}
//
//	public void init() {
//		Random r = new Random();
//		for (int i = 0; i < N; i++) {
//			ArrayList<Double> tmp = Initializer.initGenes();
//			for (int j = 0; j < tmp.size(); j++) {
//				XX[i][j] = tmp.get(j);
//			}
//			for (int j = tmp.size(); j < D; j++) {
//				XX[i][j] = r.nextDouble() * Math.PI - Math.PI / 2;
//			}
//		}
//
//		XX = sort_and_index(XX);
//		for (int i = 0; i < D; i++) {
//			alfa[i] = XX[0][i];
//		}
//		for (int i = 0; i < D; i++) {
//			beta[i] = XX[1][i];
//		}
//		for (int i = 0; i < D; i++) {
//			delta[i] = XX[2][i];
//		}
//		for (int i = 0; i < N; i++) {
//			System.out.println(calFitness(XX[i]));
//		}
//
//	}
//
//	double[][] simplebounds(double s[][]) {
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < D; j++) {
//				if (s[i][j] < Lower[j]) {
//					s[i][j] = Lower[j] * ((Upper[j] - Lower[j]) * Math.random());
//				}
//				if (s[i][j] > Upper[j]) {
//					s[i][j] = Lower[j] * ((Upper[j] - Lower[j]) * Math.random());
//				}
//			}
//		}
//		return s;
//	}
//
//	public double[][] solution() {
//
//		init();
//		int iter = 1;
//		while (iter < maxiter) {
//
//			for (int j = 0; j < D; j++) {
//				a[j] = 2.0 - ((double) iter * (2.0 / (double) maxiter));
//			}
//
//			for (int i = 0; i < N; i++) {
//				for (int j = 0; j < D; j++) {
//					r1 = Math.random();
//					r2 = Math.random();
//					for (int ii = 0; ii < D; ii++) {
//						A1[ii] = 2.0 * a[ii] * r1 - a[ii];
//					}
//					for (int ii = 0; ii < D; ii++) {
//						C1[ii] = 2.0 * r2;
//					}
//
//					X1[i][j] = alfa[j] - A1[j] * (Math.abs(C1[j] * alfa[j] - XX[i][j]));
//					X1 = simplebounds(X1);
//					r1 = Math.random();
//					r2 = Math.random();
//					for (int ii = 0; ii < D; ii++) {
//						A2[ii] = 2.0 * a[ii] * r1 - a[ii];
//					}
//					for (int ii = 0; ii < D; ii++) {
//						C2[ii] = 2.0 * r2;
//					}
//
//					X2[i][j] = beta[j] - A2[j] * (Math.abs(C2[j] * beta[j] - XX[i][j]));
//					X2 = simplebounds(X2);
//					r1 = Math.random();
//					r2 = Math.random();
//					for (int ii = 0; ii < D; ii++) {
//						A3[ii] = 2.0 * a[ii] * r1 - a[ii];
//					}
//					for (int ii = 0; ii < D; ii++) {
//						C3[ii] = 2.0 * r2;
//					}
//
//					X3[i][j] = delta[j] - A3[j] * (Math.abs(C3[j] * delta[j] - XX[i][j]));
//					X3 = simplebounds(X3);
//					XX[i][j] = (X1[i][j] + X2[i][j] + X3[i][j]) / 3.0;
//
//				}
//			}
//			XX = simplebounds(XX);
//			XX = sort_and_index(XX);
//
//			for (int i = 0; i < D; i++) {
//				XX[N - 1][i] = XX[0][i];
//			}
//
//			for (int i = 0; i < D; i++) {
//				alfa[i] = XX[0][i];
//			}
//			for (int i = 0; i < D; i++) {
//				beta[i] = XX[1][i];
//			}
//			for (int i = 0; i < D; i++) {
//				delta[i] = XX[2][i];
//			}
//
//			BESTVAL[iter] = calFitness(XX[0]);
//			System.out.println("GWO best " + iter + " : " + calFitness(alfa));
//			iter++;
//		}
//
//		double[][] out = new double[2][D];
//		for (int i = 0; i < D; i++) {
//			out[1][i] = alfa[i];
//		}
//		out[0][0] = calFitness(alfa);
//		return out;
//
//	}
//
//	void toStringnew() {
//		double[][] in = solution();
//		System.out.println("Optimized value = " + in[0][0]);
//		for (int i = 0; i < D; i++) {
//			System.out.println("x[" + i + "] = " + in[1][i]);
//		}
//	}
//
//	public static void main(String[] args) {
//		double[] iLower = new double[Info.Config.MAX_LEN];
//		double[] iUpper = new double[Info.Config.MAX_LEN];
//		Arrays.fill(iLower, -Math.PI / 2);
//		Arrays.fill(iUpper, Math.PI / 2);
//		Grey_wolf_optimizer gwo = new Grey_wolf_optimizer(iLower, iUpper, 50, 10);
//		gwo.readData("./Data/50/test14.txt");
//		gwo.init();
////		gwo.solution();
//	}
//
//}