package AirportData;

//import AirportData.AirportDataProto.AirportList;

import AirportData.AirportDataProto.AirportList;
import AirportData.AirportDataProto.Airport;

import java.lang.Math;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;

public class Server 
{
	public static void main(String[]args)
	{
		
		try{	

		AirportList list = AirportList.parseFrom(new FileInputStream("airports-proto.bin"));
			//printall(list);
			double lat = 33.150134;
			double lon = -96.918702;
			printdistance(lat,lon,list);
			
		}
		catch(Exception e)
		{
			System.out.println("FILE NOT FOUND");
		}
	}
	
	public static float distFrom(double lat1, double lon1, double lat2, double lon2) {
	    double earthRadius = 6371000; //meters
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lon2-lon1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float dist = (float) (earthRadius * c);

	    return dist;
	    }
	
	
	public static void printall(AirportList List)
	{
		for (Airport airport: List.getAirportList())
		{	
			System.out.print("Airport: " + airport.getName() + ", ");
			System.out.print(airport.getLat() + ", ");
			System.out.print(airport.getLon() + "\n");	
		}
	}
	
	public static void printdistance(double lat1, double lon1, AirportList list){
		//The two smallest distances
		double min1 = Double.NaN, min2 = Double.NaN;
		double y = 0;
		//The two closest airports
		Airport air1 = null;
		Airport air2 = null;
		for (Airport airport: list.getAirportList())
		{
			double lat2 = airport.getLat();
			double lon2 = airport.getLon();
			y = distFrom(lat1,lon1,lat2,lon2);
			if(Double.isNaN(min2)){
				min2 = y;
				air2 = airport;
			}
			if(Double.isNaN(min1)){
				if(y<min2){min1=y; air1 = airport;}
				else{
					min2 = min1; min1 = y;
					air2 = air1;
					air1 = airport; 
				}
			}
			if(y < min2){
				if(y<min1){
				air2 = air1;
				air1 = airport;
				min1 = y;
				}else{
					air2 = airport;
					min2 = y;
				}
			}
						
		}
		System.out.println(air1.getName());
		System.out.println(air1.getLat());
		System.out.println(air1.getLon());
		
		System.out.println(air2.getName());
		System.out.println(air2.getLat());
		System.out.println(air2.getLon());	
	}
	

}
