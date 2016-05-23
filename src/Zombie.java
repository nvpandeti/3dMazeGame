import java.awt.Color;
import java.util.ArrayList;

public class Zombie
{
	private double x,y,z,posH;
	private static Color[][][] zombieHead = new Color[10][10][10]; 
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Shapes> cubes;
	private ArrayList<Face> faces;
	private ZombieArm leftArm, rightArm;
	private ZombieLeg leftLeg, rightLeg;
	private static Color zombieSkin = new Color(83,167,122);
	private static Color zombieEye = new Color(133,41,46);
	private static Color zombieLid = new Color(63,240, 149);
	private static Color zombieHair = new Color(160,82,45);
	
	public Zombie(double x, double y, double z, boolean female)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		posH = 0;
		
		leftArm = new ZombieArm(x,y-.55,z+.55);
		rightArm = new ZombieArm(x, y+.55, z+.55);
		leftLeg = new ZombieLeg(x, y-.25, z-.35);
		rightLeg = new ZombieLeg(x, y+.25, z-.35);
		
		
		cubes = new ArrayList<Shapes>();
		
		cubes.add(new Cube(Color.blue, x, y, z+.2,.3,.9, .9,0,0,0));
		//cubes.add(new Cube(zombieSkin, x, y, z+.82,.3,.3, .3,0,0,0));
		
		double tempX = x-.15+.015;
		double tempY = y-.15+.015;
		double tempZ = z+.82-.15+.015;
		
		cubes.add(new Cube(zombieSkin, x+.045, y-.075, z+.82-.075, .03, .15, .15, 0, 0, 0));
		cubes.add(new Cube(zombieSkin, x+.045, y+.075, z+.82-.075, .03, .15, .15, 0, 0, 0));
		if(female)
		{
			cubes.add(new Cube(zombieHair, x+.045, y-.075, z+.82+.06, .03, .15, .12, 0, 0, 0));
			cubes.add(new Cube(zombieHair, x+.045, y+.075, z+.82+.06, .03, .15, .12, 0, 0, 0));
			cubes.add(new Sphere(zombieHair, x+.045, y, z+.82+.12,.07,10));
		}
		else
		{
			cubes.add(new Cube(zombieSkin, x+.045, y-.075, z+.82+.06, .03, .15, .12, 0, 0, 0));
			cubes.add(new Cube(zombieSkin, x+.045, y+.075, z+.82+.06, .03, .15, .12, 0, 0, 0));
		}
		
		
		for (int i = 0; i < zombieHead.length-4; i++) {
			for (int j = 0; j < zombieHead.length; j++) {
				for (int k = 1; k < zombieHead.length; k++) {
					if(zombieHead[i][j][k]!=null)
					{
						if(female && k==zombieHead.length-1)
							cubes.add(new Cube(zombieHair, tempX, tempY, tempZ, .03, .03, .03, 0,0,0));
						else
							cubes.add(new Cube(zombieHead[i][j][k], tempX, tempY, tempZ, .03, .03, .03, 0,0,0));
						if(i!=0 && k==zombieHead.length-1)
						{
							if(j==0)
							{
								ArrayList<Face> temp = cubes.get(cubes.size()-1).getFaces();
								temp.remove(0);
								temp.remove(0);
								temp.remove(0);
								temp.remove(2);
								
							}
							else if(k==zombieHead.length-1)
							{
								ArrayList<Face> temp = cubes.get(cubes.size()-1).getFaces();
								temp.remove(0);
								temp.remove(1);
								temp.remove(1);
								temp.remove(2);
							}
							else
							{
								ArrayList<Face> temp = cubes.get(cubes.size()-1).getFaces();
								temp.remove(0);
								temp.remove(0);
								temp.remove(0);
								temp.remove(0);
								temp.remove(1);
								
							}
						}
						else if(i!=0 && j==0)
						{
							ArrayList<Face> temp = cubes.get(cubes.size()-1).getFaces();
							temp.remove(0);
							temp.remove(0);
							temp.remove(0);
							temp.remove(1);
							temp.remove(1);
						}
						else if(i!=0 && j==zombieHead.length-1)
						{
							ArrayList<Face> temp = cubes.get(cubes.size()-1).getFaces();
							temp.remove(0);
							temp.remove(1);
							temp.remove(1);
							temp.remove(1);
							temp.remove(1);
						}
					}
					tempZ += .03;
				}
				tempZ = z+.82-.15+.015;
				tempY += .03;
			}
			tempY = y-.15+.015;
			tempX += .03;
		}
		
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
	
