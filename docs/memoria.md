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
- SBOM Android (#72): o job de publicação só tem o checkout, não o cache Gradle do build. Versionar
  app/gradle.lockfile real (releaseCompileClasspath/releaseRuntimeClasspath), em modo estrito; Syft lê o lock e
  inclui os componentes Maven. Atualização: resolver ambas configurações com --write-locks e revisar o diff.
  O scanner de lock não prova licenças completas nem relações de dependência; não editar BOM/lock à mão.
- A assinatura do APK só existe na CI, nos segredos `BB_ASSINATURA_*`; keystore nunca entra no repositório.

- Épico #71 corrige somente documentos da produção. #73 registra critérios/procedimentos antes de #74, reutiliza 16 aceites vigentes intactos e exige leitura independente além da CI documental. Teclado (#70) e SBOM (#72) têm correções próprias; não inventar homologação física.

## Operação e documentação (#71)

- README/guia descrevem resolução do bitmap decodificado, com redução possível de imagens grandes; não prometer
  resolução original ou teto exato ainda não demonstrado. RN-0004 e os aceites permanecem intactos.
- Runbooks em docs/operacao/README.md: APK não tem rollback de servidor. Downgrade por cima é bloqueado por
  versionCode; preferir correção nova com mesma chave. Backup do keystore não foi comprovado pelo acesso público.
- Offline significa ausência de permissão de internet no app; Compartilhar/Abrir link/área de transferência passam
  conteúdo ao Android/outro app. Fotos salvas são externas e dependem da política da galeria/provedor.

- Conferência documental #75 em docs/validacao/71-documentacao-producao.md separa entrega documental dos bugs
  ainda não publicados. A próxima release deve atualizar versão/limitações do README e registrar ensaio Android
  com hash exato; não transportar evidência de APK antigo para uma candidata nova.
