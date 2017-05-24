package Server.Domain.Mediator;


import Server.Domain.DatabaseAdapter;
import Server.Domain.Model.House;
import Server.Domain.Model.Player;
import Server.Domain.Model.PlayerList;
import Server.Domain.Persistence;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Karolina on 16/05/2017.
 */
public class ModelManager implements ModelMan {

    private PlayerList playerList;
    private Persistence storage;

    public ModelManager()
    {
        try {
            storage = new DatabaseAdapter();
            playerList = storage.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PlayerList getAll() {
        return playerList;
    }

    @Override
    public void addPlayer(Player player) {
        playerList.addPlayer(player);
    }

    @Override
    public Player removePlayer(String nickname) {
        Player player = playerList.removePlayerByNickname(nickname);
        try {
            storage.remove(player);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return player;
    }

    @Override
    public Player getPlayer(int index) {
        return playerList.getPlayer(index);
    }

    @Override
    public PlayerList getPlayers(String nickname) {
        return playerList.getPlayersByNickname(nickname);
    }

    @Override
    public int getNumberOfPlayers() {
        return playerList.getNumberOfPlayers();
    }

    public ArrayList<Pair<String, Integer>> getLeaderboard() throws IOException {
            return storage.getLeaderBoard();
    }
    public House getHouse(String faculty) throws IOException {
        return storage.getHouse(faculty);
    }

    public String houseSelection(House house) throws IOException
    {
        return storage.houseSelection(house);
    }
    public void checkPlayer(String nickname) throws IOException
    {
        storage.checkPlayer(nickname);
    }
    public void saveScore(String playerNick, int score) throws IOException
    {
        storage.saveScore(playerNick, score);
    }

}
