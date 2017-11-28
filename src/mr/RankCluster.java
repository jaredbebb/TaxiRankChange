package mr;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import apache_math_cluster.ClusteringDemo;
import apache_math_cluster.Location;




public class RankCluster {
	
	//need to emit cluster centers

  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, IntWritable>{
    
    private final static IntWritable one = new IntWritable(1);
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {  	
    	String[] cells = value.toString().split(",");
    	Text longitude = new Text();
    	Text latitude = new Text();
    	Text date = new Text();
    	Text lat_long_date = new Text();
    	try{
    	date.set(","+cells[1].substring(0,10));
    	BigDecimal longbd = new BigDecimal(cells[5]);
    	Double longd = longbd.doubleValue();	
    
    	DoubleWritable longdw = new DoubleWritable(longd);
    	longitude.set(longdw.toString());
    	
    	BigDecimal bdlat = new BigDecimal(cells[6]);
    	Double dlat = bdlat.doubleValue();
    	
    	
    	DoubleWritable lat = new DoubleWritable(dlat);
    	latitude.set(lat.toString());
    	
    	lat_long_date.set(lat.toString()+","+longdw.toString()+ date);

    	context.write(lat_long_date,one);
    	}
    	catch(NumberFormatException e){
    		System.out.println("Looks like the wrong number format. "+e);
    	}
    }
  }
  
  public static class IntSumReducer 
       extends Reducer<Text,IntWritable,Text,IntWritable> {
	  
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      for (IntWritable val : values) {
      result.set(val.get());
      context.write(key, result);
    }  
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    
    Job job = new Job(conf, "rank cluster");
    job.setJarByClass(RankCluster.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
