# Resumo dos PDFs (referência rápida)

> Resumo de apoio com o conteúdo dos 3 PDFs da pasta `pdfs-aula/` e
> `pdfs-revisao-outra-materia/`, para consulta rápida quando surgir alguma dúvida
> (evita ter que reabrir o PDF inteiro para relembrar um detalhe). Os dois últimos
> módulos (12 e 15) são revisão de **outra matéria**, usados no primeiro dia de aula;
> só o Módulo 16 é conteúdo desta disciplina.

---

## Módulo XVI — Programação Concorrente (`pdfs-aula/`)

### Concorrência
- Concorrência = dois ou mais trechos de código executando "simultaneamente".
- 1ª estratégia: subprocessos (`fork()` em C) — caro para o S.O.
- 2ª estratégia: **Threads** — muito mais leves, exigem suporte do S.O.

### Criando Threads (duas formas)
**1) Especializando `Thread`**
```java
package controle;
public class ThreadExemplo extends Thread {
    private int tempoEmMilisecs;
    public void run() {
        try { Thread.sleep(tempoEmMilisecs); }
        catch (InterruptedException ie) { ... }
    }
}
```
- Sobrescreve `run()` com o código a executar.
- Instancia e chama `.start()` (nunca chamar `start()` duas vezes no mesmo objeto —
  para nova thread, novo objeto).

**2) Implementando `Runnable`** (preferível quando a classe já estende outra coisa)
```java
public class Exemplo extends OutraClasse implements Runnable {
    private Thread minhaThread;
    public void metodoQueCriaNovaThread() {
        this.minhaThread = new Thread(this);
        this.minhaThread.start();
    }
    public void run() { ... }
}
```

Métodos de `Thread`: `start()`, `run()`, `static sleep(long ms)`, `isAlive()`.

### Propriedades das Threads
- Cada Thread tem "processo pai" e Stack própria, mas **compartilha o heap**.
- Logo: um objeto pode executar **N métodos não-sincronizados** concorrentemente,
  1 em cada thread (N threads = N métodos ao mesmo tempo nesse objeto).

### Sincronização
- **Região/seção crítica**: trecho que acessa dados/objetos compartilhados entre threads.
- **Monitor**: todo objeto Java tem um; garante exclusão mútua no que estiver
  vinculado a ele via `synchronized`.
- **Método `synchronized`**: só roda se o objeto não estiver executando *outro*
  método sincronizado em outra thread → um objeto só executa **1 método
  sincronizado por vez**, mesmo com N threads atuando nele.

```java
public synchronized void addElement(int v) throws Exception { ... }
public synchronized int getElement() throws Exception { ... }
```

- **Bloco `synchronized(objeto)`**: exclusão mútua restrita a um trecho, usando o
  monitor do `objeto` indicado — não precisa ser `this`.

```java
public void addElement(int v) throws Exception {
    synchronized(this) {
        if (total == TAM) throw new Exception("Fila cheia!");
        vetInt[(inicio + total) % TAM] = v;
        total++;
    }
}
```

### `wait()` / `notify()` / `notifyAll()`
- Só podem ser chamados **dentro de um contexto `synchronized`**.
- A mensagem vai para o objeto **dono do monitor** usado:
  - método sincronizado → `this`
  - bloco sincronizado → o objeto parâmetro do bloco
  - método sincronizado estático → o objeto `Class` da classe

### Resumo (a regra mais cobrada)
1. Um objeto pode executar N métodos **não sincronizados** concorrentemente em N threads.
2. Mas só **1 método sincronizado por vez** nas N threads.
3. Só **1 bloco `synchronized` por vez** quando o objeto for o mesmo semáforo do bloco.

→ Exemplos práticos disso estão em
[`exemplos/Exemplo 02 - Criacao de Threads em Java/`](exemplos/Exemplo%2002%20-%20Criacao%20de%20Threads%20em%20Java)
(`prjThread01`–`04`, ver o `README.md` de lá) e em [`notas-quadro.md`](notas-quadro.md)
(seção 17/08, diagrama Impressora/MeuRunnable/Thread).

