// Luis Felipe Dos Santos
package tde04.servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import tde04.model.CalculadoraEstatistica;

/**
 * Atende UM cliente: lê os inteiros que ele manda (linha a linha, até a linha
 * vazia), calcula soma / média / desvio padrão e devolve a resposta seguindo
 * o protocolo descrito em {@link ServidorEstatistica}.
 *
 * Implementa Runnable porque o servidor cria uma thread para cada conexão.
 */
public class TratadorConexao implements Runnable {

	private final Socket conexao;

	public TratadorConexao(Socket conexao) {
		this.conexao = conexao;
	}

	@Override
	public void run() {
		// try-with-resources fecha entrada, saída e o socket ao final,
		// mesmo que ocorra uma exceção no meio do caminho.
		try (Socket socket = this.conexao;
			 BufferedReader entrada = new BufferedReader(
					 new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			 PrintWriter saida = new PrintWriter(
					 new java.io.OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

			CalculadoraEstatistica calculadora = new CalculadoraEstatistica();
			String linha;

			// Lê linha a linha. readLine() devolve:
			//  - a linha sem o \n         -> mais um número (ou erro)
			//  - "" (string vazia)        -> fim do envio combinado no protocolo
			//  - null                     -> cliente fechou a conexão sem avisar
			while ((linha = entrada.readLine()) != null) {
				linha = linha.trim();

				if (linha.isEmpty())
					break; // marcador de fim: pode calcular e responder

				try {
					calculadora.adicionar(Integer.parseInt(linha));
				} catch (NumberFormatException e) {
					// Erro faz parte do protocolo: manda 1 linha ERRO= e encerra.
					saida.println("ERRO=A linha '" + linha + "' não é um número inteiro.");
					System.out.println("Recusado (valor inválido): " + linha);
					return;
				}
			}

			if (calculadora.getQuantidade() == 0) {
				saida.println("ERRO=Nenhum número foi enviado.");
				System.out.println("Recusado: nenhum número enviado.");
				return;
			}

			// Resposta feliz: 3 linhas na ordem combinada. Locale.US -> ponto decimal.
			long soma = calculadora.getSoma();
			double media = calculadora.getMedia();
			double desvio = calculadora.getDesvioPadrao();

			saida.println("SOMA=" + soma);
			saida.println(String.format(Locale.US, "MEDIA=%.4f", media));
			saida.println(String.format(Locale.US, "DESVIO=%.4f", desvio));

			System.out.printf(Locale.US,
					"Atendido: %d números | soma=%d media=%.4f desvio=%.4f%n",
					calculadora.getQuantidade(), soma, media, desvio);

		} catch (Exception e) {
			System.out.println("Erro ao atender cliente: " + e.getMessage());
		}
	}
}
