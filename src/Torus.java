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
 * A class for Toruses that implements the Shapes interface
 */
public class Torus implements Shapes
{
	private ArrayList<Face> faces;
	String s;
	private double[][] torus;
	/**
	 * A constructor for Toruses
	 * @param color Color
	 * @param x X
	 * @param y Y
	 * @param z Z
	 * @param r1 Inner Radius
	 * @param r2 Outer Radius
	 * @param quality The higher this number, the more refined the sphere will look. Default is 15.
	 * @param yaw Rotation in the xy plane
	 * @param pitch Rotation in the xz plane
	 * @param roll Rotation in the yz plane
	 */
	public Torus(Color color, double x, double y, double z, double r1, double r2, int quality, double yaw, double pitch, double roll)
	{
		s = "Torus "+color.getRed()+" "+color.getGreen()+" "+color.getBlue()+" "+x+" "+y+" "+z+" "+r1+" "+r2+" "+quality+" "+yaw+" "+pitch+" "+roll;
		double[][] torus = new double[quality*quality][3];
		this.torus = torus;
		double posH = 0;
		double posZ = 0;
		double dM = (r2+r1)/2;
		double rM = (r2-r1)/2;
		double changeH = 360.0/quality;
		double changeZ = 360.0/quality;

		//posZ += changeZ;
		for(int i=0; i<quality; i++)
		{
			for (int j = 0; j<quality; j++)
			{
				double tempX = x + (dM + rM*Math.cos(Math.toRadians(posZ)))*Math.cos(Math.toRadians(posH));
				double tempY = y + (dM + rM*Math.cos(Math.toRadians(posZ)))*Math.sin(Math.toRadians(posH));
				double tempZ = z + rM*Math.sin(Math.toRadians(posZ));
				
				if(yaw!=0)
				{
					double tempR = Math.sqrt(Math.pow(tempX-x, 2) + Math.pow(tempY-y, 2));
                    double tempAngle = Math.toDegrees(Math.atan2(tempY - y, tempX - x));
                    tempX = x + tempR * Math.cos(Math.toRadians(tempAngle + yaw));
                    tempY = y + tempR * Math.sin(Math.toRadians(tempAngle + yaw));
				}
                if(pitch!=0)
                {
                	double tempR = Math.sqrt(Math.pow(tempX-x, 2) + Math.pow(tempZ-z, 2));
                    double tempAngle = Math.toDegrees(Math.atan2(tempZ - z, tempX - x));
                    tempX = x + tempR * Math.cos(Math.toRadians(tempAngle + pitch));
                    tempZ = z + tempR * Math.sin(Math.toRadians(tempAngle + pitch));
                }
                if(roll!=0)
                {
                	double tempR = Math.sqrt(Math.pow(tempY-y, 2) + Math.pow(tempZ-z, 2));
                    double tempAngle = Math.toDegrees(Math.atan2(tempZ - z, tempY - y));
                    tempY = y + tempR * Math.cos(Math.toRadians(tempAngle + roll));
                    tempZ = z + tempR * Math.sin(Math.toRadians(tempAngle + roll));
                }
                
                torus[i*quality+j][0] = tempX;
                torus[i*quality+j][1] = tempY;
                torus[i*quality+j][2] = tempZ;				
				posZ += changeZ;
			}
			posZ = 0;
			posH += changeH;
		}
		
		faces = new ArrayList<Face>();

		for (int i = 0; i<quality-1; i++)
		{
			for (int j = 0; j<quality-1; j++)
                faces.add(new Face(color, torus[i*quality+j], torus[i*quality+j+1], torus[(i+1)*quality+j+1], torus[(i+1)*quality+j]));
            faces.add(new Face(color, torus[(i+1)*quality-1], torus[i*quality], torus[(i+1)*quality], torus[(i+2)*quality-1]));
		}
        for (int i = 0; i<quality-1; i++)
            faces.add(new Face(color, torus[(quality-1)*quality+i], torus[(quality-1)*quality+i+1], torus[i+1], torus[i]));
        faces.add(new Face(color, torus[quality*quality-1], torus[(quality-1)*quality], torus[0], torus[quality-1]));
	}
	
	/**
	 * Returns a list of the shape's Faces
	 * @return a list of the shape's Faces
	 */
	public ArrayList<Face> getFaces()
	{
		return faces;
	}
	/**
	 * Returns a string representation of the shape
	 * @return a string representation of the shape
	 */
	public String toString()
	{
		return s;
	}
	
	public void transform(double x, double y, double z)
	{
		for (int i = 0; i < torus.length; i++) 
		{
			torus[i][0] += x;
			torus[i][1] += y;
			torus[i][2] += z;
		}
	}
}