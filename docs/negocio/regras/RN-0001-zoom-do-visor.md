id: RN-0001
titulo: Zoom do visor de 1x a 4x
situacao: vigente
substituida_por:
origem: "#17"
criada_em: 2026-10-04

## Descrição

O visor mostra a imagem escolhida com zoom entre 1× e 4×. Em 1× a imagem inteira cabe no visor. Não existe zoom
menor que 1× nem maior que 4×: um pedido fora desse intervalo fica no limite mais próximo.

## Exemplos

- Dado o visor a 1×, quando o usuário pede 2×, então o zoom fica em 2×.
- Dado o visor a 4×, quando o usuário pede mais zoom, então o zoom continua em 4×.
- Dado o visor a 1×, quando o usuário pede menos zoom, então o zoom continua em 1×.

## Exceções

Nenhuma.

## Testes que cobrem

- tests/aceite/17-esqueleto-andante/EnquadramentoTest.kt
