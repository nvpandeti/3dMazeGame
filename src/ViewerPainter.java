import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.*;

import javax.swing.JComponent;

/**
 * A component that draws the shapes
 */
@SuppressWarnings("serial")
public class ViewerPainter extends JComponent
{
	
	private ArrayList<Shapes> shapes;
	private double[] origin;
	private double realX, realY, realZ;
	
	/**
	 * A default constructor for ViewerPainter
	 */
	public ViewerPainter()
	{
		//setPreferredSize(new Dimension(400,500));
		shapes = new ArrayList<Shapes>();
		origin = new double[3];
		realX = -2;
		realY = -6;
		realZ = 6.5;
		
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
	    //for(Face face: faces.subList(Math.max(0, faces.size()-2300), faces.size()))
	    for(Face face:faces)
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
	                //angleD[c] =  Math.toDegrees( Math.acos( ( (origin[0] - realX)*(corner[0] - realX) + (origin[1] - realY)*(corner[1] - realY) + (origin[2] - realZ)*(corner[2] - realZ) ) 
	                //                                        / ( Math.sqrt( Math.pow( origin[0] - realX, 2) + Math.pow( origin[1] - realY, 2) + Math.pow( origin[2] - realZ, 2) ) *  Math.sqrt( Math.pow( corner[0] - realX, 2) + Math.pow( corner[1] - realY, 2) + Math.pow( corner[2] - realZ, 2) ) ) ) );
	            	angleD[c] =  Math.toDegrees( Math.acos( ( (origin[0] - realX)*(corner[0] - realX) + (origin[1] - realY)*(corner[1] - realY) + (origin[2] - realZ)*(corner[2] - realZ) )
	            				* invSqrt(Math.pow( origin[0] - realX, 2) + Math.pow( origin[1] - realY, 2) + Math.pow( origin[2] - realZ, 2)) * invSqrt(Math.pow( corner[0] - realX, 2) + Math.pow( corner[1] - realY, 2) + Math.pow( corner[2] - realZ, 2) ) ) );
	            	
	            	
	                double t = - ( (origin[0] - realX)*(realX - corner[0]) + (origin[1] - realY)*(realY - corner[1]) + (origin[2] - realZ)*(realZ - corner[2]) ) / ( Math.pow(origin[0] - realX, 2) + Math.pow(origin[1] - realY, 2) + Math.pow(origin[2] - realZ, 2) );
	                double[] vertex = {realX + (origin[0] - realX)*t, realY + (origin[1] - realY)*t, realZ + (origin[2] - realZ)*t};
	                double[] perpendicular = {vertex[0] - (origin[1] - realY), vertex[1] + (origin[0] - realX), vertex[2]};
	                //double R = Math.acos( ( (perpendicular[0] - vertex[0])*(corner[0] - vertex[0]) + (perpendicular[1] - vertex[1])*(corner[1] - vertex[1]) + (perpendicular[2] - vertex[2])*(corner[2] - vertex[2]) ) 
	                //               / ( Math.sqrt( Math.pow( perpendicular[0] - vertex[0], 2) + Math.pow( perpendicular[1] - vertex[1], 2) + Math.pow( perpendicular[2] - vertex[2], 2) ) *  Math.sqrt( Math.pow( corner[0] - vertex[0], 2) + Math.pow( corner[1] - vertex[1], 2) + Math.pow( corner[2] - vertex[2], 2) ) ) );
	                double R = Math.acos( ( (perpendicular[0] - vertex[0])*(corner[0] - vertex[0]) + (perpendicular[1] - vertex[1])*(corner[1] - vertex[1]) + (perpendicular[2] - vertex[2])*(corner[2] - vertex[2]) ) 
	                			* invSqrt(Math.pow( perpendicular[0] - vertex[0], 2) + Math.pow( perpendicular[1] - vertex[1], 2) + Math.pow( perpendicular[2] - vertex[2], 2) ) * invSqrt(Math.pow( corner[0] - vertex[0], 2) + Math.pow( corner[1] - vertex[1], 2) + Math.pow( corner[2] - vertex[2], 2) ) );
	                
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
	        double fov = Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2)) / 110;
	        for (int i = 0; i<pointsX.length; i++)
	        {
	            if(behind && angleD[i]<55)
	                behind = false;
	            pointsX[i] = (int)Math.round(getWidth()/2.0+angleD[i]*fov*Math.cos(angleR[i]));
	            pointsY[i] = (int)Math.round(getHeight()/2.0-angleD[i]*fov*Math.sin(angleR[i]));
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
	    System.out.println("Faces: "+counter);
	    //g2 = g;
	    g.dispose();
	    g2.drawImage(img, getX()+8, getY()+31, this);
	}
	public static double invSqrt(double x) 
	{
	    double xhalf = 0.5d*x;
	    long i = Double.doubleToLongBits(x);
	    i = 0x5fe6ec85e7de30daL - (i>>1);
	    x = Double.longBitsToDouble(i);
	    x = x*(1.5d - xhalf*x*x);
	    x = x*(1.5d - xhalf*x*x);
	    //x = x*(1.5d - xhalf*x*x);
	    //x = x*(1.5d - xhalf*x*x);
	    //x = x*(1.5d - xhalf*x*x);
	    return x;
	}
	/**
	 * Loads the shapes from a specified file.
	 * @param f the specified file
	 */
	@SuppressWarnings("resource")
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
	
}