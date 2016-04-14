import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class Maze implements Hitboxable
{
	private int rows,cols;
	private boolean[][][] maze;
	private Random r;
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Cube> cubes;
	public Maze(int rows, int cols, int width, int height)
	{
		r = new Random();
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
		Viewer.viewerPainter.addShape(new Plane(Color.blue.darker(), -.5,-.5,-.51,rows*width+1, cols*width+1, Math.max(rows*width+1, cols*width+1), 0, 0, 0, false));
		Viewer.viewerPainter.addShape(new Plane(new Color(127, 0, 255).darker(), -.5,-.5,height-.49,rows*width+1, cols*width+1, Math.max(rows*width+1, cols*width+1), 0, 0, 0, true));
		Viewer.viewerPainter.addShape(new Plane(Color.yellow, rows*width+.5,(cols-1)*width+.5,-.51,width-1, width-1, width-1, 0, 0, 0, false));
		
		cubes = new ArrayList<Cube>();
		
		Color wallColor = new Color(139,69,19, 255);
		for (int i = 0; i<rows+1; i++)
			for (int j = 0; j<cols+1; j++)
				for (int h = 0; h<height; h++)
					cubes.add(new Cube(wallColor, j*width, i*width, h,1,1,1,0,0,0));
					
		for (int i = 0; i<rows; i++)
		{
			for (int j = 0; j<cols; j++)
			{
				if(maze[i][j][1])
					for (int h = 0; h<height; h++)
						for (int w = 1; w<width; w++)
							cubes.add(new Cube(wallColor, j*width+w, i*width, h,1,1,1,0,0,0));
				if(maze[i][j][4])
					for (int h = 0; h<height; h++)
						for (int w = 1; w<width; w++)
							cubes.add(new Cube(wallColor, j*width, i*width+w, h,1,1,1,0,0,0));
							
				if(i==rows-1 || j==cols-1)
				{
					if(maze[i][j][2])
						for (int h = 0; h<height; h++)
							for (int w = 1; w<width; w++)
								cubes.add(new Cube(wallColor, j*width+width, i*width+w, h,1,1,1,0,0,0));
					if(maze[i][j][3])
						for (int h = 0; h<height; h++)
							for (int w = 1; w<width; w++)
								cubes.add(new Cube(wallColor, j*width+w, i*width+width, h,1,1,1,0,0,0));
				}
			}
		}
		
		hitbox = new ArrayList<Hitbox>();
		for(Cube c: cubes)
		{
			Viewer.viewerPainter.addShape(c);
			hitbox.add(c.getHitbox().get(0));
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
	
	public ArrayList<Hitbox> getHitbox() 
	{
		return hitbox;
	}
}
