package Server;

import Server.Controller.ServerController;
import Server.View.ServerView;

/**
 * Created by adamstehlik on 23/05/2017.
 */
public class ServerMain {

    private static String OS = null;
    private static ServerController controller;
    private static ServerView view;

    public static void main (String args[]) {
        view = new ServerView();
        controller = new ServerController(view);
        controller.startRMIServer(getServerPort());
    }

    private static int getServerPort()
    {
        OS = System.getProperty("os.name");

        if(OS != null) {
            if (OS.startsWith("Mac")) {
                return  9900;
            } else if (OS.startsWith("Windows")) {
                return 1099;
            } else {
                return 5000;
            }
        } else {
            return 2000;
        }
    }
}
