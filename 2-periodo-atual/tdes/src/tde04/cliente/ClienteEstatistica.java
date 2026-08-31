// Luis Felipe Dos Santos
package tde04.cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Cliente TCP do TDE 4.
 *
 * Lê inteiros digitados pelo usuário no teclado (um por linha) e, quando o
 * usuário digita uma linha vazia (só ENTER), envia tudo ao servidor seguindo
 * o protocolo descrito em tde04.servidor.ServidorEstatistica:
 *
 *   - manda cada inteiro em uma linha;
 *   - manda uma linha vazia para dizer "acabou";
 *   - lê a resposta linha a linha até o servidor fechar a conexão;
 *   - se a 1ª linha começar com "ERRO=", mostra só o erro.
 */
public class ClienteEstatistica {

	private static final String HOST = "localhost";
	private static final int PORTA = 5000;

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);

		System.out.println("Cliente de estatísticas (TCP)");
		System.out.println("Digite um número inteiro por linha e tecle ENTER numa linha vazia para enviar.");

		// try-with-resources fecha o socket e os streams no final.
		try (Socket socket = new Socket(HOST, PORTA);
			 PrintWriter saida = new PrintWriter(
					 new java.io.OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
			 BufferedReader entrada = new BufferedReader(
					 new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {

			// --- Fase 1: coletar os números e enviar ---
			while (true) {
				System.out.print("> ");
				String linha = teclado.nextLine().trim();

				if (linha.isEmpty()) {
					saida.println();      // linha vazia = fim do envio (protocolo)
					break;
				}

				// Validação básica no próprio cliente, para não mandar lixo à toa.
				try {
					Integer.parseInt(linha);
				} catch (NumberFormatException e) {
					System.out.println("  '" + linha + "' não é um inteiro. Tente de novo.");
					continue;
				}

				saida.println(linha);     // envia o inteiro como texto
			}

			// --- Fase 2: ler a resposta do servidor ---
			System.out.println("\n--- Resposta do servidor ---");
			String resposta;
			while ((resposta = entrada.readLine()) != null) {
				if (resposta.startsWith("ERRO=")) {
					System.out.println("ERRO: " + resposta.substring("ERRO=".length()));
					return;
				}
				System.out.println(resposta);
			}

		} catch (java.net.ConnectException e) {
			System.out.println("Não consegui conectar em " + HOST + ":" + PORTA
					+ ". O servidor está rodando?");
		} catch (Exception e) {
			System.out.println("Erro no cliente: " + e.getMessage());
		}
	}
}
