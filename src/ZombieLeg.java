import java.awt.Color;
import java.util.ArrayList;


public class ZombieLeg
{
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Shapes> cubes;
	private ArrayList<Face> faces;
	private double x,y,z,posH;
	public ZombieLeg(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		posH = 0;
		Color zombieSkin = new Color(83,167,122);
		cubes = new ArrayList<Shapes>();
		
		//cubes.add(new Cube(Color.blue,x,y,z,.2,.2,.2,0,0,0));
		cubes.add(new Cube(Color.blue,x,y,z,.2,.2,.2,0,0,0));
		cubes.add(new Cube(Color.blue,x,y,z-.2,.2,.2,.2,0,0,0));
		cubes.add(new Cube(Color.blue,x,y,z-.4,.2,.2,.2,0,0,0));
		cubes.add(new Cube(Color.blue,x,y,z-.6,.2,.2,.2,0,0,0));
		cubes.add(new Cube(zombieSkin,x,y,z-.8,.15,.15,.2,0,0,0));
		cubes.add(new Cube(zombieSkin,x,y,z-1.0,.15,.15,.2,0,0,0));
		cubes.add(new Cube(zombieSkin,x,y,z-1.2,.2,.2,.2,0,0,0));
		cubes.add(new Cube(zombieSkin,x-.2,y,z-1.2,.2,.2,.2,0,0,0));
		
		
		
		hitbox = new ArrayList<Hitbox>();
		faces = new ArrayList<Face>();
		for(Shapes c: cubes)
		{
			Viewer.viewerPainter.addShape(c);
			if(c instanceof Cube)
			{
				hitbox.add(((Cube)c).getHitbox().get(0));
				faces.addAll(((Cube)c).getFaces());
			}
			if(c instanceof Plane)
			{
				hitbox.add(((Plane)c).getHitbox().get(0));
				faces.addAll(((Plane)c).getFaces());
			}
				
		}
	}
}
