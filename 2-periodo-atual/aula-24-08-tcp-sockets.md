# Aula 24/08 — Sockets TCP e o conceito de protocolo

> Áudio da aula (`24-08-2026 19.09.m4a`, ~1h51min) transcrito e organizado em
> um artifact interativo, com diagramas e trechos de código reconstruídos.

**Link do material:** https://claude.ai/code/artifact/2ecd9ead-ddcf-42c7-ba8c-ef527a594ba6

*(artifact privado — abrir com a conta que estava logada quando foi gerado; use o menu de compartilhar na própria página se precisar mandar pra outra pessoa)*

## Resumo do conteúdo

- Revisão do exemplo de Threads (Pac-Man/Swing) como ponte para "uma thread
  por conexão" no servidor.
- Camadas de rede e diferença entre TCP e UDP.
- **Tese central da aula:** sockets/TCP resolvem só o transporte dos bytes —
  o *protocolo* (o que cada mensagem significa, ordem, formato) é você quem
  define. É isso que o TDE vai cobrar.
- Portas e sockets (`ServerSocket` / `Socket`), convenção de portas < 1024.
- Streams, bytes e charset (ASCII, UTF-8, o bug clássico de exibir UTF-8 como
  ISO-8859-1).
- Exemplo 1: protocolo Echo (N strings terminadas por string vazia).
- HTTP como exemplo real de protocolo textual (anatomia de request/response).
- Exemplo 2: calculadora TCP (protocolo de 3 linhas → 1 linha, double como
  texto vs. binário, tratamento de exceção como parte obrigatória do
  protocolo — não opcional).
- Checklist prático para o TDE.

Ver também: [`notas-quadro.md`](notas-quadro.md), [`exemplos/`](exemplos/).
