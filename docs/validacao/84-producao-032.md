# Plano de validação documental — produção v0.3.2, épico #84

Este plano (#85) precede a atualização documental #86 e a conferência #87. A produção v0.3.2 já existe;
o épico só registra seus recibos e atualiza a descrição pública. Não altera APK, RN ou aceites congelados,
e não cria testes que apenas procuram frases em documentos.

## Lacuna reproduzida por leitura

Na fonte da produção `93ad178c226e2bb4ff4de2c9354b9f94419853b1`, o README ainda declara v0.3.1 e as correções
#70/#81 como futuras. Checklist, memória e runbooks remetem à entrega seguinte sem o recibo da v0.3.2.
Logo, CA-1/CA-2/CA-4 não estão satisfeitos nesses documentos, embora o APK corrigido já esteja publicado.
Os registros históricos de #71 e v0.3.1 devem conservar suas datas e resultados; a nova comprovação terá um
registro próprio e ponte nos documentos de estado atual.

## Critério, procedimento e evidência

| Critério | Conferência independente exigida | Fonte a confrontar |
| --- | --- | --- |
| CA-1 | README/guia refletem produção v0.3.2, teclado só com visor focado e teto de decodificação sem prometer pixels originais | RN-0001/0002/0004, DESIGN, ViewfinderScreen, AndroidPictureLoader, PRs #77/#82 e homologação real |
| CA-2 | Conferir fonte/CI, candidata, publicação, hashes, certificado, atestado e igualdade dos bytes; separar SHA de execução do workflow do SHA que originou o APK | [Candidata](https://github.com/BrunodosSantosVaz/screenfakecam/actions/runs/37584314353), [CI da fonte](https://github.com/BrunodosSantosVaz/screenfakecam/actions/runs/37584902704), [PR #83](https://github.com/BrunodosSantosVaz/screenfakecam/pull/83), [publicação](https://github.com/BrunodosSantosVaz/screenfakecam/actions/runs/37587716471), [Release estável](https://github.com/BrunodosSantosVaz/screenfakecam/releases/tag/v0.3.2) |
| CA-3 | Confrontar SBOM publicado e lock de release; módulos ZXing/AndroidX/Kotlin/Compose presentes, sem alegar licenças ou arestas completas | PR #78, app/gradle.lockfile, sbom-cyclonedx.json da Release v0.3.2 |
| CA-4 | Comparar relato de homologação e imagem real com Android/hash/cenários comprovados; identificar emulador e limites de cobertura | [Homologação #70](https://github.com/BrunodosSantosVaz/screenfakecam/issues/70#issuecomment-6033050720), [#72](https://github.com/BrunodosSantosVaz/screenfakecam/issues/72#issuecomment-6033051009), [#81](https://github.com/BrunodosSantosVaz/screenfakecam/issues/81#issuecomment-6033051279) |
| CA-5 | Verificar documentação/rastreabilidade, CI exata e igualdade dos caminhos de artefato/framework/RNs/aceites; concluir publicação sem nova versão do app | Diff contra a base oficial d87e0bca34f41c0d10f83a4e60d5e54f2779d4f4 e portão Publicar sem release |

## Rastreabilidade funcional reutilizada

As sete RNs vigentes e os 16 aceites permanecem byte a byte intactos:

- RN-0001/0002/0003: [aceites do esqueleto](../../tests/aceite/17-esqueleto-andante/).
- RN-0004/0005: [aceites do obturador](../../tests/aceite/36-obturador-e-foto-salva/).
- RN-0006/0007: [aceites do leitor](../../tests/aceite/37-leitor-de-qr/).

As regressões próprias dos bugs #70/#72/#81 e a CI completa da fonte `04ccad4caed538c37b3af21a6594a7158032d905`
comprovam o comportamento corrigido; não se substituem aceites nem se repete a suíte local por mudança documental.
O teto numérico decorre da regressão e do carregador, não de inferência visual sobre uma screenshot.

## Validação dos PRs documentais

```bash
python3 .bigbang/bin/bb.py verificar
python3 .bigbang/bin/bb.py esteira documentacao
python3 .bigbang/bin/bb.py esteira rastreabilidade
git diff --check
```

No SHA revisado, conferir também diff vazio contra a base em `app`, `packaging`, arquivos de build/versão,
`.bigbang`, `.agents`, `.claude`, `.github`, AGENTS, TOML, RNs e `tests/aceite`. Sintaxe e CI não substituem leitura
independente dos recibos. Screenshot deve vir do ensaio real com imagem sintética, sem reconstituição artificial.

TST-02/TST-03 preservam o contrato; DOC-03/DOC-12/DOC-15 exigem rastreabilidade, memória e README atual.
Procedimentos de recuperação de keystore, downgrade, aparelho físico, Android 8/9 e TalkBack integral não foram
exercitados por este ensaio e não devem aparecer como validados. Nenhuma imagem pessoal ou segredo integra o recibo.
