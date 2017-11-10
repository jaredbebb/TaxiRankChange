package apache_math_cluster;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.clustering.FuzzyKMeansClusterer;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.ml.clustering.MultiKMeansPlusPlusClusterer;;

public class ClusteringDemo {
	//References http://commons.apache.org/proper/commons-math/userguide/ml.html
	
	private List<Location> locations;
	private List<LocationWrapper> clusterInput;
	
	public ClusteringDemo(boolean withLocationsInConstructer){
		Location a = new Location(-63.955551147460937, 30.745941162109375);
		Location b = new Location(-72.991423645019531, 39.990977478027344);
		Location c = new Location(-73.111423645019531, 40.110977478027344);
		Location d = new Location(-83.951423645019531, 50.790977478027344);
		Location e = new Location(-33.951423645019531, 10.790977478027344);;
		
		locations = new ArrayList<Location>();
		locations.add(a);locations.add(b);locations.add(c);locations.add(d);locations.add(e);
		locations.add(a);locations.add(b);locations.add(c);locations.add(d);locations.add(e);
		locations.add(a);locations.add(b);locations.add(c);locations.add(d);locations.add(e);
		locations.add(a);locations.add(b);locations.add(c);locations.add(d);locations.add(e);

		clusterInput = new ArrayList<LocationWrapper>(locations.size());
		for (Location locate : locations)
			clusterInput.add(new LocationWrapper(locate));
	}
	
	public ClusteringDemo(){
		locations = new ArrayList<Location>();
	}
	
	public void put(Location loc){
		locations.add(loc);
	}
	public void move(){
		clusterInput = new ArrayList<LocationWrapper>(locations.size());
		for (Location locate : locations)
			clusterInput.add(new LocationWrapper(locate));
	}
	
	
	public void kmeansplusplus(int k, int maxiterations){
		// initialize a new clustering algorithm. 
		// we use KMeans++ with 10 clusters and 10000 iterations maximum.
		// we did not specify a distance measure; the default (euclidean distance) is used.
		KMeansPlusPlusClusterer<LocationWrapper> clusterer = new KMeansPlusPlusClusterer<LocationWrapper>(k, maxiterations);
		List<CentroidCluster<LocationWrapper>> clusterResults = clusterer.cluster(clusterInput);
	
		// output the clusters
		for (int i=0; i<clusterResults.size(); i++) {
		    System.out.println("\nCluster " + i);
		    for (LocationWrapper locationWrapper : clusterResults.get(i).getPoints())
		        System.out.println(locationWrapper.getLocation().getX() +","+locationWrapper.getLocation().getY() );
		}
		for (int i=0; i<clusterResults.size(); i++) {
		    //System.out.println("\nCluster " + i);
		    int size = clusterResults.get(i).getPoints().size();
		    Clusterable center = clusterResults.get(i).getCenter();
		    double[] xy =center.getPoint();
		    System.out.println(xy[0]+","+xy[1]+","+"cluster:"+i+","+"size:"+size);    
		}
	}
	
	public void kmeansplusplus(int k, int maxiterations, File out) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(out));
		
		KMeansPlusPlusClusterer<LocationWrapper> clusterer = new KMeansPlusPlusClusterer<LocationWrapper>(k, maxiterations);
		List<CentroidCluster<LocationWrapper>> clusterResults = clusterer.cluster(clusterInput);
	
		// output the clusters
		for (int i=0; i<clusterResults.size(); i++) {
		    //System.out.println("\nCluster " + i);
			writer.write("\nCluster " + i);
			writer.newLine();
		    for (LocationWrapper locationWrapper : clusterResults.get(i).getPoints()){
		        //System.out.println(locationWrapper.getLocation().getX() +","+locationWrapper.getLocation().getY());
		    	writer.write(locationWrapper.getLocation().getX() +","+locationWrapper.getLocation().getY() );
		    	writer.newLine();
		    }    	
		}
		for (int i=0; i<clusterResults.size(); i++) {
		    //System.out.println("\nCluster " + i);
		    int size = clusterResults.get(i).getPoints().size();
		    Clusterable center = clusterResults.get(i).getCenter();
		    double[] xy =center.getPoint();
		    //System.out.println(xy[0]+","+xy[1]+","+"cluster:"+i+","+"size:"+size);
		    writer.write(xy[0]+","+xy[1]+","+i+","+size);
		    writer.newLine();
		}
		writer.close();
	}	
	
	public void fuzzyKMeansClusterer(){
		//FuzzyKMeansClusterer(int k, double fuzziness)
		FuzzyKMeansClusterer<LocationWrapper> clusterer = new FuzzyKMeansClusterer<LocationWrapper>(5, 10);
		List<CentroidCluster<LocationWrapper>> clusterResults = clusterer.cluster(clusterInput);
	
		// output the clusters
		for (int i=0; i<clusterResults.size(); i++) {
		    System.out.println("\nCluster " + i);
		    for (LocationWrapper locationWrapper : clusterResults.get(i).getPoints())
		        System.out.println(locationWrapper.getLocation().getX() +","+locationWrapper.getLocation().getY() );
		}
	}
	
	public void multiKMeansplusplusClusterer(){
		//MultiKMeansClusterer
		KMeansPlusPlusClusterer<LocationWrapper> kmeansplusplusclusterer = new KMeansPlusPlusClusterer<LocationWrapper>(10, 10000);
		MultiKMeansPlusPlusClusterer<LocationWrapper> clusterer = new MultiKMeansPlusPlusClusterer<LocationWrapper>(kmeansplusplusclusterer,3);
		
		List<CentroidCluster<LocationWrapper>> clusterResults = clusterer.cluster(clusterInput);
	
		// output the clusters
		for (int i=0; i<clusterResults.size(); i++) {
		    System.out.println("\nCluster " + i);
		    for (LocationWrapper locationWrapper : clusterResults.get(i).getPoints())
		        System.out.println(locationWrapper.getLocation().getX() +","+locationWrapper.getLocation().getY() );
		}
	}
	
	public void dbscan(double eps, int minPts){
		// we use DBSCAN with eps and minPts.
		// eps = episilon, the max distance from one point in a cluster to the next closest point in the cluster
		//minPts= minimum points, the minimum points that a cluster can have to be considered a cluster

		// http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math4/ml/clustering/DBSCANClusterer.html
		// constructor DBSCANClusterer(double eps, int minPts)
		HaversineDistance hd = new HaversineDistance();
		DBSCANClusterer<LocationWrapper> clusterer = new DBSCANClusterer<LocationWrapper>(
				eps, minPts, hd);
		List<Cluster<LocationWrapper>> clusterResults = clusterer
				.cluster(clusterInput);
		
		// output the clusters
		for (int i = 0; i < clusterResults.size(); i++) {
			//System.out.println("\nCluster " + i);
			for (LocationWrapper locationWrapper : clusterResults.get(i)
					.getPoints()) {
				System.out.println(locationWrapper.getLocation().getX() + ","
						+ locationWrapper.getLocation().getY()+","+"cluster-"+i);
			}
		}
	}
	
	public static void main(String[]args){
		
		ClusteringDemo cd = new ClusteringDemo(true);
		System.out.println("\n\n---------kmeans++---------");
		cd.kmeansplusplus(10, 10000);
		System.out.println("\n\n---------fuzzykmeans---------");
		cd.fuzzyKMeansClusterer();
		System.out.println("\n\n---------multikmeansplusplusclusterer---------");
		cd.multiKMeansplusplusClusterer();
		System.out.println("\n\n---------DBSCAN---------");
		cd.dbscan(2, 3);
	}	
}

