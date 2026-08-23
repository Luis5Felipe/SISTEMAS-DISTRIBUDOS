package model;


public class Aluno {
	//
	// ATRIBUTOS
	//
	private int id;
	private String matricula;
	private String nome;
	
	//
	// ATRIBUTOS DE RELACIONAMENTO
	//
	private Curso curso;

	static {
		System.out.println("A CLASSE ALUNO FOI CARREGADA!");
	}
	
	//
	// MÉTODOS
	//
	public Aluno() {
		super();
	}

	public Aluno(String matricula, String nome, Curso curso) throws ModelException {
		super();
		this.setMatricula(matricula);
		this.setNome(nome);
		this.setCurso(curso);		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) throws ModelException {
		Aluno.validarMatricula(matricula);
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws ModelException {
		Aluno.validarNome(nome);
		this.nome = nome;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) throws ModelException {
		Aluno.validarCurso(curso);		
		if(this.curso != null)
			throw new ModelException("O aluno não pode trocar de curso");		
		// Aluno passa a referenciar o curso
		this.curso = curso;
		// O curso passa a referenciar o aluno
		curso.adicionarAluno(this);
	}
	
	public static void validarMatricula(String matricula) throws ModelException {
		if(matricula == null || matricula.length() == 0)
			throw new ModelException("A matricula não pode ser nula!");		
		if(!matricula.matches("[0-9]{7}"))
			throw new ModelException("A matrícula passada não está no formato correto (7 dígitos): " + matricula);
	}
	
	public static void validarNome(String nome) throws ModelException {
		if(nome == null || nome.length() == 0)
			throw new ModelException("O nome não pode ser nulo!");		
		if(!nome.matches("[A-Za-zÀ-ÖØ-öø-ÿ\\s-]{5,40}"))
			throw new ModelException("O nome passado não está no formato correto : " + nome);		
	}
	
	public static void validarCurso(Curso curso) throws ModelException {
		if(curso == null)
			throw new ModelException("O curso do aluno não pode ser nulo!");	
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", matricula=" + matricula + ", nome=" + nome + ", curso=" + curso + "]";
	}
}
