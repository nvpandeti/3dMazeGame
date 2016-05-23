import java.awt.Color;
import java.util.ArrayList;


public class ZombieLeg
{
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Shapes> cubes;
	private ArrayList<Face> faces;
	private double x,y,z,posH,posZ;
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
	public double[] getCenter()
	{
		double[] center = {x,y,z};
		return center;
	}
}
