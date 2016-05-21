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
 * A class for Spheres that implements the Shapes interface
 */
public class Sphere implements Shapes, Hitboxable
{
	private ArrayList<Face> faces;
	String s;
	private double[][] sphere;
	private double[] center;
	private ArrayList<Hitbox> hitbox;
	/**
	 * A constructor for Spheres
	 * @param color Color
	 * @param x X
	 * @param y Y
	 * @param z Z
	 * @param r Radius
	 * @param quality The higher this number, the more refined the sphere will look. Default is 15.
	 */
	public Sphere(Color color, double x, double y, double z, double r, int quality)
	{
		s = "Sphere "+color.getRed()+" "+color.getGreen()+" "+color.getBlue()+" "+x+" "+y+" "+z+" "+r+" "+quality;
		hitbox = new ArrayList<Hitbox>();
		hitbox.add(new Hitbox(x,y,z,r,this));
		double[] center = {x,y,z};
		this.center = center;
		double[][] sphere = new double[quality*quality+2][3];
		this.sphere = sphere;
		double posH = 0;
		double posZ = 90;
		double changeH = 360.0/quality;
		double changeZ = 180.0/(quality-1);
		sphere[0][0] = x;
		sphere[0][1] = y;
		sphere[0][2] = z+r;
		posZ -= changeZ;
		for(int i=0; i<quality-1; i++)
		{
			for (int j = 0; j<quality; j++)
			{
				sphere[i*quality+j+1][0] = x + r * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
				sphere[i*quality+j+1][1] = y + r * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
				sphere[i*quality+j+1][2] = z + r * Math.sin(Math.toRadians(posZ));
				posH += changeH;
			}
			posH = 0;
			posZ -= changeZ;
		}
		sphere[sphere.length-1][0] = x;
		sphere[sphere.length-1][1] = y;
		sphere[sphere.length-1][2] = z-r;
		
		faces = new ArrayList<Face>();
		for (int i = 0; i<quality-1; i++)
			faces.add(new Face(color, sphere[0], sphere[i+2], sphere[i+1]));
		faces.add(new Face(color, sphere[1], sphere[quality], sphere[0]));
		for (int i = 0; i<quality-1; i++)
		{
			for (int j = 0; j<quality-1; j++)
                faces.add(new Face(color, sphere[i*quality+j+1], sphere[i*quality+j+2], sphere[(i+1)*quality+j+2], sphere[(i+1)*quality+j+1]));
            faces.add(new Face(color, sphere[(i+1)*quality], sphere[i*quality+1], sphere[(i+1)*quality+1], sphere[(i+2)*quality]));
		}
        for (int i = 0; i<quality-1; i++)
            faces.add(new Face(color, sphere[(quality-1)*quality+i+1], sphere[(quality-1)*quality+i+2], sphere[quality*quality+1]));
        faces.add(new Face(color, sphere[(quality-1)*quality+1], sphere[quality*quality+1], sphere[quality*quality]));
	}
	/**
	 * Returns a list of the shape's Faces
	 * @return a list of the shape's Faces
	 */
	public ArrayList<Face> getFaces()
	{
		return faces;
	}
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
	
	public void transform(double x, double y, double z)
	{
		center[0] += x;
		center[1] += y;
		center[2] += z;
		for (int i = 0; i < sphere.length; i++) 
		{
			sphere[i][0] += x;
			sphere[i][1] += y;
			sphere[i][2] += z;
		}
		for(Hitbox h: hitbox)
		{
			h.transform(x, y, z);
		}
	}
	public void rotate(double yaw, double pitch, double roll) 
	{
		
        for (int i = 0; i<sphere.length; i++)
        {
        	double tempX = 	sphere[i][0];
            double tempY = 	sphere[i][1];
            double tempZ = 	sphere[i][2];
           	
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
            
            sphere[i][0] = tempX;
            sphere[i][1] = tempY;
            sphere[i][2] = tempZ;
        }
       	
        
	}
	public ArrayList<Hitbox> getHitbox() {
		return hitbox;
	}
}