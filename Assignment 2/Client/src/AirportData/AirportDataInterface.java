package AirportData;

import java.rmi.Remote;
import java.rmi.RemoteException;



public interface AirportDataInterface extends Remote 
{
	public String[][] getdistance(double lat1, double lon1) throws RemoteException;
		
}	

