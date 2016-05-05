
public class Light 
{
	private double radius;
	private double[] position;
	public Light(double[] pos, double r)
	{
		radius = r;
		position = pos;
	}
	
	public double getDistanceCoefficient(double[] center)
	{
		double temp = Math.pow(position[0] - center[0], 2) + Math.pow(position[1] - center[1], 2) + Math.pow(position[2] - center[2], 2);
		//double distance = temp * invSqrt(temp);
		return Math.max((radius- temp * invSqrt(temp))/radius, 0);
	}
	
	public double getShadeCoefficient(double[] center, double[] normal)
	{
		return   Math.acos( ( (position[0] - center[0])*(normal[0] - center[0]) + (position[1] - center[1])*(normal[1] - center[1]) + (position[2] - center[2])*(normal[2] - center[2]) ) 
                * invSqrt( Math.pow( position[0] - center[0], 2) + Math.pow( position[1] - center[1], 2) + Math.pow( position[2] - center[2], 2) ) *  invSqrt( Math.pow( normal[0] - center[0], 2) + Math.pow( normal[1] - center[1], 2) + Math.pow( normal[2] - center[2], 2) ) ) / Math.PI;
	}
	
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
