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
	private ViewerPainter viewerPainter;
	private Robot robot;
	private boolean[] keys;
	private double posH, posZ, realX, realY, realZ, r;
	private int mX, mY, tempX, tempY;
	private double[] origin, light;
	private Player player;
	private ArrayList<Shapes> maze;
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
		
		viewerPainter = new ViewerPainter();
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
		realX = 2.5;
		realY = 2.5;
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
		viewerPainter.drawMaze(11,11,5,4);
   	  	robot.mouseMove(getWidth()/2,getHeight()/2);
   	  	repaint();
   	  	new Thread(this).start();
	}
	/**
	 * Processes any changes
	 */
	public void repaint()
	{
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
   	  	player.move(keys, posH, posZ);
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
	
	/**
 	 * A component that draws the shapes
 	 */
	private class ViewerPainter extends JComponent
	{
		
		private ArrayList<Shapes> shapes;
		private Color color;
		private double[] origin;
		private double realX, realY, realZ;
		private int rows,cols;
		private boolean[][][] maze;
		private Random r;
		/**
		 * A default constructor for ViewerPainter
		 */
		public ViewerPainter()
		{
			//setPreferredSize(new Dimension(400,500));
			shapes = new ArrayList<Shapes>();
			color = Color.RED;
			origin = new double[3];
			realX = -2;
			realY = -6;
			realZ = 6.5;
			r = new Random();
			maze = new boolean[1][1][1];
		}
		/**
		 * Sets the origin coordinates
		 * @param origin an array of coordinates (x,y,z)
		 */
		public void setOrigin(double[] origin)
		{
			this.origin = origin;
		}
		/**
		 * Sets the real position coordinates
		 * @param x X
		 * @param y Y
		 * @param z Z
		 */
		public void setReal(double x,double y, double z)
		{
			realX = x;
			realY = y;
			realZ = z;
		}
		/**
		 * Adds a permanent shape
		 * @param s A shape that implements Shapes
		 */
		public void addShape(Shapes s)
		{
			shapes.add(s);
		}
		/**
		 * Draws the shapes. It also double buffers
		 * @param g2 the graphics
		 * @param colorB true if you want to draw colors
		 * @param shadingB true if you want to shade colors
		 * @param wireB true if you want to draw wire frame
		 */
		public void drawComponent(Graphics g2)
		{
			//super.paintComponent(g);
			Image img = createImage(getWidth(), getHeight());
			
			Graphics g = img.getGraphics();
			
			g.setColor(Color.BLACK);
			g.fillRect(0,0,getWidth(), getHeight());
			
			ArrayList<Face> faces = new ArrayList<Face>();
		    for(Shapes s: shapes)
		    {
		    	for(Face face: s.getFaces())
		    	{
		    		face.updateDistance();
		    		face.calculateShading();
		    		if(face.getShadeCoefficient() != 0)
			    	{
						faces.add(face);
			    	}
		    		
		    		
		    	}
		    }
		        
		    Collections.sort(faces);
		    
		    HashMap<double[], double[]> calculatedCorners = new HashMap<double[], double[]>();
		    int counter = 0;
		    for(Face face: faces.subList(Math.max(0, faces.size()-2300), faces.size()))
		    //for(Face face:faces)
		    {
		    	
		    	
				++counter;
		        //ArrayList<Double> angleD = new ArrayList<Double>();
		        //ArrayList<Double> angleR = new ArrayList<Double>();
		        double[] angleD = new double[face.getCorners().length];
		        double[] angleR = new double[face.getCorners().length];
		        for(int c = 0; c < angleD.length; c++)
		        {
		        	double[] corner = face.getCorners()[c];
		            if(!calculatedCorners.containsKey(corner))
		            {
		                angleD[c] =  Math.toDegrees( Math.acos( ( (origin[0] - realX)*(corner[0] - realX) + (origin[1] - realY)*(corner[1] - realY) + (origin[2] - realZ)*(corner[2] - realZ) ) 
		                                                        / ( Math.sqrt( Math.pow( origin[0] - realX, 2) + Math.pow( origin[1] - realY, 2) + Math.pow( origin[2] - realZ, 2) ) *  Math.sqrt( Math.pow( corner[0] - realX, 2) + Math.pow( corner[1] - realY, 2) + Math.pow( corner[2] - realZ, 2) ) ) ) );
		                
		                double t = - ( (origin[0] - realX)*(realX - corner[0]) + (origin[1] - realY)*(realY - corner[1]) + (origin[2] - realZ)*(realZ - corner[2]) ) / ( Math.pow(origin[0] - realX, 2) + Math.pow(origin[1] - realY, 2) + Math.pow(origin[2] - realZ, 2) );
		                double[] vertex = {realX + (origin[0] - realX)*t, realY + (origin[1] - realY)*t, realZ + (origin[2] - realZ)*t};
		                double[] perpendicular = {vertex[0] - (origin[1] - realY), vertex[1] + (origin[0] - realX), vertex[2]};
		                double R = Math.acos( ( (perpendicular[0] - vertex[0])*(corner[0] - vertex[0]) + (perpendicular[1] - vertex[1])*(corner[1] - vertex[1]) + (perpendicular[2] - vertex[2])*(corner[2] - vertex[2]) ) 
		                               / ( Math.sqrt( Math.pow( perpendicular[0] - vertex[0], 2) + Math.pow( perpendicular[1] - vertex[1], 2) + Math.pow( perpendicular[2] - vertex[2], 2) ) *  Math.sqrt( Math.pow( corner[0] - vertex[0], 2) + Math.pow( corner[1] - vertex[1], 2) + Math.pow( corner[2] - vertex[2], 2) ) ) );
		                if(corner[2]<perpendicular[2])
		                    angleR[c] = 2*Math.PI - R;
		                else
		                    angleR[c] = R;
		                double[] temp = {angleD[c], angleR[c]};
		                calculatedCorners.put(corner, temp);
		            }
		            else
		            {
		                
		                angleD[c] = calculatedCorners.get(corner)[0];
		                angleR[c] = calculatedCorners.get(corner)[1];
		            }
		        }
		        int[] pointsX = new int[face.getCorners().length];
		        int[] pointsY = new int[face.getCorners().length];
		        boolean behind = true;
		        for (int i = 0; i<pointsX.length; i++)
		        {
		            if(behind && angleD[i]<50)
		                behind = false;
		            pointsX[i] = (int)Math.round(getWidth()/2.0+angleD[i]*15*Math.cos(angleR[i]));
		            pointsY[i] = (int)Math.round(getHeight()/2.0-angleD[i]*15*Math.sin(angleR[i]));
		        }
		        
		        if(!behind)
		        {
	        		g.setColor(face.getShading());
	        		g.fillPolygon(pointsX, pointsY, pointsX.length);
	        		
	        		//g.setColor(Color.lightGray);
	        		//g.drawPolyline(pointsX, pointsY, pointsX.length);
		        	
		        }
		        
			//System.out.println (getWidth()+ " " + getHeight());
			//System.out.println(g.getClipBounds().getX() +" "+g.getClipBounds().getY());
			//g.fillRect(0,0,(int)g.getClipBounds().getX(),(int)g.getClipBounds().getY());
			
		    }
		    //System.out.println(counter);
		    //g2 = g;
		    g.dispose();
		    g2.drawImage(img, getX()+8, getY()+31, this);
		}
		/**
		 * Loads the shapes from a specified file.
		 * @param f the specified file
		 */
		public void load(File f)
		{
			if(f != null)
			{
				try
				{
					Scanner in = new Scanner(f);
					while(in.hasNextLine())
					{
						String s = in.next();
						if(s.equals("Cube"))
						{
							shapes.add( new Cube(new Color(in.nextInt(), in.nextInt(), in.nextInt()), 
											in.nextDouble(),
											in.nextDouble(),
											in.nextDouble(),
											in.nextDouble(),
											in.nextDouble(),
											in.nextDouble(),
											in.nextDouble(),
											in.nextDouble(),
											in.nextDouble()));
						}
						else if(s.equals("Sphere"))
						{
							shapes.add( new Sphere(new Color(in.nextInt(), in.nextInt(), in.nextInt()), 
												in.nextDouble(),
												in.nextDouble(),
												in.nextDouble(),
												in.nextDouble(),
												in.nextInt()));
						}
						else if(s.equals("Cylinder"))
						{
							shapes.add( new Cylinder(new Color(in.nextInt(), in.nextInt(), in.nextInt()),
												in.nextDouble(),
												in.nextDouble(),
												in.nextDouble(),
												in.nextDouble(),
												in.nextDouble(),
												in.nextInt(),
												in.nextDouble(),
												in.nextDouble(),
												in.nextDouble()));
																	
						}
						else
						{
							shapes.add( new Torus(new Color(in.nextInt(), in.nextInt(), in.nextInt()),
											in.nextDouble(),
											in.nextDouble(),
											in.nextDouble(),
											in.nextDouble(),
											in.nextDouble(),
											in.nextInt(),
											in.nextDouble(),
											in.nextDouble(),
											in.nextDouble()));
						}
					}
				}
				catch(Exception e)
				{
					
				}
			}
		
		}
		public void drawMaze(int rows, int cols, int width, int height)
		{
			shapes.clear();
			this.rows = rows;
			this.cols = cols;
			maze = new boolean[rows][cols][5];
			for (int i = 0; i<rows; i++)
				for (int j = 0; j<cols; j++)
					for (int k = 1; k<5; k++)
						maze[i][j][k] = true;
			
			int X = rows/2;
			int Y = cols/2;
			Stack<int[]> stack = new Stack<int[]>();
			int[] temp = {0,0};
			stack.push(temp);
			while(stack.size() != 0)
			{
				int[] peek = stack.peek();
				int direction = goTo(peek[0], peek[1]);
				
				if(direction==0)
					stack.pop();
				else
				{
					maze[peek[0]][peek[1]][direction] = false;
					if(direction==1)
					{
						peek[0] -=1;
						maze[peek[0]][peek[1]][3] = false;
					}
					else if(direction==2)
					{
						peek[1] +=1;
						maze[peek[0]][peek[1]][4] = false;
					}
					else if(direction==3)
					{
						peek[0] +=1;
						maze[peek[0]][peek[1]][1] = false;
					}
					else if(direction==4)
					{
						peek[1] -=1;
						maze[peek[0]][peek[1]][2] = false;
					}
					temp = new int[2];
					temp[0] = peek[0];
					temp[1] = peek[1];
					stack.push(temp);
				}
			}
			
			//maze[0][0][4] = false;
			maze[maze.length-1][maze[0].length-1][2] = false;
			/*
			for(int i = 0; i<rows*width+1; i++)
				for(int j = 0; j<cols*width+1; j++)
					shapes.add(new Cube(Color.blue, j, i, -1, 1,1,1,0,0,0));
			*/
			shapes.add(new Plane(Color.blue, -.5,-.5,-.51,rows*width+1, cols*width+1, Math.max(rows*width+1, cols*width+1), 0, 0, 0, false));
			shapes.add(new Plane(new Color(127, 0, 255), -.5,-.5,height-.49,rows*width+1, cols*width+1, Math.max(rows*width+1, cols*width+1), 0, 0, 0, true));
			shapes.add(new Plane(Color.yellow, rows*width+.5,(cols-1)*width+.5,-.51,width-1, width-1, width-1, 0, 0, 0, false));
			Color wallColor = new Color(139,69,19);
			for (int i = 0; i<rows+1; i++)
				for (int j = 0; j<cols+1; j++)
					for (int h = 0; h<height; h++)
						shapes.add(new Cube(wallColor, j*width, i*width, h,1,1,1,0,0,0));
						
			for (int i = 0; i<rows; i++)
			{
				for (int j = 0; j<cols; j++)
				{
					if(maze[i][j][1])
						for (int h = 0; h<height; h++)
							for (int w = 1; w<width; w++)
								shapes.add(new Cube(wallColor, j*width+w, i*width, h,1,1,1,0,0,0));
					if(maze[i][j][4])
						for (int h = 0; h<height; h++)
							for (int w = 1; w<width; w++)
								shapes.add(new Cube(wallColor, j*width, i*width+w, h,1,1,1,0,0,0));
								
					if(i==rows-1 || j==cols-1)
					{
						if(maze[i][j][2])
							for (int h = 0; h<height; h++)
								for (int w = 1; w<width; w++)
									shapes.add(new Cube(wallColor, j*width+width, i*width+w, h,1,1,1,0,0,0));
						if(maze[i][j][3])
							for (int h = 0; h<height; h++)
								for (int w = 1; w<width; w++)
									shapes.add(new Cube(wallColor, j*width+w, i*width+width, h,1,1,1,0,0,0));
					}
				}
			}
		}
		private boolean inBounds(int a, int b)
		{
			return a>=0 && b>=0 && a<rows && b<cols;
		}
		private int goTo(int a, int b)
		{
			ArrayList<Integer> possible = new ArrayList<Integer>();
			
			if(inBounds(a-1,b) && !maze[a-1][b][0])
				possible.add(1);
			if(inBounds(a,b+1) && !maze[a][b+1][0])
				possible.add(2);
			if(inBounds(a+1,b) && !maze[a+1][b][0])
				possible.add(3);
			if(inBounds(a,b-1) && !maze[a][b-1][0])
				possible.add(4);
				
			maze[a][b][0] = true;
			if(possible.isEmpty())
				return 0;
			
			return possible.get(r.nextInt(possible.size()));
		}
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