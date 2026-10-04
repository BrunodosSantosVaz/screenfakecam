# ADR-0002: Sem o modo "câmera para outros apps"

- **Situação:** aceita
- **Data:** 2026-10-04
- **Decisores:** Bruno dos Santos Vaz (dono); Claude Code

## Contexto e problema

O `PRODUTO.md` da F1 previa três modos, entre eles responder quando outro app pede uma foto
(`ACTION_IMAGE_CAPTURE`). A pesquisa da F2 mostrou que, a partir do Android 11, só as câmeras pré-instaladas
respondem a esse pedido; um app de terceiros só é chamado quando o pedido nomeia o pacote dele. Fazer o app se
passar pela câmera do aparelho exigiria root e interceptação, fora do escopo (`PRODUTO.md`).

## Fatores de decisão

- O modo quase nunca apareceria em celulares atuais (só Android 8 a 10 ou apps antigos).
- O dono só quer o modo se o app puder agir como a câmera do aparelho.

## Opções consideradas

1. Trocar por "fonte de imagem" quando outro app pede para escolher um arquivo.
2. Manter, documentando o limite.
3. Retirar o modo.

## Decisão e justificativa

Escolhida: **retirar o modo** (opção 3), por decisão do dono: "prefiro que ele funcione apenas como camera sozinha e
leitor de qr mesmo."

## Consequências

### Positivas

- Produto menor e honesto sobre o que funciona; nenhuma permissão ou componente exportado a mais.

### Negativas

- Não há integração com o pedido de foto de outros apps. Para QR, o usuário copia ou compartilha o resultado.

## Referências

- https://developer.android.com/about/versions/11/behavior-changes-11 (Media capture intents)
- `docs/pesquisa/2026-10-04-stack-android.md`
