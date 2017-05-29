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

    UserMovement(Player player, String movement) {
        this.player = player;
        this.movement = movement;
        timestamp = new Date();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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

    public void setMovement(String movement) {
        this.movement = movement;
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

    @Override
    public int hashCode() {
        int result = timestamp != null ? timestamp.hashCode() : 0;
        result = 31 * result + (player != null ? player.hashCode() : 0);
        result = 31 * result + (movement != null ? movement.hashCode() : 0);
        return result;
    }
}
