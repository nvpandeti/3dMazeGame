import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;


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
			angleNorm[i] /= -normalizer; 
		}
		double thetaOrig = Math.toDegrees(Math.atan2(angleNorm[1], angleNorm[0]))+180;
		//double thetaOrig = 0;
		//double phi = Math.toDegrees(Math.asin(angleNorm[2]));
		double phi = 90 - Math.toDegrees(Math.asin(angleNorm[2]));
		
		
		
		marker = new double[56][3];
		double theta = -11.25;
		for (int i = 0; i < 16; i++) 
		{
			marker[i][0] = position[0] + .15 * Math.cos(Math.toRadians(theta));
			marker[i][1] = position[1] + .15 * Math.sin(Math.toRadians(theta));
			marker[i][2] = position[2] +.1;
			theta += 22.5; 
		}
		theta = -11.25;
		for (int i = 40; i < 56; i++) 
		{
			marker[i][0] = position[0] + .2 * Math.cos(Math.toRadians(theta));
			marker[i][1] = position[1] + .2 * Math.sin(Math.toRadians(theta));
			marker[i][2] = position[2];
			theta += 22.5; 
		}
		int index = 16;
		for (int i = 15; i >= 10; i--) 
		{
			
			//for (double j = 0; j < 6; j+=1) 
			//{
				double j = 15-i;
				int numX = (int)(-Math.abs(j-2.5)+3.5); 
				//int startY = 8-numX/2;
				for(int k = 8+numX;k>8-numX;k--)
				{
					marker[index][0] = marker[i][0];
					marker[index][1] = marker[k][1];
					marker[index][2] = position[2] +.1;
					index++;
				}
			//}
		}
		
		for (int i = 0; i<marker.length; i++)
		{
			double tempX = 	marker[i][0];
            double tempY = 	marker[i][1];
            double tempZ = 	marker[i][2];
            
            double tempR = Math.sqrt(Math.pow(tempX-position[0], 2) + Math.pow(tempZ-position[2], 2));
            double tempAngle = Math.toDegrees(Math.atan2(tempZ - position[2], tempX - position[0]));
            tempX = position[0] + tempR * Math.cos(Math.toRadians(tempAngle + phi));
            tempZ = position[2] + tempR * Math.sin(Math.toRadians(tempAngle + phi));
           	
			tempR = Math.sqrt(Math.pow(tempX-position[0], 2) + Math.pow(tempY-position[1], 2));
			tempAngle = Math.toDegrees(Math.atan2(tempY - position[1], tempX - position[0]));
            tempX = position[0] + tempR * Math.cos(Math.toRadians(tempAngle + thetaOrig));
            tempY = position[1] + tempR * Math.sin(Math.toRadians(tempAngle + thetaOrig));
            
            marker[i][0] = tempX;
            marker[i][1] = tempY;
            marker[i][2] = tempZ;
		}
		double[] tempLightPos = {position[0]+.2*angleNorm[0], position[1]+.2*angleNorm[1], position[2]+.2*angleNorm[2]};
		Face.addLights(new Light(tempLightPos, 4)); 
		
		//for (int i = 0; i < marker.length; i++) {
		//	System.out.println( Arrays.toString( marker[i]));
			
		//}
		faces = new ArrayList<Face>();
		faces.add(new Face(Color.red, marker[0], marker[15], marker[16]));
		faces.add(new Face(Color.red, marker[1], marker[17], marker[2]));
		faces.add(new Face(Color.red, marker[2], marker[21], marker[3]));
		faces.add(new Face(Color.red, marker[3], marker[27], marker[4]));
		faces.add(new Face(Color.red, marker[5], marker[33], marker[6]));
		faces.add(new Face(Color.red, marker[6], marker[37], marker[7]));
		faces.add(new Face(Color.red, marker[7], marker[39], marker[8]));
		faces.add(new Face(Color.red, marker[9], marker[38], marker[10]));
		faces.add(new Face(Color.red, marker[10], marker[34], marker[11]));
		faces.add(new Face(Color.red, marker[11], marker[28], marker[12]));
		faces.add(new Face(Color.red, marker[13], marker[22], marker[14]));
		faces.add(new Face(Color.red, marker[14], marker[18], marker[15]));
		
		faces.add(new Face(Color.red, marker[1], marker[0], marker[16], marker[17]));
		faces.add(new Face(Color.red, marker[39], marker[38], marker[9], marker[8]));
		faces.add(new Face(Color.red, marker[18], marker[14], marker[22], marker[23]));
		faces.add(new Face(Color.red, marker[23], marker[22], marker[28], marker[29]));
		faces.add(new Face(Color.red, marker[29], marker[28], marker[11], marker[34]));
		faces.add(new Face(Color.red, marker[22], marker[13], marker[12], marker[28]));
		faces.add(new Face(Color.red, marker[3], marker[21], marker[26], marker[27]));
		faces.add(new Face(Color.red, marker[27], marker[26], marker[32], marker[33]));
		faces.add(new Face(Color.red, marker[33], marker[32], marker[37], marker[6]));
		faces.add(new Face(Color.red, marker[4], marker[27], marker[33], marker[5]));
		
		if(num!=1)
			faces.add(new Face(Color.black, marker[16], marker[15], marker[18], marker[19]));
		else
			faces.add(new Face(Color.red, marker[16], marker[15], marker[18], marker[19]));
		if(num!=4)
			faces.add(new Face(Color.black, marker[17], marker[16], marker[19], marker[20]));
		else
			faces.add(new Face(Color.red, marker[17], marker[16], marker[19], marker[20]));
		if(num!=1)
			faces.add(new Face(Color.black, marker[2], marker[17], marker[20], marker[21]));
		else
			faces.add(new Face(Color.red, marker[2], marker[17], marker[20], marker[21]));
		if(num>3 && num!=7)
			faces.add(new Face(Color.black, marker[19], marker[18], marker[23], marker[24]));
		else
			faces.add(new Face(Color.red, marker[19], marker[18], marker[23], marker[24]));
		if(num==1)
			faces.add(new Face(Color.black, marker[20], marker[19], marker[24], marker[25]));
		else
			faces.add(new Face(Color.red, marker[20], marker[19], marker[24], marker[25]));
		if(num!=1 && num!=5 && num!=6)
			faces.add(new Face(Color.black, marker[21], marker[20], marker[25], marker[26]));
		else
			faces.add(new Face(Color.red, marker[21], marker[20], marker[25], marker[26]));
		if(num!=1 && num!=7)
			faces.add(new Face(Color.black, marker[24], marker[23], marker[29], marker[30]));
		else
			faces.add(new Face(Color.red, marker[24], marker[23], marker[29], marker[30]));
		if(num!=7)
			faces.add(new Face(Color.black, marker[25], marker[24], marker[30], marker[31]));
		else
			faces.add(new Face(Color.red, marker[25], marker[24], marker[30], marker[31]));
		if(num!=1)
			faces.add(new Face(Color.black, marker[26], marker[25], marker[31], marker[32]));
		else
			faces.add(new Face(Color.red, marker[26], marker[25], marker[31], marker[32]));
		if(num==2 || num==6 || num==8)
			faces.add(new Face(Color.black, marker[30], marker[29], marker[34], marker[35]));
		else
			faces.add(new Face(Color.red, marker[30], marker[29], marker[34], marker[35]));
		if(num==1)
			faces.add(new Face(Color.black, marker[31], marker[30], marker[35], marker[36]));
		else
			faces.add(new Face(Color.red, marker[31], marker[30], marker[35], marker[36]));
		if(num>2)
			faces.add(new Face(Color.black, marker[32], marker[31], marker[36], marker[37]));
		else
			faces.add(new Face(Color.red, marker[32], marker[31], marker[36], marker[37]));
		if(num!=1 && num!=4 && num!=7)
			faces.add(new Face(Color.black, marker[35], marker[34], marker[10], marker[38]));
		else
			faces.add(new Face(Color.red, marker[35], marker[34], marker[10], marker[38]));
		if(num!=4 && num!=7)
			faces.add(new Face(Color.black, marker[36], marker[35], marker[38], marker[39]));
		else
			faces.add(new Face(Color.red, marker[36], marker[35], marker[38], marker[39]));
		if(num!=1)
			faces.add(new Face(Color.black, marker[37], marker[36], marker[39], marker[7]));
		else
			faces.add(new Face(Color.red, marker[37], marker[36], marker[39], marker[7])); 
		
		for(int i=0;i<15;i++)
		{
			faces.add(new Face(Color.red, marker[i],marker[i+1],marker[i+41], marker[i+40]));
		}
		faces.add(new Face(Color.red, marker[15],marker[0],marker[40], marker[55]));
		
		
		
		//System.out.println(" done"); 
		
		
		
		
		
		
		
		
		
		
		
	}

	
	public ArrayList<Face> getFaces() {
		
		return faces;
	}

	@Override
	public void transform(double x, double y, double z) 
	{
		
		
	}
}
