
// Luis Felipe Dos Santos
package Dao.Idao;

import model.Pessoa;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IDaoPessoa extends Remote {
    public boolean incluir(Pessoa novoObj) throws RemoteException;
    public boolean removerPeloCpf(String cpf) throws RemoteException;
    public Pessoa consultarPeloCpf(String cpf) throws RemoteException;
    public List<Pessoa> consultarTodos() throws RemoteException;
}
