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

    //TODO save metody, clear

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
    public void save(Player player) throws IOException {

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
    public void clear() throws IOException {
        String sql = "TRUNCATE TABLE player.";
    }
}