# ADR-0003: Testes de aceite em Kotlin com driver, pacote único e marca estrita

- **Situação:** aceita
- **Data:** 2026-10-04
- **Decisores:** Bruno dos Santos Vaz (dono, por delegação à IA); Claude Code

## Contexto e problema

O Big Bang escreve os testes de aceite do épico antes da implementação, trava `tests/aceite/` (a tarefa só retira
as próprias marcas de pendente) e confere que todo teste cita uma regra na linha da declaração. Em Kotlin, um teste
que referencia uma classe que ainda não existe não compila e quebra o build inteiro; o `@Test` fica numa linha
própria; e retirar a marca de uma anotação importada deixaria um import sem uso, que o ktlint reprova e a trava não
deixa remover.

## Fatores de decisão

- Testes de aceite escritos antes, travados e sempre compilando.
- Rastreabilidade RN → teste automática.
- Pendente estrito: um teste marcado que passa precisa reprovar.

## Opções consideradas

1. Testes que usam as classes de produção direto (não compilam antes da implementação).
2. Driver por épico (padrão de testes de aceite em quatro camadas), pacote único e nome entre crases.

## Decisão e justificativa

Escolhida a opção 2:

- **Driver:** os cenários falam com uma interface definida em `tests/aceite/<épico>/`; a tarefa que implementa
  registra a fábrica em `app/src/test/resources/META-INF/services/` (`ServiceLoader`). Sem fábrica, o teste falha e
  conta como pendente.
- **Pacote único `aceite`** para todos os testes de aceite e para o `@Pendente`: a marca não precisa de import.
- **Nome entre crases com o ID da regra** (``fun `RN-0001 CA-1 …`()``): `testes.padrao_teste = "fun \`"`.
- **`@Pendente // pendente da tarefa #N` + `PendingRule`:** pendente que falha é pulado; pendente que passa reprova.
- Os testes entram no módulo `app` por `sourceSets.test.kotlin.directories` (no AGP 9, `java.srcDir` não compila
  Kotlin e os testes não rodavam).

## Consequências

### Positivas

- Testes de aceite compilam desde o PR de teste; liberar uma marca muda só a linha da marca.

### Negativas

- Cada épico com regra nova mantém um driver pequeno; a regra `PendingRule` precisa estar em cada classe de teste.

## Referências

- Épico #17, PRs #23, #24 e #25; `docs/memoria.md`.
- Achados enviados ao Big Bang (piloto E11).
