id: RN-0002
titulo: Enquadramento sempre dentro da imagem
situacao: vigente
substituida_por:
origem: "#17"
criada_em: 2026-10-04

## Descrição

Arrastar a imagem no visor nunca mostra área fora dela: o arraste para na borda. Em 1× a imagem inteira já está
visível, então arrastar não a move.

## Exemplos

- Dado o visor a 2×, quando o usuário arrasta além da borda, então a imagem para na borda.
- Dado o visor a 1×, quando o usuário arrasta, então a imagem não sai do lugar.

## Exceções

Nenhuma.

## Testes que cobrem

- tests/aceite/17-esqueleto-andante/EnquadramentoTest.kt
