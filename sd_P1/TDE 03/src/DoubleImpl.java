// Luis Felipe Dos Santos

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DoubleImpl extends UnicastRemoteObject implements DoubleInterface {

    private static final long serialVersionUID = 1L;

    public DoubleImpl() throws RemoteException {
        super();
    }

    @Override
    public String obterBits(double valor) throws RemoteException {

        long bits = Double.doubleToRawLongBits(valor);

        StringBuilder sb = new StringBuilder();

        for (int i = 63; i >= 0; i--) {

            long bit = (bits >> i) & 1;
            sb.append(bit);

            if (i == 63 || i == 52) {
                sb.append(" | ");
            }
        }

        return sb.toString();
    }
}
