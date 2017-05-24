package Server.Controller;

import Server.Domain.Model.House;
import Server.Domain.Model.Player;
import javafx.util.Pair;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by adamstehlik on 23/05/2017.
 */
public interface RmiService extends Remote {

    void addObserver(RemoteObserver o) throws RemoteException;

    void addPlayer(Player player) throws RemoteException, IOException;
    void saveScore(String playerNick, int score) throws RemoteException, IOException;
    void checkPlayer(String nickname) throws RemoteException, IOException;
    ArrayList<Pair<String, Integer>> getLeaderBoard() throws RemoteException, IOException;
    House getHouse(String faculty) throws RemoteException, IOException;
    String houseSelection() throws RemoteException, IOException;

}
