/*
 * Nikhil Pandeti
 * Mrs. Gallatin
 * Period 2
 */
/**
 * A runner program for Sculpture Drawer
 */
 
import java.io.*;
public class GameViewer
{  
	public static void main(String[] args) throws IOException
	{        
		//new SculptureDrawer();
		new Viewer(new File("temp.txt")); 
	}   
}