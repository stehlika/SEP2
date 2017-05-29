package Server.Controller;

import Client.GameSystem.UserMovement;
import Server.Domain.Model.House;
import Server.Domain.Model.Level;
import Server.Domain.Model.Player;
import javafx.util.Pair;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by adamstehlik on 23/05/2017.
 */
public interface RmiService extends Remote {

    void addObserver(RemoteObserver o) throws RemoteException;

    Player checkPlayer(String nickname) throws IOException;

    void addPlayer(Player player) throws IOException;

    String houseSelection() throws IOException;

    void saveScore(String playernick, int score) throws IOException;

    ArrayList<Pair<String, Integer>> getLeaderBoard() throws IOException;

    House getHouse(String faculty) throws IOException;

    ArrayList<Pair<String, Integer>> getHouseLeaderBoard(String faculty) throws IOException;

    ArrayList<Integer> getHighscoreForPlayer(String name) throws IOException;

    void updateUserPosition(UserMovement userMovement) throws IOException;

    Level getLevel() throws IOException;

}
