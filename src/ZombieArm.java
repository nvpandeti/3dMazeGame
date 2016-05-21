import java.awt.Color;
import java.util.ArrayList;


public class ZombieArm
{
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Shapes> cubes;
	private ArrayList<Face> faces;
	private double x,y,z,posH;
	public ZombieArm(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		posH = 0;
		
		Color zombieSkin = new Color(83,167,122);
		cubes = new ArrayList<Shapes>();
		
		cubes.add(new Cube(Color.blue,x,y,z,.2,.2,.2,0,0,0));
		cubes.add(new Cube(Color.blue,x,y,z-.2,.2,.2,.2,0,0,0));
		cubes.add(new Cube(Color.blue,x,y,z-.4,.2,.2,.2,0,0,0));
		cubes.add(new Cube(Color.blue,x,y,z-.6,.2,.2,.2,0,0,0));
		cubes.add(new Cube(zombieSkin,x,y,z-.8,.05,.15,.2,0,0,0));
		
		cubes.add(new Cube(zombieSkin,x,y,z-.915,.05,.03,.03,0,0,0));
		cubes.add(new Cube(zombieSkin,x+.04,y,z-.915,.03,.03,.03,0,0,0));
		cubes.add(new Cube(zombieSkin,x+.07,y,z-.915,.03,.03,.03,0,0,0));
		cubes.add(new Cube(zombieSkin,x+.1,y,z-.915,.03,.03,.03,0,0,0));
		
		cubes.add(new Cube(zombieSkin,x,y-.06,z-.915,.05,.03,.03,0,0,0));
		cubes.add(new Cube(zombieSkin,x+.04,y-.06,z-.915,.03,.03,.03,0,0,0));
		cubes.add(new Cube(zombieSkin,x+.07,y-.06,z-.915,.03,.03,.03,0,0,0));
		cubes.add(new Cube(zombieSkin,x+.1,y-.06,z-.915,.03,.03,.03,0,0,0));
		
		cubes.add(new Cube(zombieSkin,x,y+.06,z-.915,.05,.03,.03,0,0,0));
		cubes.add(new Cube(zombieSkin,x+.04,y+.06,z-.915,.03,.03,.03,0,0,0));
		cubes.add(new Cube(zombieSkin,x+.07,y+.06,z-.915,.03,.03,.03,0,0,0));
		cubes.add(new Cube(zombieSkin,x+.1,y+.06,z-.915,.03,.03,.03,0,0,0));
		//cubes.add(new Cube(zombieSkin,x,y,z-1,.1,.15,.2,0,0,0));
		//cubes.add(new Cube(zombieSkin,x,y,z-1,.2,.2,.2,0,0,0));
		
		
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
	public void rotate(double yaw)
	{
		for(Shapes s: cubes)
		{
			double tempX = s.getCenter()[0];
			double tempY = s.getCenter()[1];
			double tempR = Math.sqrt(Math.pow(tempX-x, 2) + Math.pow(tempY-y, 2));
            double tempAngle = Math.toDegrees(Math.atan2(tempY - y, tempX - x));
            tempX = x + tempR * Math.cos(Math.toRadians(tempAngle + yaw - posH));
            tempY = y + tempR * Math.sin(Math.toRadians(tempAngle + yaw - posH));
            s.transform(tempX - s.getCenter()[0], tempY - s.getCenter()[1], 0);
			s.rotate(yaw-posH, 0, 0);
		}
		posH = yaw;
	}
	public double[] getCenter()
	{
		double[] center = {x,y,z};
		return center;
	}
}