---

## Módulo XII — Java Persistence API / JPA (`pdfs-revisao-outra-materia/`)

*(Revisão de outra matéria — Java Web/Persistência, não é conteúdo de Sistemas Distribuídos)*

### Maven
- `[Configure][Convert to Maven Project]` no Eclipse gera `pom.xml`.
- Dependências ficam em `<dependencies>` (após `</build>`); Maven baixa os bytecodes
  e cria a biblioteca "Maven Dependencies".

### Conceito
- JPA = especificação Java EE para persistência de objetos em SGBD relacional (ORM).
- Interface principal: `EntityManager`. Precisa de um *Persistence Provider*
  (Hibernate, EclipseLink, TopLink) + driver JDBC.

### `persistence.xml` (em `META-INF/`, ou `src/main/resources/META-INF` no Eclipse)
```xml
<persistence-unit name="PuMvcJpa" transaction-type="RESOURCE_LOCAL">
  <provider>org.hibernate.ejb.HibernatePersistence</provider>
  <properties>
    <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect" />
    <property name="javax.persistence.jdbc.driver" value="org.postgresql.Driver" />
    <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/exemplo_jpa" />
    <property name="javax.persistence.jdbc.user" value="postgres" />
    <property name="javax.persistence.jdbc.password" value="postgres" />
    <property name="hibernate.hbm2ddl.auto" value="update" />
  </properties>
</persistence-unit>
```

### Mapeamento (anotações)
- `@Entity` (classe precisa ser POJO/JavaBean: construtor vazio + gets/sets).
- `@Id` (chave primária), `@GeneratedValue` (não se preocupar com o valor).
- `@Table(name=...)`, `@Column(name=..., length=..., unique=...)`.

### Relacionamentos
- `@OneToMany(mappedBy = "depto")` — no lado "muitos", `mappedBy` aponta o atributo
  do outro lado. Pode usar `Map` com `@MapKey(name="cpf")`.
- `@ManyToOne(fetch = FetchType.LAZY)` + `@JoinColumn(name="depto", ...)` — no lado "um".
- `@OneToOne(mappedBy=...)` — só um dos dois lados precisa do `mappedBy`.
- `@ManyToMany(mappedBy=...)` ou `@JoinTable(name=..., joinColumns=..., inverseJoinColumns=...)`
  — onde tiver `@JoinTable` não precisa de `mappedBy`.

### Herança (especialização) em ORM — três estratégias
| Estratégia | Efeito |
|---|---|
| `SINGLE_TABLE` (default) | 1 tabela só para toda a hierarquia (+ `@DiscriminatorColumn`) |
| `JOINED` | 1 tabela por classe, ligadas pelo id |
| `TABLE_PER_CLASS` | 1 tabela por especialização (sem tabela para a superclasse abstrata) |

### Ciclo de vida do objeto
`transient (new, @Id=null)` → `persist()` → `managed/persistente (@Id!=null, sincronizado)`
→ `detach()` → `detached (não sincronizado)` ; `remove()` → `removed`.

### `EntityManager`
`persist(obj)` (INSERT) · `merge(obj)` (UPDATE/INSERT, retorna o objeto persistido)
· `remove(obj)` · `detach(obj)` · `refresh(obj)` (BD → objeto) · `flush()` (objeto → BD).

### `CascadeType`
`PERSIST` · `MERGE` · `REMOVE` · `REFRESH` · `ALL` · `DETACH` — propaga a operação do
objeto Pai para o(s) Filho(s) associado(s).

### Lazy vs Eager
`FetchType.LAZY` (proxy, carga sob demanda) vs `FetchType.EAGER` (carrega junto).

