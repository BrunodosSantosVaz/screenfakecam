# Auditoria e operação Android — épico #71

A auditoria da produção v0.3.1 em 07/10/2026 conferiu os bytes promovidos da rc.1: SHA256 do APK
`c8dda3c3b7c599e85a5837c2055ade66debf6f846e3567f36d7a44fc41f5b43d`, assinatura RSA4096 válida e atestado GitHub
ligado ao build da candidata. A documentação usa esse histórico como evidência, sem afirmar execução nova em
Android físico, retorno de APK ou recuperação da chave privada.

## Fontes e decisões de redação

- [Versionamento Android](https://developer.android.com/studio/publish/versioning): versionCode maior identifica
  atualização; o Android impede downgrade para número menor. Assim, voltar comportamento em produção prefere
  correção nova com mesma chave, sem mover tag antiga.
- [Assinatura Android](https://developer.android.com/studio/publish/app-signing) e
  [apksigner](https://developer.android.com/tools/apksigner): certificado valida identidade; backup da chave é
  responsabilidade do distribuidor. Rotação de senha não é rotação de chave; linhagem exige compatibilidade própria.
- Código vigente: AndroidPictureLoader decodifica com inSampleSize e orientação EXIF; Framing/Shutter recortam o
  bitmap decodificado. A promessa de resolução original em README/guia estava errada. Não se alterou RN nem runtime.
- RN-0004 diz no máximo duas vezes o lado maior da tela, mas o algoritmo arredonda a amostragem em potências de
  dois e não demonstra esse teto exato para todas as dimensões. Esta inconsistência foi registrada na auditoria e
  exige regressão numérica e decisão/correção própria; o manual só descreve redução possível, sem prometer um limite
  não comprovado. Os aceites congelados permanecem intactos.
- `bigbang.toml` e Kover já exigem cobertura80% em domain/application; a mudança da seção autoral de STACK remove
  placeholder, sem mudar o percentual ou o bloco gerado. Não há tecnologia, arquitetura ou coleta nova.

Runbooks seguem o perfil compilado real: candidata assinada, checksum/SBOM/atestado, homologação dos mesmos bytes,
produção sem recompilar, retorno compatível, incidente e custódia. A política offline separa processamento local
sem internet das ações que enviam conteúdo a outro app/navegador por comando do usuário.