	public void move(double realX, double realY, double realZ)
	{
		double yaw = Math.toDegrees(Math.atan2(realY-y, realX-x))+180;
		rotate(yaw);
		
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
		double tempX = leftArm.getCenter()[0];
		double tempY = leftArm.getCenter()[1];
		double tempR = Math.sqrt(Math.pow(tempX-x, 2) + Math.pow(tempY-y, 2));
        double tempAngle = Math.toDegrees(Math.atan2(tempY - y, tempX - x));
        tempX = x + tempR * Math.cos(Math.toRadians(tempAngle + yaw - posH));
        tempY = y + tempR * Math.sin(Math.toRadians(tempAngle + yaw - posH));
        leftArm.transform(tempX - leftArm.getCenter()[0], tempY - leftArm.getCenter()[1], 0);
		leftArm.rotate(yaw);
		
		tempX = rightArm.getCenter()[0];
		tempY = rightArm.getCenter()[1];
		tempR = Math.sqrt(Math.pow(tempX-x, 2) + Math.pow(tempY-y, 2));
        tempAngle = Math.toDegrees(Math.atan2(tempY - y, tempX - x));
        tempX = x + tempR * Math.cos(Math.toRadians(tempAngle + yaw - posH));
        tempY = y + tempR * Math.sin(Math.toRadians(tempAngle + yaw - posH));
        rightArm.transform(tempX - rightArm.getCenter()[0], tempY - rightArm.getCenter()[1], 0);
		rightArm.rotate(yaw);
		
		tempX = leftLeg.getCenter()[0];
		tempY = leftLeg.getCenter()[1];
		tempR = Math.sqrt(Math.pow(tempX-x, 2) + Math.pow(tempY-y, 2));
        tempAngle = Math.toDegrees(Math.atan2(tempY - y, tempX - x));
        tempX = x + tempR * Math.cos(Math.toRadians(tempAngle + yaw - posH));
        tempY = y + tempR * Math.sin(Math.toRadians(tempAngle + yaw - posH));
        leftLeg.transform(tempX - leftLeg.getCenter()[0], tempY - leftLeg.getCenter()[1], 0);
		leftLeg.rotate(yaw);
		//leftLeg.rotateUp(1);
		
		tempX = rightLeg.getCenter()[0];
		tempY = rightLeg.getCenter()[1];
		tempR = Math.sqrt(Math.pow(tempX-x, 2) + Math.pow(tempY-y, 2));
        tempAngle = Math.toDegrees(Math.atan2(tempY - y, tempX - x));
        tempX = x + tempR * Math.cos(Math.toRadians(tempAngle + yaw - posH));
        tempY = y + tempR * Math.sin(Math.toRadians(tempAngle + yaw - posH));
        rightLeg.transform(tempX - rightLeg.getCenter()[0], tempY - rightLeg.getCenter()[1], 0);
		rightLeg.rotate(yaw);
		
		posH = yaw;
	}
	
