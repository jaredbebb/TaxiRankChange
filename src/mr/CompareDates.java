package mr;
import java.util.HashMap;
import java.util.Set;


public class CompareDates {
	
	public HashMap<String, Integer> firstDates;
	public HashMap<String, Integer> secondDates;
	
	public CompareDates(){
		firstDates = new HashMap<String, Integer>();
		secondDates = new HashMap<String, Integer>();
	}
	
	public void putHashMap(String location, Integer val, int whichHashMap ){		
		if(whichHashMap == 0){
			firstDates.put(location, val);
		}
		else if(whichHashMap == 1){
			secondDates.put(location, val);
		}			
	}
	
	public Set<String> FirstDatesKeys(){
		return firstDates.keySet();
	}
	
	public Set<String> SecondDatesKeys(){
		return secondDates.keySet();
	}
	
	public int DifferenceFirstToSecond(String location){
		if(secondDates.containsKey(location)){
		return secondDates.get(location)-firstDates.get(location);
		}
		else{
			return 0 -firstDates.get(location);
		}
	}
	
	public int DifferenceSecondToFirst(String location){
		if(!firstDates.containsKey(location)){
			return secondDates.get(location);
		}
		else{
			return Integer.MIN_VALUE;
		}
	}
	
			
	
	public static void main(String [] args){
		CompareDates C = new CompareDates();
		C.putHashMap("-73.975,40.674", 2000, 0);
		C.putHashMap("-73.975,40.674", 3000, 1);
		System.out.println(C.DifferenceFirstToSecond("-73.975,40.674") == 1000);
	}
}
