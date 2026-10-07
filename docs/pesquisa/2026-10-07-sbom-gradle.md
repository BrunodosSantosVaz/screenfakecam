# SBOM com dependências da release Android

Correção #72 (SEG-18). O SBOM da v0.3.1 tem201 componentes de Actions/YAML/Gradle Wrapper, sem ZXing/AndroidX/Compose.
O job de publicação varre um checkout novo, sem o cache do build anterior. O APK e o atestado existentes continuam
válidos; o problema é a cobertura do inventário, não sua assinatura.

Fontes primárias: [Gradle9.8 — dependency locking](https://docs.gradle.org/9.8.0/userguide/dependency_locking.html) e
[Anchore — Java](https://oss.anchore.com/docs/capabilities/java/). O catalogador java-gradle-lockfile-cataloger aceita
gradle.lockfile, identifica Maven purls e dependências transitivas. O lock não contém metadados completos de
licença nem as relações entre componentes; não declarar que esse scanner garante essas informações.

## Solução

Ativar dependency locking estrito somente em releaseRuntimeClasspath/releaseCompileClasspath e versionar o lock
gerado pelo próprio Gradle. Não há plugin/dependência de execução nova nem edição de workflow gerado. O TOML inclui
app/gradle.lockfile nos caminhos do artefato e bb gerar atualiza a seção gerada da STACK.

Atualização controlada, com JDK/SDK configurados (revisar versões e executar CI após escrever o lock):

```bash
./gradlew :app:dependencies --configuration releaseRuntimeClasspath --write-locks
./gradlew :app:dependencies --configuration releaseCompileClasspath --write-locks
./gradlew lint ktlintCheck testDebugUnitTest koverVerifyDebug assembleRelease
```

## Evidência

Antes da correção, ReleaseDependencyLockTest compilou e falhou porque não havia inventário da release no checkout.
O teste exige ZXing, AndroidX core/activity, Compose UI/Material3 e Kotlin stdlib com versões resolvidas; rejeita
tooling de teste como runtime da release. O build estrito valida o restante do grafo. Na verificação negativa, retirar temporariamente a entrada de ZXing
do lock fez collectReleaseDependencies recusar o grafo; o lock original foi restaurado sem edição manual persistida.

O Syft1.54.0 é o usado pela candidata da v0.3.1. O binário de teste local veio da release oficial; SHA256 do arquivo
linux_amd64.tar.gz:54a87372498168b2d033e876fd41fa4e8035b872699e525a57046e1f2f09c860. A mesma varredura dir: sobre uma
cópia dos arquivos versionados mais o lock, sem app/build ou cache, gerou303 componentes,101 Maven, incluindo:

- pkg:maven/com.google.zxing/core@3.5.4
- pkg:maven/androidx.core/core-ktx@1.19.1
- pkg:maven/androidx.compose.ui/ui-android@1.12.1
- pkg:maven/androidx.compose.material3/material3-android@1.4.0
- pkg:maven/org.jetbrains.kotlin/kotlin-stdlib@2.4.20

Essa evidência local demonstra a compatibilidade com o scanner existente. A candidata nova deve confirmar o SBOM
publicado/atestado e o hash do APK; não substituir essa confirmação por uma alegação baseada no cache local.
