package Server.Domain.Mediator;


import Server.Domain.DatabaseAdapter;
import Server.Domain.Model.House;
import Server.Domain.Model.Level;
import Server.Domain.Model.Player;
import Server.Domain.Persistence;
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Karolina on 16/05/2017.
 */
public class ModelManager implements ModelMan {

    private Persistence storage;
    private Level level;

    public ModelManager() {
        storage = new DatabaseAdapter();
        level = new Level();
    }

    @Override
    public void addPlayer(Player player) {
        try {
            storage.addPlayer(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removePlayer(String nickname) {
        try {
              storage.removePlayer(nickname);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<Pair<String, Integer>> getLeaderboard() throws IOException {
            return storage.getLeaderBoard();
    }
    public House getHouse(String faculty) throws IOException {
        return storage.getHouse(faculty);
    }

    public String houseSelection() throws IOException {
        return storage.houseSelection();
    }

    public Player checkPlayer(String nickname) throws IOException
    {
        return storage.checkPlayer(nickname);
    }
    public void saveScore(String playerNick, int score) throws IOException
    {
        storage.saveScore(playerNick, score);
    }

    public ArrayList<Pair<String,Integer>> getHouseLeaderBoard(String faculty) throws IOException {
        return storage.getHouseLeaderBoard(faculty);
    }

    @Override
    public ArrayList<Integer> getHighscoreForPlayer(String name) throws IOException {
        return storage.getHighscoreForPlayer(name);
    }

    @Override
    public Level getLevel() {
        return level;
    }
}
