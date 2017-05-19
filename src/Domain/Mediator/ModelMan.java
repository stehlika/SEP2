package Domain.Mediator;

import Domain.Model.House;
import Domain.Model.Player;
import Domain.Model.PlayerList;

/**
 * Created by Karolina on 17/05/2017.
 */
public interface ModelMan {
    PlayerList getAll();
    void addPlayer(Player player);
    Player removePlayer(String nickname);
    Player getPlayer(int index);
    PlayerList getPlayers(String nickname);
    int getNumberOfPlayers();

    /*void addFaculty(House faculty);
    public House getFaculty(String faculty);*/
}
