package PlaceData;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlaceDataInterface extends Remote 
{
	public String getInfo(String city) throws RemoteException;
	
}
