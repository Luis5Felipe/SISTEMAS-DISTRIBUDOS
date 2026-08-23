package controller;

public class CtrlPrograma {

	public static void main(String[] args) {
		
		System.out.println("PROCESSO PAI EM EXECUÇÃO");
		
		// Criando a variável semáforo 
		String semaforo = "Sou um semáforo!";
		
		// Criando os objetos Thread
		Thread th1 = new Thread( new MeuRunnable(1, 1, semaforo) );
		Thread th2 = new Thread( new MeuRunnable(2, 5, semaforo) );
		Thread th3 = new Thread( new MeuRunnable(3, 9, semaforo) );
		
		// Criando as Threads e colocando-as para execução
		th1.start();
		th2.start();
		th3.start();
		
		// Verificando a conclusão das threads. Ficaremos em um 
		// loop enquanto as threads estão rodando
		while(th1.isAlive() || th2.isAlive() || th3.isAlive());
		
		System.out.println("PROCESSO PAI CONCLUÍDO");
	}
}
