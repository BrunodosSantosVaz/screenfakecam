# Entregar o APK Android

Use este runbook para mudar o artefato. Mudanças apenas documentais seguem o último parágrafo. O contrato vigente
é perfil `compilado`, sistema `android`, com `packaging/android/build.sh`: um APK assinado por candidata. Consulte
[Entrega do Big Bang](../../.bigbang/processo/09-entrega.md) e [checklist](checklist-producao.md).

## Integrar e construir

1. Confirme issues/PRs do épico ou bugs, revisão independente, autorização das zonas sensíveis e checks verdes no
   SHA exato. Aceites congelados permanecem intactos; não reaproveite CI de outro SHA.
2. Em Actions, execute **Integrar release** com `epico` ou `bug` correspondente e `simular=true`. Confira os PRs e a
   versão calculada. Para bugs agrupados, `bug` aceita lista, como `70,72`. Execute o plano autorizado com
   `simular=false`; a automação cria `release/x.y.z`, atualiza versão/changelog e dispara **Candidata**.
3. A build lê os segredos de assinatura, cria o keystore temporário e o remove ao sair. Não copie nem imprima esses
   valores. Aguarde a pre-release `vX.Y.Z-rc.N`, o PR de release e todos os checks. Falha interrompe a entrega;
   corrigir exige fluxo de PR e uma candidata nova, não substituir bytes da candidata aprovada.

## Conferir os artefatos

Baixe APK, `SHA256SUMS-android.txt` e SBOM da candidata em uma pasta vazia. Ajuste a tag/versão dos exemplos:

```bash
gh release download vX.Y.Z-rc.N --repo BrunodosSantosVaz/screenfakecam --dir candidata
cd candidata
sha256sum -c SHA256SUMS-android.txt
gh attestation verify screenfakecam-vX.Y.Z-android.apk --repo BrunodosSantosVaz/screenfakecam
apksigner verify --verbose --print-certs screenfakecam-vX.Y.Z-android.apk
```

`apksigner` vem dos Android SDK Build Tools; coloque o diretório dos Build Tools no PATH. Guarde tag, SHA fonte,
link do run, SHA256 do APK, certificado público e resultado do atestado. A assinatura autentica a chave; o atestado
liga os bytes ao build. Ambos precisam ser conferidos. Compare o certificado com o de uma versão instalada válida
(ver [assinatura](assinatura.md)). Examine o SBOM: precisa representar dependências Android reais; a lacuna da
v0.3.1 foi corrigida pelo [bug #72](https://github.com/BrunodosSantosVaz/screenfakecam/issues/72) na v0.3.2.
O [recibo atual](../validacao/032-producao.md) confere 303 componentes/101 Maven e os 100 módulos do lock de release.

## Homologar os mesmos bytes

Instale o APK conferido por cima da versão estável em um dispositivo ou emulador compatível. Teste o épico inteiro
pelos seus critérios: seleção de imagem, zoom/enquadramento, obturador e galeria, leitura de códigos, ações de
resultado, Voltar e cenários da correção. Registre Android/API, tipo de dispositivo (emulador ou físico), versão/hash,
cenários e resultados. Se só houve Robolectric, registre testes locais; não declare homologação Android. O histórico
até v0.3.1 registra emulador Android 15. O [ensaio da v0.3.2](../validacao/032-producao.md) identifica o
APK/hash e os cenários realmente executados, também em Android 15 emulado; não valida aparelho físico nem todos os Android 8+.

Registre a decisão efetivamente dada pelo dono via `bb decisao homologado N --frase "palavras do dono"`, para cada
épico/bug da candidata; rejeição registra motivo e gera correção com nova rc. Não invente decisão ou cenário testado.

## Publicar e verificar

1. Rode `bb checklist producao`. Em **Publicar em produção**, use `versao=X.Y.Z`, `simular=true` e confira o portão:
   homologação, documentação, bloqueios de segurança, PR/checks exatos, candidata intacta e changelog.
2. Com ordem explícita de produção registrada, execute `simular=false` e acompanhe o ambiente `producao` conforme
   a autorização vigente do dono. A automação promove o APK homologado sem recompilar, confere hashes, publica a
   Release e sincroniza branches/cartões. Qualquer rejeição ou falha exige investigação; não pule o portão.
3. Baixe a Release estável em outra pasta, repita checksum, atestado e assinatura. Compare seu SHA256 com a
   candidata homologada, instale a versão publicada e registre o smoke realmente executado. Confira issues,
   milestone, main/develop, limpeza de branches e posses. Reexecução idempotente só deve concluir passos faltantes.

## Publicar documentação sem APK novo

Para épico `sem-release`, conclua teste/plano, tarefa e conferência documental em PRs do épico. Integre-o em develop
com **Integrar release** e confira **Publicar sem release**, primeiro `simular=true`. O portão exige caminhos do
artefato idênticos entre main/develop, main ancestral de develop e CI verde na ponta exata. O fluxo real autorizado
avança main sem gerar versão, candidata ou homologação de APK. Se o artefato diferir, use release; não force sem-release.
