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
 * A class for Cylinders that implements the Shapes interface
 */
public class Cylinder implements Shapes
{
	private ArrayList<Face> faces;
	String s;
	private double[][] cylinder;
	private double[] center;
	/**
	 * A constructor for Cylinders
	 * @param color Color
	 * @param x X
	 * @param y Y
	 * @param z Z
	 * @param r Radius
	 * @param quality The higher this number, the more refined the sphere will look. Default is 15.
	 * @param yaw Rotation in the xy plane
	 * @param pitch Rotation in the xz plane
	 * @param roll Rotation in the yz plane
	 */
	public Cylinder(Color color, double x, double y, double z, double r, double h, int quality, double yaw, double pitch, double roll)
	{
		s = "Cylinder "+color.getRed()+" "+color.getGreen()+" "+color.getBlue()+" "+x+" "+y+" "+z+" "+r+" "+h+" "+quality+" "+yaw+" "+pitch+" "+roll;
		double[][] cylinder = new double[quality*2][3];
		this.cylinder = cylinder;
		double[] center = {x,y,z};
		this.center = center;
        double changeH = 360/quality;
        double posH = 0;
        for (int i = 0; i<quality; i++)
        {
        	double tempX = 	x + r * Math.cos(Math.toRadians(posH));
            double tempY = 	y + r * Math.sin(Math.toRadians(posH));
            double tempZ = 	z+h/2;
           	
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
            
            cylinder[i][0] = tempX;
            cylinder[i][1] = tempY;
            cylinder[i][2] = tempZ;
           	posH += changeH;
        }
       	
        posH = 0;
        for (int i = 0; i<quality; i++)
        {
        	double tempX = x + r * Math.cos(Math.toRadians(posH));
            double tempY = y + r * Math.sin(Math.toRadians(posH));
            double tempZ = z-h/2;
           	
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
            
            cylinder[i+quality][0] = tempX;
            cylinder[i+quality][1] = tempY;
            cylinder[i+quality][2] = tempZ;
           	posH += changeH;
        }
        
        faces = new ArrayList<Face>();
        double[][] top = new double[quality][3];
        for (int i = 0; i<quality; i++)
            top[quality-i-1] = cylinder[i];
        faces.add(new Face(color, top));
        for (int i = 0; i<quality-1; i++)
            faces.add(new Face(color, cylinder[i], cylinder[i+1], cylinder[i+1+quality], cylinder[i+quality]));
        faces.add(new Face(color, cylinder[quality-1], cylinder[0], cylinder[quality], cylinder[2*quality-1]));
        double[][] bottom = new double[quality][3];
        for (int i = 0; i<quality; i++)
            bottom[i] = cylinder[i+quality];
        faces.add(new Face(color, bottom));
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
	 * Returns center coordinates
	 * @return center coordinates
	 */
	public double[] getCenter()
	{
		 return center;
	}
	/**
	 * Returns a string representation of the shape
	 * @return a string representation of the shape
	 */
	public String toString()
	{
		return s;
	}
	/**
	 * Changes the position of the cylinder
	 * @param x x change
	 * @param y y change
	 * @param z z change
	 */
	public void transform(double x, double y, double z)
	{
		center[0] += x;
		center[1] += y;
		center[2] += z;
		for (int i = 0; i < cylinder.length; i++) 
		{
			cylinder[i][0] += x;
			cylinder[i][1] += y;
			cylinder[i][2] += z;
		}
		
	}

	/**
	 * Rotates the cylinder
	 * @param yaw Rotation in the xy plane
	 * @param pitch Rotation in the xz plane
	 * @param roll Rotation in the yz plane
	 */
	public void rotate(double yaw, double pitch, double roll) 
	{
		
        for (int i = 0; i<cylinder.length; i++)
        {
        	double tempX = 	cylinder[i][0];
            double tempY = 	cylinder[i][1];
            double tempZ = 	cylinder[i][2];
           	
           	if(yaw!=0)
			{
				double tempR = Math.sqrt(Math.pow(tempX-center[0], 2) + Math.pow(tempY-center[1], 2));
                double tempAngle = Math.toDegrees(Math.atan2(tempY - center[1], tempX - center[0]));
                tempX = center[0] + tempR * Math.cos(Math.toRadians(tempAngle + yaw));
                tempY = center[1] + tempR * Math.sin(Math.toRadians(tempAngle + yaw));
			}
            if(pitch!=0)
            {
            	double tempR = Math.sqrt(Math.pow(tempX-center[0], 2) + Math.pow(tempZ-center[2], 2));
                double tempAngle = Math.toDegrees(Math.atan2(tempZ - center[2], tempX - center[0]));
                tempX = center[0] + tempR * Math.cos(Math.toRadians(tempAngle + pitch));
                tempZ = center[2] + tempR * Math.sin(Math.toRadians(tempAngle + pitch));
            }
            if(roll!=0)
            {
            	double tempR = Math.sqrt(Math.pow(tempY-center[1], 2) + Math.pow(tempZ-center[2], 2));
                double tempAngle = Math.toDegrees(Math.atan2(tempZ - center[2], tempY - center[1]));
                tempY = center[1] + tempR * Math.cos(Math.toRadians(tempAngle + roll));
                tempZ = center[2] + tempR * Math.sin(Math.toRadians(tempAngle + roll));
            }
            
            cylinder[i][0] = tempX;
            cylinder[i][1] = tempY;
            cylinder[i][2] = tempZ;
        }
       	
        
	}
}