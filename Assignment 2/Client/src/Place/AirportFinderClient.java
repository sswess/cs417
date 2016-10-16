package Place;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

//import AirportData.AirportDataInterface;
import PlaceData.PlaceDataInterface;


public class AirportFinderClient {
	public static void main(String args[]) {
		//args list: place server, place port, airport server, airport port, city 
		if(args.length < 6){
			System.out.print("Not enough arguments.\n" + 
							"Usage: java AirportFinderClient [place server] [place port]" + 
							"[airport server] [airport port] [city name] [state]");
			System.exit(1);
		}
		try {
			// Attempt to find place server
			int port1 = Integer.parseInt(args[1]);
			String url1 = "//" + args[0] + ":" + args[1] + "/placeServer";
			System.out.println("looking up place server at " + url1);
			// look up the remote object named “PlaceDataInterface”
			PlaceDataInterface placeInterface = (PlaceDataInterface)Naming.lookup(url1);
			
			// Attempt to find airport server
			int port2 = Integer.parseInt(args[3]);
			String url2 = "//" + args[2] + ":" + args[3] + "/airoportServer";
			// look up the remote object named “AirportDataInterface”
			//AirportDataInterface airportInterface = (AirportDataInterface)Naming.lookup(url2);
			
//Return type subject to change
			// Get place information
			String placeData[] = placeInterface.getInfo(args[4], args[5]).split("");
			// Get corresponding airport location
			//String airportData = airportInterface.getAirport(Double.parseDouble(placeData[2]),
			//												Double.parseDouble(placeData[3]));
			
			String temp = "";
			for (int i = 0; i < placeData.length; i++){
				temp += placeData[i];
			}
			
			String[] outputData  = temp.split(" ");
			
		} catch(Exception e) {
			System.out.println("Client exception: " + e);
		}
	}
}
