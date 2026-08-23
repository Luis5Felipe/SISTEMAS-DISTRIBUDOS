package controle;

public class ProcessoPai
{
	public static int VALOR = 1;
	
	public static void main(String[] args)
	{
		String monitor = new String();

		new PrimeiraThread(monitor).start();
		new SegundaThread(monitor).start();
	}
}