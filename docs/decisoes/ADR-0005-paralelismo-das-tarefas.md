# ADR-0005: Até 3 tarefas por IA ao mesmo tempo

- **Situação:** aceita
- **Data:** 2026-10-05
- **Decisores:** Bruno dos Santos Vaz (dono); Claude Code

## Contexto e problema

Na Sprint 2, com uma tarefa por IA, as tarefas independentes dos dois épicos rodaram em série e a entrega demorou.
O dono: "era mais dinâmico quando rodava todos épicos na sprint".

## Decisão e justificativa

`ias.tarefas_por_ia = 3`: a IA pode ter até três tarefas assumidas ao mesmo tempo, cada uma na sua pasta própria e
na sua branch, desde que as tarefas não dependam umas das outras (o *Criar branches* já só libera as desbloqueadas).

## Consequências

### Positivas

- Tarefas independentes de épicos diferentes andam em paralelo.

### Negativas

- Mais pastas de trabalho abertas; a IA libera cada posse ao mesclar.

## Referências

- Sprint 2 (#36, #37); `bigbang.toml` `[ias]`.
