import java.awt.Color;
import java.util.ArrayList;

public class Bullet implements Hitboxable
{
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Shapes> cubes;
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
    	index = -1;
		for(Shapes c: cubes)
		{
			if(index==-1)
				index = Viewer.viewerPainter.addShape(c);
			else
				Viewer.viewerPainter.addShape(c);
			if(c instanceof Cube)
				hitbox.add(((Cube)c).getHitbox().get(0));
		}
		System.out.println(index);
	}
	
	public boolean move(ArrayList<Hitbox> maze)
	{
		transform(vX,vY,vZ);
   	  	//ArrayList<Hitbox> collisions = new ArrayList<Hitbox>();
		if(Math.abs(x)>500 ||  Math.abs(y)>500 || Math.abs(z)>500)
		{
			return true;
		}
  		for(int i=0; i<maze.size(); i++)
  		{
  			Hitbox m = maze.get(i);
  			
  			if(hitbox.get(0).isColliding(m))
  			{
  				System.out.println("Hitting");
  				double[] tempLightPos = {x,y,z};
  				Face.addLights(new Light(tempLightPos, 4)); 
  				Viewer.viewerPainter.addShape(new Sphere(Color.red, x,y,z,.1,3));
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
}