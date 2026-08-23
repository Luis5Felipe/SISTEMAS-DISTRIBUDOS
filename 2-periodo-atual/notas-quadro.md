# Notas do quadro (transcrição de fotos)

> Este arquivo é a transcrição em texto/diagrama das fotos de quadro branco e slides
> tiradas em sala (pasta `iimagens/`, removida após a transcrição). Organizado por data.

---

## 03/08 — Interfaces e o padrão ActionListener (Swing)

### O que é uma Interface

**Interface** ⇒ é a especificação de um **contrato de prestação de serviços**
(métodos) que pode ser **realizado** (implementado) por uma ou mais classes.

Propriedades:
- Se assemelha a uma classe abstrata;
- Não tem atributos;
- Todos os métodos são abstratos.

### Diagrama UML — JButton / ActionListener

```
+----------------+                              +---------------------------+
|    JButton     |   ·······(dependência)······>|      <<interface>>        |
+----------------+                              |       ActionListener      |
|                |                              +---------------------------+
+----------------+                              |     actionPerformed()     |
| addActionListener()                           +---------------------------+
+----------------+                                          ^
                                                              ¦ (linha tracejada
                                                              ¦  + triângulo vazado)
                                                              ¦  Realização (implementação)
                                                              ¦
                                              +---------------------------+
                                              | ActionListenerParaBotaoCancelar |
                                              +---------------------------+
                                              |                           |
                                              +---------------------------+
                                              |     actionPerformed()     |
                                              +---------------------------+
```

Legenda de setas UML vista no quadro:
- **Especialização (herança)**: seta com triângulo vazado apontando para a classe-mãe.
- **Associação unidirecional**: seta simples (linha cheia).
- **Dependência**: linha tracejada com seta aberta (usada entre `JButton` e `ActionListener` acima).
- **Realização/implementação**: linha tracejada com triângulo vazado (usada entre a interface e a classe concreta).

### Código da interface `ActionListener`

```java
public interface ActionListener {
    public void actionPerformed(ActionEvent e);
}
```

### Slide — Classe Interna Anônima (Exemplo 1: criando explicitamente a classe interna)

Ideia: em vez de usar uma classe anônima inline, cria-se uma classe separada que
implementa `ActionListener` e é passada para `addActionListener`.

```java
JButton btCancelar = new JButton("Cancelar");
btCancelar.addActionListener(new ActionListenerParaBotaoCancelar());
btCancelar.setBounds(251, 200, 89, 23);
contentPane.add(btCancelar);
this.setVisible(true);

public class ActionListenerParaBotaoCancelar implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        IIncluirAgencia ctrl = (IIncluirAgencia) getCtrl();
        ctrl.finalizar();
    }
}
```

A seta no slide liga o `new ActionListenerParaBotaoCancelar()` (uso) à declaração da
classe (implementação) — mostrando que o objeto passado ao `addActionListener` é uma
instância dessa classe interna.

---

## 17/08 — Threads, `synchronized` e semáforos

### Regra geral de concorrência

- **1 Objeto** pode executar **N métodos concorrentemente** em **N Threads**
  (1 método em cada thread).

**Mas:**

- **1 Objeto** só poderá executar **1 método `synchronized` por vez**, mesmo tendo N Threads.
- **Somente 1 bloco `synchronized` com o mesmo semáforo** será executado por vez,
  mesmo tendo N Threads.

(Ou seja: `synchronized` serializa o acesso ao mesmo objeto/monitor mesmo que existam
várias threads tentando rodar métodos diferentes simultaneamente nesse mesmo objeto;
blocos `synchronized(objeto)` só bloqueiam entre si quando usam o **mesmo** objeto
como semáforo/monitor.)

### Diagrama — Runnable + Thread + objeto compartilhado (`Impressora`)

Exemplo de 3 threads, cada uma rodando seu próprio `Runnable` (`MeuRunnable`), mas
todas chamando o método `imprime()` do **mesmo** objeto `Impressora` (recurso
compartilhado — por isso `imprime()` deve ser `synchronized`).

```
                              press
                                │
                                ▼
                          +-----------+
                          | Impressora|  <-- imprime() (chamado pelas 3 threads)
                          +-----------+
                           ▲    ▲    ▲
                imprime() /     |     \ imprime()
                         /      |      \
              +--------------+ | +--------------+
              | MeuRunnable  | | | MeuRunnable  |   ... (3º MeuRunnable igual)
              +--------------+ | +--------------+
                     ▲         |        ▲
                run()│         |run()   │run()
                     │         |        │
              +-----------+    |  +-----------+
              |  Thread   |    |  |  Thread   |   ... (3ª Thread igual)
              +-----------+    |  +-----------+
                th1: [Thread]  |     th2: [Thread]        th3: [Thread]
                start()────────┘
```

Fluxo: `th.start()` → dispara a Thread → a Thread chama `run()` do `MeuRunnable`
associado → `run()` chama `impressora.imprime()` no objeto `Impressora` compartilhado
pelas 3 threads. Como `imprime()` é o recurso compartilhado, é o candidato natural a
ser `synchronized` para evitar que as 3 threads imprimam ao mesmo tempo.
