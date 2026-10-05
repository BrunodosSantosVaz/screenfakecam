# Changelog

Todas as mudanças relevantes deste sistema, no formato
[Keep a Changelog](https://keepachangelog.com/pt-BR/1.1.0/), com versões [SemVer](https://semver.org/lang/pt-BR/).

## [Não publicado]

## [0.2.0] - 2026-10-05

### Adicionado

- Obturador, flash e tela Foto salva (#53)
- Gravar a foto sem permissão de armazenamento (#52)
- Recorte da foto e caso de uso do obturador (#50)
- Obturador no visor: a foto é exatamente o que o visor mostra, na resolução da imagem, salva na galeria
  (Pictures/ScreenFakeCam) no Android 10 ou mais novo, ou onde o usuário escolher no Android 8 e 9.
- Tela "Foto salva" com Compartilhar e Tirar outra; atalho "Última" no visor.

## [0.1.0] - 2026-10-04

### Adicionado

- Escolher imagem e visor com zoom e arraste (#28)
- Enquadramento do visor (zoom 1×–4× e posição) (#27)
- Projeto Android mínimo com tema e tela inicial (#26)
- Tela inicial com o botão "Escolher imagem", que abre o seletor de fotos do Android.
- Visor com a imagem escolhida parada, zoom de 1× a 4× (botões e pinça), arraste para enquadrar e grade de terços.

### Segurança

- O app não pede internet, acesso amplo ao armazenamento nem câmera, e não guarda cópia das imagens.
