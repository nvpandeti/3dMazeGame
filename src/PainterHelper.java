import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PainterHelper implements Runnable
{
	private List<Face> faces;
	private ArrayList<Polygon> polys;
	private ArrayList<Color> colors;
	private double realX, realY, realZ, width, height;
	private double[] origin;
	public PainterHelper(List<Face> f, double x, double y, double z, double[] orig, double w, double h)
	{
		faces = f;
		polys = new ArrayList<Polygon>();
		colors = new ArrayList<Color>();
		realX = x;
		realY = y;
		realZ = z;
		origin = orig;
		width = w;
		height = h;
	}
	private double getWidth()
	{
		return width;
	}
	private double getHeight()
	{
		return height;
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
	public void run() 
	{
		double fov = Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2)) / 110;
	    HashMap<double[], double[]> calculatedCorners = new HashMap<double[], double[]>();
		for(Face face:faces)
	    {
			int[] pointsX = new int[face.getCorners().length];
	        int[] pointsY = new int[face.getCorners().length];
	        boolean behind = true;
	        double[] angleD = new double[face.getCorners().length];
	        double[] angleR = new double[face.getCorners().length];
	        outer: 
	        for(int c = 0; c < angleD.length; c++)
	        {
	        	double[] corner = face.getCorners()[c];
	            if(!calculatedCorners.containsKey(corner))
	            {
	                //angleD[c] =  Math.toDegrees( Math.acos( ( (origin[0] - realX)*(corner[0] - realX) + (origin[1] - realY)*(corner[1] - realY) + (origin[2] - realZ)*(corner[2] - realZ) ) 
	                //                                        / ( Math.sqrt( Math.pow( origin[0] - realX, 2) + Math.pow( origin[1] - realY, 2) + Math.pow( origin[2] - realZ, 2) ) *  Math.sqrt( Math.pow( corner[0] - realX, 2) + Math.pow( corner[1] - realY, 2) + Math.pow( corner[2] - realZ, 2) ) ) ) );
	            	angleD[c] =  Math.toDegrees( Math.acos( ( (origin[0] - realX)*(corner[0] - realX) + (origin[1] - realY)*(corner[1] - realY) + (origin[2] - realZ)*(corner[2] - realZ) )
	            				* invSqrt(Math.pow( origin[0] - realX, 2) + Math.pow( origin[1] - realY, 2) + Math.pow( origin[2] - realZ, 2)) * invSqrt(Math.pow( corner[0] - realX, 2) + Math.pow( corner[1] - realY, 2) + Math.pow( corner[2] - realZ, 2) ) ) );
	            	
	            	
	                double t = - ( (origin[0] - realX)*(realX - corner[0]) + (origin[1] - realY)*(realY - corner[1]) + (origin[2] - realZ)*(realZ - corner[2]) ) / ( Math.pow(origin[0] - realX, 2) + Math.pow(origin[1] - realY, 2) + Math.pow(origin[2] - realZ, 2) );
	                double[] vertex = {realX + (origin[0] - realX)*t, realY + (origin[1] - realY)*t, realZ + (origin[2] - realZ)*t};
	                double[] perpendicular = {vertex[0] - (origin[1] - realY), vertex[1] + (origin[0] - realX), vertex[2]};
	                //double R = Math.acos( ( (perpendicular[0] - vertex[0])*(corner[0] - vertex[0]) + (perpendicular[1] - vertex[1])*(corner[1] - vertex[1]) + (perpendicular[2] - vertex[2])*(corner[2] - vertex[2]) ) 
	                //               / ( Math.sqrt( Math.pow( perpendicular[0] - vertex[0], 2) + Math.pow( perpendicular[1] - vertex[1], 2) + Math.pow( perpendicular[2] - vertex[2], 2) ) *  Math.sqrt( Math.pow( corner[0] - vertex[0], 2) + Math.pow( corner[1] - vertex[1], 2) + Math.pow( corner[2] - vertex[2], 2) ) ) );
	                double R = Math.acos( ( (perpendicular[0] - vertex[0])*(corner[0] - vertex[0]) + (perpendicular[1] - vertex[1])*(corner[1] - vertex[1]) + (perpendicular[2] - vertex[2])*(corner[2] - vertex[2]) ) 
	                			* invSqrt(Math.pow( perpendicular[0] - vertex[0], 2) + Math.pow( perpendicular[1] - vertex[1], 2) + Math.pow( perpendicular[2] - vertex[2], 2) ) * invSqrt(Math.pow( corner[0] - vertex[0], 2) + Math.pow( corner[1] - vertex[1], 2) + Math.pow( corner[2] - vertex[2], 2) ) );
	                
	                if(corner[2]<perpendicular[2])
	                    angleR[c] = 2*Math.PI - R;
	                else
	                    angleR[c] = R;
	                if(behind && angleD[c]<55)
		                behind = false;
		            pointsX[c] = (int)Math.round(getWidth()/2.0+angleD[c]*fov*Math.cos(angleR[c]));
		            pointsY[c] = (int)Math.round(getHeight()/2.0-angleD[c]*fov*Math.sin(angleR[c]));
		            	
	                double[] temp = {angleD[c], angleR[c]};
	                calculatedCorners.put(corner, temp);
	            }
	            else 
	            {
	                
	                angleD[c] = calculatedCorners.get(corner)[0];
	                angleR[c] = calculatedCorners.get(corner)[1];
	                if(behind && angleD[c]<55)
		                behind = false;
		            pointsX[c] = (int)Math.round(getWidth()/2.0+angleD[c]*fov*Math.cos(angleR[c]));
		            pointsY[c] = (int)Math.round(getHeight()/2.0-angleD[c]*fov*Math.sin(angleR[c]));
	            }
	        }
	        
		        
			
	        if(!behind)
	        {
	        	double x = 0;
				double y = 0;
				for(int c = 0; c < angleD.length; c++)
				{
					x+= pointsX[c];
					y+= pointsY[c];
					
				} 
        		//g.setColor(face.getShading());
        		//g.fillPolygon(pointsX, pointsY, pointsX.length); 
				colors.add(face.getShading());
        		polys.add(new Polygon(pointsX, pointsY, pointsX.length));
	        	
	        }
	    }
		
	}
	public ArrayList<Color> getColors()
	{
		return colors;
	}
	public ArrayList<Polygon> getPolygons()
	{
		return polys;
	}
	
}
