/*
 * Nikhil Pandeti
 * Mrs. Gallatin
 * Period 2
 */
import java.util.*;
/**
 * A class for the player
 */
public class Player implements Hitboxable
{
	private ArrayList<Hitbox> hitbox;
	private double x,y,z;
	private double footstepDist, lastStepX, lastStepY;
	private int footstepNum;
	private double health;
	public Player(double x, double y, double z)
	{
		this.x = x; 
		this.y = y; 
		this.z = z;
		health = 100;
		hitbox = new ArrayList<Hitbox>();
		hitbox.add(new Hitbox(x,y,z,1,1,1,this));
		
		footstepDist = 0;
		lastStepX = x;
		lastStepY = y;
		footstepNum = 1;
	}
	/**
	 * Change the health of the player by a certain amount 
	 * @param change a certain amount 
	 */
	public void changeHealth(double change)
	{
		health += change;
		if(health<0)
			health = 0;
		if(health>100)
			health = 100;
	}
	/**
	 * Returns health
	 * @return health
	 */
	public double getHealth()
	{
		return health;
	}
	/**
	 * Sets the position of the player
	 * @param x x
	 * @param y y
	 * @param z z
	 */
	public void setPosition(double x, double y, double z)
	{
		this.x = x; 
		this.y = y; 
		this.z = z;
		hitbox.get(0).setPosition(x, y, z);
	}
	/**
	 * Returns the position
	 * @return the position
	 */
	public double[] getPosition()
	{
		double[] ret = new double[3];
		ret[0] = x;
		ret[1] = y;
		ret[2] = z;
		return ret;
	}
	/**
	 * Manages collisions for the player
	 * @param keys keypresses
	 * @param posH horizontal angle
	 * @param posZ vertical angle
	 * @param maze Maze hitboxes
	 */
	public void move(boolean[] keys, double posH, double posZ, ArrayList<Hitbox> maze)
	{
		double moveCoefficient = .25;
		double changeX = 0;
		double changeY = 0;
		double changeZ = 0;
		if(health!=0)
		{
		  	if(keys[4])
		  	{
		  		//System.out.println ("Blahhhhh");
		  		changeX += moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
		    	changeY += moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
		    	//z += moveCoefficient * Math.sin(Math.toRadians(posZ));
		  	}
		  	if(keys[5])
		  	{
		  		changeX -= moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
		  		changeY -= moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
		    	//z -= moveCoefficient * Math.sin(Math.toRadians(posZ));
		  	}
		  	if(keys[6])
		  	{
		  		changeX += moveCoefficient * Math.sin(Math.toRadians(posH));
		  		changeY -= moveCoefficient * Math.cos(Math.toRadians(posH));
		  	}
		  	if(keys[7])
		  	{
		  		changeX -= moveCoefficient * Math.sin(Math.toRadians(posH));
		  		changeY += moveCoefficient * Math.cos(Math.toRadians(posH));
		  	}
		  	if(keys[8])
		  	{
		  		changeX += moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.sin(Math.toRadians(posZ));
		  		changeY += moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.sin(Math.toRadians(posZ));
		    	//z -= Math.sqrt(Math.pow(moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ)),2) + 
		    	//					Math.pow(moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ)), 2));
		  	}
		  	if(keys[9])
		  	{
		  		changeX -= moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.sin(Math.toRadians(posZ));
		  		changeY -= moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.sin(Math.toRadians(posZ));
		    	//z += Math.sqrt(Math.pow(moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ)),2) + 
		    	//					Math.pow(moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ)), 2));
		  	}
		}
   	  	x += changeX;
   	  	y += changeY;
   	  	z += changeZ;
   	  	
   	  	hitbox.get(0).setPosition(x, y, z);
   	  	//ArrayList<Hitbox> collisions = new ArrayList<Hitbox>();
  		for(int i=0; i<maze.size(); i++)
  		{
  			Hitbox m = maze.get(i);
  			
  			if(hitbox.get(0).isColliding(m))
  			{
  				//collisions.add(m);
  				if(i<4)
  				{
  					if(z-m.getPosition()[2]<0)
	  					hitbox.get(0).setPosition(x, y, m.getMinZ()-.5);
	  				else
	  					hitbox.get(0).setPosition(x, y, m.getMaxZ()+.5);
  				}
  				else
  				{
  					if(Math.abs(x-m.getPosition()[0])>Math.abs(y-m.getPosition()[1]))
	  				{
	  					if(x-m.getPosition()[0]<0)
		  					hitbox.get(0).setPosition(m.getMinX()-.5, y, z);
		  				else
		  					hitbox.get(0).setPosition(m.getMaxX()+.5, y, z);
	  				}
	  				else// if(Math.abs(x-m.getPosition()[0])<Math.abs(y-m.getPosition()[1]))
	  				{
	  					if(y-m.getPosition()[1]<0)
		  					hitbox.get(0).setPosition(x, m.getMinY()-.5, z);
		  				else
		  					hitbox.get(0).setPosition(x, m.getMaxY()+.5, z);
	  				}
  				}
	  				
	  				
  				double[] temp = hitbox.get(0).getPosition();
  		   	  	x = temp[0];
  		   	  	y = temp[1];
  		   	  	z = temp[2];
	  				
  			}
  				
  		}
   	  	
   	  	
   	  	if(Math.pow(x-lastStepX, 2)+Math.pow(y-lastStepY, 2)>6)
   	  	{
   	  		//int footstepNum = (int)(Math.random()*2)+1;
   	  		if(footstepNum==1)
   	  			footstepNum = 2;
   	  		if(footstepNum==2)
   	  			footstepNum = 1;
   	  		(new Thread(new Sound("footstep"+footstepNum+".wav", 0f))).start();
   	  		lastStepX = x;
   	  		lastStepY = y;
   	  	}
   	  	
	}
	/**
	 * Returns hitboxes
	 * @return hitboxes
	 */
	public ArrayList<Hitbox> getHitbox()
	{
		return hitbox;
	}
	/**
	 * Returns a list of the hitboxable's Faces
	 * @return a list of the hitboxable's Faces
	 */
	public ArrayList<Face> getFaces() 
	{
		
		return null;
	}
	
}