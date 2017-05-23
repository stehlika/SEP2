package Domain;

import Domain.Model.House;
import Domain.Model.HouseList;
import Domain.Model.Player;
import Domain.Model.PlayerList;
import Persistence.Database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Karolina on 15/05/2017.
 */
public class DatabaseAdapter implements Persistence {

    private Database db;
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://postgresql.websupport.sk:5432/sep2database";
    private static final String USER = "sep2database";
    private static final String PASSWORD = "0PXU4hYoGw";


    public DatabaseAdapter() {
        try {
            this.db = new Database(DRIVER, URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PlayerList load() throws IOException
    {
        String sql = "SELECT nickname, playtime, winratio, faculty FROM sep2_schema.player";
        PlayerList list = new PlayerList();
        ArrayList<Object[]> result = new ArrayList<>();
        String nickname = "?", faculty = "?";
       int playtime;
       double winratio;

        try {
            result = db.query(sql);

            for (int i = 0; i < result.size(); i++)
            {
                Object[] row = result.get(i);
                nickname = row [0].toString();
                playtime = (int) row [1];
                winratio = (double) row [2];
                faculty = row [3].toString();
                System.out.println(nickname + " " + playtime + " " + winratio + " " + faculty);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    @Override
    public HouseList loadHouses() throws IOException {
        String sql = "SELECT faculty, totalscore, bestplayer FROM sep2schema.house_cup";
        HouseList list = new HouseList();
        ArrayList<Object[]> result = new ArrayList<>();
        String faculty = "?", bestplayer = "?";
        int totalscore;

        try {
            result = db.query(sql);

            for (int i = 0; i < result.size(); i++)
            {
                Object[] row = result.get(i);
                faculty = row [0].toString();
                totalscore = (int) row [1];
                bestplayer = row [2].toString();
                System.out.println(faculty + " " + totalscore+ " " + bestplayer);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    @Override
    public void save(PlayerList playerList) throws IOException
    {
        for (int i=0; i<playerList.getNumberOfPlayers(); i++)
        {
            save(playerList.getPlayer(i));
        }
    }

    @Override
    public void save(Player player) throws IOException {
        try {
           /* String sql = "SELECT nickname FROM sep2_schema.player WHERE nickname = ?;";
            ArrayList<Object[]> results = db.query(sql, player.getNickname());

            if (results.size() > 0) { // not a new player
                return false; // do nothing
            }*/

           String sql = "INSERT INTO sep2_schema.player (nickname, playtime, winratio, faculty) "
                    + "VALUES (? , ? , ? , ?);";


            db.update(sql, player.getNickname(), player.getPlaytime(), player.getWinratio(), player.getFaculty());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String houseSelection(House house) throws IOException {
        String sql = "SELECT faculty FROM sep2schema.house_cup WHERE totalscore = (SELECT min(totalscore) FROM sep2_schema.house_cup)";
        String randomFaculty = "";
        ArrayList<Object[]> result;

        try {
            result = db.query(sql, house.getFaculty());
            Object [] row = result.get(0);
            randomFaculty = row[0].toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return randomFaculty;
    }

    @Override
    public Player checkPlayer(String nickname) throws IOException {

        String sql = "SELECT * FROM sep2_schema.player WHERE nickname = ?;";
        Player player = null;

        try {
            ResultSet resultSet = (ResultSet) db.query(sql, nickname);

            if(resultSet.equals(null)) return null;

            player = new Player(resultSet.getString("nickname"),
                    resultSet.getInt("playtime"),
                    resultSet.getDouble("winratio"),
                    resultSet.getString("faculty"));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return player;
    }

    @Override
    public House getHouse(String faculty) throws IOException {
        String sql = "SELECT * FROM sep2_schema.house_cup WHERE faculty = ?;";
        House house = null;

        try {
            ResultSet resultSet = (ResultSet) db.query(sql, faculty);

            if(resultSet.equals(null)) return null;

            house = new House(resultSet.getString("faculty"),
                    resultSet.getInt("totalscore"),
                    resultSet.getString("bestplayer"));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return house;
    }

    @Override
    public HashMap<String, Integer> getLeaderBoard() throws IOException {
        HashMap<String, Integer> leaderboard = new HashMap<>();

        String sql = "SELECT playernick, max(score) AS score FROM sep2_schema.player_scores GROUP BY playernick ORDER BY score DESC;";
        ArrayList<Object[]> result;
        String playernick = "";
        int score = 0;

        try {
            result = db.query(sql);

            for(int i =0; i<3; i++)
            {
                Object[] row = result.get(i);
                playernick = row [0].toString();
                score = (int) row [1];
                leaderboard.put(playernick, score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderboard;
    }

    @Override
    public boolean remove(Player player) throws IOException {
        try {
            String sql = "DELETE FROM sep2database.player WHERE nickname =?;";
            db.update(sql, player);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean clear() throws IOException {
        try {
            String sql = "TRUNCATE TABLE sep2database.sep2_schema CASCADE;";
            db.update(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return false;
    }
}