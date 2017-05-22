package Domain.Model;

import java.time.LocalTime;

/**
 * Created by Karolina on 15/05/2017.
 */
public class Player {
    private String nickname;
    private LocalTime playtime;
    private double winratio;
    /*  private String faculty;*/
    private House faculty;

    public Player(String nickname, LocalTime playtime, double winratio, House faculty) {
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

    public LocalTime getPlaytime() {
        return playtime;
    }

    public void setPlaytime(LocalTime playtime) {
        this.playtime = playtime;
    }

    public double getWinratio() {
        return winratio;
    }

    public void setWinratio(double winratio) {
        this.winratio = winratio;
    }

    public House getFaculty() {
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
}
