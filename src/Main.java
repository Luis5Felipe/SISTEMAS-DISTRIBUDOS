import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try (FileInputStream arquivo = new FileInputStream("D:\\Github\\Estudos\\Sistemas Destribuidos\\TDE 01\\src\\arquivo.bin")) {

            int byteLido;
            int contador = 0;

            while ((byteLido = arquivo.read()) != -1) {
                System.out.println("Byte " + contador + ": " + byteLido);
                contador++;
            }

            System.out.println("Total de bytes no arquivo: " + contador);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}