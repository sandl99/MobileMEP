package mainMBGA;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import GA.MBGA;

public class Rect {
	public static void main(String[] args) {
		FileOutputStream fos;
		PrintWriter pw;
		String[] str = { "Rect"};
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
						fos = new FileOutputStream("./Result/MBGA/" + s + "/" + mb.n + "/result_" + i + ".txt",
								false);
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
