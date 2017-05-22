package Domain.Model;

import java.util.ArrayList;

/**
 * Created by Karolina on 18/05/2017.
 */
public class PlayerList {
    private ArrayList<Player> players;

    public PlayerList() {
        this.players = new ArrayList<>();
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public PlayerList getPlayersByNickname(String nickname) {
        PlayerList list = new PlayerList();

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNickname().equalsIgnoreCase(nickname)) {
                list.addPlayer(players.get(i));
            }
        }
        return list;
    }
    public void addPlayer(Player player) {
        if (!players.contains(player))
            players.add(player);
    }

    public int getNumberOfPlayers()
    {
        return players.size();
    }

    public void removePlayerByIndex(int index)
    {
        players.remove(index);
    }
    public Player removePlayerByNickname(String nickname)
    {
        Player player = null;
        for (int i = 0; i < players.size(); i++)
        {
            if (players.get(i).getNickname().equalsIgnoreCase(nickname))
            {
                player = players.get(i);
                removePlayerByIndex(i);
                break;
            }
        }
        return player;
    }

    public String toString()
    {
        String all = "";
        for (int i = 0; i < players.size(); i++)
        {
            all += "\nPlayer #" + (i + 1) + "\n" + players.get(i);
            if (i < players.size() - 1)
                all += "\n";
        }
        return all;
    }
}
