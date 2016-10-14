package PlaceData;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import java.rmi.server.UnicastRemoteObject;

public class Server 
{
	public static void main(String[]args)
	{

        try {            
            LocateRegistry.createRegistry(3005);
            String url = "//localhost:" + 3005 + "/Sample";
            System.out.println("binding " + url);
            Naming.rebind(url, new PlaceDataServer());
            System.out.println("server " + url + " is running...");
           
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
	}

}
