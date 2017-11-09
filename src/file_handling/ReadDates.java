package file_handling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import mr.CompareDates;

public class ReadDates {
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		File file = new File("src/data/OutputShort.txt");
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(file));
		
		String text = null;
		
		CompareDates cd = new CompareDates();				
		while((text = reader.readLine()) != null){
			String[] cells = text.toString().split("\t");
			String[] long_lat_date = cells[0].split(",");
			System.out.println("long " +long_lat_date[0]+ " lat " +long_lat_date[1]+" date " +long_lat_date[2]);
			
			if(long_lat_date[2].equals("01/01/2016")){
				cd.putHashMap(long_lat_date[0]+","+long_lat_date[1], Integer.parseInt(cells[1]), 0);		
			}
			else if(long_lat_date[2].equals("01/02/2016")){			
				cd.putHashMap(long_lat_date[0]+","+long_lat_date[1], Integer.parseInt(cells[1]), 1);
			}
			else{
				System.out.println(long_lat_date[2]+" doesn't equal either");
			}		
		}
		
		Set<String> firstdateskeys = cd.FirstDatesKeys();
		
		System.out.println("These are the numbe of keys "+firstdateskeys.size()+" These are the differences");
		System.out.println("Trip Change from intial date to last date");
		for(String location : firstdateskeys){
			int first = cd.DifferenceFirstToSecond(location);
			System.out.println(location+"\t"+first);	
		}
		
		
		Set<String> seconddateskeys = cd.SecondDatesKeys();
		System.out.println("Trip Change from last date to initial date: "+"where initial date pickups at location = 0");
		for(String location: seconddateskeys){
			if(!cd.firstDates.containsKey(location)){
				int second = cd.DifferenceSecondToFirst(location);
				if(second != Integer.MIN_VALUE){
					System.out.println(location+"\t"+second);	
				}
				else{
					//System.out.println("Location is already in first set"+second+" "+location);
				}
			}	
		}
		
		
		reader.close();		
	}

}
