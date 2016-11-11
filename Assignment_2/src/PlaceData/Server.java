package PlaceData;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import java.rmi.server.UnicastRemoteObject;

public class Server 
{
	public static void main(String[]args)
	{
        if (args.length != 1) {
            System.err.println("usage: java PlaceServer rmi_port");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);

        try {            
            LocateRegistry.createRegistry(port);
            String url = "//localhost:" + port + "/placeServer";
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
