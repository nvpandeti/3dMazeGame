import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class Zombie implements Hitboxable
{
	private double x,y,z,posH,speed, playerX, playerY, health;
	private int index;
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
		speed = .1;
		playerX = playerY = -1;
		health = 500;
		index = -1;
		
		leftArm = new ZombieArm(x,y-.55,z+.55);
		rightArm = new ZombieArm(x, y+.55, z+.55);
		leftLeg = new ZombieLeg(x, y-.25, z-.35);
		rightLeg = new ZombieLeg(x, y+.25, z-.35);
		
		
		cubes = new ArrayList<Shapes>();
		
		cubes.add(new Cube(Color.blue, x, y, z+.2,.3,.9, .9,0,0,0));
		ArrayList<Hitbox> tempHitbox = new ArrayList<Hitbox>();
		tempHitbox.add(new Hitbox(x,y,z-.45,1,1,2.1,this));
		((Cube)cubes.get(0)).setHitbox(tempHitbox);
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
		hitbox.add(((Cube)cubes.get(0)).getHitbox().get(0));
		hitbox.add(new Hitbox(x, y, z+.82,.3,.3, .3, this));
		faces = new ArrayList<Face>();
		for(Shapes c: cubes)
		{
			if(index == -1)
				index = Viewer.viewerPainter.addShape(c);
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
	
	public void changeHealth(double change)
	{
		health += change;
		if(health<0)
			health = 0;
	}
	public double getHealth()
	{
		return health;
	}
	
	public void dispose()
	{
		for (int i = index; i < index+cubes.size(); i++) {
			
			Viewer.viewerPainter.setShape(index, null);
		}
		System.out.println("Zombie Dispose");
		
	}
	
	public void move(double realX, double realY, double realZ, ArrayList<Hitbox> maze, Hitbox player, ArrayList<Zombie> zombies)
	{
		System.out.println(Arrays.toString(cubes.get(10).getCenter()));
		double yaw = Math.toDegrees(Math.atan2(realY-y, realX-x));
		Bullet visionProbe = new Bullet(x,y,z+.75,yaw,0);
		visionProbe.multiplySpeed(10);
		int tempCollision = visionProbe.move(maze, player);
		while(tempCollision==0)
		{
			tempCollision = visionProbe.move(maze, player);
		}
		visionProbe.dispose();
		if(tempCollision==2)
		{
			playerX = player.getPosition()[0];
			playerY = player.getPosition()[1];
			if((Math.pow(playerY-y, 2) + Math.pow(playerX-x, 2)) >.2)
			{
				transform(speed * Math.cos(Math.toRadians(yaw)), speed * Math.sin(Math.toRadians(yaw)), 0);
			}
			
		}
		if(tempCollision==1 && playerX!=-1 && playerY!=-1)
		{
			if(Math.pow(playerY-y, 2) + Math.pow(playerX-x, 2) >.2)
			{
				double playerAngle = Math.toDegrees(Math.atan2(playerY-y, playerX-x)); 
				transform(speed * Math.cos(Math.toRadians(playerAngle)), speed * Math.sin(Math.toRadians(playerAngle)), 0);
			}
			
		}
		rotate(yaw+180);
		
		for(int i=0; i<zombies.size(); i++)
  		{
			//System.out.println("Z collision");
			if(zombies.get(i) != this)
			{
				//System.out.println("Real collision");
				Hitbox m = zombies.get(i).getHitbox().get(0);
	  			
	  			if(hitbox.get(0).isColliding(m))
	  			{
  					if(Math.abs(x-m.getPosition()[0])>Math.abs(y-m.getPosition()[1]))
	  				{
	  					if(x-m.getPosition()[0]<0)
		  					hitbox.get(0).setPosition(m.getMinX()-.51, y, z-.55);
		  				else
		  					hitbox.get(0).setPosition(m.getMaxX()+.51, y, z-.55);
	  				}
	  				else// if(Math.abs(x-m.getPosition()[0])<Math.abs(y-m.getPosition()[1]))
	  				{
	  					if(y-m.getPosition()[1]<0)
		  					hitbox.get(0).setPosition(x, m.getMinY()-.51, z-.55);
		  				else
		  					hitbox.get(0).setPosition(x, m.getMaxY()+.51, z-.55);
	  				}
  				}
	  				
	  				
  				double[] temp = hitbox.get(0).getPosition();
  				transform(temp[0]-x, temp[1]-y, 0);
			}
  			
  				
  		}
		
		for(int i=0; i<maze.size(); i++)
  		{
  			Hitbox m = maze.get(i);
  			
  			if(hitbox.get(0).isColliding(m))
  			{
  				//collisions.add(m);
				if(Math.abs(x-m.getPosition()[0])>Math.abs(y-m.getPosition()[1]))
  				{
  					if(x-m.getPosition()[0]<0)
	  					hitbox.get(0).setPosition(m.getMinX()-.5, y, z);
	  				else
	  					hitbox.get(0).setPosition(m.getMaxX()+.5, y, z);
  				}
  				else// if(Math.abs(x-m.getPosition()[0])<Math.abs(y-m.getPosition()[1]))
  				{
  					if(y-m.getPosition()[1]<0)
	  					hitbox.get(0).setPosition(x, m.getMinY()-.5, z);
	  				else
	  					hitbox.get(0).setPosition(x, m.getMaxY()+.5, z);
  				}
	  				
	  				
  				double[] temp = hitbox.get(0).getPosition();
  		   	  	//x = temp[0];
  		   	  	//y = temp[1];
  		   	  	//z = temp[2];
  				transform(temp[0]-x, temp[1]-y, temp[2]-z);
	  				
  			}
  				
  		}
		
		if(hitbox.get(0).isColliding(player))
		{
			((Player)player.getReference()).changeHealth(-2);
			Viewer.viewerPainter.setRedCover(true);
			//System.out.println("Hitting");
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
		leftArm.transform(x, y, z);
		rightArm.transform(x, y, z);
		leftLeg.transform(x, y, z);
		rightLeg.transform(x, y, z);
		
		hitbox.get(1).transform(x, y, z);
		
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

	public ArrayList<Hitbox> getHitbox() 
	{
		return hitbox;
	}

	@Override
	public ArrayList<Face> getFaces() {
		return faces;
	}
}