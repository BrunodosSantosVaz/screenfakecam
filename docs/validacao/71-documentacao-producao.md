# Plano de validação — documentação da produção, épico #71

A tarefa de validação #73 precede a implementação documental #74 e a conferência #75. Este épico não altera
comportamento, APK ou RN; nenhum aceite congelado será reescrito. Os critérios são conferidos por leitura
independente contra a produção e por verificações documentais, sem testes que apenas buscam frases no Markdown.

## Antes da correção

A auditoria independente de 07/10/2026 comprovou v0.3.1 publicada pela esteira, com hash/assinatura/atestação válidos,
e identificou A1 (promessa de resolução original), A4 (runbooks ausentes) e A5 (placeholder de cobertura).
Os documentos atuais falham os critérios abaixo. A revisão documental não homologa uma mudança de APK.

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
