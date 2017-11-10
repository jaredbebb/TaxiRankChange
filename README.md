TaxiRankChange

Haversine distance implements DistanceMeasure interface.
DistanceMeasure interface is part of apache commons math,  apache.commons.math3.ml.distance

Haversine distance can be found in:

TaxiRankChange/src/apache_math_cluster/HaversineDistance.java



You can find use cases for clustering at http://commons.apache.org/proper/commons-math/userguide/ml.html



One possible implementation of HaversineDistance using apache commons math machine learning clusering means++  is :

HaversineDistance haversine = new HaversineDistance();

KMeansPlusPlusClusterer<LocationWrapper> clusterer = new KMeansPlusPlusClusterer<LocationWrapper>(10, 10000,haversine);
  
List<CentroidCluster<LocationWrapper>> clusterResults = clusterer.cluster(clusterInput);
