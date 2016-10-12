package PlaceData;
import java.rmi.Naming;
import java.rmi.server.*; 
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server 
{
	public static void main(String[]args)
	{/*
		args[0]="8000";
        if (args.length != 1) 
        {
            System.err.println("usage: java SampleServer rmi_port");
            System.exit(1);
        } 
        */
        try {            
            int port = 8888;//Integer.parseInt(args[0]);
            String url = "//localhost:" + 8888;
            System.out.println("binding " + url);
            Naming.rebind(url, new PlaceDataServer());
            System.out.println("server " + url + " is running...");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}

}
