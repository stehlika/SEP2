package Server.Domain;

import Server.Domain.Model.House;
import Server.Domain.Model.Player;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Karolina on 15/05/2017.
 */
public interface Persistence {

    void addPlayer(Player player) throws IOException;

    void saveScore(String playernick, int score) throws IOException;

    void updateTotalScore(String faculty) throws IOException;
    //TODO : zavolat bud vo vnutri metody save score alebo potom
    // niekde vonku normalne vonku ked skonci hra tak save score
    // update score

    String houseSelection() throws IOException;

    Player checkPlayer(String nickname) throws IOException;

    House getHouse(String faculty) throws IOException;

    ArrayList<Pair<String, Integer>> getLeaderBoard() throws IOException;

    void removePlayer(String nickname) throws IOException;

    void updateBestPlayer(String faculty) throws IOException;
    //TODO : zavolat bud

    ArrayList<Pair<String, Integer>> getHouseLeaderBoard(String faculty) throws IOException;

    boolean clear() throws IOException;

    ArrayList<Integer> getHighscoreForPlayer(String nickname) throws IOException;


}
