package GA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import Draw_PSOGA.SensorRunableGA;
import Info.Config;
import Info.Point;
import Info.Sensor;
import PSO_GA.Initializer;

public class MBGA {
//	final int W = 100; // 100-> 10
//	final int H = 40; // 100 ->10
//	final static double VI = 2; // van toc intruder suas
//	final static int VS = 1; // van toc cam bien
//	final int nb = 100; // so nst
//	final static double DT = 0.1; // delta t
//	final static double X0 = 0.0;
//	final static double Y0 = 30.0; // sua
//	final static double YN = 10.0; // sua
//	final double XN = 100.0;

	public int n; // so Sensor
	double r;
	int k;

	double[] ybest;

	ArrayList<Sensor> list;
	public double[] xk;
	public double[] yk;

	/*
	 * Táº¡o tá»�a Ä‘á»™ cho vÃ¹ng
	 */
	public void Coordinate() {
		xk = new double[(int) Config.W * 10];
		double t = 0.0;
		for (int i = 0; i < xk.length; i++) {
			xk[i] = t;
			t += 0.1;
		}
		t = 0.0;
		yk = new double[(int) Config.H * 10];
		for (int i = 0; i < yk.length; i++) {
			yk[i] = t;
			t += 0.1;
		}
	}

	/*
	 * khá»Ÿi táº¡o cáº£m biáº¿n
	 */
	public void readData(String filename) {
		String input;
		this.list = new ArrayList<Sensor>();
		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader br = new BufferedReader(fileReader);
			input = br.readLine();
			String[] split = input.split(" ");
			this.n = Integer.parseInt(split[0]);
			for (int i = 0; i < n; i++) {
				input = br.readLine();
				split = input.split(" ");
				int numPos = Integer.parseInt(split[0]);
				Sensor s = new Sensor();
				s.setCenter(new Point(Double.parseDouble(split[1]), Double.parseDouble(split[2])));
				int dem = 3;
				for (int j = 0; j < numPos; j++) {
					int t1 = dem++;
					int t2 = dem++;
					s.setPos(new Point(Double.parseDouble(split[t1]), Double.parseDouble(split[t2])));
				}
				s.setBefore(new Point(Double.parseDouble(split[3]), Double.parseDouble(split[4])));
				s.setAfter(new Point(Double.parseDouble(split[5]), Double.parseDouble(split[6])));
				s.setNumPos(numPos);
				list.add(s);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * sinh ngáº«u nhiÃªn x
	 */
	public double[] xSolution() {

		ArrayList<Double> xS = new ArrayList<Double>();
		k = 0;
		double range = 0.0;
		while (range < Config.W) {
			double x = Math.random() * (Config.DT * Config.VI);
			range += x;
			k++;
			xS.add(x);
		}
		double[] xs = new double[xS.size()];
		for (int j = 0; j < xs.length; j++) {
			xs[j] = xS.get(j);
		}
		return xs;
	}

	/*
	 * Táº¡o máº£ng chá»©a cac so ngau nien tu 1 den n
	 */
	public static ArrayList<Integer> Random(int n) {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		do {
			int i = (int) (Math.random() * n);
			if (intList.contains(i) == false)
				intList.add(i);
		} while (intList.size() < n);
		return intList;
	}

	/*
	 * hoan vi input ngau nhien va khong vuot ra ngoai H
	 */
	public double[] hoanvi(double[] input) {
		ArrayList<Integer> list = Random(input.length);
		double[] output = new double[input.length];
		boolean is = true;
		double value = 0.0;
		do {
			for (int i = 0; i < output.length; i++) {
				output[i] = input[list.get(i)];
				value += output[i];
				if (value > Config.H)
					is = false;
			}
		} while (is = false);
		return output;
	}

	public double[][] AllSolution(double[] dty) {
		double[][] as = new double[Config.numIndi][];
		for (int i = 0; i < Config.numIndi; i++) {
			as[i] = hoanvi(dty);
		}
		return as;
	}

	public double[][] AllSolution() {
		Random r = new Random();
		double[][] as = new double[Config.numIndi][];
		for (int i = 0; i < Config.numIndi; i++) {
			ArrayList<Double> tmp = Initializer.initGenes();
			as[i] = new double[Config.MAX_LEN];
			for (int j = 0; j < tmp.size(); j++) {
				as[i][j] = Config.DS * Math.sin(tmp.get(j));
			}
			for (int j = tmp.size(); j < Config.MAX_LEN; j++) {
				as[i][j] = r.nextDouble() * Math.PI - Math.PI / 2;
			}
		}
		return as;
	}

	/*
	 * táº¡o máº£ng dty tá»« ngÃ£u nhiÃªn x
	 */
	public double[] xySolution(double[] xs) {
		double S = Config.DT * Config.VI;
		double[] ys = new double[xs.length];
		for (int i = 0; i < ys.length; i++) {
			ys[i] = Math.sqrt(S * S - (xs[i] * xs[i]));
		}
		return ys;
	}

	/*
	 * tráº£ láº¡i giÃ¡ trá»‹ input[t]
	 */
	public double p(double[] input, double t) {
		double vl = 0.0;
		for (int i = 0; i < t; i++) {
			vl += input[i];
		}
		return vl;

	}

	/*
	 * táº¡o máº£ng toáº¡n Ä‘á»™ cho intruder
	 */
	public double[] vitri(double[] input, double begin) {
		double[] output = new double[input.length];
		output[0] = begin;
		for (int i = 1; i < input.length; i++) {
			output[i] = output[i - 1] + input[i - 1];
		}
		return output;
	}

	/*
	 * giÃ¡ trá»‹ cuá»‘i cá»§a máº£ng
	 */
	public static double Obj(int[] sign, double[] ys) {
		int k = sign.length;
		int cl = ys.length / k;
		double value = Config.Y0;
		int z = 0;
		for (int i = 0; i < k - 1; i++) {
			for (int j = 0; j < cl; j++) {
				value += sign[i] * ys[z + j];
			}
			z += cl;
		}
		for (int j = 0; j < ys.length - (k - 1) * cl; j++) {
			value += ys[z + j];
		}
		return value;
	}

	/*
	 * Tinhs exp theo x[t] va y[t]
	 * 
	 */
	public double value(ArrayList<Sensor> list, double[] x, double[] y) {
		double value = 0.0;
		// sua closest
		for (Sensor s : list) {
			s.setDefault();
		}

		for (int i = 0; i < x.length; i++) {
			for (Sensor s : list) {
				if (x[i] > Config.W) {
					return value;
				}
				if (y[i] > Config.H || y[i] < 0) {
					return Double.MAX_VALUE;
				}
				value += s.MEx(x[i], y[i]) * Config.DT;
				s.move(Config.DT * Config.VS);
			}
		}

		// Closest:
		/*
		 * for(int i =0; i<x.length; i++){ for(Sensor s:list){ s.move(i*DT); } Sensor s
		 * = closest(list, x[i], y[i]); value+= s.MEx(x[i], y[i]); }
		 */
		return value;
	}

	// TÃ¬m sensor gáº§n nháº¥t:
	public Sensor closest(ArrayList<Sensor> list, double x, double y) {
		double min = 100000;
		Sensor output = null;
		for (Sensor s : list) {
			double distance = Math.sqrt(Math.pow(s.getCenter().getX() - x, 2) + Math.pow(s.getCenter().getY() - y, 2));
			if (distance < min)
				output = s;
		}
		return output;
	}

	public double[] OptimizeY(double[] input) {
		double[] yt;
		int j = 0;
		do {
			yt = vitri(input, Config.Y0);
			input[j] *= -1;
			j++;
		} while (Math.abs(yt[input.length - 1] - Config.YN) > (Config.VI * Config.DT));

		double[] output = new double[input.length];
		for (int i = 0; i < input.length; i++) {
			output[i] = input[i];
		}
		// System.out.println("out+"+vitri(output, Y0)[input.length-1]);
		return output;
	}

	/*
	 * kiem tra dieu kien y
	 */
	public boolean Check(double[] input) {
		double value = 0.0;
		boolean is = true;
		for (int i = 0; i < input.length; i++) {
			value += input[i];
			if (value > Config.H) {
				is = false;
				break;
			}
		}
		return is;
	}

	public void inDataX(int i, double[] x) {
		FileOutputStream fos;
		try {
			// fos = new FileOutputStream("dataXY/ketquaGAxtinh_"+(i+1)+".txt", true); sua
			fos = new FileOutputStream("./Result/GA/" + n + "/test" + i + ".txt", true);
			PrintWriter pw = new PrintWriter(fos);
			// System.out.println(x.length);
			for (int j = 0; j < x.length; j++) {
				String f = String.format("%.3f", x[j]);
				pw.write(f + "|");
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void inDataY(int i, double[] x) {
		FileOutputStream fos;
		try {
			// fos = new FileOutputStream("dataXY/ketquaGAytinh_"+(i+1)+".txt", true);
			fos = new FileOutputStream("./Result/GA/" + n + "/test" + i + ".txt", true);
			PrintWriter pw = new PrintWriter(fos);
			// System.out.println(x.length);
			for (int j = 0; j < x.length; j++) {
				String f = String.format("%.3f", x[j]);
				pw.write(f + "|");
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<Integer> cross(double[] dty1, double[] dty2) {
		double[] yt1 = vitri(dty1, Config.Y0);
		double[] yt2 = vitri(dty2, Config.Y0);
		ArrayList<Integer> dList = new ArrayList<Integer>();
		for (int i = 1; i < dty1.length - 1; i++) {
			if ((yt1[i] > yt2[i] && yt1[i + 1] < yt2[i + 1]) || (yt1[i] < yt2[i] && yt1[i + 1] > yt2[i + 1])) {
				dList.add(i);
			}
		}
		return dList;
	}

	/*
	 * GA
	 */

	/*
	 * LAI GHEP, TAO THEM nbC CA THE
	 */
	private double[][] Crossover(double[][] input, int nbC) {
		double[][] output = new double[nbC + input.length][input[0].length];
		for (int i = 0; i < input.length; i++) {
			output[i] = input[i];
		}

		Random rand = new Random();
		int count = input.length - 1;
		double[] c1;
		double[] c2;
		int k = 0;
		for (int i = 0; i < nbC / 2; i++) {
			boolean is;
			int dem = 0;
			do {
				is = false;
				int x1 = rand.nextInt(Config.numIndi);
				int x2 = x1;
				while (x1 == x2) {
					x2 = rand.nextInt(Config.numIndi);
				}
				c1 = new double[input[0].length];
				c2 = new double[input[0].length];
				for (int j = 0; j < c1.length; j++) {
					c1[j] = input[x1][j];
					c2[j] = input[x2][j];
				}
				ArrayList<Integer> iList = cross(c1, c2);
				if (iList.size() >= 1) {
					is = true;
					k = rand.nextInt(iList.size());
					k = iList.get(k);
				}
				dem += 1;
			} while (is == false && dem <= 1000);

			// System.out.println("k: "+k);
			double[] c3 = new double[input[0].length];
			double[] c4 = new double[input[0].length];
			for (int j = 0; j < k; j++) {
				c3[j] = c1[j];
				c4[j] = c2[j];
			}
			for (int j = k; j < c3.length; j++) {
				c3[j] = c2[j];
				c4[j] = c1[j];
			}
			count++;
			output[count] = c3;
			count++;
			output[count] = c4;

		}

		return output;
	}

	/*
	 * 
	 */

	private double[][] Mutation(double[][] input, int nbM) {
		double[][] output = new double[nbM + input.length][input[0].length];
		for (int i = 0; i < input.length; i++) {
			output[i] = input[i];
		}

		Random rand = new Random();
		int count = input.length - 1;
		for (int i = 0; i < nbM; i++) {
			// System.out.println(i);
			double[] y = him_Climming(input[i]);
			count++;
			output[count] = y;
		}
		return output;
	}

	public double[] him_Climming(double[] input) {

		double[] output = new double[input.length];

		int dis = 50;
		int dem = 0;
		do {
			int x, x1, x2;
			x = findMax(input, 0, input.length);
			// System.out.println(x);

			if (x > dis)
				x1 = findMin(input, x - dis, x);
			else
				x1 = findMin(input, 0, x);
			if (x < input.length - dis)
				x2 = findMin(input, x, x + dis);
			else
				x2 = findMin(input, x, input.length);

			// System.out.println("x = "+ x+" x1 = "+x1+" x2 = "+ x2);

			for (int i = 0; i < x1; i++) {
				output[i] = input[i];
			}
			for (int i = x1; i < x2; i++) {
				output[i] = input[x2 + x1 - i];
			}
			for (int i = x2; i < input.length; i++) {
				output[i] = input[i];
			}
			dem += 1;
		} while (Check(output) == false && dem < 1000);
		return output;
	}

	int findMax(double[] input, int begin, int end) {
		int index = end;
		double[] x = xySolution(input);
		x = vitri(x, Config.X0);
		input = vitri(input, Config.Y0);
		// System.out.println(x[7]);
		double max = -1000000;
		for (Sensor s : list) {
			s.move(begin * Config.DT * Config.VS);
		}
		for (int i = begin; i < end; i++) {
			// System.out.println(i);
			double v = 0;
			for (Sensor s : list) {
				s.move(Config.DT * Config.VS);
				v += s.MEx(x[i], input[i]);

//				System.out.println(s.MEx(x[i], input[i]));
			}
			// System.out.println(v);
			if (v > max) {
				max = v;
				index = i;
			}
		}
		return index;
	}

	int findMin(double[] input, int begin, int end) {
		int index = end;
		double[] x = xySolution(input);
		x = vitri(x, Config.X0);
		input = vitri(input, Config.Y0);
		double min = Double.MAX_VALUE;
		for (Sensor s : list) {
			s.move(begin * Config.DT * Config.VS);
		}
		for (int i = begin; i < end; i++) {
			double value = 0.0;
			for (Sensor s : list) {
				s.move(Config.DT * Config.VS);
				value += s.MEx(x[i], input[i]) * Config.DT;
//				s.setDefault();
			}
			if (value < min) {
				min = value;
				index = i;
			}
		}
		return index;
	}

	/*
	 * lua chon
	 */
	private double[][] Selection(double[][] input) {
		double[][] output = new double[Config.numIndi][];
		double[] f = new double[input.length];
		for (int i = 0; i < f.length; i++) {
			double[] yt = vitri(input[i], Config.Y0);
			double[] dtx = xySolution(input[i]);
			double[] xt = vitri(dtx, Config.X0);
			f[i] = value(list, xt, yt);
		}
		for (int i = 0; i < Config.numIndi; i++) {
			double min = Double.MAX_VALUE;
			int index = 0;
			for (int j = 0; j < f.length; j++) {
				if (f[j] < min) {
					min = f[j];
					index = j;
				}
			}
			output[i] = input[index];
			f[index] = Double.MAX_VALUE;
		}

		return output;
	}

	private double[] searchGA(int iter, double[] dty) {

		double[][] init = AllSolution(dty);
		double[] y_best = new double[init[0].length];
		for (int i = 0; i < init[0].length; i++) {
			y_best[i] = init[0][i];
		}
		double value = Double.MAX_VALUE;
		double epsilon = 0.001;
		int count = 0;
		double kqtruoc = 0.0;

		for (int i = 0; i < iter; i++) {
			// System.out.println("\n################## " + i );
			double[][] cr = Crossover(init, 20);
			double[][] mu = Mutation(cr, 10);
			double[][] se = Selection(mu);
			init = se;
			if (i == iter - 1) {
				for (int j = 0; j < se[0].length; j++) {
					y_best[j] = se[0][j];
				}
			}
			double[] yt = vitri(se[0], Config.Y0);
			double[] xt = vitri(xySolution(se[0]), Config.X0);
			double testvalue = value(list, xt, yt);
//			System.out.println("\n @@@: " + i + "---" + testvalue);
		}
		return y_best;
		/*
		 * double ytemp[] = new double[y_best.length]; for (int j = 0; j < se[0].length;
		 * j++) { ytemp[j] = se[0][j]; } double[] dtx = xySolution(ytemp); double[] xt =
		 * vitri(dtx, X0); double[] yt = vitri(ytemp, Y0); double kqhientai =
		 * value(list, xt, yt); if(Math.abs(kqhientai-kqtruoc) < epsilon){ count++;
		 * if(count == 50) break; }else count = 0; if( kqhientai< value){ y_best =
		 * ytemp; } kqtruoc =kqhientai; }
		 */

	}

	/*
	 * private double[] searchGA(int iter, double[] dty, double[] xt) {
	 * 
	 * double[][] init = AllSolution(dty); double[] y_best = new
	 * double[init[0].length]; double value = Double.MAX_VALUE; for (int i = 0; i <
	 * iter; i++) { double[][] cr = Crossover(init, 20); double[][] mu =
	 * Mutation(cr, 10); double[][] se = Selection(mu, xt); if (i == iter - 1) { for
	 * (int j = 0; j < se[0].length; j++) { y_best[j] = se[0][j]; } }
	 * 
	 * } return y_best;
	 * 
	 * }
	 */

	/*
	 * lay ket qua
	 */

	public double result(double[] dty) {
//		System.out.println("begin");
		ybest = searchGA(200, dty);
//		ybest = dty;
		double[] yt = vitri(ybest, Config.Y0);
		double[] xt = vitri(xySolution(ybest), Config.X0);
		double value = value(list, xt, yt);

		ArrayList<Double> tmp = new ArrayList<>();
		for (int i = 0; i < ybest.length; i++) {
			tmp.add(Math.asin(ybest[i] / Config.DS));
		}
//		Paint(tmp);
		return value;

	}

	public void Paint(ArrayList<Double> tmp) {
		SensorRunableGA drw = new SensorRunableGA();
		drw.list = list;
		drw.paintSensor(tmp);
	}

	public double getStandar(double[] rs, double kqAV) {
		double total = 0;
		for (int i = 0; i < rs.length; i++) {
			total += Math.pow(rs[i] - kqAV, 2);
		}
		double temp = Math.sqrt(total / rs.length);
		return temp;
	}

	public static void main(String[] args) {
		FileOutputStream fos;
		PrintWriter pw;
		String[] str = { "Rect", "RanPoint", "PathWay" };
		for (String s : str) {
			for (int nums = 1; nums <= 4; nums++) {
//			n = nums * 25;
				for (int i = 11; i <= 20; i++) {
					MBGA mb = new MBGA();
					mb.n = nums * 25;
					mb.readData("./Data/" + s + "/" + mb.n + "/test_" + i + ".txt"); // sua

					double[] kq = new double[20];
					double[] time = new double[kq.length];

					for (int k = 0; k < kq.length; k++) {
						System.out.println(s + "---- n = " + mb.n + " i = " + i);
						System.out.print("Epoch: " + k + ": ");
						double[] dtx = mb.xSolution();
						double[] dty0 = mb.xySolution(dtx);
						double[] dty = mb.OptimizeY(dty0);

						long begin = Calendar.getInstance().getTimeInMillis();
						kq[k] = mb.result(dty);
						long end = Calendar.getInstance().getTimeInMillis();

						System.out.println(kq[k]);
						time[k] = (end - begin);

					}
					double ketqua = 0.0;
					double thoigian = 0.0;

					try {
						fos = new FileOutputStream("./Result/PSO_GA/" +  s + "/" + nums * 25 + "/result_" + i + ".txt", false);
						pw = new PrintWriter(fos);
						for (int j = 0; j < kq.length; j++) {
							ketqua += kq[j];
							thoigian += time[j];

						}
						ketqua = ketqua / kq.length;
						thoigian = thoigian / kq.length;

						pw.println("MEP: " + ketqua);
						pw.println("DEV: " + mb.getStandar(kq, ketqua));
						pw.println("TIM: " + thoigian);

						pw.close();
						fos.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
