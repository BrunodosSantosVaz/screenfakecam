# Memória do projeto

Pegadinhas que a próxima sessão (de qualquer IA ou pessoa) precisa saber. Uma linha por item, com o motivo.

## Ambiente

- Build local: `export JAVA_HOME=~/Android/jdk-21 ANDROID_HOME=~/Android/Sdk` (instalados só no usuário, F5). Na CI, o
  runner `ubuntu-24.04` já tem JDK e Android SDK; o AGP baixa a plataforma que faltar.
- Rode o Gradle com `</dev/null` em scripts: o `cp`/`rm` do shell do dono é interativo e um prompt escondido trava o
  comando.

## Build e testes

- Teto de decodificação (#81, RN-0004): escolher potência de dois até ceil(lado/sample)<=maxSide; comparar a
  próxima amostra para preservar lado>=maxSide excedia o teto. MainActivity passa duas vezes o lado maior da tela.
  O teste unitário antigo esperava 800 para maxSide 500 e foi corrigido para 400 no commit de regressão; aceites/RN
  ficaram intactos. PNG 10000/maxSide 4800 reproduziu largura 5000 antes do fix. Redução pode ficar abaixo do teto
  por amostragem em potência de dois; não prometer conservação dos pixels originais.

- `compileSdk = 37`: as bibliotecas do Compose 1.12 (BOM 2026.09) exigem compilar contra a API 37; `targetSdk` segue 36.
- Robolectric roda na imagem do **Android 35** (`app/src/test/resources/robolectric.properties`): na 36 ele falha com
  "Failed to interact with raw FileDescriptor internals" no JDK 21. Os testes também precisam de
  `--add-opens=java.base/java.io=ALL-UNNAMED` (já no `app/build.gradle.kts`).
- Testes de aceite ficam em `tests/aceite/`, **todos no pacote `aceite`** (o `@Pendente` então não precisa de import:
  liberar a marca não deixa import sem uso, que a trava não deixaria remover). Entram no módulo `app` por
  `sourceSets.test.kotlin.directories` (no AGP 9, `java.srcDir` não compila Kotlin, e o teste "passava" sem rodar).
- Nome do teste de aceite entre crases com o ID da regra: ``fun `RN-0001 CA-1 …`()`` (`testes.padrao_teste`).
- Cenários falam com um *driver* (interface em `tests/aceite/`, implementação registrada por `ServiceLoader` em
  `app/src/test/resources/META-INF/services/`): compilam antes de a funcionalidade existir.
- `@Pendente // pendente da tarefa #N` + `PendingRule`: pendente que falha = pulado; pendente que passa = reprova.
- Funções `@Composable` começam com maiúscula: o ktlint está configurado para isso no `.editorconfig`.
- Cobertura mínima de 80% vale só para `domain` e `application` (Kover).
- Robolectric com `graphicsMode=NATIVE` (`robolectric.properties`): no modo simulado, `ImageBitmap` dá NullPointerException.
- Arquitetura: só `ScreenFakeCamApp` (raiz de composição) conhece `infrastructure`; a `ui` fala com as portas de
  `application` (ex.: `PictureLoader`). O Konsist reprova `ui` importando `infrastructure`.

## Segurança

- O manifesto não pede internet, armazenamento amplo nem câmera; `PermissionsTest` reprova se alguém acrescentar.
- A assinatura do APK só existe na CI, nos segredos `BB_ASSINATURA_*`; keystore nunca entra no repositório.
