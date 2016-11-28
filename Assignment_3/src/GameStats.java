import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import javafx.scene.text.Text;

/*
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper;
*/


public class GameStats {
	
	private enum Outcome {WIN, LOSS, DRAW};
	
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
		            	   }
		            	   else if(temp.contains("1-0")){
		            		   context.write(white, one);
		            	   }
		            	   else {
		            		   context.write(black, one);
		            	   }
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
			 Configuration conf = getConf();
		     Job job = new Job(conf, "WordCountJob");
		     job.setJarByClass(WordCount.class);
		     
		     job.setMapperClass(Map.class);
		     job.setReducerClass(Reduce.class);

		     job.setOutputKeyClass(Text.class);
		     job.setOutputValueClass(IntWritable.class);

		     job.setInputFormatClass(TextInputFormat.class);
		     job.setOutputFormatClass(TextOutputFormat.class);
		     
		     FileInputFormat.addInputPath(job, new Path(args[0]));
		     FileOutputFormat.setOutputPath(job, new Path(args[1]));

		     job.waitForCompletion(true);
			
			
		}
}
