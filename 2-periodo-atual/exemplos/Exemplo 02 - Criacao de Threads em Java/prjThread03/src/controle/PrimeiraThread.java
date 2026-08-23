package controle;

public class PrimeiraThread extends Thread {
	private Object monitor;

	public PrimeiraThread(Object semaforo) {
		this.monitor = semaforo;
		// this.setPriority(Thread.MAX_PRIORITY);
	}

	public void run() {
		for (int i = 0; i < 50; i++) {

			synchronized(this.monitor) {
				System.out.println("Início da Primeira - #" + i);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Fim da Primeira - #" + i);
			}
			
		}
	}
}