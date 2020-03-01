package mainPSOGA;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import Info.Config;
import PSO_GA.PSO_Search;

public class RanPoint {
	public static void main(String[] args) {
//		Config.DS = Double.parseDouble(args[0]);
//		Config.MAX_LEN = (int) (200 / Config.DS);
//		Config.DT = Config.DS / Config.VI;
//		System.out.println("DS:=  " + Config.DS);
//		System.out.println("DT:=  " + Config.DT);
//		System.out.println("MAX_GEN:= " + Config.MAX_LEN);
		FileOutputStream fos;
		PrintWriter pw;
		String[] str = {"RanPoint"};
		for (String s : str) {
			for (int nums = 1; nums <= 4; nums++) {
				int n = 25 * nums;
				for (int i = 21; i <= 21; i++) {

					PSO_Search pso = new PSO_Search();
					pso.readData("./Data/" + s + "/" + n + "/test_" + i + ".txt");
					double[] kq = new double[1];
					double[] time = new double[kq.length];

					for (int k = 0; k < kq.length; k++) {
						System.out.println(s + "---- n = " + n + " i = " + i);
						System.out.print("Epoch: " + k + ": ");
						pso.init();
						long begin = Calendar.getInstance().getTimeInMillis();
						pso.runPSO();
						long end = Calendar.getInstance().getTimeInMillis();
						kq[k] = pso.fitnessGBest;
						System.out.println("MEV:= " + kq[k]);
						System.out.println("Time:= " + (end - begin));
						time[k] = end - begin;
					}
					double ketqua = 0.0;
					double thoigian = 0.0;
					try {
						fos = new FileOutputStream("./Result/PSO_GA/" +  s + "/" + n + "/result_" + i + ".txt", false);
						pw = new PrintWriter(fos);

						for (int j = 0; j < kq.length; j++) {
							ketqua += kq[j];
							thoigian += time[j];

						}
						ketqua = ketqua / kq.length;
						thoigian = thoigian / kq.length;

						pw.println("MEP: " + ketqua);
						pw.println("DEV: " + pso.getStandar(kq, ketqua));
						pw.println("TIM: " + thoigian);

						pw.close();

						pw.close();
						fos.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//
				}
			}
		}
	}
}
