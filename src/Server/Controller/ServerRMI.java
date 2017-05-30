package Server.Controller;

import Client.GameSystem.UserMovement;
import Server.Domain.Mediator.ModelManager;
import Server.Domain.Model.House;
import Server.Domain.Model.Level;
import Server.Domain.Model.Player;
import javafx.util.Pair;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by adamstehlik on 23/05/2017.
 */
public class ServerRMI extends Observable implements RmiService {

    private static final long servialVersionUID = 1L;
    private ModelManager modelManager;

    private class WrapperObserver implements Observer, Serializable {
        private static final long serialVersionUID = 1L;

        private RemoteObserver ro = null;

        WrapperObserver(RemoteObserver ro) {
            this.ro = ro;
        }

        @Override
        public void update(Observable o, Object arg) {
            try {
                ro.update(o.toString(), arg);
            } catch (RemoteException e) {
                System.out.println("Not possible to notify user. ");
            }
        }
    }

    private ServerRMI() {
        this.modelManager = new ModelManager();
    }

    @Override
    public synchronized void addObserver(RemoteObserver o) {
        WrapperObserver mo = new WrapperObserver(o);
        addObserver(mo);
    }

    @Override
    public void addPlayer(Player player) throws IOException {
        modelManager.addPlayer(player);
    }

    static void startServer(int serverPort) throws Exception {
        Registry reg = LocateRegistry.createRegistry(serverPort);
        RmiService rmiService = (RmiService) UnicastRemoteObject.exportObject(new ServerRMI(), serverPort);
        reg.bind("RmiService", rmiService);
    }

    @Override
    public void saveScore(String playerNick, int score) throws IOException {
        modelManager.saveScore(playerNick, score);
    }

    @Override
    public Player checkPlayer(String nickname) throws IOException {
        return modelManager.checkPlayer(nickname);
    }

    @Override
    public ArrayList<Pair<String, Integer>> getLeaderBoard() throws IOException {
        return modelManager.getLeaderboard();
    }

    @Override
    public House getHouse(String faculty) throws IOException {
        return modelManager.getHouse(faculty);
    }

    @Override
    public ArrayList<Pair<String, Integer>> getHouseLeaderBoard(String faculty) throws IOException {
        return modelManager.getHouseLeaderBoard(faculty);
    }

    @Override
    public ArrayList<Integer> getHighscoreForPlayer(String name) throws IOException {
        return modelManager.getHighscoreForPlayer(name);
    }

    @Override
    public void updateUserPosition(UserMovement userMovement) {
        setChanged();
        notifyObservers(userMovement);
    }

    @Override
    public Level getLevel() throws IOException {
        return modelManager.getLevel();
    }

    @Override
    public String houseSelection() throws  IOException {
        return modelManager.houseSelection();
    }
}
