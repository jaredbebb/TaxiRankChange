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




public class Rank {

  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, IntWritable>{
    
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
    	
    	String[] cells = value.toString().split(",");
    	Text longitude = new Text();
    	Text latitude = new Text();
    	Text long_lat = new Text();
    	Text date = new Text();
    	Text long_lat_date = new Text();
    	try{
    	date.set(","+cells[1].substring(0,10));
    	BigDecimal longbd = new BigDecimal(Double.parseDouble(cells[5]));
    	longbd = longbd.setScale(3, RoundingMode.DOWN);
    	Double longd = longbd.doubleValue();
    	
    	
    	DoubleWritable longdw = new DoubleWritable(longd);
    	longitude.set(longdw.toString());
    	
    	BigDecimal bdlat = new BigDecimal(Double.parseDouble(cells[6]));
    	bdlat = bdlat .setScale(3, RoundingMode.DOWN);
    	Double dlat = bdlat.doubleValue();
    	
    	
    	DoubleWritable lat = new DoubleWritable(dlat);
    	latitude.set(lat.toString());
    	
    	long_lat.set(longdw.toString()+","+lat.toString());
    	long_lat_date.set(longdw.toString()+","+lat.toString()+ date);
    	
    	context.write(long_lat_date,one);
    	}
    	catch(NumberFormatException e){
    		System.out.println("Looks like the wrong number format. "+e);
    	}
    	
    	
    	
//      StringTokenizer itr = new StringTokenizer(value.toString(), ",");
//      while (itr.hasMoreTokens()) {
//        word.set(itr.nextToken());
//        context.write(word, one);
//      }
    }
  }
  
  public static class IntSumReducer 
       extends Reducer<Text,IntWritable,Text,IntWritable> {
	  
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      if( sum > 0){
    	  context.write(key, result); 
      }     
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    
    Job job = new Job(conf, "word count");
    job.setJarByClass(Rank.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileInputFormat.addInputPath(job, new Path(args[1]));
    
    FileOutputFormat.setOutputPath(job, new Path(args[2]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
