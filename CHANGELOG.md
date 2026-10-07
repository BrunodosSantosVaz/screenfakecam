# Changelog

Todas as mudanças relevantes deste sistema, no formato
[Keep a Changelog](https://keepachangelog.com/pt-BR/1.1.0/), com versões [SemVer](https://semver.org/lang/pt-BR/).

## [Não publicado]

### Corrigido

- Documentação da resolução da imagem decodificada, redução de imagens grandes e cobertura mínima de 80% (#71).

### Adicionado

- Runbooks de entrega do APK, retorno de versão compatível, incidente e assinatura/custódia/recuperação/rotação;
  inventário dos dados locais, compartilhamento e backup das fotos (#71).

## [0.3.1] - 2026-10-05

### Corrigido

- Rótulos dos botões de Código lido em uma linha (#61) (#62)
- Ícone próprio do app (#64) (#65)

## [0.3.0] - 2026-10-05

### Adicionado

- Ler QR e código de barras pela tela inicial e pelo visor (#56)
- Leitor de QR e códigos de barras com ZXing e regra de links (#51)
- Leitor de QR code e código de barras (13 formatos), pela tela inicial ou pelo enquadramento do visor, com Copiar,
  Compartilhar e Abrir link.

### Segurança

- Só endereços http/https podem ser abertos, e só por toque; outros esquemas (javascript:, intent://, file://…)
  nunca são abertos.

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
