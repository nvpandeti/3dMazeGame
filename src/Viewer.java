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
public class Viewer extends JFrame implements ActionListener, KeyListener, Runnable //, MouseMotionListener
{
	public static ViewerPainter viewerPainter = new ViewerPainter();
	private Robot robot;
	private boolean[] keys;
	private double posH, posZ, realX, realY, realZ, r;
	private int mX, mY, tempX, tempY;
	private double[] origin, light;
	private Player player;
	private Maze maze;
	private Thread bkgMusic;
	/**
	 * A constructor for Viewer
	 * @param load The file you wish to view from
	 */
	public Viewer(File load)
	{
		setTitle("Sculpture Maker");
		try{
		setIconImage(ImageIO.read( getClass().getResourceAsStream("icon2.png")));
		}
		catch(IOException e){}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		//viewerPainter.addMouseMotionListener(this);
		//viewerPainter.load(load);
		add(viewerPainter, BorderLayout.CENTER);
		
		viewerPainter.addKeyListener(this);
		
		setVisible(true);
		
		//mX = mY = tempX = tempY = -1;
		keys = new boolean[10];
		posH = 45;
		posZ = 0;
		realX = 7.5;
		realY = 7.5;
		realZ = 1;
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
		
		viewerPainter.setReal(realX, realY, realZ);
		viewerPainter.setOrigin(origin);
		Face.setReal(realX, realY, realZ);
		player = new Player(realX, realY, realZ);
		maze = new Maze(11,11,5,4);
   	  	robot.mouseMove(getWidth()/2,getHeight()/2);
   	  	repaint();
   	  	new Thread(this).start();
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
   	  	player.move(keys, posH, posZ, maze.getHitbox());
   	  	double[] position = player.getPosition();
   	  	realX = position[0];
   	  	realY = position[1];
   	  	realZ = position[2];
   	  	
   	  	origin[0] = realX + r * Math.cos(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
    	origin[1] = realY + r * Math.sin(Math.toRadians(posH)) * Math.cos(Math.toRadians(posZ));
    	origin[2] = realZ + r * Math.sin(Math.toRadians(posZ));
    	
    	viewerPainter.setReal(realX, realY, realZ);
		Face.setReal(realX, realY, realZ);
		double[] light = {realX, realY, realZ};
		Face.setLight(light);
		
		long tempTime = System.currentTimeMillis();
   	  	viewerPainter.drawComponent(getGraphics());
   	  	long diff = System.currentTimeMillis() - tempTime;
		System.out.println(diff);
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
		}
		
		
	}

	/**
	 * Updates the keys array based on the key that was released
	 * @param e the KeyEvent representing the released key
	 */
	public void keyReleased(KeyEvent e)
	{
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
	
	
	
	public void run()
	{
		long tempTime = System.currentTimeMillis();
		try
		{
			while(true)
			{
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