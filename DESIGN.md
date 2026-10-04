# ScreenFakeCam — Design

> Escrito na Fundação (F3). Só muda com decisão do dono registrada em ADR.
>
> **Regra para o front (`FE-01`): todo componente novo usa só os tokens e componentes daqui.** Cor, espaçamento e
> fonte literais no código são reprovados pelo lint.

## Identidade

- **Nome visual:** ScreenFakeCam, com cara de **câmera de verdade**: fundo preto, visor ocupando a tela, controles
  brancos, obturador redondo grande e destaque **amarelo âmbar**. Tema escuro sempre (não segue o tema do sistema).
- **Logo:** `docs/design/logo.svg` (corpo de câmera em contorno âmbar com uma imagem dentro, sobre preto). No
  Android vira o ícone adaptativo (fundo preto, primeiro plano âmbar).
- **Uso:** área de respiro de 1/8 do lado; tamanho mínimo 24 px; sempre sobre fundo escuro.

## Paleta

Contraste calculado pela fórmula WCAG 2.2 (nível **AA**: 4,5:1 para texto normal; 3:1 para texto grande e
componentes). Todos os pares usados passam com folga.

| Token | Valor | Uso | Contraste sobre o fundo |
| --- | --- | --- | --- |
| `--cor-fundo` | `#000000` | Visor e fundo das telas | — |
| `--cor-superficie` | `#1C1C1E` | Barras, cartões, folhas | — |
| `--cor-superficie-2` | `#2C2C2E` | Botões secundários | — |
| `--cor-texto` | `#FFFFFF` | Texto principal, obturador | 21:1 (fundo); 17:1 (superfície); 13,9:1 (superfície 2) |
| `--cor-texto-secundario` | `#B3B3B3` | Legendas, explicações | 10:1 (fundo); 8,1:1 (superfície) |
| `--cor-primaria` | `#FFC107` | Botão principal, zoom ativo, foco, logo | 12,9:1 (fundo); 8,6:1 (superfície 2) |
| `--cor-sobre-primaria` | `#000000` | Texto sobre o âmbar | 12,9:1 |
| `--cor-erro` | `#FF6B6B` | Mensagem de erro | 6,1:1 (superfície) |
| `--cor-sucesso` | `#4ADE80` | "Salva na galeria" | 12:1 (fundo) |
| `--cor-alerta` | `#FFC107` | Avisos | 12,9:1 (fundo) |

## Tipografia

Roboto, a fonte do próprio Android: nada para baixar (o app não usa internet).

| Token | Família | Tamanho | Peso | Altura de linha |
| --- | --- | --- | --- | --- |
| `--fonte-titulo` | Roboto | 22 sp | 600 | 28 sp |
| `--fonte-corpo` | Roboto | 16 sp | 400 | 24 sp |
| `--fonte-legenda` | Roboto | 13 sp | 500 | 18 sp |

Tamanhos em `sp`: respeitam o tamanho de fonte escolhido pelo usuário no Android.

## Espaçamento, raio e sombra

| Token | Valor |
| --- | --- |
| `--espaco-1` … `--espaco-6` | 4, 8, 12, 16, 24, 32 dp |
| `--raio-padrao` | 12 dp (cartões, miniaturas, folhas) |
| `--raio-pilula` | totalmente arredondado (botões) |
| `--sombra-padrao` | `0 4 16 rgba(0,0,0,.6)` (só no aviso flutuante) |
| `--toque-minimo` | 48 dp |
| `--obturador` | 76 dp |

Fonte única dos valores: `docs/design/tokens.css`. No app, os mesmos valores vivem no tema Compose (`ui/theme`).

## Ícones

Material Symbols (Apache-2.0), traço de 2 dp, 24 dp. Ícone sozinho (voltar, trocar imagem, ler código) sempre tem
rótulo acessível (`contentDescription`); nas ações principais, ícone com texto.

## Componentes base

| Componente | Quando usar |
| --- | --- |
| Botão primário (âmbar, pílula) | A ação principal da tela; no máximo um por tela |
| Botão secundário (superfície 2, pílula) | Ações de apoio: compartilhar, copiar, escolher outra |
| Botão de ícone | Voltar, trocar imagem, ler código no visor; sempre com rótulo acessível |
| Obturador | Só no visor: círculo branco de 76 dp com anel; tira a foto (nunca dispara sozinho) |
| Controle de zoom | Pílula translúcida sobre o visor: 1×, 2×, 4×; o valor ativo fica âmbar; pinça também funciona |
| Cartão | Conteúdo lido (texto do código) e mensagens de erro |
| Folha inferior | Seletor de fotos do Android (o real; no protótipo, simulado) |
| Modal de confirmação | Toda confirmação e todo aviso (nunca `alert()`/`confirm()`) |
| Aviso (toast/snackbar) | Confirmação curta: "Texto copiado." |

## Padrões de tela

| Situação | Padrão |
| --- | --- |
| Lista | Não há listas no app (o seletor é o do Android) |
| Formulário | Não há formulários |
| Detalhe | Foto salva e Código lido: conteúdo no cartão, ações abaixo, primária por último |
| Vazio | Início: logo, explicação em uma frase, as duas ações e "Nada sai do seu celular" |
| Erro | Cartão com ícone e título em `--cor-erro`, explicação do que fazer e duas saídas (ex.: "Nenhum código encontrado") |
| Carregando | Véu escuro com indicador circular âmbar e o texto do que está acontecendo ("Lendo o código…") |
| Confirmação | Modal do design kit; **nunca** `alert()` ou `confirm()` |

## Telas principais

1. **Início** — escolher imagem ou ler código (é também o estado vazio).
2. **Visor** — imagem parada com grade de terços, zoom (1×/2×/4× e pinça), arrastar para enquadrar, obturador,
   miniatura da última foto e atalho para ler código da imagem.
3. **Foto salva** — a foto enquadrada, "Salva na galeria em 04/10/2026, 14:05", Compartilhar e Tirar outra.
4. **Código lido** — tipo e texto do código; Copiar, Abrir link (no navegador do usuário), Compartilhar; erro
   "Nenhum código encontrado" com a saída "Enquadrar no visor".

## Responsividade

- Usável a partir de **360 dp** de largura, sem rolagem horizontal; retrato como orientação principal.
- Área de toque de pelo menos **48×48** dp (padrão do Android, acima dos 44 px do Big Bang).

## Acessibilidade

**WCAG 2.2 nível AA**: contraste (tabela acima), TalkBack com rótulos em todo ícone, foco visível em âmbar,
navegação por teclado/D-pad (setas movem o enquadramento, +/- mudam o zoom), anúncio do zoom e das trocas de tela,
movimento reduzido respeitado (sem flash animado). Na CI, os testes de UI Compose conferem rótulos e o Android Lint
as regras de acessibilidade.

## Formatos pt-BR

| Tipo | Formato |
| --- | --- |
| Data | `03/10/2026` |
| Hora | `14:05` (fuso do usuário) |
| Moeda | `R$ 1.234,56` (não usado) |
| Número | `1.234,5` (não usado) |

## Protótipo aprovado

`docs/prototipos/fundacao/index.html` (navegável; abra no navegador). Links diretos para revisão: `#visor:paisagem`,
`#salva:paisagem`, `#resultado:qr`, `#resultado:paisagem` (erro), `#seletor`. Issue da Fundação F3: #8.

## Histórico de mudanças

| Data | O que mudou | ADR |
| --- | --- | --- |
| 2026-10-04 | Versão inicial (Fundação F3) | — |
