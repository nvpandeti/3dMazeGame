import java.util.ArrayList;


public class Marker implements Shapes
{
	private double[] position;
	private double[] normal;
	private int num;
	private ArrayList<Hitbox> hitbox;
	private ArrayList<Shapes> shapes;
	private ArrayList<Face> faces;
	private double[][] marker;
	
	public Marker(double[] pos, double[] norm, int n)
	{
		num = n;
		position = pos;
		normal = norm;
		double[] angleNorm = new double[3];
		for (int i = 0; i < angleNorm.length; i++) 
		{
			angleNorm[i] = normal[i] - position[i];
		}
		double normalizer = Math.sqrt(Math.pow(angleNorm[0],2) + Math.pow(angleNorm[1],2) + Math.pow(angleNorm[2],2));
		for (int i = 0; i < angleNorm.length; i++) 
		{
			angleNorm[i] /= normalizer;
		}
		double theta = Math.toDegrees(Math.atan2(angleNorm[1], angleNorm[0]));
		double phi = Math.toDegrees(Math.asin(angleNorm[2]));
		
		
		
		marker = new double[40][3];
		
	}

	
	public ArrayList<Face> getFaces() {
		
		return faces;
	}

	@Override
	public void transform(double x, double y, double z) 
	{
		
		
	}
}