### JPQL
```java
TypedQuery q = em.createQuery("SELECT d FROM Departamento d", Departamento.class);
// com parâmetro:
TypedQuery q2 = em.createQuery("SELECT e FROM Empregado e WHERE e.idade < :maximo", Empregado.class);
q2.setParameter("maximo", 1000);
List<Empregado> r = q2.getResultList();     // ou q.getSingleResult() se for 1 item só
```
`@NamedQuery` permite declarar a query na própria `@Entity`. UPDATE/DELETE também
podem ser feitos via JPQL com `query.executeUpdate()`.

---

## Módulo XV — Outros Elementos da Linguagem Java (`pdfs-revisao-outra-materia/`)

*(Revisão de outra matéria — fundamentos de Java, não é conteúdo de Sistemas Distribuídos)*

### Especialização (`extends`)
- Sem herança múltipla; toda classe deriva direta/indiretamente de `java.lang.Object`
  (o compilador acrescenta `extends Object` se você não escrever nada).

### Construtores
- Nome igual ao da classe, **sem** tipo de retorno (nem `void`).
- **Regra**: todo construtor de subclasse deve chamar `super(...)` na 1ª linha —
  se você não escrever, o compilador insere `super();` automaticamente. Se a
  superclasse não tiver construtor vazio, isso vira **erro de compilação**.
- Se a classe não tiver nenhum construtor, o compilador gera um construtor default
  vazio (só com `super();`) — mas só se você não declarar nenhum outro.
- `this(...)` chama outro construtor da mesma classe (evita repetir código).
- `super.<método>(...)` chama a implementação do método na superclasse (útil
  quando o método foi sobrescrito).
- Atributos podem ser inicializados já na declaração (`private int voltagem = 110;`)
  — isso roda **antes** do corpo do construtor.
- Regra semântica: método chamado dentro de um construtor deveria ser `final`
  (evita que uma sobrescrita em subclasse rode antes do objeto estar pronto).

### Classe/método abstrato
- `abstract class` não pode ser instanciada diretamente.
- Método `abstract` não tem corpo; força as especializações a implementá-lo (ou
  elas também precisam ser `abstract`).

### `static`
- Bloco `static { ... }` roda uma vez, quando a classe é carregada pelo Class Loader;
  só manipula atributos/métodos estáticos.

### Late binding
- Polimorfismo em Java é resolvido em **tempo de execução** (late binding), não em
  tempo de compilação (early binding, como em C). `Object ptr = new Pessoa(); ptr.toString();`
  chama a versão de `Pessoa`, mesmo a variável sendo declarada `Object`.

### Padrão JavaBeans / POJO
- Construtor sem argumentos.
- Atributos `private`, acesso via `getX()`/`setX(x)`; para `boolean`, usa-se `isX()`
  em vez de `getX()`.

### Visibilidade
| Modificador | Acesso |
|---|---|
| `private` | só dentro da própria classe |
| *(sem modificador)* | classes do mesmo pacote |
| `protected` | mesmo pacote + subclasses (mesmo em outro pacote) |
| `public` | qualquer classe |

- Classe: só pode ser `public` ou "package" (sem modificador) — nunca `private`/`protected`
  no nível top-level.

### Classes internas
- Declaradas dentro de outra classe; acessadas de fora como `Externa.Interna`.
- **Classe interna anônima**: instancia e implementa/sobrescreve na hora, sem nome —
  base do padrão clássico de `ActionListener` do Swing:
```java
btCancelar.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
});
```
- Com expressão lambda (quando a interface tem 1 único método — interface funcional):
```java
btCancelar.addActionListener(e -> {
    IIncluirAgencia ctrl = (IIncluirAgencia) getCtrl();
    ctrl.finalizar();
});
```

### `java.lang.Class` / Reflection
- Três formas de obter o objeto `Class`: `objeto.getClass()`, `Pessoa.class`,
  `Class.forName("model.Pessoa")` (esta última também carrega o bytecode se
  ainda não carregado).
- `objClass.getMethods()` / `objClass.getFields()` retornam `Method[]`/`Field[]` —
  base do mecanismo de Reflection (usado em frameworks, injeção de dependência).
