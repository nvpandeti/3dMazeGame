import java.util.*;
public class Player implements Hitboxable
{
	private ArrayList<Hitbox> hitbox;
	private double x,y,z;
	private double footstepDist, lastStepX, lastStepY;
	private int footstepNum;
	public Player(double x, double y, double z)
	{
		this.x = x; 
		this.y = y; 
		this.z = z;
		hitbox = new ArrayList<Hitbox>();
		hitbox.add(new Hitbox(x,y,z,1,1,1));
		
		footstepDist = 0;
		lastStepX = x;
		lastStepY = y;
		footstepNum = 1;
	}
	public void setPosition(double x, double y, double z)
	{
		this.x = x; 
		this.y = y; 
		this.z = z;
		hitbox.get(0).setPosition(x, y, z);
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
   	  	
   	  	if(Math.pow(x-lastStepX, 2)+Math.pow(y-lastStepY, 2)>6)
   	  	{
   	  		//int footstepNum = (int)(Math.random()*2)+1;
   	  		if(footstepNum==1)
   	  			footstepNum = 2;
   	  		if(footstepNum==2)
   	  			footstepNum = 1;
   	  		(new Thread(new Sound("footstep"+footstepNum+".wav", -8f))).start();
   	  		lastStepX = x;
   	  		lastStepY = y;
   	  	}
   	  	
	}
	public ArrayList<Hitbox> getHitbox()
	{
		return hitbox;
	}
	
}