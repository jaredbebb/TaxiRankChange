package apache_math_cluster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException  {
		ClusteringDemo clusteringdemo = new ClusteringDemo();
		File in = new File("/home/cloudera/Desktop/Green Taxi Data 15_16_17/2015/GreenTaxi_2015.txt");
		BufferedReader reader = null;		
		try {
			reader = new BufferedReader(new FileReader(in));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		String text = null;
		
		int count = 0;
		long starttime = System.currentTimeMillis();
		System.out.print("Starttime:"+starttime);
		
		try {
			while((text = reader.readLine()) != null){
				if(count >= 0 && (count%10==0)){
					System.out.println("count:"+count);
					String[] split = text.split(",");
					double lat=Double.parseDouble(split[0]);
					double lon=Double.parseDouble(split[1]);
					Location loc = new Location(lon,lat);
					clusteringdemo.put(loc);
				}			
				count++;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clusteringdemo.move();
		
		File out = new File("/home/cloudera/Desktop/Green Taxi Data 15_16_17/2015/ResultsJan_June2015.csv");
		clusteringdemo.kmeansplusplus(1000, 10000,out);
		//clusteringdemo.dbscan(.000001, 5);
		long endTime = System.currentTimeMillis();
		long totalTime = (endTime - starttime)/1000;
		System.out.println("totalTime(seconds):"+totalTime);
	}
}
