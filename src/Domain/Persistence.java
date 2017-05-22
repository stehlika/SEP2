package Domain;

import Domain.Model.Player;
import Domain.Model.PlayerList;

import java.io.IOException;

/**
 * Created by Karolina on 15/05/2017.
 */
public interface Persistence {

    public PlayerList load() throws IOException;
    public void save(PlayerList playerList) throws IOException;
    public boolean save(Player player) throws IOException;
    public boolean remove (Player player) throws IOException;
    public boolean clear() throws IOException;
  /*public void save(House faculty) throws IOException;*/

}
