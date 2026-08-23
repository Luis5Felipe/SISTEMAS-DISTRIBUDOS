package controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

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

			// Solicitando à JVM que faça a carga da classe Aluno
			Class objClasseAluno = Class.forName("model.Aluno");
			
			System.out.println("\nListando os construtores de " + objClasseAluno);
			System.out.println("===========================================");
			for(Constructor c: objClasseAluno.getConstructors())
				System.out.println("Construtor: " + c);
			System.out.println();
			
			// Recuperando o construtor de Aluno com assinatura vazia 
			Constructor construtorDeAluno = objClasseAluno.getConstructor();
			// Instanciando um objeto Aluno sem o uso do operador 'new'
			Object a = construtorDeAluno.newInstance();
			// Recuperando o objeto Field que descreve o atributo 'nome' de Aluno
			Field atrNomeDeAluno = objClasseAluno.getDeclaredField("nome");
			// Quebro a visibilidade de 'nome' no objeto Field
			atrNomeDeAluno.setAccessible(true);
			// Determino o valor do atributo 'nome' para o objeto instanciado
			atrNomeDeAluno.set(a, "####");
			// Imprimindo o toString do Aluno
			System.out.println(a);
			
			
			//
			// Criando um objeto e preenchendo seus atributos (na forma que o JPA faz)
			//
			Object a2 = construtorDeAluno.newInstance();
			Method metodoSetId = objClasseAluno.getDeclaredMethod("setId", int.class);
			Method metodoSetMatricula = objClasseAluno.getDeclaredMethod("setMatricula", String.class);
			Method metodoSetNome = objClasseAluno.getDeclaredMethod("setNome", String.class);
			Method metodoSetCurso = objClasseAluno.getDeclaredMethod("setCurso", Curso.class);
			metodoSetId.invoke(a2, 2);
			metodoSetMatricula.invoke(a2, "1041020");
			metodoSetNome.invoke(a2, "José da Silva");
			metodoSetCurso.invoke(a2, c1);
			System.out.println(a2);
			

			//
			// Chamando os métodos get (na forma que o JPA faz)
			//
			Method metodoGetId = objClasseAluno.getDeclaredMethod("getId");
			Method metodoGetMatricula = objClasseAluno.getDeclaredMethod("getMatricula");
			Method metodoGetNome = objClasseAluno.getDeclaredMethod("getNome");
			Method metodoGetCurso = objClasseAluno.getDeclaredMethod("getCurso");
			System.out.println("Id = " + metodoGetId.invoke(a2));
			System.out.println("Matrícula = " + metodoGetMatricula.invoke(a2));
			System.out.println("Nome = " + metodoGetNome.invoke(a2));
			System.out.println("Curso = " + metodoGetCurso.invoke(a2));
			System.out.println(a2);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
