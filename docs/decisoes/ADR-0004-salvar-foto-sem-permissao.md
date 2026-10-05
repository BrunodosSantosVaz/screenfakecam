# ADR-0004: Salvar a foto sem permissão de armazenamento

- **Situação:** aceita
- **Data:** 2026-10-05
- **Decisores:** Bruno dos Santos Vaz (dono, por delegação à IA); Claude Code

## Contexto e problema

O obturador (#36) grava uma foto nova. O produto proíbe permissão de armazenamento amplo (RN-0003, ASVS L1). No
Android 10 ou mais novo, o app pode criar arquivos na galeria pelo MediaStore sem permissão; no Android 8 e 9, gravar
na galeria exige `WRITE_EXTERNAL_STORAGE`.

## Fatores de decisão

- Nenhuma permissão nova (RN-0003).
- Funcionar do Android 8 em diante (PRODUTO.md).

## Opções consideradas

1. Pedir `WRITE_EXTERNAL_STORAGE` só no Android 8/9 (`maxSdkVersion="28"`).
2. Android 10+: MediaStore; Android 8/9: janela "salvar como" do sistema (`ACTION_CREATE_DOCUMENT`).
3. Só compartilhar a foto, sem salvar.

## Decisão e justificativa

Escolhida a opção 2: nenhuma permissão em nenhuma versão; no Android 8/9 o usuário escolhe o lugar, o que também
deixa claro onde a foto ficou. A foto é gravada como `IS_PENDING` até terminar e apagada se falhar, para nunca
deixar arquivo pela metade na galeria.

## Consequências

### Positivas

- O teste de permissões (RN-0003) continua valendo sem exceção.

### Negativas

- No Android 8/9 há um passo a mais (escolher o lugar), documentado no guia de uso.

## Referências

- Épico #36; RN-0003, RN-0005; `docs/guia/usar.md`.
