id: RN-0003
titulo: O app não pede internet, armazenamento amplo nem câmera
situacao: vigente
substituida_por:
origem: "#17"
criada_em: 2026-10-04

## Descrição

Nada sai do celular do usuário: o app não pede permissão de internet, de acesso amplo ao armazenamento nem de
câmera. A imagem entra só pelo seletor de fotos do Android, que dá acesso apenas ao arquivo escolhido
(`PRODUTO.md`, condições do ASVS L1).

## Exemplos

- Dado o app instalado, quando se lê o manifesto, então ele não pede internet, armazenamento amplo nem câmera.

## Exceções

Nenhuma. Mudar esta regra exige rever o nível ASVS.

## Testes que cobrem

- tests/aceite/17-esqueleto-andante/PermissoesTest.kt
