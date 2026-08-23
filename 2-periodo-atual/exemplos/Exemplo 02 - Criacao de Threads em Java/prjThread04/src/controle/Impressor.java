package controle;

import java.util.Random;

public class Impressor {
	
	final public static int MEIO_SEGUNDO = 5;
	
	public synchronized void imprime(int numTabs, String mensagem) {
		String tabulacao = "";
		for (int j = 0; j < numTabs; j++)
			tabulacao += "\t";

		Random sorteador = new Random();
		for(int i = 1; i <= 50; i++) {
			// Sorteio um tempo de espera de 0s a 5s
			int tempoDeEspera = Math.abs(sorteador.nextInt() % 6);
			try {
				Thread.sleep(tempoDeEspera * MEIO_SEGUNDO);
			} 
			catch (InterruptedException e) {
			}
			System.out.println(tabulacao + "Thread #" + numTabs + "(" + i + "): " + mensagem);
		}		
	}
}
