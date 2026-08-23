package model;

import java.util.ArrayList;
import java.util.List;

public class Curso implements Comparable<Curso> {
	//
	// ATRIBUTOS
	// 
	private int id;	
	private String codigo;
	private String nome;
	
	//
	// ATRIBUTOS DE RELACIONAMENTO
	//
	private List<Aluno> listaAlunos;
	private List<Disciplina> listaDisciplinas;
	
	
	static {
		System.out.println("A CLASSE CURSO FOI CARREGADA!");
	}
	
	//
	// MÉTODOS
	//
	public Curso() {
		super();
	}
	
	public Curso(String codigo, String nome) throws ModelException {
		super();
		this.setCodigo(codigo);
		this.setNome(nome);
		this.listaAlunos = new ArrayList<Aluno>();
		this.listaDisciplinas = new ArrayList<Disciplina>();
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) throws ModelException {
		Curso.validarCodigo(codigo);
		this.codigo = codigo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) throws ModelException{
		Curso.validarNome(nome);
		this.nome = nome;
	}

	public List<Aluno> getListaAlunos() {
		// devolvendo uma cópia da lista oficial
		return new ArrayList<Aluno>(this.listaAlunos);
	}

	public void setListaAlunos(List<Aluno> listaAlunos) throws ModelException {
		Curso.validarListaAlunos(listaAlunos);
		this.listaAlunos = listaAlunos;
	}

	public List<Disciplina> getListaDisciplinas() {
		// devolvendo uma cópia da lista oficial
		return new ArrayList<Disciplina>(this.listaDisciplinas);
	}

	public void setListaDisciplinas(List<Disciplina> listaDisciplinas) throws ModelException {
		Curso.validarListaDisciplinas(listaDisciplinas);
		this.listaDisciplinas = listaDisciplinas;
	}
	
	public void adicionarAluno(Aluno aluno) throws ModelException  {
		Curso.validarAluno(aluno);
		this.listaAlunos.add(aluno);
	}

	public void removerAluno(Aluno aluno) throws ModelException {
		Curso.validarAluno(aluno);
		this.listaAlunos.remove(aluno);
	}

	public void adicionarDisciplina(Disciplina disc) throws ModelException {
		if(!this.listaDisciplinas.contains(disc)) {			
			Curso.validarDisciplina(disc);
			this.listaDisciplinas.add(disc);
			disc.adicionarCurso(this);
		}
	}

	public void removerAluno(Disciplina disc) throws ModelException {
		Curso.validarDisciplina(disc);
		this.listaDisciplinas.remove(disc);
	}

	public static void validarCodigo(String codigo) throws ModelException {
		if(codigo == null || codigo.length() == 0)
			throw new ModelException("O código não pode ser nula!");		
		if(!codigo.matches("[A-Z]{2,4}"))
			throw new ModelException("O código passado não está no formato correto (2 caracteres maiúsculos): " + codigo);
	}
	
	public static void validarNome(String nome) throws ModelException {
		if(nome == null || nome.length() == 0)
			throw new ModelException("O nome não pode ser nulo!");		
		if(!nome.matches("[A-Za-zÀ-ÖØ-öø-ÿ\\s-]{5,40}"))
			throw new ModelException("O nome passado não está no formato correto: " + nome);		
	}
	
	public static void validarAluno(Aluno aluno) throws ModelException {
		if(aluno == null)
			throw new ModelException("O aluno não pode ser nulo!");	
	}

	public static void validarDisciplina(Disciplina disc) throws ModelException {
		if(disc == null)
			throw new ModelException("A disciplina não pode ser nula!");	
	}

	public static void validarListaDisciplinas(List<Disciplina> lista) throws ModelException {
		if(lista == null)
			throw new ModelException("A lista de disciplinas não pode ser nula!");	
	}

	public static void validarListaAlunos(List<Aluno> lista) throws ModelException {
		if(lista == null)
			throw new ModelException("A lista de alunos não pode ser nula!");	
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", codigo=" + codigo + ", nome=" + nome + "]";
	}

	public int compareTo(Curso c) {
		return this.codigo.compareTo(c.codigo);
	}
	
}
