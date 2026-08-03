// LUis Felipe Dos Santos
package model;

import Dao.Idao.IDaoPessoa;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Cliente {
    public static void main(String[] args) {
        try {

            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            IDaoPessoa daoPessoa = (IDaoPessoa) registry.lookup("DaoPessoa");
            System.out.println("[CLIENTE] Conexão com DaoPessoa estabelecida via RMI.\n");
            Pessoa p1 = new Pessoa("111.111.111-11", "Alice Souza", 30);
            Pessoa p2 = new Pessoa("222.222.222-22", "Bruno Lima", 25);
            Pessoa p3 = new Pessoa("333.333.333-33", "Carla Mendes", 40);

            System.out.println("=== INCLUINDO PESSOAS ===");
            System.out.println("Incluir Alice:  " + daoPessoa.incluir(p1));
            System.out.println("Incluir Bruno:  " + daoPessoa.incluir(p2));
            System.out.println("Incluir Carla:  " + daoPessoa.incluir(p3));
            System.out.println("\n=== CONSULTANDO TODOS ===");

            List<Pessoa> todos = daoPessoa.consultarTodos();
            for (Pessoa p : todos) {
                System.out.println(p);
            }

            System.out.println("\n=== CONSULTA POR CPF (222.222.222-22) ===");
            Pessoa encontrada = daoPessoa.consultarPeloCpf("222.222.222-22");

            if (encontrada != null) {
                System.out.println("Pessoa encontrada: " + encontrada);
            } else {
                System.out.println("Pessoa não encontrada.");
            }

            System.out.println("\n=== REMOVENDO PESSOA COM CPF 222.222.222-22 ===");
            boolean removido = daoPessoa.removerPeloCpf("222.222.222-22");
            System.out.println("Remoção bem-sucedida: " + removido);

            System.out.println("\n=== PESSOAS REMANESCENTES ===");
            List<Pessoa> remanescentes = daoPessoa.consultarTodos();
            for (Pessoa p : remanescentes) {
                System.out.println(p);
            }

        } catch (Exception e) {
            System.err.println("[CLIENTE] Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}