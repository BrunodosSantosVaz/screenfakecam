# ScreenFakeCam

Sistema em construção com o [Big Bang](.bigbang/README.md), um framework para uma pessoa e suas IAs planejarem,
construírem e manterem sistemas profissionais.

## Situação

Em **Fundação**: as decisões de produto, stack, design e GitHub ainda estão sendo tomadas. Acompanhe pelas issues com a
label `fundacao`.

## Para quem trabalha neste repositório

- Abra sua IA na pasta e diga o que quer fazer; as instruções para IAs estão em `AGENTS.md`.
- O que o sistema é: `PRODUTO.md`. A stack: `STACK.md`. O design: `DESIGN.md` (nascem na Fundação).
- O processo de trabalho: `.bigbang/processo/`.

## Instalar e usar

Veja [docs/guia/usar.md](docs/guia/usar.md). O APK assinado de cada versão fica nas
[Releases](https://github.com/BrunodosSantosVaz/screenfakecam/releases).

## Desenvolver

Android Studio ou, na linha de comando, JDK 17+ e Android SDK (`JAVA_HOME`, `ANDROID_HOME`): os comandos estão em
`bigbang.toml` (`[comandos]`), por exemplo `./gradlew testDebugUnitTest`. Pegadinhas em `docs/memoria.md`.

<!-- A IA completa este README ao fim da Fundação: o que o sistema faz, como rodar localmente, variáveis de ambiente
     (sem valores) e como publicar. -->
