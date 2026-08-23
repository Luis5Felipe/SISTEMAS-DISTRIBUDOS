package tde03.model;

import java.util.Random;

public class Cavalo implements Runnable {

	private final String nome;
	private int posicao;
	private int velocidade;
	private final Thread thread;
	private final Corrida corrida;

	public Cavalo(String nome, Corrida corrida) {
		this.nome = nome;
		this.corrida = corrida;
		this.posicao = 0;
		this.thread = new Thread(this);
	}

	public void iniciar() {
		this.thread.start();
	}

	public void aguardarChegada() throws InterruptedException {
		this.thread.join();
	}

	@Override
	public void run() {
		Random sorteador = new Random();

		while (this.posicao < Corrida.DISTANCIA_METROS) {
			this.velocidade = sorteador.nextInt(10) + 1; // entre 1 e 10
			this.posicao += this.velocidade;
			if (this.posicao > Corrida.DISTANCIA_METROS)
				this.posicao = Corrida.DISTANCIA_METROS;

			System.out.println("---- Cavalo " + this.nome + " avançou " + this.velocidade
					+ " metros. Posição: " + this.posicao + "m");

			try {
				Thread.sleep(200); // só pra dar tempo de acompanhar a corrida no console
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		this.corrida.registrarChegada(this.nome);
	}

	public String getNome() {
		return this.nome;
	}

	public int getPosicao() {
		return this.posicao;
	}
}
