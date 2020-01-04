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

import Info.Sensor;

public class MBGA {
	final int W = 100; // 100-> 10
	final int H = 40; // 100 ->10
	final double VI = 2; // van toc intruder suas
	final static int VS = 1; // van toc cam bien
	final int nb = 100; // so nst
	final static double DT = 0.1; // delta t
	final static double X0 = 0.0;
	final static double Y0 = 30.0; // sua
	final static double YN = 10.0; // sua
	final double XN = 100.0;

	int n; // so Sensor
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
		xk = new double[W * 10];
		double t = 0.0;
		for (int i = 0; i < xk.length; i++) {
			xk[i] = t;
			t += 0.1;
		}
		t = 0.0;
		yk = new double[H * 10];
		for (int i = 0; i < yk.length; i++) {
			yk[i] = t;
			t += 0.1;
		}
	}

	/*
	 * khá»Ÿi táº¡o cáº£m biáº¿n
	 */
	public void readData(String fileName) {
		list = new ArrayList<>();
		FileReader fr;
		try {
			fr = new FileReader(fileName);
			BufferedReader input = new BufferedReader(fr);
			String sf = input.readLine();
			String[] s1 = sf.split(" ");
			n = Integer.parseInt(s1[0]);
			r = Double.parseDouble(s1[1]);
			for (int i = 0; i < n; i++) {
				String temp = input.readLine();
				String[] t = temp.split(" ");
				Sensor Sen = new Sensor((int) Double.parseDouble(t[0]), (int) Double.parseDouble(t[1]),
						(int) Double.parseDouble(t[2]), (int) Double.parseDouble(t[3]), (int) Double.parseDouble(t[4]),
						(int) Double.parseDouble(t[5]), (int) Double.parseDouble(t[6]), (int) Double.parseDouble(t[7]),
						r);
				list.add(Sen);
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		while (range < W) {
			double x = Math.random() * (DT * VI);
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
				if (value > H)
					is = false;
			}
		} while (is = false);
		return output;
	}

	public double[][] AllSolution(double[] dty) {
		double[][] as = new double[nb][];
		for (int i = 0; i < nb; i++) {
			as[i] = hoanvi(dty);
		}
		return as;
	}

	/*
	 * táº¡o máº£ng dty tá»« ngÃ£u nhiÃªn x
	 */
	public double[] xySolution(double[] xs) {
		double S = DT * VI;
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
		double value = Y0;
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
			for (int i = 0; i < x.length; i++) {
				s.move(i * DT);
				value += s.MEx(x[i], y[i]) * DT;
				s.setDefault();
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
			yt = vitri(input, Y0);
			input[j] *= -1;
			j++;
		} while (Math.abs(yt[input.length - 1] - YN) > (VI * DT));

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
			if (value > H) {
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
		double[] yt1 = vitri(dty1, Y0);
		double[] yt2 = vitri(dty2, Y0);
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
			do {
				is = false;
				int x1 = rand.nextInt(nb);
				int x2 = x1;
				while (x1 == x2) {
					x2 = rand.nextInt(nb);
				}
				c1 = new double[input[0].length];
				c2 = new double[input[0].length];
				for (int j = 0; j < c1.length; j++) {
					c1[j] = input[x1][j];
					c2[j] = input[x2][j];
				}
				ArrayList<Integer> iList = cross(c1, c2);
				if (iList.size() > 1) {
					is = true;
					k = rand.nextInt(iList.size() - 1) + 1;
					k = iList.get(k);
				}
			} while (is = false);

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
		} while (Check(output) == false);
		return output;
	}

	int findMax(double[] input, int begin, int end) {
		int index = end;
		double[] x = xySolution(input);
		x = vitri(x, X0);
		input = vitri(input, Y0);
		// System.out.println(x[7]);
		double max = -1000000;
		for (int i = begin; i < end; i++) {
			// System.out.println(i);
			double v = 0;
			for (Sensor s : list) {
				s.move(i * DT);
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
		x = vitri(x, X0);
		input = vitri(input, Y0);
		double min = Double.MAX_VALUE;
		for (int i = begin; i < end; i++) {
			double value = 0.0;
			for (Sensor s : list) {
				s.move(i * DT);
				value += s.MEx(x[i], input[i]) * DT;
				s.setDefault();
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
		double[][] output = new double[nb][];
		double[] f = new double[input.length];
		for (int i = 0; i < f.length; i++) {
			double[] yt = vitri(input[i], Y0);
			double[] dtx = xySolution(input[i]);
			double[] xt = vitri(dtx, X0);
			f[i] = value(list, xt, yt);
		}
		for (int i = 0; i < nb; i++) {
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
			double[] yt = vitri(se[0], Y0);
			double[] xt = vitri(xySolution(se[0]), X0);
			double testvalue = value(list, xt, yt);
			System.out.println("\n @@@: " + i + "---" + testvalue);
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
		System.out.println("begin");
		ybest = searchGA(200, dty);
		double[] yt = vitri(ybest, Y0);
		double[] xt = vitri(xySolution(ybest), X0);
		double value = value(list, xt, yt);
		return value;

	}

	double getStandar(double[] rs, double kqAV) {
		double total = 0;
		for (int i = 0; i < rs.length; i++) {
			total += Math.pow(rs[i] - kqAV, 2);
		}
		double temp = Math.sqrt(total / rs.length);
		return temp;
	}

	// /*

	public static void main(String[] args) {
		for (int nums = 2; nums <= 2; nums++) {
//			n = nums * 25;
			for (int i = 12; i <= 12; i++) {
				MBGA mb = new MBGA();
				mb.n = nums * 25;
				mb.readData("./Data/" + mb.n + "/test" + i + ".txt"); // sua

				double[] kq = new double[1];
				double[] time = new double[kq.length];

				for (int k = 0; k < kq.length; k++) {
					System.out.println("n = " + mb.n + " i = " + i);
					double[] dtx = mb.xSolution();
					double[] dty0 = mb.xySolution(dtx);
					double[] dty = mb.OptimizeY(dty0);

					long begin = Calendar.getInstance().getTimeInMillis();
					kq[k] = mb.result(dty);
					System.out.println(kq[k]);

					long end = Calendar.getInstance().getTimeInMillis();
					time[k] = (end - begin);
					double[] y = mb.vitri(mb.ybest, Y0);
//					mb.inDataY(i, y);
					dtx = mb.xySolution(mb.ybest);
					double[] xt = mb.vitri(dtx, X0);
//					mb.inDataX(i, dtx);
				}
				double ketqua = 0.0;
				double thoigian = 0.0;

				FileOutputStream fos;
				try {
					fos = new FileOutputStream("./Result/GA/" + mb.n + "/test" + i + ".txt", false);
					PrintWriter pw = new PrintWriter(fos);
					for (int j = 0; j < kq.length; j++) {
						ketqua += kq[j];
						thoigian += time[j];
						pw.println("ket qua: " + kq[j]);
						pw.println("thoi gian: " + time[j]);
						pw.println("____________________________________");
					}
					ketqua = ketqua / kq.length;
					thoigian = thoigian / kq.length;

					pw.println("ket qua trung binh : " + ketqua);
					pw.println("do lech chuan: " + mb.getStandar(kq, ketqua));
					pw.println("thoi gian trung binh : " + thoigian);

					pw.close();
//					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
