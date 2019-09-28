package Info;

import java.io.*;
import java.io.FileWriter;
import java.util.ArrayList;

import Info.Config;
public class GenData {
	private static int n;
	private ArrayList<Sensor> sL = new ArrayList<Sensor>();

	public GenData() {
		for (int i = 0; i < n; i++) {
			Sensor s = new Sensor((int)Config.W, (int)Config.H, Config.R);
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

	public static void main(String[] args) {
		
		
		for (int num = 1; num <= 4; num++) {
			n = num * 25;
			for (int i = 10; i <= 20; i++) {
				GenData genData = new GenData();
				genData.printData(n, i);
			}
		}

	}

}
