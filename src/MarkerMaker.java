/*
 * Nikhil Pandeti
 * Mrs. Gallatin
 * Period 2
 */
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
/**
 * A class for MarkerMakers
 */
public class MarkerMaker implements Hitboxable
{
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Shapes> cubes;
	private ArrayList<Face> faces;
	private double x,y,z,vX,vY,vZ; 
	private int index;
	private int num;
	private Marker marker;
	/**
	 *  A constructor for Bullets
	 * @param x x position
	 * @param y y position
	 * @param z z position
	 * @param posH horizontal angle
	 * @param posZ vertical angle
	 * @param num number of the marker
	 */
	public MarkerMaker(double x, double y, double z, double posH, double posZ, int num)
	{
		this.x = x;
		this.y = y;
		this.z = z; 
		double moveCoefficient = .05;
		this.num = num;
		marker = null;
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
	 * @return if the bullet should be disposed
	 */
	public boolean move(ArrayList<Hitbox> maze)
	{
		transform(vX,vY,vZ);
   	  	//ArrayList<Hitbox> collisions = new ArrayList<Hitbox>();
		if(Math.abs(x)>250 ||  Math.abs(y)>250 || Math.abs(z)>50)
		{
			return true;
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
  				ArrayList<Face> copy=m.getReference().getFaces();
  				Face min = copy.get(0);
  				double minDist = Math.sqrt(Math.pow(x - min.getCenter()[0], 2) + Math.pow(y - min.getCenter()[1], 2) + Math.pow(z - min.getCenter()[2], 2));
  				for(int j=1; j<copy.size();j++)
  				{
  					if(copy.get(j).getOriginalShadeCoefficient() != 0)
  					{
  						double tempDist = Math.sqrt(Math.pow(x - copy.get(j).getCenter()[0], 2) + Math.pow(y - copy.get(j).getCenter()[1], 2) + Math.pow(z - copy.get(j).getCenter()[2], 2));
	  					if(minDist > tempDist)
	  					{
	  						minDist = tempDist;
	  						min = copy.get(j);
	  					}
  					}
  					
  				}
  				double[] minPoint = min.getCenter();
  				for(int j=0; j<min.getCorners().length;j++)
  				{
					double tempDist = Math.sqrt(Math.pow(x - min.getCorners()[j][0], 2) + Math.pow(y - min.getCorners()[j][1], 2) + Math.pow(z - min.getCorners()[j][2], 2));
  					if(minDist > tempDist)
  					{
  						minDist = tempDist;
  						minPoint = min.getCorners()[j];
  					}
  				}
  				//min.setColor(Color.green);
  				//double[] tempLightPos = min.getCenter();
  				//Face.addLights(new Light(tempLightPos, 4)); 
  				//Viewer.viewerPainter.addShape(new Sphere(Color.red, tempLightPos[0],tempLightPos[1],tempLightPos[2],.2,3));
  				marker = new Marker(minPoint,min.getCenter(),min.getNormal(),num);
  				//dispose();
  				return true;
  			}
  		}
  		return false;
	}
	/**
	 * Returns the Marker
	 * @return the Marker
	 */
	public Marker getMarker()
	{
		return marker;
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
		
		for(Shapes s: cubes)
		{
			s.transform(x, y, z);
		}
	}
	/**
	 * Removes the MarkerMaker
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
	 * Returns a list of the hitboxable's Faces
	 * @return a list of the hitboxable's Faces
	 */
	public ArrayList<Face> getFaces()
	{
		return faces;
	}
}