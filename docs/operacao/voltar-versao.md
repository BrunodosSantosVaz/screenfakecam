# Retornar a um comportamento anterior

No perfil compilado, não existe servidor para reimplantar nem botão de rollback de APK: o usuário instala as
Releases. Preserve as tags e APKs já publicados. O Android normalmente impede instalar um APK com `versionCode`
menor sobre uma versão mais nova; baixar a versão antiga não equivale a conseguir atualizá-la por cima.

## Caminho preferencial: correção compatível

1. Registre incidente/bug, versão afetada, última versão funcional e reprodução sem imagens pessoais. O dono decide
   a correção e a publicação; acompanhe [incidente](incidente.md).
2. Restaure o comportamento necessário numa branch de correção oficial, com teste que reproduz a regressão, PR
   independente e CI. Integre uma versão nova, cujo versionCode seja maior que o instalado; mantenha applicationId
   e a mesma chave de assinatura. Não mova a tag antiga nem reutilize sua versão para bytes diferentes.
3. Construa, homologue e publique pelo [runbook de entrega](entrega.md). Confira atualização por cima da versão
   afetada e cenários recuperados. Não copie cegamente o código antigo se isso reintroduzir uma falha de segurança.

## Uso excepcional do APK antigo

Baixe a Release anterior, verifique checksum, atestado e certificado antes de instalar. Se o Android recusar o
retorno, não recomende `adb install -d` como solução de produção: a permissão de downgrade depende do APK/ambiente.
Desinstalar a versão nova e reinstalar a antiga pode funcionar, porém descarta os dados privados do app e interrompe
a sessão. Só faça em dispositivo de teste ou com consentimento do usuário, após proteger as fotos que deseja manter.
O ScreenFakeCam não guarda histórico no app; fotos na galeria/documento escolhido são arquivos externos, sujeitos
às regras do Android e do provedor. Confira sua presença antes e depois, sem presumir que desinstalar faz backup.

Registre APK antigo, hash, Android, método, resultado e consequências. Esse procedimento não foi exercitado neste
épico documental; um relato de retorno real exige evidência separada. Referência primária:
[versionamento Android](https://developer.android.com/studio/publish/versioning).
