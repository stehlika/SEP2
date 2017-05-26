package Server.Domain;

import Server.Domain.Model.House;
import Server.Domain.Model.Player;
import Server.Persistence.Database;
import javafx.util.Pair;
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
    public void addPlayer(Player player) throws IOException {

        String sql = "INSERT INTO sep2_schema.player (nickname, playtime, winratio, faculty) VALUES (? , ? , ? , ?);";
        try {
            db.update(sql, player.getNickname(), player.getPlaytime(), player.getWinratio(), player.getFaculty());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveHouse(House house) throws IOException {
        //metoda na vytvoreni house
        try {
            String sql = "SELECT faculty FROM sep2_schema.house_cup WHERE faculty = ?;";
            ArrayList<Object[]> results = db.query(sql, house.getFaculty());

            if (results.size() > 0) { // not a house
            }

            sql = "INSERT INTO sep2_schema.house_cup (faculty, totalscore, bestplayer) "
                    + "VALUES (? , ? , ?);";


            db.update(sql, house.getFaculty(), house.getTotalscore(), house.getBestplayer());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveScore(String playernick, int score) throws IOException {
        //metoda ktera zapise akorat nahrane skore
        String sql = "INSERT INTO sep2_schema.player_scores (playernick, score)"
                + "VALUES (? , ?);";

        try {
            db.update(sql, playernick, score);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTotalScore(String faculty) throws IOException {
        //metoda ktera zapise nove totalscore house po skonceni hry

        String sql = "UPDATE sep2_schema.house_cup SET totalscore = (SELECT sum(score) FROM sep2_schema.player_scores "
                + "JOIN sep2_schema.player ON player.nickname = player_scores.playernick WHERE player.faculty = ?) "
                + "WHERE faculty = ?;";

        try {
            db.update(sql, faculty);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String houseSelection() throws IOException {
        //metoda na zjisteni v jake fakulte je nejmene hracu
        String sql = "SELECT faculty FROM sep2_schema.house_cup WHERE totalscore = (SELECT min(totalscore) FROM sep2_schema.house_cup)";
        String randomFaculty = "";
        ArrayList<Object[]> result;

        try {
            result = db.query(sql);
            Object[] row = result.get(0);
            randomFaculty = row[0].toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return randomFaculty;
    }


    @Override
    public Player checkPlayer(String nickname) throws IOException
    //metoda se vstupem nickname, projde vsechny players, return player nebo null
    {

        String sql = "SELECT * FROM sep2_schema.player WHERE nickname = ?;";
        Player player = null;
        String username;
        int playtime;
        double winratio;
        String faculty;
        ArrayList<Object[]> result;

        try {
            result = db.query(sql, nickname);


            if (result.size() == 0) return null;
            Object[] row = result.get(0);
            username = row[0].toString();
            playtime = (int) row[1];
            winratio = (double) row[2];
            faculty = row[3].toString();
            player = new Player(username, playtime, winratio, faculty);
            player.setNickname(username);
            player.setPlaytime((int) row[1]);
            player.setWinratio((double) row[2]);
            player.setFaculty(row[3].toString());
            System.out.println(player.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    @Override
    public House getHouse(String faculty) throws IOException {
        //metoda ktera vraci house a vsechny data
        String sql = "SELECT * FROM sep2_schema.house_cup WHERE faculty = ?;";
        House house = null;
        ArrayList<Object[]> result;

        try {
            result = db.query(sql);

            if (result.size() == 0) return null;
            Object[] row = result.get(0);
            house.setFaculty(row[0].toString());
            house.setTotalscore((int) row[1]);
            house.setBestplayer(row[2].toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return house;
    }

    @Override
    public ArrayList<Pair<String, Integer>> getLeaderBoard() throws IOException {
        //leaderboard hracu se skore
        ArrayList<Pair<String, Integer>> leaderboard = new ArrayList<>();

        String sql = "SELECT playernick, max(score) AS score FROM sep2_schema.player_scores GROUP BY playernick ORDER BY score DESC;";
        ArrayList<Object[]> result;
        String playernick = "";
        int score = 0;

        try {
            result = db.query(sql);

            for (int i = 0; i < result.size(); i++) {
                Object[] row = result.get(i);
                playernick = row[0].toString();
                score = (int) row[1];
                leaderboard.add(new Pair<>(playernick, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderboard;
    }

    @Override
    public void removePlayer(String nickname) throws IOException {
        //mazani playera podle nickname
        try {
            String sql = "DELETE FROM sep2database.player WHERE nickname =?;";
            db.update(sql, nickname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBestPlayer(String faculty) throws IOException {
        String sql = "UPDATE sep2_schema.house_cup SET bestplayer=(SELECT playernick  FROM sep2_schema.player_scores JOIN (SELECT nickname FROM sep2_schema.player\n" +
                "  WHERE sep2_schema.player.faculty='?') AS faculty_players ON faculty_players.nickname=player_scores.playernick\n" +
                "WHERE score=(SELECT max(score) FROM sep2_schema.player_scores)) WHERE faculty='?';";
        try {
            db.update(sql, faculty);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Pair<String, Integer>> getHouseLeaderBoard(String faculty) throws IOException {
        //TODO
        return null;
    }

    @Override
    public boolean clear() throws IOException {
        //todo
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