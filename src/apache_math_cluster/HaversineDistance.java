package apache_math_cluster;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

public class HaversineDistance extends Object implements DistanceMeasure {

	private static final long serialVersionUID = 1L;

	@Override
	public double compute(double[] a, double[] b)
			throws DimensionMismatchException {
		return distance(a, b);
	}

	public double distance(double[] p1, double[] p2
	) throws DimensionMismatchException {
	checkEqualLength(p1,p2);
	double distancelatitude = Math.toRadians(p2[0] - p1[0]);
	double distancelongitude = Math.toRadians(p2[1]- p1[1]);	
	double startlatitude = Math.toRadians(p1[0]);
	double endlatitude = Math.toRadians(p2[0]);	
	double a = Math.pow(Math.sin(distancelatitude/2), 2)+
			Math.cos(startlatitude)* Math.cos(endlatitude)*
			Math.pow(Math.sin(distancelongitude/2), 2);
	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	
	Double earthRadiusKM = 6371.00;
	return earthRadiusKM * c;
}
	/**
	 * 
	 * @param a
	 * @param b
	 */
	public void checkEqualLength(double[]a, double[]b){
		checkEqualLength(a,b,true);
	}
	/**
	 * 
	 * @param a
	 * @param b
	 * @param abort
	 * @return
	 */
	public static boolean checkEqualLength(double[]a, double[]b,boolean abort){
		if(a.length == b.length){
			return true;
		}
		else{
			if(abort){
				throw new DimensionMismatchException(a.length, b.length);
			}
			return false;
		}		
	}	
}
