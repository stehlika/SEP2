package Client.Quidditch.GameSystem;

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
}
