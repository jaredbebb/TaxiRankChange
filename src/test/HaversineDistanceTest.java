package test;

import static org.junit.Assert.*;

import java.text.DecimalFormat;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import apache_math_cluster.HaversineDistance;

public class HaversineDistanceTest {
	
	public static DecimalFormat df = new DecimalFormat("#.####");


	@Test
	public void testCompute() {
		HaversineDistance h = new HaversineDistance();
		double[] p1={47.6788206,-122.3271205};
		double[] p2={47.6788206,-122.6271205};
		double distance =h.distance(p1, p2);
		Assert.assertEquals(22.4598,  Double.valueOf(df.format(distance)));
	}
	
	@Test
	public void testCompute1() {
		HaversineDistance h = new HaversineDistance();
		double[] NYC={40.730610,-73.935242};
		double[] SPAIN={40.416775,-3.3703790};
		double distance =h.distance(NYC, SPAIN);
		Assert.assertEquals(5762,  Double.valueOf(df.format(distance)));
	}


	@Test
	public void testDistance() {
		HaversineDistance h = new HaversineDistance();
		double[] p1={47.6788206,-122.3271205};
		double[] p2={47.6788206,-122.6271205};
		double distance =h.distance(p1, p2);
		Assert.assertEquals(22.4598,  Double.valueOf(df.format(distance)));
	}
}
