package Server.Controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by adamstehlik on 23/05/2017.
 */
public class ServerController {

    public static void startRMIServer(int serverPort) {
        try {
            System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
            System.out.println(InetAddress.getLocalHost());
            ServerRMI.startServer(serverPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
