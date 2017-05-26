package Client.Quidditch.Controller;

import Server.Controller.RemoteObserver;
import Server.Controller.RmiService;
import Server.Domain.Model.House;
import Server.Domain.Model.Player;
import javafx.util.Pair;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by adamstehlik on 25/05/2017.
 */
public class ClientRMI extends UnicastRemoteObject implements RemoteObserver {

    private static final long servialVersionUID = 1L;
    private RmiService rmiService;

    private static ClientRMI instance = null;
    private ClientRMI() throws RemoteException {

    }

    public static ClientRMI getInstance() {
        if(instance == null) {
            try {
                instance = new ClientRMI();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public void startClient(String ipAddress, int port) {
        try {
            rmiService = (RmiService) Naming.lookup("rmi://" + ipAddress + ":" + port + "/RmiService");
            ClientRMI clientRMI = new ClientRMI();
            rmiService.addObserver(clientRMI);
        } catch (Exception e) {
            e.printStackTrace();
            MasterController.showAlertView("Sorry connection to server failed.", 404);
        }
    }


    public void addPlayer(Player player) throws  IOException {
        try {
            rmiService.addPlayer(player);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void saveScore(String playerNick, int score) throws IOException {
        try {
            rmiService.saveScore(playerNick, score);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public Player checkPlayer(String nickname) throws IOException {
        try {
            Player player;
            try {
                player = rmiService.checkPlayer(nickname);
                return player;
            } catch (NullPointerException e) {
//                System.out.println("Tu sme ");
                return null;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Pair<String, Integer>> getLeaderBoard() throws IOException {
        try {
            return rmiService.getLeaderBoard();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Pair<String, Integer>> getHouseLeaderBoard(String house) throws IOException {
        try {
            return rmiService.getHouseLeaderBoard(house);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public House getHouse(String faculty) throws IOException {
        try {
//            System.out.println("Client RMI class get house ");
            return rmiService.getHouse(faculty);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String houseSelection() throws IOException {
        try {
//            System.out.println("Client RMI class");
            return rmiService.houseSelection();
        } catch (RemoteException e) {
            e.printStackTrace();
            return "";
        }
    }

    public ArrayList<Integer> getHighScoreForPlayer(String nickname) throws IOException {
        try {
            return rmiService.getHighscoreForPlayer(nickname);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Object observable, Object msg) throws RemoteException {

    }
}




/*

    @Override
    public void update(Object observable, Object updateMsg) throws RemoteException {
        System.out.println("New trip created:" + updateMsg);
    }

*/