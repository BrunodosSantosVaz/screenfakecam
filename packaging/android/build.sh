#!/usr/bin/env bash
# Candidate build of the android system (bigbang.toml compilado.build_android, ADR-0009 contract): writes exactly
# one signed APK into $BB_SAIDA. Reads BB_VERSAO (checked against version.properties) and the signing secrets
# BB_ASSINATURA_ARQUIVO (keystore in base64), BB_ASSINATURA_SENHA, BB_ASSINATURA_ALIAS, BB_ASSINATURA_SENHA_CHAVE.
# The keystore only exists in a temporary file during the build; nothing is printed.
set -euo pipefail
trap 'echo "::error::$(basename "$0") falhou na linha $LINENO (código $?)" >&2' ERR
saida="${BB_SAIDA:?}"
versao=$(sed -n 's/^version=//p' version.properties)
if [ -n "${BB_VERSAO:-}" ] && [ "$BB_VERSAO" != "$versao" ]; then
  echo "::error::BB_VERSAO=$BB_VERSAO, mas version.properties diz $versao"; exit 1
fi
: "${BB_ASSINATURA_ARQUIVO:?defina o segredo BB_ASSINATURA_ARQUIVO (keystore em base64): o APK de release precisa de assinatura}"
chave=$(mktemp --suffix=.jks)
trap 'rm -f "$chave"' EXIT
printf '%s' "$BB_ASSINATURA_ARQUIVO" | base64 -d > "$chave"
BB_KEYSTORE_FILE="$chave" BB_KEYSTORE_PASSWORD="${BB_ASSINATURA_SENHA:?}" BB_KEY_ALIAS="${BB_ASSINATURA_ALIAS:?}" \
  BB_KEY_PASSWORD="${BB_ASSINATURA_SENHA_CHAVE:-$BB_ASSINATURA_SENHA}" ./gradlew --no-configuration-cache assembleRelease
apk=app/build/outputs/apk/release/app-release.apk
[ -f "$apk" ] || { echo "::error::o build não gerou um APK assinado ($apk)"; exit 1; }
mkdir -p "$saida"
cp "$apk" "$saida/screenfakecam.apk"
echo "APK assinado: $saida/screenfakecam.apk"
