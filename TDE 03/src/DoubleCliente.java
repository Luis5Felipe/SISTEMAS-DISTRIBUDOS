// Luis Felipe Dos Santos

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DoubleCliente {

    private static final String HOST = "localhost";

    public static void main(String[] args) {

        double[] valoresTeste = {
                -12.5,0.0,-0.0,1.0,-1.0, 3.14,
             Double.MAX_VALUE,
             Double.MIN_VALUE,
             Double.POSITIVE_INFINITY,
             Double.NaN
        };

        System.out.println("=== Cliente RMI - Representação IEEE 754 ===");
        System.out.println("Conectando ao servidor em " + HOST + ":" + DoubleServidor.PORTA);
        System.out.println();

        try {
            Registry registry = LocateRegistry.getRegistry(HOST, DoubleServidor.PORTA);
            DoubleInterface servico = (DoubleInterface) registry.lookup(DoubleServidor.NOME_SERVICO);

            System.out.println("Conexão estabelecida! Enviando requisições...");
            System.out.println("Formato: [sinal(1)] | [expoente(11)] | [mantissa(52)]");
            System.out.println("=".repeat(80));
            for (double valor : valoresTeste) {
                String bits = servico.obterBits(valor);
                System.out.printf("Double : %s%n", valor);
                System.out.printf("Bits   : %s%n", bits);
                System.out.println("-".repeat(80));
            }

        } catch (java.rmi.NotBoundException e) {
            System.err.println("ERRO: Serviço \"" + DoubleServidor.NOME_SERVICO
                    + "\" não encontrado no registry. O servidor está rodando?");
            e.printStackTrace();

        } catch (java.rmi.ConnectException e) {
            System.err.println("ERRO: Não foi possível conectar ao servidor em "
                    + HOST + ":" + DoubleServidor.PORTA
                    + ". Verifique se o DoubleServidor está em execução.");
            e.printStackTrace();

        } catch (Exception e) {
            System.err.println("ERRO inesperado no cliente RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
