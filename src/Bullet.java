/*
 * Nikhil Pandeti
 * Mrs. Gallatin
 * Period 2
 */
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
/**
 * A class for bullets and vision probes
 */
public class Bullet implements Hitboxable
{
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Shapes> cubes;
	private ArrayList<Face> faces;
	private double x,y,z,vX,vY,vZ; 
	private int index;
	/**
	 *  A constructor for Bullets
	 * @param x x position
	 * @param y y position
	 * @param z z position
	 * @param posH horizontal angle
	 * @param posZ vertical angle
	 */
	public Bullet(double x, double y, double z, double posH, double posZ)
	{
		this.x = x;
		this.y = y;
		this.z = z; 
		double moveCoefficient = .05;
		vX = moveCoefficient * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
    	vY = moveCoefficient * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
    	vZ = moveCoefficient * Math.sin(Math.toRadians(posZ));
    	
    	cubes = new ArrayList<Shapes>();
    	cubes.add(new Cube(Color.white, x,y,z,.05,.05,.05,0,0,0));
    	
    	hitbox = new ArrayList<Hitbox>();
    	faces = new ArrayList<Face>();
    	index = -1;
		for(Shapes c: cubes)
		{
			if(index==-1)
				index = Viewer.viewerPainter.addShape(c);
			else
				Viewer.viewerPainter.addShape(c);
			if(c instanceof Cube)
			{
				hitbox.add(((Cube)c).getHitbox().get(0));
				faces.addAll(((Cube)c).getFaces());
			}
				
		}
		//System.out.println(index);
	}
	/**
	 * Multiply the speed of the bullet
	 * @param multiplier the speed multiplier
	 */
	public void multiplySpeed(double multiplier)
	{
		vX *= multiplier;
		vY *= multiplier;
		vZ *= multiplier;
	}
	/**
	 * Manages collisions for bullets
	 * @param maze Maze hitboxes
	 * @param markers All markers
	 * @param zombies All zombies
	 * @return if the bullet should be disposed
	 */
	public boolean move(ArrayList<Hitbox> maze, ArrayList<Marker> markers, ArrayList<Zombie> zombies)
	{
		transform(vX,vY,vZ);
   	  	//ArrayList<Hitbox> collisions = new ArrayList<Hitbox>();
		if(Math.abs(x)>100 ||  Math.abs(y)>100 || Math.abs(z)>25)
		{
			return true;
		}
		for (int i = 0; i < markers.size(); i++) 
		{
			Hitbox m = markers.get(i).getHitbox().get(0);
			if(hitbox.get(0).isColliding(m))
			{
				//Viewer.viewerPainter.addShape(new Sphere(Color.red, x,y,z,.1,3));
				
				
				
				i-= markerExplosion(i, m, maze, markers, zombies);
				
				return true;
			}
		}
		for (int i = 0; i < zombies.size(); i++) 
		{
			Hitbox m = zombies.get(i).getHitbox().get(0);
			Hitbox m1 = zombies.get(i).getHitbox().get(1);
			if(hitbox.get(0).isColliding(m) || hitbox.get(0).isColliding(m1) )
			{
				zombies.get(i).changeHealth(-10);
				if(zombies.get(i).getHealth() == 0)
				{
					zombies.get(i).dispose();
					zombies.remove(i);
					i--;
				}
				//Viewer.viewerPainter.addShape(new Torus(Color.red, x,y,z,.1,.2,3,0,90,0));
				return true;
			}
		}
			
  		for(int i=0; i<maze.size(); i++)
  		{
  			Hitbox m = maze.get(i);
  			
  			if(hitbox.get(0).isColliding(m))
  			{
  				System.out.println("Hitting");
  				//double[] tempLightPos = {x,y,z};
  				//Face.addLights(new Light(tempLightPos, 4)); 
  				//Viewer.viewerPainter.addShape(new Sphere(Color.red, x,y,z,.1,3));
//  				ArrayList<Face> copy= new ArrayList<Face>();
//  				copy.addAll(m.getReference().getFaces());
//  				Collections.sort(copy);
//  				copy.get(copy.size()-1).setColor(Color.green);
  				/*
  				if(m.getReference() instanceof Cube && m.getReference().getFaces().size()<=2)
  				{
  					ArrayList<Face> copy=m.getReference().getFaces();
	  				for(int c=0;c<copy.size();)
	  				{
	  					copy.remove(0);
	  				}
	  				maze.remove(i);
	  				i--;
  				}
  				*/
  				
//  				Face min = copy.get(0);
//  				double minDist = Math.sqrt(Math.pow(x - min.getCenter()[0], 2) + Math.pow(y - min.getCenter()[1], 2) + Math.pow(z - min.getCenter()[2], 2));
//  				for(int j=1; j<copy.size();j++)
//  				{
//  					double tempDist = Math.sqrt(Math.pow(x - copy.get(j).getCenter()[0], 2) + Math.pow(y - copy.get(j).getCenter()[1], 2) + Math.pow(z - copy.get(j).getCenter()[2], 2));
//  					if(minDist > tempDist)
//  					{
//  						minDist = tempDist;
//  						min = copy.get(j);
//  					}
//  				}
  				//min.setColor(Color.green);
  				//double[] tempLightPos = min.getCenter();
  				//Face.addLights(new Light(tempLightPos, 4)); 
  				//Viewer.viewerPainter.addShape(new Sphere(Color.red, tempLightPos[0],tempLightPos[1],tempLightPos[2],.2,3));
  				//Viewer.viewerPainter.addShape(new Marker(min.getCenter(),min.getNormal(),3));
  				//dispose();
  				return true;
  			}
  		}
  		return false;
	}
	public int markerExplosion(int markerIndex, Hitbox m, ArrayList<Hitbox> maze, ArrayList<Marker> markers, ArrayList<Zombie> zombies)
	{
		((Marker)m.getReference()).dispose();
		markers.remove(markerIndex);
		Hitbox explosion = new Hitbox(((Marker)m.getReference()).getCenter()[0], ((Marker)m.getReference()).getCenter()[1], ((Marker)m.getReference()).getCenter()[2], .65,null);
		for(int j=0; j<maze.size(); j++)
  		{
			//System.out.println("explosion");
  			Hitbox mazeH = maze.get(j);
  			
  			if(mazeH.getReference().getFaces().size()<=2 && explosion.isColliding(mazeH))
  			{
  				ArrayList<Face> copy=mazeH.getReference().getFaces();
  				for(int c=0;c<copy.size();)
  				{
  					copy.remove(0);
  				}
  				maze.remove(j);
  				j--;
  			}
  		}
		
		Hitbox explosion2 = new Hitbox(((Marker)m.getReference()).getCenter()[0], ((Marker)m.getReference()).getCenter()[1], ((Marker)m.getReference()).getCenter()[2], 1.5,null);
		for (int i = 0; i < zombies.size(); i++) 
		{
			Hitbox zombieH = zombies.get(i).getHitbox().get(0);
			Hitbox zombieH1 = zombies.get(i).getHitbox().get(1);
			if(explosion2.isColliding(zombieH) || explosion2.isColliding(zombieH1) )
			{
				zombies.get(i).changeHealth(-100);
				if(zombies.get(i).getHealth() == 0)
				{
					zombies.get(i).dispose();
					zombies.remove(i);
					i--;
				}
				//Viewer.viewerPainter.addShape(new Sphere(Color.red, x,y,z,1.5,10));
			}
		}
  		int markerHits = 0;
  		
  		for(int j=0; j<markers.size(); j++)
  		{
			System.out.println("explosion2");
  			Hitbox markerH = markers.get(j).getHitbox().get(0);
  			
  			if(explosion2.isColliding(markerH))
  			{
  				j-= markerExplosion(j, markerH, maze, markers, zombies);
  				markerHits++;
  			}
  		}
  		return 1 + markerHits;
	}
	/**
	 * Manages collisions for vision probes
	 * @param maze Maze hitboxes
	 * @param markers All markers
	 * @return a number based on what the vision probe has hit
	 */
	public int move(ArrayList<Hitbox> maze, Hitbox player)
	{
		transform(vX,vY,vZ);
   	  	//ArrayList<Hitbox> collisions = new ArrayList<Hitbox>();
		if(hitbox.get(0).isColliding(player))
		{
			return 2;
		}
  		for(int i=0; i<maze.size(); i++)
  		{
  			Hitbox m = maze.get(i);
  			
  			if(hitbox.get(0).isColliding(m))
  			{
  				return 1;
  			}
  		}
  		return 0;
	}
	/**
	 * Changes the position of the bullet
	 * @param x x change
	 * @param y y change
	 * @param z z change
	 */
	public void transform(double x, double y, double z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
		
		for(Shapes s: cubes)
		{
			s.transform(x, y, z);
			s.rotate(10, 5, 0);
		}
	}
	/**
	 * Removes the bullet
	 */
	public void dispose()
	{
		for(int i=index; i<index+cubes.size(); i++)
		{
			Viewer.viewerPainter.setShape(i, null);
		}
	}
	/**
	 * Returns hitboxes
	 * @return hitboxes
	 */
	public ArrayList<Hitbox> getHitbox() {
		return hitbox;
	}
	/**
	 * Returns faces
	 * @return faces
	 */
	public ArrayList<Face> getFaces()
	{
		return faces;
	}
}