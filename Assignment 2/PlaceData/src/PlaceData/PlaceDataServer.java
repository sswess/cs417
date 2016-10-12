package PlaceData;
import java.io.FileInputStream;


import PlaceData.PlaceDataProto.Place;
import PlaceData.PlaceDataProto.PlaceList;


public class PlaceDataServer 
{
	public static void main(String[]args)
	{
		//Place.Builder place = new Place.newBuilder();
		//PlaceList placeList;
		try{	
				PlaceList placeList = PlaceList.parseFrom(new FileInputStream("places-proto.bin"));
				//sol(placeList);
				
				System.out.println(getInfo(placeList, "Hackberry town"));
		}
		catch(Exception e)
		{
			System.out.println("FILE NOT FOUND");
		}
		//System.out.println("Good to go BOI!!");
		
	}
	
		public static void sol(PlaceList placeList)
		{
			for (Place place: placeList.getPlaceList())
			{	
				System.out.println("Place: " + place.getName());
			}
		}
	
		public static String getInfo(PlaceList placeList, String city)
		{
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
			
			
			//return "";
		}
	

}
