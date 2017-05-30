package Client.GameSystem;

import Server.Domain.Model.Player;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by adamstehlik on 28/05/2017.
 */
public class UserMovement implements Serializable {

    private Date timestamp;
    private Player player;
    private String movement;
    private Double newY;

    UserMovement(Player player, String movement, Double newY) {
        this.player = player;
        this.movement = movement;
        this.timestamp = new Date();
        this.newY = newY;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getMovement() {
        return movement;
    }

    public Double getNewY() {
        return newY;
    }

    @Override
    public String toString() {
        return "UserMovement{" +
                "timestamp=" + timestamp +
                ", player=" + player +
                ", movement='" + movement + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMovement that = (UserMovement) o;

        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (player != null ? !player.equals(that.player) : that.player != null) return false;
        return movement != null ? movement.equals(that.movement) : that.movement == null;
    }

}
