package Server.Controller;

import Server.View.ServerView;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by adamstehlik on 23/05/2017.
 */
public class ServerController {

    private ServerView view;
    public ServerController(ServerView view) {
        this.view = view;
        this.view.showStartUPScreen();
    }

    public void startRMIServer(int serverPort) {
        try {
            System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
            try {
                ServerRMI.startServer(serverPort);
                view.say("Server is running on: " + InetAddress.getLocalHost().getHostAddress() + " with port: " + serverPort);
            } catch (Exception e) {
                e.printStackTrace();
                view.say("Sorry we were unable to start server error message: \n" + e.getMessage());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            view.say("We were unable to detect your IP Address");
        }
    }
}
