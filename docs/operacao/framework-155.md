# Manutenção da esteira com Big Bang v1.5.5

Pedido do dono: atualizar framework e Actions de develop/main, preservando a produção v0.3.2 do aplicativo.
Rastreamento: [#91](https://github.com/BrunodosSantosVaz/screenfakecam/issues/91);
[pacote oficial](https://github.com/BrunodosSantosVaz/big-bang/releases/tag/v1.5.5).

## Atualização e publicação

1. `python3 .bigbang/bin/bb.py atualizar 1.5.5 --simular`: conferir pacote, hash, atestação e migração.
2. `python3 .bigbang/bin/bb.py atualizar 1.5.5`: preparar framework/v1.5.5 a partir da develop pelo atualizador oficial.
3. Conferir que só framework, versão de bigbang.toml, arquivos gerados e documentação autorizada mudam.
   Configuração do projeto, caminhos do artefato, dependências, RNs e aceites permanecem iguais. Modo padrão preservado.
4. Aguardar CI estrutural completa e revisão independente no SHA final. Mesclar o PR pela esteira.
5. Liberar a posse e executar Publicar sem release pela main: simulação verde antes da execução real,
   com a autorização do dono no ambiente producao. Conferir main/develop no mesmo commit.
6. Fechar a issue, executar Faxina (simular antes), conferir branches/PRs/posses e remover a pasta de trabalho própria.

Não gerar candidata ou nova release do aplicativo nesta manutenção. A CI valida a fonte; os artefatos publicados abaixo
permanecem os mesmos. O recibo final dos runs e da comparação dos assets fica na issue da atualização.

## Faxina e recuperação

A publicação real já faz a faxina; o novo workflow recupera sobras diariamente e por botão, executando apenas scripts da main.
Simulações dos workflows de publicação e encerramento não disparam uma limpeza real separada. Só branches incorporadas e
concluídas podem ser apagadas; main/develop, tags, PRs abertos e commits exclusivos ficam preservados. Falhas de leitura
da API são explícitas. Encerrar exige faxina limpa; se falhar após registrar a data/milestone, resolver os pontos e repetir
é idempotente, sem desfazer uma publicação. Stashes locais não pertencem à faxina.

## Artefatos preservados da produção v0.3.2

| Asset | SHA-256 registrado antes da manutenção |
| --- | --- |
| `sbom-cyclonedx.json` | `sha256:62100900817910c2f69080922f1eab47c7a680c25ce4063a0b37c0bc7bddaa32` |
| `screenfakecam-v0.3.2-android.apk` | `sha256:29d89a99d2839185138259dafc688ee85fbcb087a6a788a8ebc8612dd3a43fca` |
| `SHA256SUMS-android.txt` | `sha256:828b5fc304956a26b84f4f1b02d0ce9829e4cf5caefe64ad75d0efaa99870f44` |

Comparar também IDs, tamanho e datas dos assets; não sobrescrever releases anteriores. Para homologação do aplicativo,
continuam valendo os recibos da release vigente, sem alegar novo ensaio físico ou novo deploy nesta atualização.
