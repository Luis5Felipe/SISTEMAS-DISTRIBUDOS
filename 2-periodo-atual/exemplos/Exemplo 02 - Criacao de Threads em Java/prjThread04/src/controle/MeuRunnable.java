package controle;

public class MeuRunnable implements Runnable {
	private int id;
	private Impressor press;

	public MeuRunnable(int id, Impressor press) {
		this.id = id;
		this.press = press;
	}

	public void run() {
		press.imprime(this.id, "Sou a thread #" + this.id);
	}
}
