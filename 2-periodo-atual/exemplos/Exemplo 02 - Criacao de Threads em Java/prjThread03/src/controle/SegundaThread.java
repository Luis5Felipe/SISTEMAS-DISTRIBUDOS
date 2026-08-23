package controle;

import java.util.*;

public class SegundaThread extends Thread {
	private Object monitor;

	public SegundaThread(Object semaforo) {
		this.monitor = semaforo;
	}

	public void run() {

		for (int i = 0; i < 50; i++) {
			
			synchronized (this.monitor) {
				System.out.println("\t\t\t\t\tInício da Segunda - #" + i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("\t\t\t\t\tFim da Segunda - #" + i);
			}
		}
	}
}