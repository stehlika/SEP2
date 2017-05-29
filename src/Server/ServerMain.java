package Server;

import Server.Controller.ServerController;

/**
 * Created by adamstehlik on 23/05/2017.
 */
public class ServerMain {

    private static String OS = null;
    private static int serverPort;

    public static void main (String args[]) {
        getOsName();
        ServerController.startRMIServer(serverPort);
    }

    private static void getOsName()
    {
        OS = System.getProperty("os.name");

        if(OS != null) {
            if (OS.startsWith("Mac")) {
                serverPort = 9900;
            } else if (OS.startsWith("Windows")) {
                serverPort = 1100;
            } else {
                serverPort = 5000;
            }
        } else {
            serverPort = 2000;
        }
    }
}
