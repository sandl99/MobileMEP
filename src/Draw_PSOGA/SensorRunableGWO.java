//package Draw_PSOGA;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//import java.awt.geom.Arc2D;
//import java.awt.geom.Line2D;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.concurrent.TimeUnit;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//import GWO.Grey_wolf_optimizer;
//import Info.Config;
//import Info.Sensor;
//
//public class SensorRunableGWO extends Grey_wolf_optimizer {
//	public SensorRunableGWO(double iLower[], double iUpper[], int imaxiter, int iN) {
//		super(iLower, iUpper, imaxiter, iN);
//	}
//	
//	private static int i = 0;
//	private static boolean check = false;
//	public void paintSensor(ArrayList<Double> indi) {
//		for (Sensor s : list) {
//			s.setDefault();
//		}
//		JFrame frame = null;
//		for (i = 0; i <= Config.MAX_LEN; i++) {
//			if (frame == null) {
//				frame = new JFrame();
//				JPanel panel = new JPanel() {
//					public void paintComponent(Graphics g) {
//						Graphics2D g2 = (Graphics2D) g;
//						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//								RenderingHints.VALUE_ANTIALIAS_ON);
//						g2.setColor(Color.GREEN);
//						double x, y, r;
//						for (Sensor s : list) {
//							x = s.getCenter().getX() * 10;
//							y = s.getCenter().getY() * 10;
//							r = Config.R * 10;
//							g2.fill(new Arc2D.Double(x - r, Config.H * 10 - y - r, 
//								r * 2, r * 2, 0, 360, Arc2D.OPEN));
//						}
//						g2.setColor(Color.RED);
//						double xCur = Config.X0 * 10;
//						double yCur = Config.Y0 * 10;
//						double phi, xNext, yNext;
//						for (int j = 0; j <= i; j++) {
//							phi = indi.get(j);
//							xNext = xCur + Math.cos(phi) * Config.DT * Config.VI * 10;
//							yNext = yCur + Math.sin(phi) * Config.DT * Config.VI * 10;
//							if (xNext > Config.W * 10)
//								check = true;
//							g2.draw(new Line2D.Double(xCur, Config.H * 10 - yCur, 
//									xNext, Config.H * 10 - yNext));
//							
//							xCur = xNext;
//							yCur = yNext;
//						}
//					}
//				};
//				frame.add(panel);
//				frame.setSize((int)(Config.W * 10), (int)(Config.H * 10));
//				frame.setVisible(true);
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			}
//			else {
//				frame.repaint();
//			}
//			for (Sensor s : list) {
//				s.move(Config.VS * Config.DT);
//			}
//			try {
//				TimeUnit.MILLISECONDS.sleep(40);
//			}
//			catch(Exception e) {
//				e.printStackTrace();
//			}
//			if (check)
//				return;
//				
//		}
//	}
//	
//	public void paintAll(double[][] indies) {
//		for (Sensor s : list) {
//			s.setDefault();
//		}
//		JFrame frame = null;
//		for (i = 0; i <= Config.MAX_LEN; i++) {
//			if (frame == null) {
//				frame = new JFrame();
//				JPanel panel = new JPanel() {
//					public void paintComponent(Graphics g) {
//						Graphics2D g2 = (Graphics2D) g;
//						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//								RenderingHints.VALUE_ANTIALIAS_ON);
//						g2.setColor(Color.GREEN);
//						double x, y, r;
//						for (Sensor s : list) {
//							x = s.getCenter().getX() * 10;
//							y = s.getCenter().getY() * 10;
//							r = Config.R * 10;
//							g2.fill(new Arc2D.Double(x - r, Config.H * 10 - y - r, 
//								r * 2, r * 2, 0, 360, Arc2D.OPEN));
//						}
//						g2.setColor(Color.RED);
//						for (int indi = 0; indi < 10; indi++) {
//							double xCur = Config.X0 * 10;
//							double yCur = Config.Y0 * 10;
//							double phi, xNext, yNext;
//							for (int j = 0; j <= i; j++) {
//								phi = indies[indi][j];
//								xNext = xCur + Math.cos(phi) * Config.DT * Config.VI * 10;
//								yNext = yCur + Math.sin(phi) * Config.DT * Config.VI * 10;
//								if (xNext > Config.W * 10)
//									check = true;
//								g2.draw(new Line2D.Double(xCur, Config.H * 10 - yCur, 
//										xNext, Config.H * 10 - yNext));
//								
//								xCur = xNext;
//								yCur = yNext;
//							}
//						}
//					}
//				};
//				frame.add(panel);
//				frame.setSize((int)(Config.W * 10), (int)(Config.H * 10));
//				frame.setVisible(true);
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			}
//			else {
//				frame.repaint();
//			}
//			for (Sensor s : list) {
//				s.move(Config.VS * Config.DT);
//			}
//			try {
//				TimeUnit.MILLISECONDS.sleep(40);
//			}
//			catch(Exception e) {
//				e.printStackTrace();
//			}
//			if (check)
//				return;
//				
//		}
//	}
//	public static void main(String[] args) {
//		double[] iLower = new double[Info.Config.MAX_LEN];
//		double[] iUpper = new double[Info.Config.MAX_LEN];
//		Arrays.fill(iLower, - Math.PI / 2);
//		Arrays.fill(iUpper, Math.PI / 2);
//		SensorRunableGWO gwo = new SensorRunableGWO(iLower, iUpper, 50, 10);
//		gwo.readData("./Data/50/test18.txt");
//		gwo.init();
//		gwo.solution();
//		gwo.paintAll(gwo.XX);
//	}
//} 
