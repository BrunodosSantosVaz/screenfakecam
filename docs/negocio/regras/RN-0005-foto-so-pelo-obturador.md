id: RN-0005
titulo: A foto só sai pelo obturador e é sempre um arquivo novo
situacao: vigente
substituida_por:
origem: "#36"
criada_em: 2026-10-05

## Descrição

Nenhuma foto é tirada sozinha: só quando o usuário aperta o obturador. Cada foto é salva como um arquivo novo
(JPEG); a imagem escolhida nunca é alterada. No Android 10 ou mais novo a foto vai para a galeria
(Pictures/ScreenFakeCam); no Android 8 e 9 o usuário escolhe onde salvar, sem o app pedir permissão de
armazenamento (RN-0003).

## Exemplos

- Dado o visor aberto, quando o usuário não aperta o obturador, então nenhuma foto é gerada.
- Dado o obturador apertado duas vezes, quando as fotos são salvas, então há duas fotos novas e a imagem escolhida
  continua igual.

## Exceções

Nenhuma.

## Testes que cobrem

- tests/aceite/36-obturador-e-foto-salva/ObturadorTest.kt
