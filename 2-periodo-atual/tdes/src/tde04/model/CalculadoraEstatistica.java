// Luis Felipe Dos Santos
package tde04.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Lógica pura das estatísticas, separada da parte de rede.
 *
 * O servidor só cuida do transporte (sockets/TCP) e de traduzir o protocolo
 * de texto; toda a "regra de negócio" (soma, média, desvio padrão) fica aqui,
 * o que deixa a classe fácil de testar sem precisar abrir um socket.
 */
public class CalculadoraEstatistica {

	private final List<Integer> numeros = new ArrayList<>();

	public void adicionar(int valor) {
		this.numeros.add(valor);
	}

	public int getQuantidade() {
		return this.numeros.size();
	}

	public long getSoma() {
		long soma = 0;
		for (int n : this.numeros)
			soma += n;
		return soma;
	}

	public double getMedia() {
		if (this.numeros.isEmpty())
			throw new IllegalStateException("Não há números para calcular a média.");
		return (double) getSoma() / this.numeros.size();
	}

	/**
	 * Desvio padrão populacional: raiz da média dos quadrados dos desvios em
	 * relação à média (divide por N, não por N-1). É preciso combinar isso com
	 * o cliente — faz parte do protocolo saber qual fórmula está sendo usada.
	 */
	public double getDesvioPadrao() {
		if (this.numeros.isEmpty())
			throw new IllegalStateException("Não há números para calcular o desvio padrão.");

		double media = getMedia();
		double somaQuadrados = 0;
		for (int n : this.numeros) {
			double desvio = n - media;
			somaQuadrados += desvio * desvio;
		}
		return Math.sqrt(somaQuadrados / this.numeros.size());
	}
}
