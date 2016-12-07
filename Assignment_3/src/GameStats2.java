package jar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/*
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper;
*/


public class GameStats2 {
	
	public static enum TOTAL_COUNTER { BLACK, WHITE, DRAW, ALL };
	
	public static class Map extends Mapper<LongWritable, Text, Text, IntWritable>
	{
	      private final static IntWritable one = new IntWritable(1);
	      private Text draw = new Text("Draw");
	      private Text white = new Text("White");
	      private Text black = new Text("Black");
	         
	           public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	           {
	               String line = value.toString();
	               StringTokenizer tokenizer = new StringTokenizer(line);
	           
	               while (tokenizer.hasMoreTokens())
	               {
	            	   String temp = tokenizer.nextToken();
	            	   if(temp.contains("/")){
	            		   context.write(draw, one);
	            		   context.getCounter(TOTAL_COUNTER.DRAW).increment(1);
	            	   }
	            	   else if(temp.contains("1-0")){
	            		   context.write(white, one);
	            		   context.getCounter(TOTAL_COUNTER.WHITE).increment(1);
	            	   }
	            	   else {
	            		   context.write(black, one);
	            		   context.getCounter(TOTAL_COUNTER.BLACK).increment(1);
	            	   }
	            	   context.getCounter(TOTAL_COUNTER.ALL).increment(1);
	               }
	           }
           
	}
	
	public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable>
	{
		
	      public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
	      {
	    	  int sum = 0;
	            for (IntWritable value : values)
	            {
	                  sum += value.get();
	            }
	            
	            
	            context.write(key, new IntWritable(sum));
	       }
	      
	}
	
	public static void main(String[] args) throws Exception {
		 //Configuration conf = getConf();
	     Job job = new Job();
	     job.setJobName("Game Stats");
	     job.setJarByClass(GameStats2.class);
	     
	     job.setMapperClass(Map.class);
	     job.setReducerClass(Reduce.class);

	     job.setOutputKeyClass(Text.class);
	     job.setOutputValueClass(IntWritable.class);

	     job.setInputFormatClass(TextInputFormat.class);
	     job.setOutputFormatClass(TextOutputFormat.class);
	     
	     FileInputFormat.addInputPath(job, new Path(args[0]));
	     FileOutputFormat.setOutputPath(job, new Path(args[1]));

	     job.waitForCompletion(true);
	     
	     long countALL = job.getCounters().findCounter(TOTAL_COUNTER.ALL).getValue();
	     long countDRAW = job.getCounters().findCounter(TOTAL_COUNTER.DRAW).getValue();
	     long countWHITE = job.getCounters().findCounter(TOTAL_COUNTER.WHITE).getValue();
	     long countBLACK = job.getCounters().findCounter(TOTAL_COUNTER.BLACK).getValue();
		
	     float percentDRAW = countDRAW/countALL;
	     float percentWHITE = countWHITE/countALL;
	     float percentBLACK = countBLACK/countALL;
	     
	     String output = 
	    		 "White \t"+countWHITE+"\t"+percentWHITE+
	    		 "\nBlack \t"+countBLACK+"\t"+percentBLACK+
	    		 "\nDraw \t"+countDRAW+"\t"+percentDRAW;
	     
	     File outputFile = new File(args[1]+"output.txt");
	     
	     try{
	    	 FileWriter fw = new FileWriter(outputFile);
	    	 BufferedWriter writer = new BufferedWriter(fw);
	    	 writer.write(output);
	    	 writer.close();
	     }
	     catch (IOException e){
	    	 System.out.println("Failed to write to output file "+outputFile.getAbsolutePath());
	     }
	     
		
	}
}
