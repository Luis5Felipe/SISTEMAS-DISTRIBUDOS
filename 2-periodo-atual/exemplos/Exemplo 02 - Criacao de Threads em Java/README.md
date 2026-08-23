# Exemplo 02 — Criação de Threads em Java

Quatro projetos de exemplo (descompactados a partir dos `.zip` originais do professor),
cada um demonstrando uma estratégia diferente de criação/sincronização de threads.

| Pasta | Estratégia |
|---|---|
| [`prjThread01/`](prjThread01) | Especialização da classe `Thread` (`extends Thread`) |
| [`prjThread02/`](prjThread02) | Realização da interface `Runnable` (`implements Runnable`) |
| [`prjThread03/`](prjThread03) | Sincronização usando **blocos** `synchronized` com um objeto semáforo explícito |
| [`prjThread04/`](prjThread04) | Sincronização usando **método** `synchronized` (recurso compartilhado) |

> Nota: os arquivos originais eram `prjThread01.zip`, `prjThreads02.zip`, `prjThreads3.zip`
> e `prjThreads4.zip`. O que veio marcado como `prjThreads3.zip` é, na prática, o exemplo
> de **método `synchronized`** (`prjThread04` aqui), e `prjThreads4.zip` é o de **bloco**
> `synchronized` (`prjThread03` aqui) — a numeração original dos arquivos não batia com
> o conteúdo, então a ordem foi ajustada pelo conteúdo real de cada um.
> Também removi os artefatos de build do Eclipse (`.project`, `.classpath`, `.settings/`,
> `bin/*.class`) que vinham dentro dos zips 3 e 4 — só o código-fonte (`src/`) foi mantido.

---

## prjThread01 — especialização de `Thread`

`MinhaThread extends Thread` recebe `id`, `numTabs` e uma referência de `semaforo`
(uma `String`, usada só como texto a imprimir aqui — **não há sincronização** neste
exemplo). Três threads (`th1`, `th2`, `th3`) são criadas e iniciadas com `start()`, e o
`main` fica em busy-wait (`while (th1.isAlive() || ...)`) até todas terminarem.

## prjThread02 — realização de `Runnable`

Mesma ideia de `CtrlPrograma`, mas a lógica de execução fica em `MeuRunnable implements
Runnable`, passado para `new Thread(runnable)`. Mostra a diferença estrutural entre
estender `Thread` diretamente (prjThread01) e implementar `Runnable` (mais flexível,
pois a classe fica livre para estender outra coisa).

## prjThread03 — bloco `synchronized` com semáforo explícito

`ProcessoPai` cria um objeto `monitor` (`new String()`) compartilhado e passa o mesmo
objeto para `PrimeiraThread` e `SegundaThread` (ambas `extends Thread`). Cada uma
executa seu laço dentro de `synchronized (this.monitor) { ... }`. Como as duas usam o
**mesmo** objeto como monitor/semáforo, só uma consegue estar dentro do bloco por vez —
é o exemplo prático de "somente 1 bloco `synchronized` com o mesmo semáforo executa por
vez", visto no quadro (ver [`../../notas-quadro.md`](../../notas-quadro.md), seção 17/08).

## prjThread04 — método `synchronized` (recurso compartilhado)

`Impressor.imprime(...)` é declarado `public synchronized void imprime(...)`. Três
`MeuRunnable` (cada um em sua própria `Thread`, criadas em `Programa.main`) chamam
`imprime()` no **mesmo** objeto `Impressor`. Por o método ser `synchronized`, o mesmo
objeto só executa `imprime()` para uma thread por vez — este é exatamente o diagrama
"Impressora / MeuRunnable / Thread" transcrito em
[`../../notas-quadro.md`](../../notas-quadro.md) (seção 17/08).
