package Info;

public class Config {
	public static final double W = 100; 
	public static final double H = 40; 
	public static final double R = 3;
//	Velocity of Intruder
	public static final double VI = 2; 
	
//	Velocity of Sensor
	public final static double VS = 1; // van toc cam bien
	
// Delta t
	public final static double DT = 0.1; // delta t

//	Begin position
	public final static double X0 = 0.0;
	public final static double Y0 = 30.5;
	public final static double YN = 10.5;

//	Delta s
	public final static double DS = 0.2;
	
//	Number of Individual
	public final static int numIndi = 100;
	
	
	public final static double C = 1, C1 = 0.8, C2 = 0.7;
	
	public final static int ITERATOR = 100;
	
	public final static double r_init = 0.6;
	public final static double LAMDA = 1.0;
	public final static int ALPHA = 2;
	
//	Length of Individual
	public final static int MAX_LEN = 1000;
	
}
