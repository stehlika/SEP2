package Server;

import Server.Controller.ServerController;
import Server.Domain.DatabaseAdapter;
import Server.View.ServerView;

/**
 * Created by adamstehlik on 23/05/2017.
 */
public class ServerMain {

    private static ServerController controller;
    private static ServerView view;

    public static void main (String args[]) {
        view = new ServerView();
        controller = new ServerController(view);
        controller.startRMIServer(4398);
    }
}
