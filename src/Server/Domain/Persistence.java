package Server.Domain;

import Server.Domain.Model.House;
import Server.Domain.Model.HouseList;
import Server.Domain.Model.Player;
import Server.Domain.Model.PlayerList;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Karolina on 15/05/2017.
 */
public interface Persistence {

    public PlayerList load() throws IOException;
    public HouseList loadHouses() throws IOException;
    public void push() throws IOException;
    public void createPlayer(String nickname, String house) throws IOException;
    public void save(PlayerList playerList) throws IOException;
    public void save (HouseList houseList) throws IOException;
    public void save(Player player) throws IOException;
    public boolean save(House house) throws IOException;
    public void saveScore (String playernick, int score) throws IOException;
    public void saveTotalScore(String faculty) throws IOException;
    public String houseSelection() throws IOException;
    public Player checkPlayer(String nickname) throws IOException;
    public House getHouse(String faculty) throws IOException;
    public ArrayList<Pair<String, Integer>> getLeaderBoard() throws IOException;
    public boolean remove (Player player) throws IOException;
    public void updateBestPlayer(String faculty) throws IOException;
    public boolean clear() throws IOException;


}
