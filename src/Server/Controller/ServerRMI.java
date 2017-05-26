package Server.Controller;

import Server.Domain.Mediator.ModelManager;
import Server.Domain.Model.House;
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
                e.printStackTrace();
            }
        }
    }

    public ServerRMI() {
        this.modelManager = new ModelManager();
    }

    @Override
    public synchronized void addObserver(RemoteObserver o) {
        WrapperObserver mo = new WrapperObserver(o);
        addObserver(mo);
        System.out.println("Client was connected " + mo);
    }

    @Override
    public void addPlayer(Player player) throws IOException {
        modelManager.addPlayer(player);
    }


    public static void startServer(int serverPort) {
        try {
            Registry reg = LocateRegistry.createRegistry(serverPort);
            RmiService rmiService = (RmiService) UnicastRemoteObject.exportObject(new ServerRMI(), serverPort);
            reg.bind("RmiService", rmiService);
            System.out.println("Server started");
        } catch (Exception e) {
            System.out.println("We were unable to start server");
            e.printStackTrace();
        }
    }


    @Override
    public void saveScore(String playerNick, int score) throws RemoteException, IOException {
        modelManager.saveScore(playerNick, score);

    }

    @Override
    public Player checkPlayer(String nickname) throws RemoteException, IOException {
        return modelManager.checkPlayer(nickname);
    }

    @Override
    public ArrayList<Pair<String, Integer>> getLeaderBoard() throws IOException {
        return modelManager.getLeaderboard();
    }

    @Override
    public House getHouse(String faculty) throws IOException {
        System.out.println("Server RMI class get house ");
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
    public String houseSelection() throws  IOException {
        System.out.println("ServerRMI");
        return modelManager.houseSelection();
    }
}
