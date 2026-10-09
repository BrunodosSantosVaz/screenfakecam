id: RN-0004
titulo: A foto é exatamente o que o visor mostra
situacao: vigente
substituida_por:
origem: "#36"
criada_em: 2026-10-05

## Descrição

Ao apertar o obturador, a foto é a parte da imagem escolhida que o visor está mostrando, na resolução da imagem
(não na resolução da tela). Em 1× a foto é a imagem inteira; com zoom, é só o trecho enquadrado.

## Exemplos

- Dado o visor a 1× com uma imagem de 1000×800, quando o usuário aperta o obturador, então a foto tem 1000×800.
- Dado o visor a 2× centralizado, quando o usuário aperta o obturador, então a foto é o centro da imagem (500×400).
- Dado o visor a 2× na borda esquerda, quando o usuário aperta o obturador, então a foto começa na borda esquerda.

## Exceções

A resolução é a da imagem decodificada pelo app (no máximo o dobro do lado maior da tela), não a do arquivo
original quando ele é maior que isso.

## Testes que cobrem

- tests/aceite/36-obturador-e-foto-salva/ObturadorTest.kt
