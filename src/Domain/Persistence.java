package Domain;

import Domain.Model.House;
import Domain.Model.HouseList;
import Domain.Model.Player;
import Domain.Model.PlayerList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Karolina on 15/05/2017.
 */
public interface Persistence {

    public PlayerList load() throws IOException;
    public HouseList loadHouses() throws IOException;
    public void save(PlayerList playerList) throws IOException;
    public void save (HouseList houseList) throws IOException;
    public void save(Player player) throws IOException;
    public boolean save(House house) throws IOException;
    public void saveScore (String playernick, int score) throws IOException;
    public String houseSelection(House house) throws IOException;
    public Player checkPlayer(String nickname) throws IOException;
    public House getHouse(String faculty) throws IOException;
    public HashMap<String, Integer> getLeaderBoard() throws IOException;
    public boolean remove (Player player) throws IOException;
    public boolean clear() throws IOException;


}
