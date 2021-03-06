/*
 * Nikhil Pandeti
 * Mrs. Gallatin
 * Period 2
 */
import java.awt.Color;
import java.util.ArrayList;

/**
 * A class for ZombieLegs
 */
public class ZombieLeg
{
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Shapes> cubes;
	private ArrayList<Face> faces;
	private double x,y,z,posH,posZ;
	private int index;
	/**
	 * 
	 * @param x x
	 * @param y y
	 * @param z z
	 */
	public ZombieLeg(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		posH = 0;
		posZ = 90;
		
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
	 * Removes zombie leg
	 */
	public void dispose()
	{
		for (int i = index; i < index+cubes.size(); i++) {
			
			Viewer.viewerPainter.setShape(i, null);
		}
		System.out.println("Zombie Dispose");
		
	}
	/**
	 * Changes the position of the zombie leg
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
	 * Rotates the zomie leg
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
	 * Not used
	 * @param posH horizontal angle
	 * @param angle vertical angle
	 */
	public void rotateUp(double angle)
	{
		for(Shapes s: cubes)
		{
			double tempX = s.getCenter()[0];
			double tempY = s.getCenter()[1];
			double tempZ = s.getCenter()[2];
			double tempH = Math.sqrt(Math.pow(tempX-x, 2) + Math.pow(tempY-y, 2));
			double tempAngleH = Math.toDegrees(Math.atan2(tempY - y, tempX - x));
			double tempR = Math.sqrt(Math.pow(tempH, 2) + Math.pow(tempZ-z, 2));
            double tempAngle = Math.toDegrees(Math.atan2(tempZ - z, tempH));
            
            tempX = x + tempR * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(tempAngle - angle - posZ));
        	tempY = y + tempR * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(tempAngle - angle - posZ));
        	tempZ = z + tempR * Math.sin(Math.toRadians(tempAngle - angle - posZ));
        	
            s.transform(tempX - s.getCenter()[0], tempY - s.getCenter()[1], tempZ - s.getCenter()[2]);
			//((Cube)s).rotateUp(posH, angle-posZ);
		}
		posZ = angle;
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
