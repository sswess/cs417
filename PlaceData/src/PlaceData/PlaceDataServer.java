package PlaceData;
import java.io.FileInputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.*;


import PlaceData.PlaceDataProto.Place;
import PlaceData.PlaceDataProto.PlaceList;


public class PlaceDataServer
extends UnicastRemoteObject
implements PlaceDataInterface {


	private int a;
		public PlaceDataServer() throws RemoteException
		{
			System.out.println("New instance of Sample created");
			a=1;
		}


		public String getInfo(String city)throws RemoteException
		{
			try{
				PlaceList placeList = PlaceList.parseFrom(new FileInputStream("places-proto.bin"));
			
			
			double lat,lon=0;
			String state="";
			String str="";
			boolean flag = false;
			for (Place place: placeList.getPlaceList())
			{	
				if(place.getName().equals(city))
				{
					flag = true;
					lat = place.getLat();
					lon = place.getLon();
					state = place.getState();
					str = place.getName()+","+state+","+Double.toString(lat)+","+Double.toString(lon);
					break;
				}
				//System.out.println("Place: " + place.getName());
			}
			if(flag)
			{
				return str;
			}
			else
				return "City Not Found";
			}
			catch(Exception e)
			{
				return "Err occured";
			}
						
			//return "";
		}
	

}
