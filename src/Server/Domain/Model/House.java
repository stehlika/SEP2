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

    public int getTotalscore() {
        return totalscore;
    }

    public String getBestplayer() {
        return bestplayer;
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
