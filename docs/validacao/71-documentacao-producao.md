# Plano de validação — documentação da produção, épico #71

A tarefa de validação #73 precede a implementação documental #74 e a conferência #75. Este épico não altera
comportamento, APK ou RN; nenhum aceite congelado será reescrito. Os critérios são conferidos por leitura
independente contra a produção e por verificações documentais, sem testes que apenas buscam frases no Markdown.

## Antes da correção

A auditoria independente de 07/10/2026 comprovou v0.3.1 publicada pela esteira, com hash/assinatura/atestação válidos,
e identificou A1 (promessa de resolução original), A4 (runbooks ausentes) e A5 (placeholder de cobertura).
Na auditoria inicial, os documentos falhavam os critérios abaixo. A revisão documental não homologa uma mudança de APK.

| Critério | Procedimento e resultado exigido | Regra/evidência existente | Responsável |
| --- | --- | --- | --- |
| CA-1 | Ler recursos/limitações/guia e comparar com o carregador e RN-0004: explicitar que imagens grandes podem ser reduzidas na decodificação; não prometer resolução original sempre preservada | RN-0004, AndroidPictureLoaderTest; aceite ObturadorTest CA-1/2/3 continua congelado | #74 implementa; revisor independente confere |
| CA-2 | Executar leitura do runbook: distinguir candidata/produção, verificar APK/checksum/assinatura/atestado e promoção dos mesmos bytes; retorno de versão precisa explicar limite de downgrade Android | Artefatos v0.3.1/rc.1, CI e publicação históricos; nenhum APK novo neste épico | #74 implementa; #75 rastreia e revisor confere |
| CA-3 | Conferir preservação/custódia/recuperação da chave e restrições de rotação: não publicar keystore/valor de segredo, não substituir chave silenciosamente, explicar instalação/atualização | RN-0003 e SECURITY; certificado público RSA4096 idêntico nas quatro versões estáveis | #74 implementa; #75 confere checklist |
| CA-4 | Comparar cobertura no trecho autoral da STACK com TOML/Kover (80% domínio/aplicação), executar documentação e rastreabilidade; nenhuma edição do bloco gerado | Kover/bigbang.toml e comandos abaixo | #74 implementa; revisor confere a CI exata |

## Comandos e provas

```bash
python3.11 .bigbang/bin/bb.py verificar
python3.11 .bigbang/bin/bb.py esteira documentacao
python3.11 .bigbang/bin/bb.py esteira rastreabilidade
git diff --check
```

Comandos devem passar no SHA revisado e a CI preservar segurança/CodeQL. Validade sintática não substitui leitura:
a revisão deve comparar texto, código e limites reais. Sem mudança de comportamento não há retirada de pendências,
nova regra de negócio ou novo teste de produto neste épico (TST-02/TST-03; DOC-03/DOC-15).

## Rastreabilidade preservada

- RN-0003: `tests/aceite/17-esqueleto-andante/PermissoesTest.kt`, CA-6.
- RN-0004: `tests/aceite/36-obturador-e-foto-salva/ObturadorTest.kt`, CA-1/2/3.
- RN-0005: mesmo ObturadorTest, CA-4/5: obturador manual, arquivo novo e original intacto.
- Os demais aceites RN-0001/0002/0006/0007 permanecem nos épicos originais, total de 16 cenários.

A lacuna de teclado é bug #70 e o SBOM é bug #72. Este épico documental não declara esses bugs corrigidos nem
inventa homologação em aparelho físico; o histórico disponível é de emulador Android 15.

## Conferência documental #75

Implementação #74 mesclada no [PR #79](https://github.com/BrunodosSantosVaz/screenfakecam/pull/79), merge
`7a1f89ba2dfa04dcec56700f16da6914eedcd7d7`, após revisão independente e CI completa na fonte
`b7b09e9516a5a5a1652abd27d3bf0526b7899539`. Esta conferência cobre o que foi entregue em documentação; não é
homologação de código e não declara os bugs #70/#72 ou o desvio do teto de RN-0004 resolvidos em produção.

| Critério | Documento/evidência entregue | Resultado da leitura |
| --- | --- | --- |
| CA-1 | [README](../../README.md), [guia](../guia/usar.md), [glossário](../negocio/glossario.md), [pesquisa](../pesquisa/2026-10-07-operacao-android.md) | Resolução decodificada e redução possível explícitas; original preservado. Não promete o teto exato enquanto o runtime ainda diverge da RN |
| CA-2 | [Entrega](../operacao/entrega.md), [retorno](../operacao/voltar-versao.md), [incidente](../operacao/incidente.md) | Etapas e artefatos distintos; promoção dos mesmos bytes; downgrade por versionCode explicado; sem afirmar execução nova em aparelho |
| CA-3 | [Assinatura](../operacao/assinatura.md), [inventário](../dados/inventario.md) | Custódia/recuperação/rotação distintas; backup privado não presumido; retenção e ações externas descritas sem valores de segredo |
| CA-4 | [STACK](../../STACK.md), TOML e Kover | 80% domínio/aplicação, sem alteração de configuração; blocos gerados preservados |

### Checklist da seção 9.4

| Item | Aplicação neste épico |
| --- | --- |
| RN | Nenhuma regra nova/alterada; sete RNs vigentes e 16 aceites congelados preservados |
| Glossário | Precisado o termo imagem decodificada e a descrição do obturador; não muda comportamento |
| C4/arc42 | Não se aplica mudança: um APK, camadas e portas continuam iguais; diagramas existentes preservados |
| API | Não se aplica: sem API/servidor, nenhum contrato novo |
| Dados | Nenhuma coleta nova; inventário técnico dos fluxos locais existentes e backup das fotos documentados |
| Runbook | Entrega, retorno, incidente, assinatura/custódia/recuperação/rotação e dados/backup nos documentos acima |
| Guia/README | Produção v0.3.1, recursos, instalação, uso, limites e print real existente conferidos; tela/ícone não mudaram neste épico |
| ADR | Nenhuma decisão técnica/produto nova; corrige texto para refletir implementação/configuração existentes |
| Changelog | Rascunho documental em Não publicado; versão do APK não alterada |
| Checklist de produção | [Referências](../operacao/checklist-producao.md) adicionadas; portões e não aplicabilidade de backend preservados |

`bb verificar`, `bb esteira documentacao`, `bb esteira rastreabilidade` e `git diff --check` passam nesta conferência.
O diff de #75 contém apenas Markdown; não modifica caminhos do artefato, RNs, tests/aceite, framework ou workflows.
A CI exata do PR #75 deve concluir antes da aprovação. Não se criou teste que apenas procura texto em documentos
nem se repetiu suíte local de runtime para comportamento inalterado.

Pendências fora deste épico: correções #70 (teclado), #72 (SBOM) e teto numérico de decodificação devem percorrer
regressão→correção→revisão→candidata agrupada→homologação real→produção. Runbooks da entrega futura exigem registrar
Android/hash/cenários reais; a evidência histórica até v0.3.1 é emulador Android 15, sem aparelho físico demonstrado.
