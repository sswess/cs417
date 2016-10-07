package PlaceData;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server 
{
	public static void main(String[]args)
	{
        if (args.length != 1) 
        {
            System.err.println("usage: java SampleServer rmi_port");
            System.exit(1);
        }
        try {            
            int port = Integer.parseInt(args[0]);
            String url = "//localhost:" + port;
            System.out.println("binding " + url);
            Naming.rebind(url, new PlaceDataServer());
            System.out.println("server " + url + " is running...");
        }
        catch (Exception e) {
            System.out.println("Sample server failed:" + e.getMessage());
        }
	}

}
