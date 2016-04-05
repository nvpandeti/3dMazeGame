/*
 * Nikhil Pandeti
 * Mrs. Gallatin
 * Period 2
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
/**
 * A class for faces
 */
public class Face implements Comparable<Face>
{
	private static double[] light = {0,0,0};
	private static double realX = 0;
	private static double realY = 0;
	private static double realZ = 0;
	private double[][] corners;
	private Color color,shading;
	private double[] center, normal;
	private double distance, shade;
	/**
	 * A constructor for Faces
	 * @param color Color
	 * @param args a variable number of coordinates (x,y,z)
	 */
	public Face(Color color, double[]... args)
	{
		this.color = color;
		corners = args;
		distance = 0;
		
		double x = 0;
		double y = 0;
		double z = 0;
		for(double[] corner:corners)
		{
			x+= corner[0];
			y+= corner[1];
			z+= corner[2];
		}
		center = new double[3];
		center[0] = x/corners.length;
		center[1] = y/corners.length;
		center[2] = z/corners.length;
		
		double[] a = {corners[1][0] - corners[0][0], corners[1][1] - corners[0][1], corners[1][2] - corners[0][2]};
        double[] b = {corners[2][0] - corners[0][0], corners[2][1] - corners[0][1], corners[2][2] - corners[0][2]};
        
        double[] normal = { center[0] + (a[1]*b[2] - a[2]*b[1]),
                        	center[1] + (a[2]*b[0] - a[0]*b[2]),
                        	center[2] + (a[0]*b[1] - a[1]*b[0]) };
        this.normal = normal;
        
        updateDistance();
        calculateShading();
		shade =  Math.toDegrees( Math.acos( ( (light[0] - center[0])*(normal[0] - center[0]) + (light[1] - center[1])*(normal[1] - center[1]) + (light[2] - center[2])*(normal[2] - center[2]) ) 
                                         / ( Math.sqrt( Math.pow( light[0] - center[0], 2) + Math.pow( light[1] - center[1], 2) + Math.pow( light[2] - center[2], 2) ) *  Math.sqrt( Math.pow( normal[0] - center[0], 2) + Math.pow( normal[1] - center[1], 2) + Math.pow( normal[2] - center[2], 2) ) ) ) ) / 180
                                         *	 Math.max((10-distance)/10, 0);
		//System.out.println (shade);
		shading = new Color((int)(color.getRed() * shade), (int)(color.getGreen() * shade), (int)(color.getBlue() * shade));
	}
	/**
	 * Sets the posiion of the light
	 * @param tempLight the posiion of the light (x,y,z)
	 */
	public static void setLight(double[] tempLight)
	{
		light = tempLight;
	}
	/**
	 * Sets the real position coordinates
	 * @param x X
	 * @param y Y
	 * @param z Z
	 */
	public static void setReal(double x,double y, double z)
	{
		realX = x;
		realY = y;
		realZ = z;
	}
	
	public void updateDistance()
	{
		distance = Math.sqrt( Math.pow(realX - this.center[0], 2) + Math.pow(realY - this.center[1], 2) + Math.pow(realZ - this.center[2], 2));
	}
	/**
	 * Returns the coordinates (x,y,z) of the center of the face
	 * @return the coordinates (x,y,z) of the center of the face
	 */
	public double[] getCenter()
	{
		return center;
	}
	/**
	 * Returns a list of the corners
	 * @return a list of the corners
	 */
	public double[][] getCorners()
	{
		return corners;
	}
	/**
	 * Returns the color of the Face
	 * @return the color of the Face
	 */
	public Color getColor()
	{
		return color;
	}
	public double getShadeCoefficient()
	{
		return shade;
	}
	/**
	 * Returns the calculated shading of the Face
	 * @return the calculated shading of the Face
	 */
	public Color getShading()
	{
		return shading;
		
	}
	public void calculateShading()
	{
		double distCoefficient = Math.max((10-distance)/10, 0);
		if(distCoefficient != 0)
			shade =   Math.acos( ( (light[0] - center[0])*(normal[0] - center[0]) + (light[1] - center[1])*(normal[1] - center[1]) + (light[2] - center[2])*(normal[2] - center[2]) ) 
	                                         / ( Math.sqrt( Math.pow( light[0] - center[0], 2) + Math.pow( light[1] - center[1], 2) + Math.pow( light[2] - center[2], 2) ) *  Math.sqrt( Math.pow( normal[0] - center[0], 2) + Math.pow( normal[1] - center[1], 2) + Math.pow( normal[2] - center[2], 2) ) ) ) / Math.PI;
		//System.out.println (shade);
		if(shade<.5)
			shade = 0;
		shade *= distCoefficient;
		shading = new Color((int)(color.getRed() * shade), (int)(color.getGreen() * shade), (int)(color.getBlue() * shade));
		
	}
	/**
	 * Calculates which Face is farther from the real position
	 * @param face the other Face
	 * @return whichever face is farther
	 */
	public int compareTo(Face face)
	{
		/*
		double temp = Math.sqrt( Math.pow(realX - face.center[0], 2) + Math.pow(realY - face.center[1], 2) + Math.pow(realZ - face.center[2], 2)) - 
						Math.sqrt( Math.pow(realX - this.center[0], 2) + Math.pow(realY - this.center[1], 2) + Math.pow(realZ - this.center[2], 2));
		*/
		double temp = face.distance - this.distance;
		if(temp<0)
			return -1;
		if(temp>0)
			return 1;
		return 0;
	}
}