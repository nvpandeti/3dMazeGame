/*
 * Nikhil Pandeti is a nub
 * hi Mrs. Gallatin
 * Period 2
 */
/**
 * A runner program for Sculpture Drawer
 */
import java.io.*;

import javax.swing.plaf.SliderUI;
public class GameViewer
{  
	public static void main(String[] args) throws IOException
	{
		int level = 1;
		/*
		for (int i = 0; i < 10; i++) 
		{
			Viewer v = new Viewer(new File("temp.txt"), level); 
			int status = v.getStatus();
			while(status==0)
			{
				
				status = v.getStatus();
				//System.out.print("w");
			}
				
			if(status==2)
				level++;
			//new Viewer(new File("temp.txt")); 
		}
		*/
		new Viewer(new File("temp.txt"), level); 
	}
}