import java.awt.Color;
import java.awt.Graphics;
import java.util.*;
public class Bitmap 
{
	private Color[][] current, previous;
	private int width, height;
	public Bitmap(int h, int w)
	{
		width = w;
		height = h;
		
		previous= new Color[height][width];
		current = new Color[height][width];
	}
	
	public void drawPolygon(int[] xPoints, int[] yPoints, Color color)
	{
		MyLine[] lines = new MyLine[xPoints.length];
		for (int i = 0; i < lines.length-1; i++) 
		{
			lines[i] = new MyLine(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]);
		}
		lines[lines.length-1] = new MyLine(xPoints[lines.length-1], yPoints[lines.length-1], xPoints[0], yPoints[0]);
		
		int min = yPoints[0];
		int max = yPoints[0];
		for (int i = 0; i < lines.length; i++) 
		{
			if(yPoints[i]<min)
				min = yPoints[i];
			else if(yPoints[i]>max)
				max = yPoints[i];
		}
		for(int y=min;y<=max;y++)
		{
			int counter = 0;
			int[] intersects = new int[2];
			for(int i=0;i<lines.length && counter<2;i++)
			{
				Integer temp = lines[i].getIntersection(y);
				if(temp!=null)
				{
					intersects[counter] = temp;
					counter++;
				}
				
			}
			if(intersects[0]>intersects[1])
			{
				int temp = intersects[0];
				intersects[0] = intersects[1];
				intersects[1] = temp;
			}
			//System.out.println(Arrays.toString(intersects));
			for(int x = intersects[0];x<=intersects[1];x++)
			{
				if(inBounds(x, y) && current[y][x]==null)
				{
					current[y][x] = color;
				}
			}
		}
		/*
		for(int i=1;i<xPoints.length-1;i++)
		{
			int scanStart = Math.min(yPoints[0], Math.min(yPoints[i], yPoints[i+1]));
			int scanEnd = Math.max(yPoints[0], Math.max(yPoints[i], yPoints[i+1]));
			double m01 = calculateSlope(xPoints[0], yPoints[0], xPoints[i], yPoints[i]);
			double m02 = calculateSlope(xPoints[0], yPoints[0], xPoints[i+1], yPoints[i+1]);
			double m12 = calculateSlope(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]);
			for(int y=scanStart; y<=scanEnd;y++)
			{
				ArrayList<Integer> intersects = new ArrayList<Integer>();
				
				if(m01 != Double.MAX_VALUE)
				{
					int temp = (int)(m01*(y-yPoints[0]) + xPoints[0]);
					if((temp<=xPoints[i] && temp>=xPoints[0]) || (temp>=xPoints[i] && temp<=xPoints[0]))
						intersects.add( temp);
					
				}
				else
				{
					intersects.add(xPoints[0]);
					intersects.add(xPoints[i]);
				}
				if(m02 != Double.MAX_VALUE)
				{
					int temp = (int)(m02*(y-yPoints[0]) + xPoints[0]);
					if((temp<=xPoints[i+1] && temp>=xPoints[0]) || (temp>=xPoints[i+1] && temp<=xPoints[0]))
						intersects.add( temp);
					
				}
				else
				{
					intersects.add(xPoints[0]);
					intersects.add(xPoints[i+1]);
				}
				if(m12 != Double.MAX_VALUE)
				{
					int temp =  (int)(m12*(y-yPoints[i]) + xPoints[i]);
					if((temp<=xPoints[i+1] && temp>=xPoints[1]) || (temp>=xPoints[i+1] && temp<=xPoints[1]))
						intersects.add( temp);
				}
				else
				{
					intersects.add(xPoints[i+1]);
					intersects.add(xPoints[i]);
				}
				System.out.println(intersects.size());
				int xStart = Collections.min(intersects);
				int xEnd = Collections.max(intersects);
				for(int x=xStart;x<=xEnd;x++)
				{
					if(inBounds(x, y) && current[y][x]==0)
						current[y][x] = color.getRGB();
				}
			}
			
		}
		*/
	}
	private boolean inBounds(int a, int b)
	{
		return a>=0 && b>=0 && a<width && b<height;
	}
	private double calculateSlope(int x1,int y1, int x2, int y2)
	{
		try
		{
			return (x2-x1)*1.0/(y2-y1);
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
			System.out.println("yay2");
			return Double.MAX_VALUE;
		}
		
	}
	private void copy()
	{
		for (int i = 0; i < current.length; i++) 
		{
			for (int j = 0; j < current[0].length; j++) 
			{
				Color temp = new Color(current[i][j].getRed(), current[i][j].getGreen(), current[i][j].getBlue());
				//previous[i][j] = Integer.valueOf(current[i][j]);
				previous[i][j] = temp;
			}
			
		}
	}
	
	public void flip(Graphics g)
	{
		for (int i = 0; i < current.length; i++) 
		{
			for (int j = 0; j < current[0].length; j++) 
			{ 
				//g.setColor(new Color(current[i][j]));
				g.setColor(current[i][j]);
				//g.drawRect(j, i, 1, 1); 
				g.drawLine(j, i, j, i);
			}
			
		}
		clear();
		
	}
	private void clear()
	{
		for (int i = 0; i < current.length; i++) 
		{
			for (int j = 0; j < current[0].length; j++) 
			{ 
				current[i][j] = null;
				
			}
			
		}
	}
	
	
	
	private class MyLine
	{
		private int startX,startY,endX,endY;
		private double m;
		private boolean vertical;
		public MyLine(int startX, int startY,int endX,int endY)
		{
			if(startX<endX)
			{
				this.startX= startX;
				this.startY = startY;
				this.endX = endX;
				this.endY = endY;
				m = (double)(endY-startY)/(endX-startX);
				vertical = false;
			}
			else if(endX<startX)
			{
				this.startX= endX;
				this.startY = endY;
				this.endX = startX;
				this.endY = startY;
				m = (double)(endY-startY)/(endX-startX);
				vertical = false;
			}
			else
			{
				if(startY<endY)
				{
					this.startY= startY;
					this.endY = endY;
				}
				else
				{
					this.endY = startY;
					this.startY = endY;
				}
				this.startX= startY;
				this.endX = endX;
				
				m = 0;
				vertical = true;
			}
		}
		
		public Integer getIntersection(int y)
		{
			if(vertical)
			{
				if(startY<=y && y<endY)
					return startX;
				else
					return null; 
			}
			else
			{
				int x = (int)((y - startY)/m + startX);
				if(startX<=x && x<endX)
					return x;
			}
			return null;
		}
	}
}

