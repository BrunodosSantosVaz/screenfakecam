id: RN-0006
titulo: Ler QR code e código de barras de uma imagem
situacao: vigente
substituida_por:
origem: "#37"
criada_em: 2026-10-05

## Descrição

O app lê o QR code ou código de barras de uma imagem escolhida (ou do enquadramento do visor) e mostra o texto
completo e o tipo do código. Tipos lidos: QR, Data Matrix, Aztec, PDF417, EAN-13, EAN-8, UPC-A, UPC-E, Code 128,
Code 39, Code 93, ITF e Codabar. A leitura é feita no celular, sem internet (RN-0003).

## Exemplos

- Dado uma imagem com um QR de "https://exemplo.com/cardapio", quando o usuário lê, então vê esse texto e o tipo QR.
- Dado uma imagem com um EAN-13, quando o usuário lê, então vê os 13 dígitos e o tipo EAN-13.
- Dado uma imagem sem código, quando o usuário lê, então o app diz que nenhum código foi encontrado.

## Exceções

Código muito pequeno ou borrado pode não ser encontrado: o usuário enquadra no visor (zoom) e lê de novo.

## Testes que cobrem

- tests/aceite/37-leitor-de-qr/LeitorDeCodigosTest.kt
