// Luis Felipe Dos Santos
package tde04.servidor;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor TCP do TDE 4.
 *
 * PROTOCOLO DE COMUNICAÇÃO (texto, uma informação por linha, encoding UTF-8)
 * ------------------------------------------------------------------------
 * Escolhi a ideia apresentada em aula no "Exemplo 1" (protocolo Echo):
 * o cliente manda um número indefinido de linhas, cada uma com um inteiro,
 * e sinaliza o fim do envio com uma LINHA VAZIA.
 *
 *   Cliente  ->  Servidor : "10\n"
 *   Cliente  ->  Servidor : "20\n"
 *   Cliente  ->  Servidor : "30\n"
 *   Cliente  ->  Servidor : "\n"            (linha vazia = acabou)
 *
 * Resposta do servidor: 3 linhas, nesta ordem, e depois ele fecha a conexão.
 *
 *   Servidor ->  Cliente  : "SOMA=60"
 *   Servidor ->  Cliente  : "MEDIA=20.0000"
 *   Servidor ->  Cliente  : "DESVIO=8.1650"
 *
 * Os números "double" (média e desvio) são enviados como texto, com ponto
 * como separador decimal (Locale.US) e 4 casas.
 *
 * Se algo der errado (uma linha que não é inteiro, ou nenhum número enviado),
 * o servidor responde UMA única linha e fecha:
 *
 *   Servidor ->  Cliente  : "ERRO=A linha 'abc' não é um número inteiro."
 *
 * Tratar o erro faz parte do protocolo (foi dito em aula que isso não é
 * opcional): o cliente sempre sabe se recebeu 3 linhas de resultado ou 1 de erro.
 *
 * Cada conexão é atendida por uma thread própria ("uma thread por conexão"),
 * então vários clientes podem ser atendidos ao mesmo tempo.
 */
public class ServidorEstatistica {

	public static final int PORTA = 5000;

	public static void main(String[] args) {
		// try-with-resources: garante que o ServerSocket seja fechado ao sair.
		try (ServerSocket servidor = new ServerSocket(PORTA)) {
			System.out.println("Servidor de estatísticas ouvindo na porta " + PORTA + "...");

			// Laço infinito: o servidor fica no ar aceitando novos clientes.
			while (true) {
				// accept() bloqueia até um cliente conectar.
				Socket conexao = servidor.accept();
				System.out.println("Cliente conectado: " + conexao.getInetAddress().getHostAddress());

				// Uma thread por conexão: o main volta na hora para o accept()
				// e continua aceitando outros clientes enquanto este é atendido.
				new Thread(new TratadorConexao(conexao)).start();
			}
		} catch (Exception e) {
			System.out.println("Erro ao subir o servidor: " + e.getMessage());
		}
	}
}
