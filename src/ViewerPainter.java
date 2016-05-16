import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.awt.geom.*;

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
	private Bitmap bitmap;
	
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
	public int addShape(Shapes s)
	{
		shapes.add(s);
		return shapes.size()-1;
	}
	public void setShape(int index, Shapes s)
	{
		shapes.set(index, s);
	}
	public void addShapes(ArrayList<Shapes> s)
	{
		int size = s.size();
		int index = 0;
		int counter = 0;
		for (int i = 0; i < shapes.size(); i++) {
			if(shapes.get(i)==null)
			{
				if(counter == 0)
					index = i;
				counter++;
			}
			else
			{
				counter = 0;
			}
			if(counter==size)
			{
				break;
			}
			
		}
		if(counter==size)
		{
			for(int i=index;i<index+size;i++)
			{
				shapes.set(i, s.get(1));
			}
		}
		else
		{
			for(Shapes p: s)
			{
				shapes.add(p);
			}
		}
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
		//Image img = createImage(getWidth(), getHeight());
		bitmap = new Bitmap(getHeight(), getWidth());
		BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = img.createGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,getWidth(), getHeight());
		//AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_ATOP);  
		//g.setComposite(ac);
		
		ArrayList<Face> faces = new ArrayList<Face>();
	    for(Shapes s: shapes)
	    {
	    	if(s!=null)
	    	{
	    		for(Face face: s.getFaces())
		    	{
		    		face.updateDistance();
		    		face.calculateShading();
		    		if(face.getOriginalShadeCoefficient() != 0)
			    	{
						faces.add(face);
			    	}  
		    	}
	    	}	
	    }
	        
	    Collections.sort(faces);
	    //Collections.reverse(faces);
	    double divide = faces.size()/4.0;
	    //System.out.print(divide);  
	    PainterHelper ph1 = new PainterHelper(faces.subList((int) divide, (int) (divide*2)), realX, realY, realZ, origin, getWidth(), getHeight()); 
	    PainterHelper ph2 = new PainterHelper(faces.subList((int) (divide*2), (int) (divide*3)), realX, realY, realZ, origin, getWidth(), getHeight());
	    PainterHelper ph3 = new PainterHelper(faces.subList((int) (divide*3), (int) (divide*4)), realX, realY, realZ, origin, getWidth(), getHeight());
	    Thread t1 = new Thread(ph1);
	    Thread t2 = new Thread(ph2);
	    Thread t3 = new Thread(ph3);
	    t1.start();
	    t2.start();
	    t3.start();
	    
	    double fov = Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2)) / 110;
	    HashMap<double[], double[]> calculatedCorners = new HashMap<double[], double[]>();
	    int counter = 0;
	    //for(Face face: faces.subList(Math.max(0, faces.size()-2300), faces.size()))
	    boolean startBlack = false;
	    //outerOuter:
	    //for(Face face:faces)  
	    for(Face face:faces.subList(0, (int)divide))
	    {
//	    	if(!startBlack)
//	    	{
//	    		
//	    		if(face.getShadeCoefficient()!=0)
//	    			startBlack = true;
//	    		else
//	    			continue outerOuter;
//	    	}
	    	
	    	//System.out.println(face.getDistance()+"   "+face.getShadeCoefficient()); 
	    	if(face.getDistance() > 20) 
	    	//if(face.getShadeCoefficient() == 0)
	    	{
	    		/*
	    		double[] corner = face.getCenter();
	    		double D = Math.toDegrees( Math.acos( ( (origin[0] - realX)*(corner[0] - realX) + (origin[1] - realY)*(corner[1] - realY) + (origin[2] - realZ)*(corner[2] - realZ) )
        					* invSqrt(Math.pow( origin[0] - realX, 2) + Math.pow( origin[1] - realY, 2) + Math.pow( origin[2] - realZ, 2)) * invSqrt(Math.pow( corner[0] - realX, 2) + Math.pow( corner[1] - realY, 2) + Math.pow( corner[2] - realZ, 2) ) ) );
	    		double t = - ( (origin[0] - realX)*(realX - corner[0]) + (origin[1] - realY)*(realY - corner[1]) + (origin[2] - realZ)*(realZ - corner[2]) ) / ( Math.pow(origin[0] - realX, 2) + Math.pow(origin[1] - realY, 2) + Math.pow(origin[2] - realZ, 2) );
                double[] vertex = {realX + (origin[0] - realX)*t, realY + (origin[1] - realY)*t, realZ + (origin[2] - realZ)*t};
                double[] perpendicular = {vertex[0] - (origin[1] - realY), vertex[1] + (origin[0] - realX), vertex[2]};
                double R = Math.acos( ( (perpendicular[0] - vertex[0])*(corner[0] - vertex[0]) + (perpendicular[1] - vertex[1])*(corner[1] - vertex[1]) + (perpendicular[2] - vertex[2])*(corner[2] - vertex[2]) ) 
            			* invSqrt(Math.pow( perpendicular[0] - vertex[0], 2) + Math.pow( perpendicular[1] - vertex[1], 2) + Math.pow( perpendicular[2] - vertex[2], 2) ) * invSqrt(Math.pow( corner[0] - vertex[0], 2) + Math.pow( corner[1] - vertex[1], 2) + Math.pow( corner[2] - vertex[2], 2) ) );
            
	            if(corner[2]<perpendicular[2])
	                R = 2*Math.PI - R;
	            double fov = Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2)) / 110;
	            int X = (int)Math.round(getWidth()/2.0+D*fov*Math.cos(R));
	            int Y = (int)Math.round(getHeight()/2.0-D*fov*Math.sin(R));
	            //System.out.print(X+" "+Y);
	            //System.out.println("   "+((BufferedImage)img).getRGB(X, Y));
	            ++counter;
	            if(X>=0 && X<getWidth() && Y>=0 && Y<getHeight() ) 
	            {
	            	//System.out.println("opt plz"); 
	            	
	            	//System.out.println("   "+((BufferedImage)img).getRGB(X, Y)); 
	            	if(((BufferedImage)img).getRGB(X, Y) == Color.black.getRGB())
	            	{
	            		//System.out.println("really"); 
	            		continue outerOuter;  
	            	}
	            		  
	            }
	            */
	            	
	    	}
	    	
			
	        //ArrayList<Double> angleD = new ArrayList<Double>();
	        //ArrayList<Double> angleR = new ArrayList<Double>();
			int[] pointsX = new int[face.getCorners().length];
	        int[] pointsY = new int[face.getCorners().length];
	        boolean behind = true;
	        
	        double[] angleD = new double[face.getCorners().length];
	        double[] angleR = new double[face.getCorners().length];
	        outer: 
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
	                if(behind && angleD[c]<55)
		                behind = false;
		            pointsX[c] = (int)Math.round(getWidth()/2.0+angleD[c]*fov*Math.cos(angleR[c]));
		            pointsY[c] = (int)Math.round(getHeight()/2.0-angleD[c]*fov*Math.sin(angleR[c]));
//		            if(face.getShadeCoefficient()==0 && pointsX[c]>0 && pointsX[c]<getWidth() && pointsY[c]>0 && pointsY[c]<getHeight() &&
//		            		((BufferedImage)img).getRGB(pointsX[c], pointsY[c])==0)
//		            {
//		            	System.out.println("Break");
//		            	break outer;
//		            }
		            	
	                double[] temp = {angleD[c], angleR[c]};
	                calculatedCorners.put(corner, temp);
	            }
	            else 
	            {
	                
	                angleD[c] = calculatedCorners.get(corner)[0];
	                angleR[c] = calculatedCorners.get(corner)[1];
	                if(behind && angleD[c]<55)
		                behind = false;
		            pointsX[c] = (int)Math.round(getWidth()/2.0+angleD[c]*fov*Math.cos(angleR[c]));
		            pointsY[c] = (int)Math.round(getHeight()/2.0-angleD[c]*fov*Math.sin(angleR[c]));
//		            if(face.getShadeCoefficient()==0 && pointsX[c]>0 && pointsX[c]<getWidth() && pointsY[c]>0 && pointsY[c]<getHeight() &&
//		            		((BufferedImage)img).getRGB(pointsX[c], pointsY[c])==0)
//		            	break outer;
	            }
	        }
	        
