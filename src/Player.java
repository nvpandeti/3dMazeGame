import java.util.*;
public class Player implements Hitboxable
{
	private Hitbox hitbox;
	private double x,y,z;
	public Player(double x, double y, double z)
	{
		this.x = x; 
		this.y = y; 
		this.z = z;
		hitbox = new Hitbox(x,y,z,1,1,1);
	}
	public void setPosition(double x, double y, double z)
	{
		this.x = x; 
		this.y = y; 
		this.z = z;
		hitbox.setPosition(x, y, z);
	}
	public double[] getPosition()
	{
		double[] ret = new double[3];
		ret[0] = x;
		ret[1] = y;
		ret[2] = z;
		return ret;
	}
	public void move(boolean[] keys, double posH, double posZ)
	{
		double moveCoefficient = .25;
   	  	if(keys[4])
   	  	{
   	  		//System.out.println ("Blahhhhh");
   	  		x += moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
        	y += moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
        	//z += moveCoefficient * Math.sin(Math.toRadians(posZ));
   	  	}
   	  	if(keys[5])
   	  	{
   	  		x -= moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
        	y -= moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
        	//z -= moveCoefficient * Math.sin(Math.toRadians(posZ));
   	  	}
   	  	if(keys[6])
   	  	{
   	  		x += moveCoefficient * Math.sin(Math.toRadians(posH));
        	y -= moveCoefficient * Math.cos(Math.toRadians(posH));
   	  	}
   	  	if(keys[7])
   	  	{
   	  		x -= moveCoefficient * Math.sin(Math.toRadians(posH));
        	y += moveCoefficient * Math.cos(Math.toRadians(posH));
   	  	}
   	  	if(keys[8])
   	  	{
   	  		x += moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.sin(Math.toRadians(posZ));
        	y += moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.sin(Math.toRadians(posZ));
        	//z -= Math.sqrt(Math.pow(moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ)),2) + 
        	//					Math.pow(moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ)), 2));
   	  	}
   	  	if(keys[9])
   	  	{
   	  		x -= moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.sin(Math.toRadians(posZ));
        	y -= moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.sin(Math.toRadians(posZ));
        	//z += Math.sqrt(Math.pow(moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ)),2) + 
        	//					Math.pow(moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ)), 2));
   	  	}
	}
	public Hitbox getHitbox()
	{
		return hitbox;
	}
	
}