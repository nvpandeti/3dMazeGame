/*
 * Nikhil Pandeti
 * Mrs. Gallatin
 * Period 2
 */
import java.awt.*;

import javax.swing.*;
import javax.imageio.*;

import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.awt.image.*;


/**
   A Frame for viewing the shapes sans the ability to change them
*/
public class Viewer extends JFrame implements ActionListener, KeyListener, Runnable, MouseListener //, MouseMotionListener
{
	public static ViewerPainter viewerPainter;// = new ViewerPainter();
	private Robot robot;
	private boolean[] keys;
	private boolean[] mouseKeys;
	private double posH, posZ, realX, realY, realZ, r;
	private int mX, mY, tempX, tempY, status;
	private double[] origin, light;
	private Player player;
	private Maze maze;
	private Thread bkgMusic;
	private ArrayList<Bullet> playerBullets;
	private int markerNum;
	private ArrayList<Integer> availableMarkers;
	private ArrayList<Zombie> zombies;
	private ArrayList<Marker> markers;
	private Thread GameLoopThread;
	
	/**
	 * A constructor for Viewer
	 * @param load The file you wish to view from
	 */
	public Viewer(File load, int level)
	{
		viewerPainter = new ViewerPainter();
		try {
			RandomAccessFile raf = new RandomAccessFile(getClass().getProtectionDomain().getClassLoader().getResource("").getPath()+"\\tunnelBetter.BMP", "r");
			System.out.println("FILE SIZE "+raf.length());
			for(int i=0;i<54;i++)
				raf.read();
			for(int i = 54; i<raf.length()-2;i++)
			{
				//System.out.print(raf.read()+", ");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		setTitle("Darkness");
		try{
		setIconImage(ImageIO.read( getClass().getResourceAsStream("icon2.png")));
		}
		catch(IOException e){}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		//setUndecorated(true);
		setExtendedState(MAXIMIZED_BOTH);
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		setCursor(blankCursor);
		
		try
		{
			robot = new Robot();
		}
		catch(AWTException e)
		{
			robot = null;
		}
		
		
		setLayout(new BorderLayout());
		addKeyListener(this);
		
		//viewerPainter = new ViewerPainter();
		viewerPainter.setFocusable(true);
		addMouseListener(this);
		//viewerPainter.addMouseMotionListener(this);
		//viewerPainter.load(load);
		add(viewerPainter, BorderLayout.CENTER);
		
		viewerPainter.addKeyListener(this);
		
		setVisible(true);
		
		//mX = mY = tempX = tempY = -1;
		status = 0;
		keys = new boolean[10];
		mouseKeys = new boolean[3];
		posH = 45;
		posZ = 0;
		//realX = 7.5;
		//realY = 7.5;
		realX = realY = 3; 
		realZ = 2;
		r = 10;
		origin = new double[3];
		origin[0] = 0;
		origin[1] = 0;
		origin[2] = 0;
		light = new double[3];
		light[0] = 0;
		light[1] = 0;
		light[2] = 10;
		Face.setLight(light);
		markerNum = -1;
		availableMarkers = new ArrayList<Integer>();
		for(int i = 1; i<10; i++)
		{
			availableMarkers.add(i);
		}
		
		markers = new ArrayList<Marker>();
		
		playerBullets = new ArrayList<Bullet>();
		
		viewerPainter.setReal(realX, realY, realZ);
		viewerPainter.setOrigin(origin);
		Face.setReal(realX, realY, realZ);
		player = new Player(realX, realY, realZ);
		maze = new Maze(11,11,5,4);
		Zombie.loadZombieHead();
		//for(int i=1;i<10;i++)
		//{
		//	double[] temp123 = {i,2,1};
		//	double[] temp1234 = {i+1,3,2}; 
		//	viewerPainter.addShape(new Marker (temp123,temp1234,i));   
		//}
		//new ZombieArm(6,2.2,1.75); 
		//new ZombieArm(6,3.3,1.75); 
		//new ZombieLeg(6,2.5,1.05);
		//new ZombieLeg(6,3.0,1.05);
		zombies = new ArrayList<Zombie>();
		zombies.add(new Zombie(9, 1.5, 1.25,false));
		zombies.add(new Zombie(9, 3, 1.25,true)); 
		
   	  	robot.mouseMove(getWidth()/2,getHeight()/2);
   	  	
   	  	repaint();
   	  	
   	  	GameLoopThread = new Thread(this);
   	  	GameLoopThread.start();
	}
	/**
	 * Processes any changes
	 */
	public void repaint()
	{
		if(bkgMusic==null || !bkgMusic.isAlive())
		{
			bkgMusic = new Thread(new Sound("HauntedHouseStory.wav", -5f));
			bkgMusic.start(); 
		}
		try
		{
			//tempX = e.getX();
			//tempY = e.getY();
			tempX = (int)viewerPainter.getMousePosition().getX();
			tempY = (int)viewerPainter.getMousePosition().getY()+23;
			//posH += (tempX - mX)/30.0;
			//posZ -= (tempY - mY)/30.0;
			posH += (tempX - getWidth()/2)/3.0;
			posZ -= (tempY - getHeight()/2)/3.0;
			//System.out.println (tempY +"  "+getHeight()/2);
			if(posH<0)
	  			posH = 359;
	  		if(posH>360)
	  			posH = 0;
	  		if(posZ>89)
	  			posZ = 89;
	  		if(posZ<-89)
	  			posZ = -89;
			
			//mX = tempX;
			//mY = tempY;
			
			robot.mouseMove(getWidth()/2,getHeight()/2);
		}
		catch(Exception e){}
		
		if(keys[0])
   	  	{
   	  		posH -= 6;
   	  		if(posH<0)
   	  			posH = 359;
   	  	}
   	  	if(keys[1])
   	  	{
   	  		posH += 6;
   	  		if(posH>360)
   	  			posH = 0;
   	  	}
   	  	if(keys[2] && posZ>-89)
   	  	{
   	  		posZ -= 2;
   	  		if(posZ<-89)
  				posZ = -89;
   	  	}
   	  	if(keys[3] && posZ<89)
   	  	{
   	  		posZ += 2;
   	  		if(posZ>89)
  				posZ = 89;
   	  	}
   	  	//maze.transform(.01, 0, 0);
   	  	player.move(keys, posH, posZ, maze.getHitbox());
   	  	if(player.getHealth() != 0)
   	  		player.changeHealth(.1);
   	  	else
   	  		status = 1;
   	  	double[] position = player.getPosition();
   	  	realX = position[0];
   	  	realY = position[1];
   	  	realZ = position[2];
   	  	
   	  	origin[0] = realX + r * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
    	origin[1] = realY + r * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
    	origin[2] = realZ + r * Math.sin(Math.toRadians(posZ));
    	
    	if(player.getHealth() != 0)
    	{
			if(mouseKeys[0])
			{
				playerBullets.add(new Bullet(realX, realY, realZ, posH, posZ));
				playerBullets.get(playerBullets.size()-1).multiplySpeed(3);
				mouseKeys[0] = false;
				//(new Thread(new Sound("kazoo.wav", -17f))).start();
				
			}
			if(mouseKeys[2])
			{
				mouseKeys[2] = false; 
				Bullet b = new Bullet(realX, realY, realZ, posH, posZ);
				while(!b.move(maze.getHitbox(), markers, zombies))
				{
					
				}
		    	b.dispose(); 
			}
			if(markerNum != -1 && availableMarkers.contains(markerNum))
			{
				availableMarkers.remove(new Integer(markerNum));
				MarkerMaker b = new MarkerMaker(realX, realY, realZ, posH, posZ, markerNum);
				while(!b.move(maze.getHitbox()))
				{
					
				}
		    	b.dispose();
		    	if(b.getMarker() != null)
		    		markers.add(b.getMarker());
		    	System.out.println("markers: "+markers.size());
		    	markerNum = -1;
			}
    	}
    	
    	//System.out.println(playerBullets.size());
    	bulletOuter:
    	for(int i = 0; i<playerBullets.size(); i++)
    	{
    		for (int j = 0; j < 5; j++) 
    		{
    			//System.out.print(i);
				if(playerBullets.get(i).move(maze.getHitbox(), markers, zombies))
	    		{
					
	    			playerBullets.get(i).dispose();
	    			playerBullets.remove(i);
	    			i--;
	    			continue bulletOuter;
	    		}
			}
    	}
    	
    	for(int i=0;i<zombies.size();i++)
    	{
    		zombies.get(i).move(realX, realY, realZ, maze.getHitbox(), player.getHitbox().get(0), zombies);
    	}
    	    	
    	viewerPainter.setReal(realX, realY, realZ);
		Face.setReal(realX, realY, realZ);
		double[] light = {realX, realY, realZ};
		Face.setLight(light);
		
		long tempTime = System.currentTimeMillis();
   	  	viewerPainter.drawComponent(getGraphics(), player);
   	  	long diff = System.currentTimeMillis() - tempTime;
		//System.out.println(diff); 
	}
	/**
	 * Processes button clicks
	 * @param e An ActionEvent
	 */
	public void actionPerformed(ActionEvent e)
   	{
   		
   	}
   	/**
	 * Updates the keys array based on the key that was pressed
	 * @param e the KeyEvent representing the pressed key
	 */
   	public void keyPressed(KeyEvent e)
	{
		//System.out.println ("blahhhhhhhh");
		/*
		if (e.getKeyCode() == KeyEvent.VK_LEFT && !keys[0])
		{
			//keys[0] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !keys[1])
		{
			//keys[1] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && !keys[2])
		{
			//keys[2] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && !keys[3])
		{
			//keys[3] = true;
			//repaint();
		}
		*/
		if (e.getKeyCode() == KeyEvent.VK_W && !keys[4])
		{
			keys[4] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_S && !keys[5])
		{
			keys[5] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_A && !keys[6])
		{
			keys[6] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_D && !keys[7])
		{
			keys[7] = true;
			//repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_Q)
		{
			//keys[8] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_2)
		{
			//keys[9] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
			//GameLoopThread.interrupt();
			status = 1;
			//bkgMusic.stop();
			//dispose();
		}
		
		
	}

	/**
	 * Updates the keys array based on the key that was released
	 * @param e the KeyEvent representing the released key
	 */
	public void keyReleased(KeyEvent e)
	{
		//System.out.println(); 
		if(Character.isDigit(e.getKeyChar()) && e.getKeyChar()!='0')
		{
			markerNum = e.getKeyChar()-48;
			
		}
		else
		{
			markerNum = -1;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			keys[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			keys[1] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			keys[2] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			keys[3] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_W)
		{
			keys[4] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S)
		{
			keys[5] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			keys[6] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			keys[7] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_Q)
		{
			keys[8] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_2)
		{
			keys[9] = false;
		}
	}
	
	/**
	 * Needed to satisfy the KeyListener
	 * @param e not used
	 */
	public void keyTyped(KeyEvent e)
	{
      //no code needed here
	}
	
	
	/**
	 * Resets the mouse position
	 * @param e not used
	 */
	public void mouseMoved(MouseEvent e)
	{
		/*
		//tempX = e.getX();
		//tempY = e.getY();
		tempX = (int)viewerPainter.getMousePosition().getX();
		tempY = (int)viewerPainter.getMousePosition().getY()+23;
		//posH += (tempX - mX)/30.0;
		//posZ -= (tempY - mY)/30.0;
		posH += (tempX - getWidth()/2)/3.0;
		posZ -= (tempY - getHeight()/2)/3.0;
		System.out.println (tempY +"  "+getHeight()/2);
		if(posH<0)
  			posH = 359;
  		if(posH>360)
  			posH = 0;
  		if(posZ>89)
  			posZ = 89;
  		if(posZ<-89)
  			posZ = -89;
		
		//mX = tempX;
		//mY = tempY;
		repaint();
		
		robot.mouseMove(getWidth()/2,getHeight()/2);
		*/
		//repaint();
	}
	/**
	 * Processes mouse drags
	 * @param e MouseEvent representing a mouse drag
	 */
	public void mouseDragged(MouseEvent e)
	{
			
		//System.out.println (e.getX() +" "+ e.getY());
		//repaint();
	}
	
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseKeys[e.getButton()-1] = true;
		System.out.println("Mouse: "+e.getButton());
	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseKeys[e.getButton()-1] = false;
	}
	
	public int getStatus()
	{
		return status;
	}
	public void run()
	{
		long tempTime = System.currentTimeMillis();
		try
		{
			while(true)
			{
				if(Thread.currentThread().isInterrupted())
					break;
				long diff = System.currentTimeMillis() - tempTime;
				System.out.println("    "+diff); 
				
				//Thread.currentThread().sleep(Math.max(0, 130 - diff));
				//System.out.println("Yay");
				tempTime = System.currentTimeMillis();
				repaint();
			}
		}
		catch(Exception e)
		{
			
		}
	}
	
}