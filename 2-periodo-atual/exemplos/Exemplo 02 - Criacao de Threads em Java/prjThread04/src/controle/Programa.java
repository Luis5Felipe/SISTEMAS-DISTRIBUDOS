package controle;

public class Programa {
	public static void main(String[] args) {
		Impressor press = new Impressor();

		Thread t1 = new Thread(new MeuRunnable(0, press));
		Thread t2 = new Thread(new MeuRunnable(1, press));
		Thread t3 = new Thread(new MeuRunnable(2, press));

		t1.start();
		t2.start();
		t3.start();

		while (t1.isAlive() || t2.isAlive() || t3.isAlive())
			; // Não faço nada! Fique aguardando!

		System.out.flush();
		System.out.println("As threads terminaram de ser executadas!");

		t1.start(); // Uma vez que um objeto Thread j� foi utilizado, ele n�o pode ser
					// reutilizado para abrir uma nova Thread no S.O. Aqui ocorrer� uma
					// Exception!
	}
}
