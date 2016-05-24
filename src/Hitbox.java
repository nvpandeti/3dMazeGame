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
 * A class for HitBoxes
 */
public class Hitbox
{
	private double x, y, z, length, width, height, radius;
	private boolean isBox;
	private Hitboxable reference;
	/**
	 * A constructor for Rectangular Hitboxes
	 * @param x X
	 * @param y Y
	 * @param z Z
	 * @param length X Length
	 * @param width Y Width
	 * @param hight Z Height
	 * @param ref a reference to the Hitboxable that owns the Hitbox
	 */
	public Hitbox(double x, double y, double z, double length, double width, double height, Hitboxable ref)
	{
		this.x = x; 
		this.y = y; 
		this.z = z; 
		this.length = length; 
		this.width = width;
		this.height	= height;
		isBox = true;
		reference = ref;
	}
	/**
	 * A constructor for Rectangular Hitboxes
	 * @param x X
	 * @param y Y
	 * @param z Z
	 * @param radius radius
	 * @param ref a reference to the Hitboxable that owns the Hitbox
	 */
	public Hitbox(double x, double y, double z, double radius, Hitboxable ref)
	{
		this.x = x; 
		this.y = y; 
		this.z = z; 
		this.radius = radius;
		isBox = false;
		reference = ref;
	}
	/**
	 * Returns the position
	 * @return the position
	 */
	public double[] getPosition()
	{
		double[] pos = {x,y,z};
		return pos;
		
	}
	/**
	 * Returns the Hitboxable object
	 * @return the Hitboxable object
	 */
	public Hitboxable getReference()
	{
		return reference;
	}
	/**
	 * Sets the position of the hitbox
	 * @param x x
	 * @param y y
	 * @param z z
	 */
	public void setPosition(double x, double y, double z)
	{
		this.x = x; 
		this.y = y; 
		this.z = z; 
	}
	/**
	 * Changes the position of the cube
	 * @param x x change
	 * @param y y change
	 * @param z z change
	 */
	public void transform(double x, double y, double z)
	{
		this.x += x; 
		this.y += y; 
		this.z += z;
	}
	/**
	 * Returns max x
	 * @return max x
	 */
	public double getMaxX()
	{
		return x+length/2;
	}
	/**
	 * Returns max y
	 * @return max y
	 */
	public double getMaxY()
	{
		return y+width/2;
	}
	/**
	 * Returns max z
	 * @return max z
	 */
	public double getMaxZ()
	{
		return z+height/2;
	}
	/**
	 * Returns min x
	 * @return min x
	 */
	public double getMinX()
	{
		return x-length/2;
	}
	/**
	 * Returns min y
	 * @return min y
	 */
	public double getMinY()
	{
		return y-width/2;
	}
	/**
	 * Returns min y
	 * @return min y
	 */
	public double getMinZ()
	{
		return z-height/2;
	}
	/**
	 * Returns true if this HitBox is intersecting the other HitBox
	 * @return true if this HitBox is intersecting the other HitBox
	 */
	public boolean isColliding(Hitbox o)
	{
		if(this.isBox && o.isBox)
		{
			
			return  this.z - this.height/2 < o.z + o.height/2 && this.z + this.height/2 > o.z - o.height/2 &&
					this.y - this.width/2 < o.y + o.width/2 && this.y + this.width/2 > o.y - o.width/2 &&
					this.x - this.length/2 < o.x + o.length/2 && this.x + this.length/2 > o.x - o.length/2;
		}
		
		else if(this.isBox && !o.isBox)
		{
			double dmin = 0;
			if (o.x < this.x - this.length/2) 
			{
		        dmin += Math.pow(o.x - (this.x - this.length/2), 2);
		    } 
		    else if (o.x > this.x + this.length/2) 
		    {
		        dmin += Math.pow(o.x - (this.x + this.length/2), 2);
		    }
		
		    if (o.y < this.y - this.width/2) 
		    {
		        dmin += Math.pow(o.y - (this.y - this.width/2), 2);
		    } 
		    else if (o.y > this.y + this.width/2) 
		    {
		        dmin += Math.pow(o.y - (this.y + this.width/2), 2);
		    }
		
		    if (o.z < this.z - this.height/2) 
		    {
		        dmin += Math.pow(o.z - (this.z - this.height/2), 2);
		    } 
		    else if (o.z > this.z + this.height/2) 
		    {
		        dmin += Math.pow(o.z - (this.z + this.height/2), 2);
		    }
		
		    return dmin <= Math.pow(o.radius, 2);
		}
			
		
		else if(!this.isBox && o.isBox)
		{
			double dmin = 0;
			if (this.x < o.x - o.length/2) 
			{
		        dmin += Math.pow(this.x - (o.x - o.length/2), 2);
		    } 
		    else if (this.x > o.x + o.length/2) 
		    {
		        dmin += Math.pow(this.x - (o.x + o.length/2), 2);
		    }
		
		    if (this.y < o.y - o.width/2) 
		    {
		        dmin += Math.pow(this.y - (o.y - o.width/2), 2);
		    } 
		    else if (this.y > o.y + o.width/2) 
		    {
		        dmin += Math.pow(this.y - (o.y + o.width/2), 2);
		    }
		
		    if (this.z < o.z - o.height/2) 
		    {
		        dmin += Math.pow(this.z - (o.z - o.height/2), 2);
		    } 
		    else if (this.z > o.z + o.height/2) 
		    {
		        dmin += Math.pow(this.z - (o.z + o.height/2), 2);
		    }
		
		    return dmin <= Math.pow(this.radius, 2);
		}
			
		else
		{
			return Math.pow(this.x - o.x, 2)+Math.pow(this.y - o.y, 2)+Math.pow(this.z - o.z, 2) < Math.pow(this.radius + o.radius, 2);
		}
			
	}
}