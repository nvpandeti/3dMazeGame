import java.util.*;
public class Bitmap 
{
	private int[][][] current, previous;
	private int width, height;
	public Bitmap(int h, int w)
	{
		width = w;
		height = h;
		
		previous= new int[height][width][3];
		current = new int[height][width][3];
	}
	
	private void copy()
	{
		for (int i = 0; i < current.length; i++) 
		{
			for (int j = 0; j < current[0].length; j++) 
			{
				int[] temp = {current[i][j][0], current[i][j][1], current[i][j][2]};
				previous[i][j] = temp;
			}
			
		}
	}
	
	
}
