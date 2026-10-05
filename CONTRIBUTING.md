# Como contribuir com ScreenFakeCam

Obrigado pelo interesse! Este projeto é construído com o framework [Big Bang](https://github.com/BrunodosSantosVaz/big-bang):
uma pessoa decide e IAs executam, e toda mudança passa por uma esteira com testes, revisão e homologação.

## Relatar um bug ou pedir algo

1. Procure nas [issues](https://github.com/BrunodosSantosVaz/screenfakecam/issues) se já existe.
2. Abra uma issue pelo formulário (Bug ou Ideia). Para bug: o que você fez, o que esperava e o que aconteceu, com a
   versão e o aparelho ou navegador.
3. Falha de segurança: **não** abra issue pública com detalhes; veja [SECURITY.md](SECURITY.md).

## Enviar código

- Leia o [README](README.md) (seção *Para desenvolvedores*) e o [`AGENTS.md`](AGENTS.md), que valem para pessoas e IAs.
- Trabalhe numa branch a partir da `develop` (bug: a partir da `main`), no padrão de
  [`.bigbang/processo/07-branches-e-commits.md`](.bigbang/processo/07-branches-e-commits.md).
- Commits e código em inglês ([Conventional Commits](https://www.conventionalcommits.org/pt-br/)); issues, PRs e
  documentação em português do Brasil.
- Bug: o primeiro commit traz o teste que falha; a correção vem no segundo.
- Os testes de aceite em `tests/aceite/` são travados: não os altere.
- Ambiente: Android Studio, ou JDK 17+ e Android SDK; antes do PR rode `./gradlew lint ktlintCheck testDebugUnitTest
  koverVerifyDebug` (os comandos estão em `bigbang.toml`, `[comandos]`).
- Atualize a documentação e o README que a mudança tocar.
- Abra o PR com *O que muda*, *Testes* e *Issue* (`Refs #n`). A CI precisa ficar verde.

## Código de conduta

Ao participar, você concorda com o [código de conduta](CODE_OF_CONDUCT.md).
