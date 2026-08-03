// Luis Felipe Dos Santos
package Dao;

import Dao.Idao.IDaoPessoa;
import model.Pessoa;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DaoPessoa extends UnicastRemoteObject implements IDaoPessoa {
    private static final long serialVersionUID = 1L;

    private static List<Pessoa> listaPessoa = new ArrayList<>();

    public DaoPessoa() throws RemoteException {
        super();
    }

    @Override
    public boolean incluir(Pessoa novoObj) throws RemoteException {
        for (Pessoa p : listaPessoa) {
            if (p.getCpf().equals(novoObj.getCpf())) {
                System.out.println("[SERVIDOR] Pessoa com CPF " + novoObj.getCpf() + " já existe. Inclusão negada.");
                return false;
            }
        }
        listaPessoa.add(novoObj);
        System.out.println("[SERVIDOR] Pessoa incluída: " + novoObj);
        return true;
    }

    @Override
    public boolean removerPeloCpf(String cpf) throws RemoteException {
        for (Pessoa p : listaPessoa) {
            if (p.getCpf().equals(cpf)) {
                listaPessoa.remove(p);
                System.out.println("[SERVIDOR] Pessoa com CPF " + cpf + " removida.");
                return true;
            }
        }
        System.out.println("[SERVIDOR] Pessoa com CPF " + cpf + " não encontrada para remoção.");
        return false;
    }

    @Override
    public Pessoa consultarPeloCpf(String cpf) throws RemoteException {
        for (Pessoa p : listaPessoa) {
            if (p.getCpf().equals(cpf)) {
                System.out.println("[SERVIDOR] Consulta por CPF " + cpf + ": encontrada.");
                return p;
            }
        }
        System.out.println("[SERVIDOR] Consulta por CPF " + cpf + ": não encontrada.");
        return null;
    }

    @Override
    public List<Pessoa> consultarTodos() throws RemoteException {
        System.out.println("[SERVIDOR] Consultando todos. Total: " + listaPessoa.size() + " pessoa(s).");
        return new ArrayList<>(listaPessoa);
    }
}