package file_handling;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class MinFile {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		File file1 = new File("/home/cloudera/Desktop/java_jars_terminal/yellow_tripdata_2016-01.csv");		
		File[] files = {file1};
		BufferedWriter writer = null;
		File out = new File("/home/cloudera/Desktop/java_jars_terminal/yellow_tripdata_2016-01__100_000.csv");
		writer = new BufferedWriter(new FileWriter(out));
		
		for (File curr : files){
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(curr));		
			String text = null;		
			int count = 0;			
			while((text = reader.readLine()) != null && count <= 100000){
				writer.write(text);
				writer.newLine();
	
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
