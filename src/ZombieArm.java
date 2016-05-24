/*
 * Nikhil Pandeti
 * Mrs. Gallatin
 * Period 2
 */
import java.awt.Color;
import java.util.ArrayList;

/**
 * A class for ZombieArms
 */
public class ZombieArm
{
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Shapes> cubes;
	private ArrayList<Face> faces;
	private double x,y,z,posH;
	private int index;
	/**
	 * 
	 * @param x x
	 * @param y y
	 * @param z z
	 */
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
		index = -1;
		for(Shapes c: cubes)
		{
			if(index == -1)
				index = Viewer.viewerPainter.addShape(c);
			else
				Viewer.viewerPainter.addShape(c);
			/*
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
			*/
		}
	}
	/**
	 * Removes zombie arm
	 */
	public void dispose()
	{
		for (int i = index; i < index+cubes.size(); i++) {
			
			Viewer.viewerPainter.setShape(i, null);
		}
		System.out.println("Zombie Dispose");
		
	}
	/**
	 * Changes the position of the zombie arm
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
	 * Rotates the zombie arm
	 * @param yaw Rotation in the xy plane
	 */
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
	/**
	 * Returns center coordinates
	 * @return center coordinates
	 */
	public double[] getCenter()
	{
		double[] center = {x,y,z};
		return center;
	}
}
