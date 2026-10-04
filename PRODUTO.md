# ScreenFakeCam — Produto

> Escrito na Fundação (F1) a partir da entrevista com o dono. Só muda com decisão do dono registrada em ADR.

## O que é

Uma câmera para Android cujo "visor" é uma imagem guardada no celular: o usuário enquadra a imagem (zoom, posição) e
aperta o obturador quando quiser, e o app também lê QR code e código de barras da imagem.

## Problema e para quem

Às vezes a imagem que a pessoa precisa entregar já está no celular (um print, um documento escaneado, um QR code
recebido por mensagem), mas o app que pede a foto só oferece a câmera. Hoje a saída é fotografar a tela de outro
aparelho ou procurar um leitor que aceite imagem da galeria. O ScreenFakeCam resolve isso para qualquer pessoa com
Android.

## Quem usa e quantos

| Perfil | Quantas pessoas | Com que frequência |
| --- | --- | --- |
| Usuário do app (sem cadastro) | Público geral, sem estimativa | Ocasional, quando precisa entregar uma imagem guardada |

## Onde roda

Celular **Android 8.0 (API 26) ou mais novo**. Sem versão para iPhone.

Dois modos:

1. **Câmera sozinha:** o usuário escolhe uma imagem; ela fica aberta como visor, sem disparo automático; o usuário
   ajusta zoom e enquadramento e aperta o obturador; a "foto" é salva como imagem nova na galeria.
2. **Leitor de QR code e código de barras:** lê o código da imagem escolhida e oferece copiar o texto, abrir o link
   ou compartilhar.

## Login e perfis de acesso

Sem login e sem perfis. Não há conta nem servidor.

## Funciona sem internet?

Sim, totalmente. O app **não pede a permissão de internet**: nada sai do celular. Abrir um link lido de um QR code é
feito pelo navegador do usuário, não pelo app.

## Dados sensíveis e nível ASVS

| Dado | Tipo (dinheiro, saúde, dado pessoal, outro) | Por que o sistema precisa dele |
| --- | --- | --- |
| Imagem escolhida pelo usuário | Outro (pode conter qualquer coisa, inclusive dado pessoal, mas o app não a guarda) | É o conteúdo do visor e da foto |

**Nível ASVS:** L1. O dono classificou o app como sem dado sensível, com estas condições, que viram requisitos:

- o app só lê a imagem que o usuário escolhe no seletor do Android (sem permissão ampla de armazenamento);
- não guarda cópia das imagens nem histórico: o que sai do app é só a foto salva na galeria a pedido do usuário;
- sem permissão de internet, sem analytics, sem anúncios, sem rastreamento.

Se alguma condição deixar de valer, o nível precisa ser revisto (L2 com dado pessoal).

## Integrações obrigatórias

Só o próprio Android: seletor de fotos, a galeria (MediaStore) e o compartilhamento. Nenhum serviço externo.

## Orçamento mensal de hospedagem

R$ 0. Distribuição pelo GitHub Releases (APK assinado), sem servidor. F-Droid pode vir depois; Play Store fora por
enquanto.

## O que o dono já domina

Nunca fez app Android. A stack precisa ser simples de manter e bem documentada; o dono conhece web/TypeScript.

## Prazo do primeiro uso real

Sem pressa: é o piloto do Big Bang, o ritmo segue a esteira e as correções do framework.

## Fora do escopo

- **Câmera virtual que se passa pela câmera do aparelho dentro de outros apps** (por root, Xposed/LSPosed ou
  similar). Esse tipo de ferramenta serve sobretudo para enganar verificações de presença (selfie de identidade,
  ponto, prova de entrega) e não será feito. Apps que abrem a própria câmera ao vivo, como a maioria dos leitores de
  QR, não verão o ScreenFakeCam; para QR, use o leitor do próprio app.
- Responder quando outro app pede uma foto (`ACTION_IMAGE_CAPTURE`): a partir do Android 11 só as câmeras
  pré-instaladas recebem esse pedido (ADR-0002).
- Disparo automático da foto: a foto só é tirada quando o usuário aperta o obturador.
- Edição de imagem além de zoom e enquadramento; vídeo.
- iPhone, conta, sincronização, servidor.

## Histórico de mudanças

| Data | O que mudou | ADR |
| --- | --- | --- |
| 2026-10-04 | Versão inicial (Fundação F1) | — |
| 2026-10-04 | Retirado o modo "câmera para outros apps" (limite do Android 11) | ADR-0002 |
