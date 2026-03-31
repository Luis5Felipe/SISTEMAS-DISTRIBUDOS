// Luis Felipe Dos Santos

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DoubleServidor {

    public static final String NOME_SERVICO = "ServicoDouble";

    public static final int PORTA = 1099;

    public static void main(String[] args) {

        try {
            System.out.println("=== Servidor RMI - Representação IEEE 754 ===");

            DoubleImpl objetoRemoto = new DoubleImpl();
            System.out.println("[1/3] Objeto remoto criado com sucesso.");
            Registry registry = LocateRegistry.createRegistry(PORTA);
            System.out.println("[2/3] RMI Registry iniciado na porta " + PORTA + ".");

            registry.rebind(NOME_SERVICO, objetoRemoto);
            System.out.println("[3/3] Serviço \"" + NOME_SERVICO + "\" registrado no registry.");

            System.out.println("\nServidor pronto. Aguardando requisições dos clientes...");
            System.out.println("(Pressione Ctrl+C para encerrar)");

        } catch (Exception e) {
            System.err.println("ERRO no servidor RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
