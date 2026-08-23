package model;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
	//
	// ATRIBUTOS
	// 
	private int id;
	private String codigo;
	private String nome;
	
	//
	// ATRIBUTOS DE RELACIONAMENTO
	//
	private List<Curso> listaCursos;
	
	static {
		System.out.println("A CLASSE DISCIPLINA FOI CARREGADA!");
	}
	
	//
	// MÉTODOS
	//
	public Disciplina() {
		super();
	}

	public Disciplina(String codigo, String nome) throws ModelException {
		super();
		this.setCodigo(codigo);
		this.setNome(nome);
		this.listaCursos = new ArrayList<Curso>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) throws ModelException {
		Disciplina.validarCodigo(codigo);
		this.codigo = codigo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) throws ModelException{
		Disciplina.validarNome(nome);
		this.nome = nome;
	}

	public List<Curso> getListaCursos() {
		// devolvendo uma cópia da lista oficial
		return new ArrayList<Curso>(this.listaCursos);
	}

	public void setListaCursos(List<Curso> listaCursos) throws ModelException  {
		Disciplina.validarListaCursos(listaCursos);
		this.listaCursos = listaCursos;
	}
	
	public void adicionarCurso(Curso curso) throws ModelException {
		if(!this.listaCursos.contains(curso)) {
			Disciplina.validarCurso(curso);		
			this.listaCursos.add(curso);
			curso.adicionarDisciplina(this);
		}		
	}

	public void removerCurso(Curso curso) throws ModelException {
		Disciplina.validarCurso(curso);
		this.listaCursos.remove(curso);
	}

	public static void validarCodigo(String codigo) throws ModelException {
		if(codigo == null || codigo.length() == 0)
			throw new ModelException("O código não pode ser nula!");		
		if(!codigo.matches("[A-Z]{3}[0-9]{3}"))
			throw new ModelException("O código passado não está no formato correto (2 caracteres maiúsculos): " + codigo);
	}
	
	public static void validarNome(String nome) throws ModelException {
		if(nome == null || nome.length() == 0)
			throw new ModelException("O nome não pode ser nulo!");		
		if(!nome.matches("[A-Za-zÀ-ÖØ-öø-ÿ\\s-]{5,40}"))
			throw new ModelException("O nome passado não está no formato correto: " + nome);		
	}
	
	public static void validarCurso(Curso curso) throws ModelException {
		if(curso == null)
			throw new ModelException("O curso não pode ser nulo!");	
	}

	public static void validarListaCursos(List<Curso> lista) throws ModelException {
		if(lista == null)
			throw new ModelException("A lista de cursos não pode ser nulo!");	
	}
}
