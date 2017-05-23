package Server.Domain.Mediator;


import Server.Domain.DatabaseAdapter;
import Server.Domain.Model.Player;
import Server.Domain.Model.PlayerList;
import Server.Domain.Persistence;

import java.io.IOException;


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
}
