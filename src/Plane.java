/*
 * Nikhil Pandeti
 * Mrs. Gallatin
 * Period 2
 */
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.RandomAccessFile;
import java.util.*;
/**
 * A class for Cubes that implements the Shapes interface
 */
public class Plane implements Shapes, Hitboxable
{
	private ArrayList<Face> faces;
	String s;
	private double[][] plane;
	private ArrayList<Hitbox> hitbox;
	private double x,y,z;
	private int quality;
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
		this.plane = plane;
		this.x = x;
		this.y = y;
		this.z = z;
		this.quality = quality;
		hitbox = new ArrayList<Hitbox>();
		hitbox.add(new Hitbox(x+length/2,y+width/2,z,length,width,.01));
		for(int i = 0; i<quality+1; i++)
		{
			for(int j = 0; j<quality+1; j++)
			{
				plane[i*(quality+1)+j][0] = x+i*(length/quality);
				plane[i*(quality+1)+j][1] =	y+j*(width/quality);
				plane[i*(quality+1)+j][2] = z;
				//plane[i*(quality+1)+j][2] = z+Math.sin(plane[i*(quality+1)+j][0]/5)/2 + Math.sin(plane[i*(quality+1)+j][1]/5)/2;
				
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
	public ArrayList<Hitbox> getHitbox()
	{
		return hitbox;
	}
	public void transform(double x, double y, double z)
	{
		for (int i = 0; i < plane.length; i++) 
		{
			plane[i][0] += x;
			plane[i][1] += y;
			plane[i][2] += z;
		}
		for(Hitbox h: hitbox)
		{
			hitbox.get(0).transform(x, y, z);
		}
	}
	public void rotate(double yaw, double pitch, double roll){
		
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
	}
	public void drawImg(String file)
	{
		try{
			RandomAccessFile raf = new RandomAccessFile(getClass().getProtectionDomain().getClassLoader().getResource("").getPath()+"\\"+file, "r");
			//for(int i=0;i<54;i++)
			//	raf.read();
			raf.skipBytes(54); 
			//for(int i = 54; i<raf.length()-2;i++)
			//{
				//System.out.print(raf.read()+", ");
				
			//}
			int counter = 0;
			int temp = 0;
			int prev = -1;
			for(Face f:faces)
			{
				int r=0,g=0,b=0;
				for(int i = 0;i<3;i++)
				{
					temp = raf.read();
					System.out.println(temp);
					//while(temp==0)
					//	temp = raf.read();
					counter++;
					if(counter == quality*3)
					{
						counter = 0;
						raf.read();
						raf.read();
					}
					
					if(i==0)
						b = temp;
					if(i==1)
						g = temp;
					else
						r = temp;
					prev = temp;
				}
					
				//b = raf.read();
				//g = raf.read();
				//r = raf.read();
				
				f.setColor(new Color(r, g, b));
				System.out.println(new Color(r, g, b));
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}