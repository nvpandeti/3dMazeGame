/*
 * Nikhil Pandeti
 * Mrs. Gallatin
 * Period 2
 */
/**
 * A class for Lights
 */
public class Light 
{
	private double radius;
	private double[] position;
	/**
	 * Constructor for lights
	 * @param pos position coordinate
	 * @param r radius of light
	 */
	public Light(double[] pos, double r)
	{
		radius = r;
		position = pos;
	}
	/**
	 * Returns how much light in shown based on distance
	 * @param center center coordinate
	 * @return 0.0 - 1.0 based on how bright it should be
	 */
	public double getDistanceCoefficient(double[] center)
	{
		double temp = Math.pow(position[0] - center[0], 2) + Math.pow(position[1] - center[1], 2) + Math.pow(position[2] - center[2], 2);
		//double distance = temp * invSqrt(temp);
		return Math.max((radius- temp * invSqrt(temp))/radius, 0);
	}
	/**
	 * Returns how much light in shown based on angle
	 * @param center center coordinate
	 * @param normal normal vector
	 * @return 0.0 - 1.0 based on how bright it should be
	 */
	public double getShadeCoefficient(double[] center, double[] normal)
	{
		return   Math.acos( ( (position[0] - center[0])*(normal[0] - center[0]) + (position[1] - center[1])*(normal[1] - center[1]) + (position[2] - center[2])*(normal[2] - center[2]) ) 
                * invSqrt( Math.pow( position[0] - center[0], 2) + Math.pow( position[1] - center[1], 2) + Math.pow( position[2] - center[2], 2) ) *  invSqrt( Math.pow( normal[0] - center[0], 2) + Math.pow( normal[1] - center[1], 2) + Math.pow( normal[2] - center[2], 2) ) ) / Math.PI;
	}
	/**
	 * John Carmak's inverse sqrt formula
	 * @param x input
	 * @return output
	 */
	public static double invSqrt(double x) 
	{
	    double xhalf = 0.5d*x;
	    long i = Double.doubleToLongBits(x);
	    i = 0x5fe6ec85e7de30daL - (i>>1);
	    x = Double.longBitsToDouble(i);
	    x = x*(1.5d - xhalf*x*x);
	    x = x*(1.5d - xhalf*x*x);
	    //x = x*(1.5d - xhalf*x*x);
	    //x = x*(1.5d - xhalf*x*x);
	    //x = x*(1.5d - xhalf*x*x);
	    return x;
	}
}
