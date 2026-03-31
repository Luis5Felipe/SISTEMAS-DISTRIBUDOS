// LUis Felipe Dos Santos

package infra;
import Dao.DaoPessoa;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            DaoPessoa daoPessoa = new DaoPessoa();
            registry.rebind("DaoPessoa", daoPessoa);
            System.out.println("[SERVIDOR] Servidor RMI iniciado e DaoPessoa registrado com sucesso.");
            System.out.println("[SERVIDOR] Aguardando conexões na porta 1099...");
        } catch (Exception e) {
            System.err.println("[SERVIDOR] Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}