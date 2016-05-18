import java.awt.Color;
import java.util.ArrayList;


public class Zombie 
{
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Shapes> cubes;
	private ArrayList<Face> faces;
	private double x,y,z,posH;
	private ZombieArm leftArm, rightArm;
	private ZombieLeg leftLeg, rightLeg;
	public Zombie(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		posH = 0;
		Color zombieSkin = new Color(83,167,122);
		Color zombieEye = new Color(89,22,27);
		cubes = new ArrayList<Shapes>();
		
		leftArm = new ZombieArm(x,y-.55,z+.5); 
		rightArm = new ZombieArm(x,y+.55,z+.5); 
		leftLeg = new ZombieLeg(x,y-.25,z-.4);
		rightLeg = new ZombieLeg(x,y+.25,z-.4);
		
		cubes.add(new Cube(Color.blue,x,y,z+.16,.4,.8,.88,0,0,0));
		cubes.add(new Cube(zombieSkin, x,y,z+.75,.3,.3,.3,0,0,0));
		
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
