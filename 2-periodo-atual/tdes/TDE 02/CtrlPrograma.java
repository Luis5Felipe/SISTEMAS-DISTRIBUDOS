// Luis Felipe Dos Santos

package controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import model.Curso;

public class CtrlPrograma {
	public static void main(String[] args) {
		try {
			// Instanciando objetos Curso
			Curso c1 = new Curso("BSI","Sistemas de Informação");
			Curso c2 = new Curso("DIR","Direito");
			Curso c3 = new Curso("ARQ","Arquitetura");
			Curso c4 = new Curso("AZZ","Agronomia");
			
			// Colocando objetos Curso em um ArrayList
			ArrayList<Curso> listaCursos = new ArrayList<>();
			listaCursos.add(c1);
			listaCursos.add(c2);
			listaCursos.add(c3);
			listaCursos.add(c4);
			
			// Ordenando a lista através do método sort da classe Collections
			// O método sort espera um objeto Comparator e esse é passado 
			// através da expressão lambda que descreve o método 'compare' 
			// A ordenação será pelo nome
			Collections.sort(listaCursos, (a,b) -> a.getNome().compareTo(b.getNome()));
			// Listando os cursos ordenados pelo nome
			System.out.println("Apresentando os cursos ordenados pelo nome:");
			System.out.println("===========================================");
			for(Curso c : listaCursos)
				System.out.println(c);
			System.out.println();
			
			// Ordenando a lista através do método sort da classe Collections
			// O método sort espera um objeto Comparator e esse é passado 
			// através da expressão lambda que descreve o método 'compare' 
			// A ordenação agora será pelo código
			Collections.sort(listaCursos, (a,b) -> a.getCodigo().compareTo(b.getCodigo()));
			// Listando os cursos
			System.out.println("Apresentando os cursos ordenados pelo código:");
			System.out.println("=============================================");
			for(Curso c : listaCursos)
				System.out.println(c);

			System.out.println();
			System.out.println();
			System.out.println();

			// Obtendo o objeto Class que descreve a classe Curso através do envio 
			// da mensagem 'getClass()' para o objeto apontado por c1
			Class objClasseCurso = c1.getClass();
			System.out.println("Classe do objeto apontado por c1: " + objClasseCurso);
			
			System.out.println("Listando as anotações associadas à classe " + objClasseCurso);
			System.out.println("===========================================");
			for(Annotation a : objClasseCurso.getAnnotations())
				System.out.println("Anotação: " + a);			

			System.out.println("Listando os atributos de " + objClasseCurso);
			System.out.println("===========================================");
			for(Field f : objClasseCurso.getDeclaredFields()) {
				System.out.println("Atributo: " + f);
				System.out.println("Anotações no atributo:");
				for(Annotation a : f.getAnnotations())
					System.out.println("\tAnotação: " + a);
			}
			
			System.out.println("\nListando os métodos de " + objClasseCurso);
			System.out.println("===========================================");
			for(Method m: objClasseCurso.getDeclaredMethods())
				System.out.println("Método: " + m);
			
			System.out.println();
			System.out.println();
			System.out.println();

			// Solicitando ao usuário o nome completo (com pacote) da classe a ser instanciada
			// Ex.: model.Aluno, model.Curso, model.Disciplina, model.Empresa
			Scanner teclado = new Scanner(System.in);
			System.out.print("Informe o nome completo da classe: ");
			String nomeClasse = teclado.nextLine();

			// Solicitando à JVM que faça a carga da classe informada
			Class<?> objClasse = Class.forName(nomeClasse);

			// Recuperando o construtor sem argumentos e instanciando o objeto
			// (na forma que o JPA faz, sem o uso do operador 'new')
			Object objeto = objClasse.getConstructor().newInstance();

			// Percorrendo os atributos declarados na classe
			for (Field atributo : objClasse.getDeclaredFields()) {
				Class<?> tipoAtributo = atributo.getType();

				// Por simplificação, só tratamos atributos do tipo int e String
				if (tipoAtributo == int.class || tipoAtributo == String.class) {
					String nomeAtributo = atributo.getName();
					String nomeMetodoSet = "set" + Character.toUpperCase(nomeAtributo.charAt(0)) + nomeAtributo.substring(1);

					try {
						// Recuperando o método set correspondente ao atributo
						Method metodoSet = objClasse.getDeclaredMethod(nomeMetodoSet, tipoAtributo);

						System.out.print("Informe o valor do atributo '" + nomeAtributo + "' (" + tipoAtributo.getSimpleName() + "): ");

						if (tipoAtributo == int.class) {
							int valor = Integer.parseInt(teclado.nextLine());
							metodoSet.invoke(objeto, valor);
						} else {
							String valor = teclado.nextLine();
							metodoSet.invoke(objeto, valor);
						}
					} catch (NoSuchMethodException e) {
						// A classe não expõe um método set para este atributo; passamos adiante
						System.out.println("(Atributo '" + nomeAtributo + "' não possui método " + nomeMetodoSet + "; ignorando.)");
					}
				}
			}

			// Chamando o método toString() para vermos que os valores foram atribuídos ao objeto
			System.out.println();
			System.out.println("Objeto criado: " + objeto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
