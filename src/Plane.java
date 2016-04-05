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
 * A class for Cubes that implements the Shapes interface
 */
public class Plane implements Shapes
{
	private ArrayList<Face> faces;
	String s;
	/**
	 * A constructor for Planes
	 * @param color Color
	 * @param x X
	 * @param y Y
	 * @param z Z
	 * @param length X Length
	 * @param width Y Width
	 * @param hight Z Height
	 * @param yaw Rotation in the xy plane
	 * @param pitch Rotation in the xz plane
	 * @param roll Rotation in the yz plane
	 */
	public Plane(Color color, double x, double y, double z, double length, double width, int quality, double yaw, double pitch, double roll, boolean inverse)
	{
		s = "Cube "+color.getRed()+" "+color.getGreen()+" "+color.getBlue()+" "+x+" "+y+" "+z+" "+length+" "+width+" "+quality+" "+yaw+" "+pitch+" "+roll;
		double[][] plane = new double[(quality+1)*(quality+1)][3];
		for(int i = 0; i<quality+1; i++)
		{
			for(int j = 0; j<quality+1; j++)
			{
				plane[i*(quality+1)+j][0] = x+i*(length/quality);
				plane[i*(quality+1)+j][1] =	y+j*(width/quality);
				plane[i*(quality+1)+j][2] = z;
			}
				
		}
			
		
		for (int i = 0; i<(quality+1)*(quality+1); i++)
		{
			double tempX = 	plane[i][0];
            double tempY = 	plane[i][1];
            double tempZ = 	plane[i][2];
           	
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
            
            plane[i][0] = tempX;
            plane[i][1] = tempY;
            plane[i][2] = tempZ;
		}
		faces = new ArrayList<Face>();
		for(int i = 0; i<quality; i++)
			for(int j = 0; j<quality; j++)
				if(inverse)
					faces.add(new Face(color, plane[i*(quality+1)+j], plane[(i+1)*(quality+1)+j], plane[(i+1)*(quality+1)+j+1], plane[i*(quality+1)+j+1]));
				else
					faces.add(new Face(color, plane[i*(quality+1)+j], plane[i*(quality+1)+j+1], plane[(i+1)*(quality+1)+j+1], plane[(i+1)*(quality+1)+j]));
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
	
	
}