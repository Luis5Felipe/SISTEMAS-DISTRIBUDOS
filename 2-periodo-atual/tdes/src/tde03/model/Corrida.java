package tde03.model;

import java.util.ArrayList;
import java.util.List;

public class Corrida {

	public static final int DISTANCIA_METROS = 400;

	private final List<String> classificacao = new ArrayList<>();

	// synchronized: várias threads (cavalos) podem cruzar a linha de chegada
	// quase ao mesmo tempo. Sem isso, duas threads poderiam ler
	// classificacao.size() com o mesmo valor antes de qualquer uma adicionar
	// seu nome, e as duas "empatariam" na mesma colocação.
	public synchronized void registrarChegada(String nomeCavalo) {
		this.classificacao.add(nomeCavalo);
		int colocacao = this.classificacao.size();
		System.out.println(medalha(colocacao) + colocacao + "º lugar: " + nomeCavalo);
	}

	public synchronized List<String> getClassificacao() {
		return new ArrayList<>(this.classificacao);
	}

	private String medalha(int colocacao) {
		if (colocacao == 1) return "campeão ";
		if (colocacao == 2) return "Segundo ";
		if (colocacao == 3) return "Terceiro ";
		return "";
	}
}
