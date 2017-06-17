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
    private static final String URL = "jdbc:postgresql://postgresql.websupport.sk:5432/g1fz7wy5";
    private static final String USER = "g1fz7wy5";
    private static final String PASSWORD = "1b7pKT16bX";

    public DatabaseAdapter() {
        try {
            this.db = new Database(DRIVER, URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    /**
     * The method adding the player to the database (the Player object is created is SignInController).
     * @param player
     * @throws IOException
     */
    @Override
    public void addPlayer(Player player) throws IOException {

        String sql = "INSERT INTO sep2_schema.player (nickname, playtime, winratio, faculty) VALUES (? , ? , ? , ?);";
        try {
            db.update(sql, player.getNickname(), player.getPlaytime(), player.getWinratio(), player.getFaculty());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * The method adding data to player_score table (including username and the score)
     * @param playernick
     * @param score
     * @throws IOException
     */
    @Override
    public void saveScore(String playernick, int score) throws IOException {
        String sql = "INSERT INTO sep2_schema.player_scores (playernick, score)"
                + "VALUES (? , ?);";

        try {
            db.update(sql, playernick, score);
            updateEverything(playernick,score);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * The method callled if the new gained score in GameSystemSingle is higher than the player's highest score. Method checks for players new highest score.
     * @param faculty
     * @throws IOException
     */
    @Override
    public void updateTotalScore(String faculty) throws IOException {
        String sql = "UPDATE sep2_schema.house_cup SET totalscore = (SELECT sum(score) FROM sep2_schema.player_scores "
                + "JOIN sep2_schema.player ON player.nickname = player_scores.playernick WHERE player.faculty = ?) "
                + "WHERE faculty = ?;";

        try {
            db.update(sql, faculty);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method returning the house which has the least total score (all scores gaind by all house members).
     * @return
     * @throws IOException
     */
    @Override
    public String houseSelection() throws IOException {
        String sql = "SELECT faculty FROM sep2_schema.house_cup WHERE totalscore = (SELECT min(totalscore) FROM sep2_schema.house_cup)";
        String randomFaculty = "";
        ArrayList<Object[]> result;

        try {
            result = db.query(sql);
            Object[] row = result.get(0);
            randomFaculty = row[0].toString();
            return randomFaculty;
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * Method checking whether the given Player exists in the database. If he/she does, it returns the object Player with all data. If not, null is returned (and Player is created in controller).
     * @param nickname
     * @return
     * @throws IOException
     */
    @Override
    public Player checkPlayer(String nickname) throws IOException
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    /**
     * Method returning House object based on the house name.
     * @param faculty
     * @return
     * @throws IOException
     */
    @Override
    public House getHouse(String faculty) throws IOException {
        String sql = "SELECT * FROM sep2_schema.house_cup WHERE faculty = ?;";
        House house = null;
        ArrayList<Object[]> result;

        try {
            result = db.query(sql, faculty);

            if (result.size() == 0) return null;
            Object[] row = result.get(0);
            String houseFaculty = row[0].toString();
            int houseTotalScore  = (int) row[1];
            String houseBestPlayer = row[2].toString();
            house = new House(houseFaculty, houseTotalScore, houseBestPlayer);
            return house;
        } catch (SQLException e) {
            e.printStackTrace();
            return house;
        }
    }

    /**
     * Method returning the player_score table in form of ArrayList of Pairs.
     * @return
     * @throws IOException
     */
    @Override
    public ArrayList<Pair<String, Integer>> getLeaderBoard() throws IOException {
        ArrayList<Pair<String, Integer>> leaderboard = new ArrayList<>();

        String sql = "SELECT playernick, max(score) AS score FROM sep2_schema.player_scores GROUP BY playernick ORDER BY score DESC;";
        ArrayList<Object[]> result;
        String playernick = "";
        int score = 0;

        try {
            result = db.query(sql);

            for (Object[] row : result) {
                playernick = row[0].toString();
                score = (int) row[1];
                leaderboard.add(new Pair<>(playernick, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    /**
     * Method deleting the player from database by the given nickname.
     * @param nickname
     * @throws IOException
     */
    @Override
    public void removePlayer(String nickname) throws IOException {
        try {
            String sql = "DELETE FROM sep2database.player WHERE nickname =?;";
            db.update(sql, nickname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method updating the Player with the highest score for the given house.
     * @param faculty
     * @throws IOException
     */
    @Override
    public void updateBestPlayer(String faculty) throws IOException {
        String sql = "UPDATE sep2_schema.house_cup SET bestplayer=(SELECT playernick  FROM sep2_schema.player_scores JOIN (SELECT nickname FROM sep2_schema.player\n" +
                "  WHERE sep2_schema.player.faculty=?) AS faculty_players ON faculty_players.nickname=player_scores.playernick\n" +
                "WHERE score=(SELECT max(score) FROM sep2_schema.player_scores)) WHERE faculty=?;";
        try {
            db.update(sql, faculty);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method returning the player_score table in form of ArrayList of Pairs for given house.
     * @param faculty
     * @return
     * @throws IOException
     */
    @Override
    public ArrayList<Pair<String, Integer>> getHouseLeaderBoard(String faculty) throws IOException {
        ArrayList<Pair<String, Integer>> leaderboard = new ArrayList<>();

        String sql = "SELECT playernick, max(score) AS score FROM sep2_schema.player_scores JOIN sep2_schema.player ON (playernick = nickname) WHERE faculty=? GROUP BY playernick ORDER BY score DESC;";
        ArrayList<Object[]> result;
        String playernick = "";
        int score = 0;

        try {
            result = db.query(sql, faculty);
            for (Object[] row : result) {
                playernick = row[0].toString();
                score = (int) row[1];
                leaderboard.add(new Pair<>(playernick, score));
            }
            return leaderboard;
        } catch (SQLException e) {
            e.printStackTrace();
            return leaderboard;
        }
    }

    /**
     * Method returning ArrayList of Integers - all scores gained by given player.
     * @param nickname
     * @return
     * @throws IOException
     */
    @Override
    public ArrayList<Integer> getScoresForPlayer(String nickname) throws IOException {
        String sql = "SELECT score FROM sep2_schema.player_scores WHERE playernick= ? ORDER BY score DESC;";
        ArrayList<Object[]> result;
        ArrayList<Integer> scores=new ArrayList<>();
        int score = 0;
        try {
            result = db.query(sql,nickname);
            for (Object[] row : result) {
                score = (int) row[0];
                scores.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }

    /**
     * Method called after each game to update the data in database.
     * @param nickname
     * @param score
     */
    private void updateEverything(String nickname,int score){
        String sqlTemp = "SELECT faculty FROM sep2_schema.player WHERE nickname=?";
        String faculty;
        ArrayList<Object[]> result;
        try {
            result=db.query(sqlTemp,nickname);
            Object[] row=result.get(0);
            faculty=row[0].toString();
            String sql = "UPDATE sep2_schema.house_cup SET bestplayer=( " +
                    "SELECT playernick FROM sep2_schema.player_scores WHERE score=( " +
                    "SELECT max(ps.score) FROM sep2_schema.player_scores ps JOIN sep2_schema.player p ON ps.playernick = p.nickname " +
                    "WHERE p.faculty=?) GROUP BY playernick) WHERE faculty=?;";
            db.update(sql,faculty,faculty);
            String sql2 = "UPDATE sep2_schema.house_cup SET totalscore = (SELECT sum(score) FROM sep2_schema.player_scores "
                    + "JOIN sep2_schema.player ON player.nickname = player_scores.playernick WHERE player.faculty = ?) "
                    + "WHERE faculty = ?;";

            db.update(sql2,faculty,faculty);
            String sql3= "UPDATE sep2_schema.player " +
                    "SET playtime=(SELECT sum(score) FROM sep2_schema.player_scores WHERE playernick=?) WHERE nickname=?;";
            db.update(sql3,nickname,nickname);



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}