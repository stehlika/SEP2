package Server.Domain.Model;

import java.io.Serializable;

/**
 * Created by Karolina on 15/05/2017.
 */
public class House implements Serializable {
    private String faculty;
    private int totalscore;
    private String bestplayer;

    public House(String faculty, int totalscore, String bestplayer) {
        this.faculty = faculty;
        this.totalscore = totalscore;
        this.bestplayer = bestplayer;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(int totalscore) {
        this.totalscore = totalscore;
    }

    public String getBestplayer() {
        return bestplayer;
    }

    public void setBestplayer(String bestplayer) {
        this.bestplayer = bestplayer;
    }

    @Override
    public String toString() {
        return "House{" +
                "faculty='" + faculty + '\'' +
                ", totalscore=" + totalscore +
                ", bestplayer='" + bestplayer + '\'' +
                '}';
    }
}
