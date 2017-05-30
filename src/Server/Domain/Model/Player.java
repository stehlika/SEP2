package Server.Domain.Model;

import java.io.Serializable;

/**
 * Created by Karolina on 15/05/2017.
 */
public class Player implements Serializable {
    private String nickname;
    private int playtime;
    private double winratio;
    private String faculty;

    public Player(String nickname, String faculty) {
        this.nickname = nickname;
        this.playtime = 0;
        this.winratio = 0;
        this.faculty = faculty;
    }

    public Player(String nickname, int playtime, double winratio, String faculty) {
        this.nickname = nickname;
        this.playtime = playtime;
        this.winratio = winratio;
        this.faculty = faculty;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    public double getWinratio() {
        return winratio;
    }

    public void setWinratio(double winratio) {
        this.winratio = winratio;
    }

    public void setFaculty(String faculty)
    {
        this.faculty = faculty;
    }

    public String getFaculty() {
        return faculty;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", playtime=" + playtime +
                ", winratio=" + winratio +
                ", faculty='" + faculty + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (playtime != player.playtime) return false;
        if (Double.compare(player.winratio, winratio) != 0) return false;
        if (nickname != null ? !nickname.equals(player.nickname) : player.nickname != null) return false;
        return faculty != null ? faculty.equals(player.faculty) : player.faculty == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = nickname != null ? nickname.hashCode() : 0;
        result = 31 * result + playtime;
        temp = Double.doubleToLongBits(winratio);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (faculty != null ? faculty.hashCode() : 0);
        return result;
    }
}
