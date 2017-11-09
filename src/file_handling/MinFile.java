package file_handling;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class MinFile {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		//File file = new File("/home/cloudera/Desktop/Green Taxi Data 16_17/2017/green_tripdata_2017-01.csv");
		File file1 = new File("/home/cloudera/Desktop/Green Taxi Data 15_16_17/2015/green_tripdata_2015-01.csv");
		File file2 = new File("/home/cloudera/Desktop/Green Taxi Data 15_16_17/2015/green_tripdata_2015-02.csv");
		File file3 = new File("/home/cloudera/Desktop/Green Taxi Data 15_16_17/2015/green_tripdata_2015-03.csv");
		File file4 = new File("/home/cloudera/Desktop/Green Taxi Data 15_16_17/2015/green_tripdata_2015-04.csv");
		File file5 = new File("/home/cloudera/Desktop/Green Taxi Data 15_16_17/2015/green_tripdata_2015-05.csv");
		File file6 = new File("/home/cloudera/Desktop/Green Taxi Data 15_16_17/2015/green_tripdata_2015-06.csv");
		
		File[] files = {file1,file2,file3,file4,file5,file6};
		BufferedWriter writer = null;
		File out = new File("/home/cloudera/Desktop/Green Taxi Data 15_16_17/2015/Jan_June2015.csv");
		writer = new BufferedWriter(new FileWriter(out));
		
		for (File curr : files){
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(curr));		
			String text = null;		
			int count = 0;			
			while((text = reader.readLine()) != null){
				if(count > 0){
					writer.write(text);
					writer.newLine();
					//System.out.println(text);
				}		
				//contents.append(text).append(System.getProperty("line.separator"));
				count++;
			}
			reader.close();
			//System.out.println(contents.toString());
			System.out.println("finished with file:"+curr.getAbsolutePath());
		}
		writer.close();
		System.out.println("finished");
		
//		BufferedReader reader = null;
//		
//		reader = new BufferedReader(new FileReader(file));
//		
//		String text = null;
//		
//		int count = 0;
//		
//		while((text = reader.readLine()) != null &&  count < 10){
//			if(count >= 0){
//				System.out.println(text);
//			}
//			
//			//contents.append(text).append(System.getProperty("line.separator"));
//			count++;
//		}
//		reader.close();
//		//System.out.println(contents.toString());
//		

	}

}
