package Domain;

import Domain.Model.Player;
import Domain.Model.PlayerList;
import Persistence.Database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


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
        String sql = "SELECT nickname, playtime, winratio, faculty FROM sep2database.player";
        PlayerList list = new PlayerList();
        ArrayList<Object[]> result = new ArrayList<>();
        String nickname = "?", playtime = "?", winratio = "?", faculty = "?";

        try {
            result = db.query(sql);

            for (int i = 0; i < result.size(); i++)
            {
                Object[] row = result.get(i);
                nickname = row [0].toString();
                playtime = row [1].toString();
                winratio = row [2].toString();
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
    public void save(PlayerList playerList) throws IOException
    {
        for (int i=0; i<playerList.getNumberOfPlayers(); i++)
        {
            save(playerList.getPlayer(i));
        }
    }

    @Override
    public boolean save(Player player) throws IOException {
        try {
            String sql = "SELECT nickname FROM sep2database.player WHERE nickname = ?;";
            ArrayList<Object[]> results = db.query(sql, player.getNickname());

            if (results.size() > 0) { // not a new player
                return false; // do nothing
            }

            sql = "INSERT INTO sep2database.player (nickname, playtime,winratio, faculty) "
                    + "VALUES (? , ? , ? , ?);";

            String temp = player.getNickname();

            db.update(sql, player.getNickname(), player.getPlaytime(), player.getWinratio(), player.getFaculty(),
                    "No uploads", "No uploads");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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