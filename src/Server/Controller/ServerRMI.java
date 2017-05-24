package Server.Controller;

import Server.Domain.Mediator.ModelMan;
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
import java.util.HashMap;
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

    @Override
    public synchronized void addObserver(RemoteObserver o) {
        WrapperObserver mo = new WrapperObserver(o);
        addObserver(mo);
        System.out.println("Client was connected " + mo);
    }

    public static void startServer(int serverPort) {
        try {
            Registry reg = LocateRegistry.createRegistry(serverPort);
            RmiService rmiService = (RmiService) UnicastRemoteObject.exportObject(new ServerRMI(), serverPort);
            reg.bind("RmiService", rmiService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPlayer(Player player) throws RemoteException, IOException {
        modelManager.addPlayer(player);

    }

    @Override
    public void saveScore(String playerNick, int score) throws RemoteException, IOException {
        modelManager.saveScore(playerNick, score);

    }

    @Override
    public void checkPlayer(String nickname) throws RemoteException, IOException {
        modelManager.checkPlayer(nickname);
    }

    @Override
    public ArrayList<Pair<String, Integer>> getLeaderBoard() throws RemoteException, IOException {
        return modelManager.getLeaderboard();
    }

    @Override
    public House getHouse(String faculty) throws RemoteException, IOException {
        return modelManager.getHouse(faculty);

    }

    @Override
    public String houseSelection(House house) throws RemoteException, IOException {
        return modelManager.houseSelection(house);
    }
}