//	        for (int i = 0; i<pointsX.length; i++)
//	        {
//	            if(behind && angleD[i]<55)
//	                behind = false;
//	            pointsX[i] = (int)Math.round(getWidth()/2.0+angleD[i]*fov*Math.cos(angleR[i]));
//	            pointsY[i] = (int)Math.round(getHeight()/2.0-angleD[i]*fov*Math.sin(angleR[i]));
//	        }
		        
			
	        if(!behind)
	        {
	        	double x = 0;
				double y = 0;
				for(int c = 0; c < angleD.length; c++)
				{
					x+= pointsX[c];
					y+= pointsY[c];
					//if(face.getShadeCoefficient()==0 && pointsX[c]>0 && pointsX[c]<getWidth() && pointsY[c]>0 && pointsY[c]<getHeight() &&
		            //		((BufferedImage)img).getRGB(pointsX[c], pointsY[c])==0)
					//	continue outerOuter;
				} 
				//g.setComposite(ac); 
				
        		g.setColor(face.getShading());
        		g.fillPolygon(pointsX, pointsY, pointsX.length); 
        		//g.fill(new Polygon(pointsX, pointsY, pointsX.length));
        		//g2.drawImage(img, getX()+8, getY()+31, this); 
        		//bitmap.drawPolygon(pointsX, pointsY, face.getShading());
        		//g.setColor(Color.lightGray);
        		//g.drawPolyline(pointsX, pointsY, pointsX.length);
	        	
	        }
	    
		//System.out.println (getWidth()+ " " + getHeight());
		//System.out.println(g.getClipBounds().getX() +" "+g.getClipBounds().getY());
		//g.fillRect(0,0,(int)g.getClipBounds().getX(),(int)g.getClipBounds().getY());
		
	    }
	    //System.out.println("Faces: "+counter);
	    //g2 = g;
	    //System.out.println(((BufferedImage)img).getRGB(50, 50)); 
	    //System.out.println("    "+Color.black.getRGB());
	     
	    //bitmap.flip(g); 
	    //System.out.println("yay?");
	    
	    //g.setColor(Color.BLACK);
	    //g.fillRect(0,0,getWidth(), getHeight());
	    
	    while(t1.isAlive() || t2.isAlive() || t3.isAlive())
	    {
	    	//wait
	    	//System.out.print("1");  
	    }
	    //System.out.println();
	    ArrayList<Color> colors = new ArrayList<Color>();
	    colors.addAll(ph1.getColors());
	    colors.addAll(ph2.getColors());
	    colors.addAll(ph3.getColors());
	    ArrayList<Polygon> polys = new ArrayList<Polygon>();
	    polys.addAll(ph1.getPolygons());
	    polys.addAll(ph2.getPolygons());
	    polys.addAll(ph3.getPolygons());
	    for(int i = 0;i<colors.size();i++)
	    {
	    	g.setColor(colors.get(i));
	    	g.fillPolygon(polys.get(i));
	    }
	    
	    g.setColor(Color.white);
	    int midX = getWidth()/2;
	    int midY = getHeight()/2;
	    g.fillRect(midX-10, midY-2, 20, 4);
	    g.fillRect(midX-2, midY-10, 4, 20);
	    
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