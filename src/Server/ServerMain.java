package Server;

import Server.Controller.ServerController;

/**
 * Created by adamstehlik on 23/05/2017.
 */
public class ServerMain {

    public static void main (String args[]) {
        ServerController.startRMIServer(9998);

    }


}
