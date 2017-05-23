package Server.Controller;

import Server.Domain.Model.House;
import Server.Domain.Model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * Created by adamstehlik on 23/05/2017.
 */
public interface RmiService extends Remote {

    void addObserver(RemoteObserver o) throws RemoteException;

    void addPlayer(Player player) throws RemoteException;
    void saveScore(String playerNick, int score) throws RemoteException;
    void checkPlayer(String nickname) throws RemoteException;
    HashMap<String, Integer> getLeaderBoard() throws RemoteException;
    House getHouose(String faculty) throws RemoteException;
    String houseSelection(House house) throws RemoteException;

}
