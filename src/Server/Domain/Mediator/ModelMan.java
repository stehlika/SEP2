package Server.Domain.Mediator;

import Server.Domain.Model.House;
import Server.Domain.Model.Player;
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Karolina on 17/05/2017.
 */
public interface ModelMan {

    void addPlayer(Player player);

    void removePlayer(String nickname);

    ArrayList<Pair<String, Integer>> getLeaderboard() throws IOException;

    House getHouse(String faculty) throws IOException;

    String houseSelection() throws IOException;

    Player checkPlayer(String nickname) throws IOException;

    void saveScore(String playerNick, int score) throws IOException;

    ArrayList<Pair<String, Integer>> getHouseLeaderBoard(String faculty) throws IOException;

    ArrayList<Integer> getHighscoreForPlayer(String name) throws IOException;

}
