# Memória do projeto

Pegadinhas que a próxima sessão (de qualquer IA ou pessoa) precisa saber. Uma linha por item, com o motivo.

## Ambiente

- Build local: `export JAVA_HOME=~/Android/jdk-21 ANDROID_HOME=~/Android/Sdk` (instalados só no usuário, F5). Na CI, o
  runner `ubuntu-24.04` já tem JDK e Android SDK; o AGP baixa a plataforma que faltar.
- Rode o Gradle com `</dev/null` em scripts: o `cp`/`rm` do shell do dono é interativo e um prompt escondido trava o
  comando.

## Build e testes

- `compileSdk = 37`: as bibliotecas do Compose 1.12 (BOM 2026.09) exigem compilar contra a API 37; `targetSdk` segue 36.
- Robolectric roda na imagem do **Android 35** (`app/src/test/resources/robolectric.properties`): na 36 ele falha com
  "Failed to interact with raw FileDescriptor internals" no JDK 21. Os testes também precisam de
  `--add-opens=java.base/java.io=ALL-UNNAMED` (já no `app/build.gradle.kts`).
- Testes de aceite ficam em `tests/aceite/` (pacote `aceite`) e entram como fonte de teste do módulo `app`.
- Funções `@Composable` começam com maiúscula: o ktlint está configurado para isso no `.editorconfig`.
- Cobertura mínima de 80% vale só para `domain` e `application` (Kover).

## Segurança

- O manifesto não pede internet, armazenamento amplo nem câmera; `PermissionsTest` reprova se alguém acrescentar.
- A assinatura do APK só existe na CI, nos segredos `BB_ASSINATURA_*`; keystore nunca entra no repositório.
