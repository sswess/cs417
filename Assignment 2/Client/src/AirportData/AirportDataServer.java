package AirportData;
import java.io.FileInputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.Arrays;

import AirportData.AirportDataProto.AirportList;
import AirportData.AirportDataProto.Airport;

public class AirportDataServer
extends UnicastRemoteObject
implements AirportDataInterface
{
	private int a;
	public AirportDataServer() throws RemoteException
	{
		System.out.println("New instance of Sample created");
		a=1;
	}
	public static int distFrom(double lat1, double lon1, double lat2, double lon2) {
	    double earthRadius = 6371000; //meters
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lon2-lon1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float dist = (float) (earthRadius * c) / (float)1600;

	    return (int)dist;
	}
	
	public String[][] getdistance(double lat1, double lon1){
		//The two smallest distances
		AirportList list = null;
		String[][] returns = new String[5][4];
		try{	

			list = AirportList.parseFrom(new FileInputStream("airports-proto.bin"));				
			}
			catch(Exception e)
			{
				System.out.println("FILE NOT FOUND");
			}
		
		//The two closest airports
		Airport air1 = null;
		Airport air2 = null;
		Airport air3 = null;
		Airport air4 = null;
		Airport air5 = null;
		int count = 1;
		for (Airport airport: list.getAirportList())
		{
			count ++;
		}
		double [] distancesorted = new double[count];
		double [] distance = new double[count];
		Airport [] airports = new Airport[count];
		int i = 0;
		for (Airport airport: list.getAirportList())
		{
			i++;
			double lat2 = airport.getLat();
			double lon2 = airport.getLon();
			distancesorted[i] = distFrom(lat1,lon1,lat2,lon2);
			distance[i] = distFrom(lat1,lon1,lat2,lon2);
			airports[i] = airport;
		}
		Arrays.sort(distancesorted);
		double index1, index2, index3, index4, index0;
		index0 = distancesorted[1];
		index1 = distancesorted[2];
		index2 = distancesorted[3];
		index3 = distancesorted[4];
		index4 = distancesorted[5];
		for(i=0;i < distance.length;i++){
			if(index0 == distance[i]) air1 = airports[i];
			if(index1 == distance[i]) air2 = airports[i];
			if(index2 == distance[i]) air3 = airports[i];
			if(index3 == distance[i]) air4 = airports[i];
			if(index4 == distance[i]) air5 = airports[i];
		}

			returns[0][0] = air1.getCode();
			returns[0][1] = air1.getName();
			returns[0][2] = air1.getState();
			returns[0][3] = Double.toString(distancesorted[1]); 
			returns[1][0] = air2.getCode();
			returns[1][1] = air2.getName();
			returns[1][2] = air2.getState();
			returns[1][3] = Double.toString(distancesorted[2]); 
			returns[2][0] = air3.getCode();
			returns[2][1] = air3.getName();
			returns[2][2] = air3.getState();
			returns[2][3] = Double.toString(distancesorted[3]); 
			returns[3][0] = air4.getCode();
			returns[3][1] = air4.getName();
			returns[3][2] = air4.getState();
			returns[3][3] = Double.toString(distancesorted[4]);
			returns[4][0] = air5.getCode();
			returns[4][1] = air5.getName();
			returns[4][2] = air5.getState();
			returns[4][3] = Double.toString(distancesorted[5]); 
		
		return returns;
	}
	
	}
	






 
