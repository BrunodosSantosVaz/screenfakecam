# Pesquisa de stack — ScreenFakeCam (2026-10-04)

Feita na F2 para um app Android offline, sem login, com visor parado numa imagem, obturador manual e leitura de
QR/código de barras, distribuído como APK pelo GitHub Releases (talvez F-Droid depois), licença GPL-3.0, dono sem
experiência em Android.

## Restrição da plataforma que mudou o produto

A partir do Android 11, só as câmeras pré-instaladas respondem a `ACTION_IMAGE_CAPTURE` (pedido de foto de outro
app); um app de terceiros só é chamado se o pedido nomear o pacote dele. O modo "câmera para outros apps" foi
retirado do produto por decisão do dono (ADR-0002).
Fonte: https://developer.android.com/about/versions/11/behavior-changes-11

## Versões atuais (consultadas em 2026-10-04)

| Item | Versão estável | Fonte |
| --- | --- | --- |
| Android Gradle Plugin | 9.4.1 (9.4.0 de setembro de 2026; suporta até API 37) | https://developer.android.com/build/releases/agp-9-4-0-release-notes |
| Kotlin | 2.4.20 (setembro de 2026) | https://kotlinlang.org/docs/whatsnew2420.html |
| Compose BOM | 2026.09.00 (Compose 1.12) | https://developer.android.com/develop/ui/compose/bom |
| ZXing core | 3.5.4 (novembro de 2025), Apache-2.0, Java puro | https://central.sonatype.com/artifact/com.google.zxing/core |
| Flutter | 3.47 (agosto de 2026) | https://flutter.dev/blog/whats-new-in-flutter-3-47 |
| Expo SDK / React Native | 57 / 0.86 (junho de 2026) | https://expo.dev/changelog/sdk-57 |

## Leitura de código de barras e licença

- **ML Kit** (Google) é proprietário: o F-Droid não aceita no repositório principal e marca como "Non-Free
  Dependencies". Fonte: https://f-droid.org/en/docs/Inclusion_Policy/
- **ZXing core** é livre (Apache-2.0, compatível com GPL-3.0), roda offline e decodifica de um `Bitmap`, exatamente o
  caso de "ler o código da imagem escolhida". Projeto em manutenção mínima (só correções), mas estável há anos.

## Incertezas

- Versões mudam rápido: o `STACK.md` fixa as versões e o Dependabot propõe as novas.
- O tamanho do APK é estimativa (nativo ~4–6 MB; Flutter ~15–20 MB; React Native ~25–35 MB).
