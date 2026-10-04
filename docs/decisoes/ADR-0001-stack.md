# ADR-0001: Stack — Kotlin com Jetpack Compose, ZXing e perfil compilado (APK)

- **Situação:** aceita
- **Data:** 2026-10-04
- **Decisores:** Bruno dos Santos Vaz (dono); Claude Code

## Contexto e problema

O ScreenFakeCam é um app Android pequeno, offline, sem login e sem servidor (`PRODUTO.md`): mostra uma imagem
guardada como visor, com zoom e enquadramento, salva a "foto" na galeria e lê QR e código de barras. É distribuído
como APK pelo GitHub Releases, sob GPL-3.0, talvez no F-Droid depois. O dono nunca fez app Android.

## Fatores de decisão

- Só Android: nenhum ganho em ser multiplataforma.
- Licença livre de ponta a ponta (GPL-3.0, possível F-Droid): sem bibliotecas proprietárias.
- Offline e sem permissão de internet.
- Manutenção simples e documentação abundante para quem começa (dono e IAs).
- APK pequeno; custo zero.

## Opções consideradas

1. **Kotlin + Jetpack Compose** (nativo), ZXing para códigos.
2. **Flutter** (Dart), ZXing via plugin.
3. **React Native + Expo** (TypeScript), leitor pelo plugin da câmera (ML Kit) ou módulo nativo próprio.

## Decisão e justificativa

Escolhida: **Kotlin + Jetpack Compose**, porque é o caminho oficial do Android, sem camada intermediária para um app
100% Android; gera o menor APK (~4–6 MB contra ~15–20 MB e ~25–35 MB); combina com GPL e F-Droid (ZXing é Apache-2.0,
o ML Kit é proprietário); e tem a maior base de documentação e exemplos. A familiaridade do dono com TypeScript não
compensa, na opção 3, a dependência de código nativo para o seletor e o leitor, nem o leitor proprietário.

Detalhes que fazem parte da decisão:

- minSdk 26 (Android 8.0); compileSdk/targetSdk 36; JDK 21 no build; Gradle com catálogo de versões.
- Sem permissão `INTERNET`, sem permissão ampla de armazenamento: a imagem entra pelo seletor de fotos do Android
  e a foto sai pelo MediaStore.
- Camadas `domain` (Kotlin puro), `application`, `infrastructure` (Android, MediaStore, ZXing) e `ui` (Compose),
  conferidas por Konsist.
- Testes locais com JUnit 4, Robolectric e Compose UI Test, sem emulador na CI. Testes de aceite em `tests/aceite/`,
  ligados como fonte de teste do módulo `app`. Teste pendente marcado com a anotação `@Pendente` do projeto
  (falha esperada estrita).
- Perfil compilado do Big Bang, sistema `android`: um APK assinado por candidata, promovido sem recompilar.

## Consequências

### Positivas

- Menos peças entre o código e o Android; APK pequeno; caminho livre para o F-Droid.

### Negativas

- O dono aprende Kotlin e Compose: a IA explica o básico no `docs/memoria.md` e nas revisões.
- ZXing está em manutenção mínima (só correções): se deixar de servir, troca pelo portão de tecnologia
  (`bb-nova-tecnologia`).
- Sem testes em aparelho real na CI: a homologação é feita pelo dono instalando o APK.

## Referências

- `docs/pesquisa/2026-10-04-stack-android.md` (versões e fontes oficiais).
- Política de inclusão do F-Droid: https://f-droid.org/en/docs/Inclusion_Policy/
