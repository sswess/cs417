package Place;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import AirportData.AirportDataInterface;
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
			String url1 = "//" + args[0] + ":" + args[1] + "/placeServer";
			System.out.println("looking up place server at " + url1);
			// look up the remote object named “PlaceDataInterface”
			PlaceDataInterface placeInterface = (PlaceDataInterface)Naming.lookup(url1);
			
			// Attempt to find airport server
			String url2 = "//" + args[2] + ":" + args[3] + "/airportServer";
			System.out.println("looking up airport server at " + url2);
			// look up the remote object named “AirportDataInterface”
			AirportDataInterface airportInterface = (AirportDataInterface)Naming.lookup(url2);
			
			// Get place information
			String placeData[] = placeInterface.getInfo(args[4], args[5]).split(",");
			
			// Code, Name, State, Distance
			String[][] airportData = new String[5][4];
			if(!placeData[0].equals("City Not Found") || !placeData[0].equals("Err occured")){
				System.out.println("\nLooking for airports near " + placeData[0] + "...");
				
				// Get corresponding airport location
				airportData = airportInterface.getdistance(Double.parseDouble(placeData[2]),
																Double.parseDouble(placeData[3]));
				
				for (int i = 0; i < 5; i++){
					// Convert distance to miles, output airports.
					int distance = (int)Double.parseDouble(airportData[i][3]) / 1600;
					System.out.println((i + 1) + ": " + airportData[i][0] + " " + airportData[i][1] +
										" " + airportData[i][2] + " " + distance + " miles");
				}
			}
			else { System.out.println("The city " + args[4] + ", " + args[5] + " was not found."); }
			
		} catch(Exception e) {
			System.out.print("Client exception: ");
			e.printStackTrace();
		}
	}
}
