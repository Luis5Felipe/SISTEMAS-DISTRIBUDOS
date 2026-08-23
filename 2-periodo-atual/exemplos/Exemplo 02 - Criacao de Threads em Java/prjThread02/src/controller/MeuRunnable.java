package controller;

import java.util.Random;

public class MeuRunnable implements Runnable {
	//
	// CONSTANTES
	//
	final public static int UM_SEGUNDO = 400; 
	
	//
	// ATRIBUTOS
	//
	final private int 		idThread;  // Guarda o id da Thread (é final, pois, depois de atribuído no construtor, não pode ser alterado)
	final private String    semaforo;  // Referência para o objeto semáforo 
	private String    		tabs = ""; // Definir a tabulação das strings a serem impressas pela Thread
	
	//
	// MÉTODOS
	//
	public MeuRunnable(int id, int numTabs, String semaforo) {
		this.idThread = id;
		this.semaforo = semaforo;
		for(int i = 0; i < numTabs; i++)    
			this.tabs += '\t';
	}

	@Override
	public void run() {
		Random sorteador = new Random();
		for(int i = 1; i <= 50; i++) {
			// Sorteio um tempo de espera de 0s a 5s
			int tempoDeEspera = Math.abs(sorteador.nextInt() % 6);
			try {
				Thread.sleep(tempoDeEspera * UM_SEGUNDO);
			} 
			catch (InterruptedException e) {
			}
			System.out.println(this.tabs + "Thread #" + this.idThread + "(" + i + "): " + this.semaforo.toUpperCase());
		}
	}
}





