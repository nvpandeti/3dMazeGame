import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class Bullet implements Hitboxable
{
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Shapes> cubes;
	private ArrayList<Face> faces;
	private double x,y,z,vX,vY,vZ; 
	private int index;
	
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
		System.out.println(index);
	}
	
	public void multiplySpeed(double multiplier)
	{
		vX *= multiplier;
		vY *= multiplier;
		vZ *= multiplier;
	}
	public boolean move(ArrayList<Hitbox> maze, ArrayList<Marker> markers)
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
				
				((Marker)m.getReference()).dispose();
				markers.remove(i);
				i--;
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
	public void dispose()
	{
		for(int i=index; i<index+cubes.size(); i++)
		{
			Viewer.viewerPainter.setShape(i, null);
		}
	}

	public ArrayList<Hitbox> getHitbox() {
		// TODO Auto-generated method stub
		return hitbox;
	}
	public ArrayList<Face> getFaces()
	{
		return faces;
	}
}