	public static void loadZombieHead()
	{
		for (int i = 0; i < zombieHead.length; i++) {
			for (int j = 0; j < zombieHead.length; j++) {
				for (int k = 0; k < zombieHead.length; k++) {
					if(i==0 || j==0 || k==0 || i==zombieHead.length-1 || j==zombieHead.length-1 || k==zombieHead.length-1)
					{
						zombieHead[i][j][k] = zombieSkin;
					}
					
				}
				
			}
		}
		
		zombieHead[0][2][2] = null;
		zombieHead[0][3][2] = Color.white;
		zombieHead[0][4][2] = null;
		zombieHead[0][5][2] = null;
		zombieHead[0][6][2] = Color.white;
		zombieHead[0][7][2] = null;
		
		zombieHead[0][2][3] = null;
		zombieHead[0][3][3] = null;
		zombieHead[0][4][3] = null;
		zombieHead[0][5][3] = null;
		zombieHead[0][6][3] = null;
		zombieHead[0][7][3] = null;
		
		zombieHead[0][2][4] = null;
		zombieHead[0][3][4] = Color.white;
		zombieHead[0][4][4] = null;
		zombieHead[0][5][4] = null;
		zombieHead[0][6][4] = Color.white;
		zombieHead[0][7][4] = null;
		
		zombieHead[0][1][6] = zombieLid;
		zombieHead[0][2][6] = zombieLid;
		zombieHead[0][3][6] = zombieLid;
		zombieHead[0][1][7] = zombieLid;
		zombieHead[0][2][7] = null;
		zombieHead[0][3][7] = zombieLid;
		zombieHead[0][1][8] = zombieLid;
		zombieHead[0][2][8] = zombieLid;
		zombieHead[0][3][8] = zombieLid;
		
		zombieHead[0][6][6] = zombieLid;
		zombieHead[0][7][6] = zombieLid;
		zombieHead[0][8][6] = zombieLid;
		zombieHead[0][6][7] = zombieLid;
		zombieHead[0][7][7] = null;
		zombieHead[0][8][7] = zombieLid;
		zombieHead[0][6][8] = zombieLid;
		zombieHead[0][7][8] = zombieLid;
		zombieHead[0][8][8] = zombieLid;
		
		//zombie eue
		zombieHead[1][2][7] = zombieEye;
		zombieHead[1][7][7] = zombieEye;
		
		//zombie tongue
		zombieHead[1][4][2] = Color.RED;
		zombieHead[1][5][2] = Color.RED;
		zombieHead[2][4][2] = Color.RED;
		zombieHead[2][5][2] = Color.RED;
		
		
		//left side of mouth
		zombieHead[1][1][2] = Color.BLACK;
		zombieHead[1][1][3] = Color.BLACK;
		zombieHead[1][1][4] = Color.BLACK;
		zombieHead[2][1][2] = Color.BLACK;
		zombieHead[2][1][3] = Color.BLACK;
		zombieHead[2][1][4] = Color.BLACK;
		zombieHead[3][1][2] = Color.BLACK;
		zombieHead[3][1][3] = Color.BLACK;
		zombieHead[3][1][4] = Color.BLACK;
		
		//right side of mouth
		zombieHead[1][8][2] = Color.BLACK;
		zombieHead[1][8][3] = Color.BLACK;
		zombieHead[1][8][4] = Color.BLACK;
		zombieHead[2][8][2] = Color.BLACK;
		zombieHead[2][8][3] = Color.BLACK;
		zombieHead[2][8][4] = Color.BLACK;
		zombieHead[3][8][2] = Color.BLACK;
		zombieHead[3][8][3] = Color.BLACK;
		zombieHead[3][8][4] = Color.BLACK;
		
		for(int y=2;y<8;y++)
		{
			zombieHead[1][y][5] = Color.BLACK;
			zombieHead[2][y][5] = Color.BLACK;
			zombieHead[3][y][5] = Color.BLACK;
			zombieHead[4][y][4] = Color.BLACK;
			zombieHead[4][y][3] = Color.BLACK;
			zombieHead[4][y][2] = Color.BLACK;
			zombieHead[3][y][1] = Color.BLACK;
			zombieHead[2][y][1] = Color.BLACK;
			zombieHead[1][y][1] = Color.BLACK;
		}
		
		
	}
}