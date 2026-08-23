// Luis Felipe Dos Santos

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DoubleInterface extends Remote {
    String obterBits(double valor) throws RemoteException;
}
