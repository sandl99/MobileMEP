package genData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Info.Config;
import Info.Point;
import Info.Sensor;

public class GenData {
//	private static final int W = 100;
//	private static final int H = 40;
	private static int n;
//	private static int R = 3;
	private ArrayList<Sensor> sL = new ArrayList<Sensor>();

	public GenData() {
		for (int i = 0; i < n; i++) {
			Sensor s = new Sensor((int) Config.W, (int) Config.H, 4, Config.R);
			sL.add(s);
		}
	}

	public void printData(int num_sensor, int indi) {
		String fileName = "./Data/" + num_sensor + "/test" + indi + ".txt";
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter br = new BufferedWriter(fileWriter);
			String data = num_sensor + " " + Config.R + " " + Config.W + " " + Config.H;
			br.write(data);
			br.write("\n");
			for (Sensor s : sL) {
				data = "";
				for (int i = 0; i < 4; i++) {
					data = data + s.pL.get(i).getX() + " " + s.pL.get(i).getY() + " ";
				}
				br.write(data);
				br.write("\n");
			}
			br.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void genDataForRect(int num_sensor, int indi) {
		String fileName = "./Data/Rect/" + num_sensor + "/test_" + indi + ".txt";
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter br = new BufferedWriter(fileWriter);
			br.write(Integer.toString(num_sensor));
			br.write("\n");
			Random rd = new Random();
			for (int i = 0; i < num_sensor; i++) {
				String data = "";
				int numPos = 4;
				Point p[] = new Point[4];
				int y;
				do {
					y = (int) (rd.nextGaussian() * Math.sqrt(400) + Config.Y0);
				} while (y <= 0 || y >= 40);
				p[0] = new Point((int) (Math.random() * Config.W), y);
				do {
					y = (int) (rd.nextGaussian() * Math.sqrt(400) + Config.Y0);
				} while (y <= 0 || y >= 40);
				p[2] = new Point((int) (Math.random() * Config.W), y);
				
				p[1] = new Point(p[0].getX(), p[2].getY());
				p[3] = new Point(p[2].getX(), p[0].getY());

				// Start Point
				data += Integer.toString(numPos) + " ";
				data += p[0].getX() + " " + p[0].getY() + " ";
				// List Point Position
				for (int j = 0; j < 4; j++) {
					data = data + p[j].getX() + " " + p[j].getY() + " ";
				}
				br.write(data);
				br.write("\n");

			}
			br.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void genDataForRandomPoint(int num_sensor, int indi) {
		String fileName = "./Data/RanPoint/" + num_sensor + "/test_" + indi + ".txt";
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter br = new BufferedWriter(fileWriter);
			br.write(Integer.toString(num_sensor));
			br.write("\n");
			Random rd = new Random();
			for (int i = 0; i < num_sensor; i++) {
				String data = "";
				int numPos = rd.nextInt(7) + 3;
				Point p[] = new Point[numPos];
				
				for (int j = 0; j < numPos; j++) {
					if (j == 0) {
						int y;
						do {
							y = (int) (rd.nextGaussian() * Math.sqrt(64) + Config.Y0);
						} while (y <= 0 || y >= 40);
						
						p[j] = new Point((int) (Math.random() * Config.W), y);
					} else {
						int y;
						do {
							y = (int) (rd.nextGaussian() * Math.sqrt(200) + Config.Y0);
						} while (y <= 0 || y >= 40);
						p[j] = new Point((int) (Math.random() * Config.W), y);
					}
					
				}

				// Start Point
				data += Integer.toString(numPos) + " ";
				data += p[0].getX() + " " + p[0].getY() + " ";
				// List Point Position
				for (int j = 0; j < numPos; j++) {
					data = data + p[j].getX() + " " + p[j].getY() + " ";
				}
				br.write(data);
				br.write("\n");

			}
			br.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void genDataForPathWay(int num_sensor, int indi) {
		String fileName = "./Data/PathWay/" + num_sensor + "/test_" + indi + ".txt";
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter br = new BufferedWriter(fileWriter);
			boolean checkOdd = false;
			if (num_sensor % 2 == 1) {
				checkOdd = true;
			}
			if (checkOdd) {
				br.write(Integer.toString(num_sensor - 1));
			} else {
				br.write(Integer.toString(num_sensor));
			}

			br.write("\n");
			Random rd = new Random();
			
			int numSenW = (int) Config.H * num_sensor / (int) Config.W;
			int numSenH = num_sensor - numSenW;
			if (checkOdd) numSenH -= 1;
			for (int i = 0; i < numSenW / 2; i++) {
				String data = "";
				int numPos = 2;
				Point p[] = new Point[numPos];
				
				int pos = rd.nextInt((int) Config.H);
				p[0] = new Point(0, pos);
				p[1] = new Point(((int) Config.W), pos);
				
				// Start Point
				data += Integer.toString(numPos) + " ";
				data += p[0].getX() + " " + p[0].getY() + " ";
				// List Point Position
				for (int j = 0; j < numPos; j++) {
					data = data + p[j].getX() + " " + p[j].getY() + " ";
				}
				br.write(data);
				br.write("\n");
				data = "";
				pos += 2;
				p[1] = new Point(0, pos);
				p[0] = new Point(((int) Config.W), pos);
				
				// Start Point
				data += Integer.toString(numPos) + " ";
				data += p[0].getX() + " " + p[0].getY() + " ";
				// List Point Position
				for (int j = 0; j < numPos; j++) {
					data = data + p[j].getX() + " " + p[j].getY() + " ";
				}
				br.write(data);
				br.write("\n");
				
			}
			
			for (int i = 0; i < numSenH / 2; i++) {
				String data = "";
				int numPos = 2;
				Point p[] = new Point[numPos];
				
				int pos = rd.nextInt((int) Config.W);
				p[0] = new Point(pos, 0);
				p[1] = new Point(pos, (int) Config.H);
				
				// Start Point
				data += Integer.toString(numPos) + " ";
				data += p[0].getX() + " " + p[0].getY() + " ";
				// List Point Position
				for (int j = 0; j < numPos; j++) {
					data = data + p[j].getX() + " " + p[j].getY() + " ";
				}
				br.write(data);
				br.write("\n");
				data = "";
				pos += 2;
				p[1] = new Point(pos, 0);
				p[0] = new Point(pos, (int) Config.H);
				
				// Start Point
				data += Integer.toString(numPos) + " ";
				data += p[0].getX() + " " + p[0].getY() + " ";
				// List Point Position
				for (int j = 0; j < numPos; j++) {
					data = data + p[j].getX() + " " + p[j].getY() + " ";
				}
				br.write(data);
				br.write("\n");
				
			}
			
			br.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {

		for (int num = 1; num <= 4; num++) {
			n = num * 25;
			for (int i = 21; i <= 21; i++) {
				GenData genData = new GenData();
				genData.genDataForRect(n, i);
//				genData.genDataForRandomPoint(n, i);
//				genData.genDataForPathWay(n, i);
			}
		}

	}

}
