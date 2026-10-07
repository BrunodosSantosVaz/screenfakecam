# Teclado e D-pad do visor

Pesquisa da correção #70 (FE-04). O DESIGN já prevê setas para o enquadramento e +/- para zoom; não há dependência
nova nem mudança de RN. O visor precisa participar do foco, consumir somente as teclas reconhecidas em KeyDown e
deixar Tab/Voltar e outros atalhos para o Android. O contorno âmbar usa os tokens do design.

Fonte primária: [Compose UI Test](https://developer.android.com/reference/kotlin/androidx/compose/ui/test/package-summary)
e [KeyInjectionScope](https://developer.android.com/reference/kotlin/androidx/compose/ui/test/KeyInjectionScope).
`performKeyInput` injeta pares de eventos e o foco é solicitado pela ação semântica RequestFocus. Entre comandos
sucessivos, o teste espera a recomposição. O teste novo usa createComposeRule v2, compatível com Compose1.12.1.

O commit de regressão compilou e os cinco cenários falharam porque o visor não tinha RequestFocus. Após a correção,
a comparação numérica dos offsets usa delta0: +0.0 e -0.0 representam a mesma posição na borda; nenhuma tolerância
para deslocamento foi introduzida. Zoom1×/4× e fronteiras continuam limitados por Framing, coberto pelos aceites
originais intactos.

Limite da evidência: Robolectric/Compose não são teste do APK em aparelho físico. O emulador disponível para
homologação usa Android15; qualquer execução nele deve ser registrada separadamente, com o hash do APK usado.
