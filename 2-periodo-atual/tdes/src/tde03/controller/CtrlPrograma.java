// Luis Felipe Dos Santos
package tde03.controller;

import java.util.List;

import tde03.model.Cavalo;
import tde03.model.Corrida;

public class CtrlPrograma {

	public static void main(String[] args) throws InterruptedException {
		Corrida corrida = new Corrida();

		Cavalo[] cavalos = {
				new Cavalo("Relâmpago", corrida),
				new Cavalo("Trovão", corrida),
				new Cavalo("Ventania", corrida),
				new Cavalo("Foguete", corrida),
				new Cavalo("Tempestade", corrida)
		};

		System.out.println("A corrida vai começar! Distância: " + Corrida.DISTANCIA_METROS + "m\n");

		for (Cavalo c : cavalos)
			c.iniciar();

		for (Cavalo c : cavalos)
			c.aguardarChegada();

		System.out.println("\nCorrida finalizada! Classificação final:");
		List<String> classificacao = corrida.getClassificacao();
		for (int i = 0; i < classificacao.size(); i++)
			System.out.println((i + 1) + "º lugar: " + classificacao.get(i));
	}
}
