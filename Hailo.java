import java.io.*;
import java.lang.*;

public class Hailo
{
	int MAX=10000;
	int EARTHRAD=6371;
	double [][] coordinates;
	double [] timestamp;

	public Hailo(String pathName)
	{
		coordinates  = new double[MAX][2];
		timestamp = new double[MAX];
		arrayer(pathName);
	}

	public void arrayer(String pathName)
	{
		File file = new File(pathName);
		BufferedReader reader = null;
		int a=0;
		try {
	    	reader = new BufferedReader(new FileReader(file));
	    	String text = null;
	    	while ((text = reader.readLine()) != null) 
		    	{
		        	String [] csv = text.split(",");
		        	coordinates[a][0]= Double.parseDouble(csv[0]);
		        	coordinates[a][1]= Double.parseDouble(csv[1]);
		        	timestamp[a]= Double.parseDouble(csv[2]);
		        	a++;
			    }
			} 
		catch (FileNotFoundException e) 
			{e.printStackTrace();}
		catch (IOException e) 
			{e.printStackTrace();}
		finally {
				 try {
				     	if (reader != null) {reader.close();}
				     }
				 catch (IOException e) {}
				}		
		double[][] tmpCoord = new double[a][a];
		double[] tmpTime = new double[a];
		/*
		System.out.println(coordinates.length);
		System.out.println(timestamp.length);
		*/
		System.arraycopy(coordinates,0,tmpCoord,0,a);
		System.arraycopy(timestamp,0,tmpTime,0,a);
		coordinates=tmpCoord;
		timestamp=tmpTime;
		/*
		for(int h=0;h<a;h++)
		{
			System.out.println(coordinates[h][0]);			
			System.out.println(coordinates[h][1]);
			System.out.println(timestamp[h]);
		}
		System.out.println(coordinates.length);
		System.out.println(timestamp.length);
		*/
		
	}

	public double distance(double[] pointA, double[] pointB)
	{
		double dLat=(pointB[0]-pointA[0])*(Math.PI/180);
		double dLon=(pointB[1]-pointA[1])*(Math.PI/180);
		double a= Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(pointA[0]*Math.PI/180) * Math.cos(pointB[0]*Math.PI/180) * Math.sin(dLon/2) * Math.sin(dLon/2);
		double b= 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return EARTHRAD * b;
	}

	public double[][] filter()
	{
		double[][] result=new double[coordinates.length][3];
		result[0]=new double[]{coordinates[0][0],coordinates[0][1],timestamp[0]};
		System.out.println(coordinates[0][0]);			
		System.out.println(coordinates[0][1]);
		System.out.println(timestamp[0]);
		double space,time,speed;
		int idx=1;
		for(int i=1;i<coordinates.length-1;i++)
		{
			space=distance(coordinates[i-1],coordinates[i]);
			time=timestamp[i]-timestamp[i-1];
			speed=(space/time)*3600;
			//1 (metre per second) = 3.6 km per hour
			if (speed < 40)
			{
				result[idx]=new double[]{coordinates[i][0],coordinates[i][1],timestamp[i]};
				System.out.println(result[idx][0]);			
				System.out.println(result[idx][1]);
				System.out.println(result[idx][2]);
				idx++;
			}
		}
		return result;
	}

	public static void main(String[] args)	
	{
		Hailo a=new Hailo(args[0]);
		double[][] test = a.filter();
		for(int i=0;i<test.length;i++)
		{
			System.out.println(test[i][0]);			
			System.out.println(test[i][1]);
			System.out.println(test[i][2]);
		}
		
	}
}


