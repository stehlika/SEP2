package Server.Controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by adamstehlik on 23/05/2017.
 */
public interface RemoteObserver extends Remote {
    void update(Object observable, Object msg) throws RemoteException;

}
