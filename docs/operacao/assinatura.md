# Assinatura, custódia e recuperação

O Android exige assinatura para instalar o APK e conferir atualização. A mesma identidade de assinatura precisa
acompanhar as versões do ScreenFakeCam. A distribuição atual é pelo GitHub Releases, sem Play App Signing;
não existe recuperação da chave pelo Google neste fluxo.

## Segredos e certificado público

| Nome de segredo da build | Finalidade |
| --- | --- |
| `BB_ASSINATURA_ARQUIVO` | Keystore em base64; base64 não é criptografia |
| `BB_ASSINATURA_SENHA` | Senha do keystore |
| `BB_ASSINATURA_ALIAS` | Alias da chave usada para assinar |
| `BB_ASSINATURA_SENHA_CHAVE` | Senha da chave; se ausente, build usa a senha do keystore |

Nunca coloque valores no repositório, issue, log, print, artefato ou shell compartilhado. `packaging/android/build.sh`
decodifica em arquivo temporário e remove-o ao sair. Nomes internos `BB_KEYSTORE_*`/`BB_KEY_*` só conectam a build
Gradle ao arquivo temporário; não são configuração de execução do app (SEG-IA-04).

O certificado público observado na v0.3.1 e nas versões anteriores verificadas é RSA4096, SHA256:
`4edaf7eae89d5da175d0817739b5866f9e7180acdcb3f260bb84b7143742e20d`.
Verifique um APK com `apksigner verify --verbose --print-certs arquivo.apk`; compare a identidade com a Release
estável. Conferir esse certificado não revela a chave privada.

## Backup e restauração da chave

O dono deve manter cópia de recuperação criptografada do keystore e guardar senhas/alias em canal privado controlado,
fora de Git e fora dos artefatos da CI. Restrinja acesso e registre apenas data, responsável e resultado do exercício.
A existência de uma cópia recuperável não foi demonstrada por esta auditoria; não confunda os segredos configurados
no GitHub com prova de backup externo.

Para exercitar recuperação: em ambiente isolado do responsável, restaure a cópia, produza um APK de teste sem
publicação, verifique assinatura/certificado e teste atualização sobre a versão estável em dispositivo de teste.
Registre somente hashes, certificado público, Android e resultado. Remova a cópia temporária depois. Se certificado
ou instalação divergir, pare e investigue antes de trocar segredos da CI. Não use imagens pessoais neste exercício.

## Rotação

Trocar senhas de acesso ao mesmo keystore/chave, ou credenciais de acesso ao GitHub, não deve trocar a identidade de
assinatura. O dono atualiza os segredos correspondentes de forma privada e verifica um APK de candidata com o mesmo
certificado. Não misture troca de senha e criação de uma chave nova.

Trocar a chave privada é migração de identidade. Exige plano próprio de compatibilidade/linhagem e homologação nas
versões Android suportadas; não substitua `BB_ASSINATURA_ARQUIVO` por uma chave nova para corrigir um erro de CI.
Sem migração compatível, APK assinado com outra chave não atualiza a instalação atual. O suporte a rotação varia por
versão Android/esquema de assinatura; o minSdk26 exige atenção ao Android8/9, além de versões recentes.

Se a chave privada foi comprometida ou perdida, interrompa publicações e trate como [incidente](incidente.md).
Não gere uma chave nova silenciosamente: o dono deve avaliar a possibilidade de continuidade compatível ou uma nova
instalação/identidade do app e comunicar o impacto aos usuários. Referências:
[assinatura Android](https://developer.android.com/studio/publish/app-signing) e
[apksigner](https://developer.android.com/tools/apksigner).
