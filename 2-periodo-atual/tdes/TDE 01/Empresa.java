package model;

/**
 * Luis Felipe Dos Santos
 */
public class Empresa {
	//
	// ATRIBUTOS
	//
	private String cnpj;
	private String nome;
	private int numEmpregados;

	static {
		System.out.println("A CLASSE EMPRESA FOI CARREGADA!");
	}

	//
	// MÉTODOS
	//
	public Empresa() {
		super();
	}

	public Empresa(String cnpj, String nome, int numEmpregados) throws ModelException {
		super();
		this.setCnpj(cnpj);
		this.setNome(nome);
		this.setNumEmpregados(numEmpregados);
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) throws ModelException {
		Empresa.validarCnpj(cnpj);
		this.cnpj = cnpj;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) throws ModelException {
		Empresa.validarNome(nome);
		this.nome = nome;
	}

	public int getNumEmpregados() {
		return this.numEmpregados;
	}

	public void setNumEmpregados(int numEmpregados) throws ModelException {
		Empresa.validarNumEmpregados(numEmpregados);
		this.numEmpregados = numEmpregados;
	}

	public static void validarCnpj(String cnpj) throws ModelException {
		if (cnpj == null || cnpj.length() == 0)
			throw new ModelException("O CNPJ não pode ser nulo!");
		if (!cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}"))
			throw new ModelException("O CNPJ passado não está no formato correto (00.000.000/0000-00): " + cnpj);
	}

	public static void validarNome(String nome) throws ModelException {
		if (nome == null || nome.length() == 0)
			throw new ModelException("O nome não pode ser nulo!");
		if (!nome.matches("[A-Za-zÀ-ÖØ-öø-ÿ0-9\\s\\.-]{2,60}"))
			throw new ModelException("O nome passado não está no formato correto: " + nome);
	}

	public static void validarNumEmpregados(int numEmpregados) throws ModelException {
		if (numEmpregados < 0)
			throw new ModelException("O número de empregados não pode ser negativo: " + numEmpregados);
	}

	@Override
	public String toString() {
		return "Empresa [cnpj=" + cnpj + ", nome=" + nome + ", numEmpregados=" + numEmpregados + "]";
	}
}
