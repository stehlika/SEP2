package Client.Controller;

import Client.GameSystem.GameSystemMulti;
import Client.GameSystem.UserMovement;
import Server.Controller.RemoteObserver;
import Server.Controller.RmiService;
import Server.Domain.Model.House;
import Server.Domain.Model.Level;
import Server.Domain.Model.Player;
import javafx.util.Pair;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by adamstehlik on 25/05/2017.
 */
public class ClientRMI extends UnicastRemoteObject implements RemoteObserver {

    private static final long servialVersionUID = 1L;
    private static ClientRMI instance = null;
    private RmiService rmiService;

    /**
     * Method returning the user back to main screen after he clicks on arrow button.
     *
     * @param backBtn     back - button
     * @param actionEvent a button click
     */

    /**
     * Private constructor to ensure Singleton design pattern.
     * @throws RemoteException
     */
    private ClientRMI() throws RemoteException {

    }

    /**
     * Methor returning instance of ClientRMI, very important part of Singleton design pattern
     */
    public static ClientRMI getInstance() {
        if (instance == null) {
            try {
                instance = new ClientRMI();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * Method that is starting RMI connection between server and client.
     * @param ipAddress - address on which is server running
     * @param port - server port
     * @throws Exception when connection to the server  was not successful
     */
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


    /**
     * Mathod for creating new player into the system.
     * @param player - player to be added to the database and system
     * @throws IOException when some connection issues occur
     */
    public void addPlayer(Player player) throws IOException {
        try {
            rmiService.addPlayer(player);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for saving new played score for one game.
     * @param playerNick - to identify which user played that score
     * @param score - score itself in seconds
     * @throws IOException - when some connection issues occur
     */
    public void saveScore(String playerNick, int score) throws IOException {
        try {
            rmiService.saveScore(playerNick, score);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for checking existence for user with given nickname
     * @param nickname - nickname of requested user
     * @returns Player object if user with nickname exists, if not returns null.
     * @throws IOException - when some connection issues occur
     */
    public Player checkPlayer(String nickname) throws IOException {
        try {
            Player player;
            try {
                player = rmiService.checkPlayer(nickname);
                return player;
            } catch (NullPointerException e) {
                return null;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method getLeaderBoard is useful when user wants to know how his opponents are coping
     * @returns ArrayList of pairs consisting of Username and total score played.
     * @throws IOException - when some connection issues occur
     */
    public ArrayList<Pair<String, Integer>> getLeaderBoard() throws IOException {
        try {
            return rmiService.getLeaderBoard();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method is taking care of all scores for each House. Method is used in House Cup View
     * @param house - House name for getting scores with corresponding name
     * @returns array list of pairs constisitng of String and Integers (nicknames and total score played)
     * @throws IOException - when some connection issues occur
     */
    public ArrayList<Pair<String, Integer>> getHouseLeaderBoard(String house) throws IOException {
        try {
            return rmiService.getHouseLeaderBoard(house);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method for getting whole House object
     * @param faculty - is necessary for determining which house user wants to return.
     * @returns entire House object
     * @throws IOException - when some connection issues occur
     */
    public House getHouse(String faculty) throws IOException {
        try {
            return rmiService.getHouse(faculty);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method for selecting right House for each new user. Method is called on user Sign Up
     * @returns String with House name.
     * @throws IOException - when some connection issues occur
     */
    public String houseSelection() throws IOException {
        try {
            return rmiService.houseSelection();
        } catch (RemoteException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * When user wants to know all his/her scores of all time.
     * @param nickname - for knowing which users wants to know score
     * @returns ArrayList os Integer scores for each game
     * @throws IOException - when some connection issues occur
     */
    public ArrayList<Integer> getHighScoreForPlayer(String nickname) throws IOException {
        try {
            return rmiService.getScoresForPlayer(nickname);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Crucial method of Multi-player game. Method is notifying players of other user actions.
     * @param observable - each connected aspect of system
     * @param msg - user Action
     * @throws RemoteException - when some connection issues occur
     */
    @Override
    public void update(Object observable, Object msg) throws RemoteException {
        GameSystemMulti.getInstance().updateUser2((UserMovement) msg);
    }

    /**
     * Sending user actions to the server
     * @param userMovement - class designed specially for user actions
     */
    public void userUpdate(UserMovement userMovement) {
        try {
            rmiService.updateUserPosition(userMovement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * For multiplayer game it is necessary that each user would see the same environment.
     * @returning whole Game level with all coordinates for each object
     */
    public Level getLevel() {
        try {
            return rmiService.getLevel();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
