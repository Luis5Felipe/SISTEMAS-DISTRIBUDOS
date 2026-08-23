package controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Empresa;

/**
 * Luis Felipe Dos Santos
 */
public class CtrlPrograma {
	public static void main(String[] args) {
		try {
			// Instanciando objetos Empresa
			Empresa e1 = new Empresa("12.345.678/0001-90", "Alfa Tecnologia", 120);
			Empresa e2 = new Empresa("23.456.789/0001-81", "Beta Sistemas", 45);
			Empresa e3 = new Empresa("34.567.890/0001-72", "Contoso Industrial", 300);
			Empresa e4 = new Empresa("45.678.901/0001-63", "Delta Comércio", 15);

			// Colocando os objetos Empresa em uma List
			List<Empresa> listaEmpresas = new ArrayList<>();
			listaEmpresas.add(e1);
			listaEmpresas.add(e2);
			listaEmpresas.add(e3);
			listaEmpresas.add(e4);

			// Ordenando a lista através do método sort da classe Collections
			// O método sort espera um objeto Comparator e esse é passado
			// através da expressão lambda que descreve o método 'compare'
			// A ordenação será pelo nome
			Collections.sort(listaEmpresas, (a, b) -> a.getNome().compareTo(b.getNome()));
			System.out.println("Apresentando as empresas ordenadas pelo nome:");
			System.out.println("==============================================");
			for (Empresa e : listaEmpresas)
				System.out.println(e);
			System.out.println();

			// Ordenando a lista agora pelo número de empregados
			Collections.sort(listaEmpresas, (a, b) -> Integer.compare(a.getNumEmpregados(), b.getNumEmpregados()));
			System.out.println("Apresentando as empresas ordenadas pelo número de empregados:");
			System.out.println("===============================================================");
			for (Empresa e : listaEmpresas)
				System.out.println(e);
			System.out.println();

			// Obtendo o objeto Class que descreve a classe Empresa através do envio
			// da mensagem 'getClass()' para o objeto apontado por e1
			Class<? extends Empresa> objClasseEmpresa = e1.getClass();
			System.out.println("Classe do objeto apontado por e1: " + objClasseEmpresa);

			System.out.println("\nListando os atributos de " + objClasseEmpresa);
			System.out.println("==============================================");
			for (Field f : objClasseEmpresa.getDeclaredFields())
				System.out.println("Atributo: " + f);

			System.out.println("\nListando os métodos de " + objClasseEmpresa);
			System.out.println("==============================================");
			for (Method m : objClasseEmpresa.getDeclaredMethods())
				System.out.println("Método: " + m);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
