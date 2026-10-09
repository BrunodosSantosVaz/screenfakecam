id: RN-0007
titulo: Link lido só abre por toque e só se for http ou https
situacao: vigente
substituida_por:
origem: "#37"
criada_em: 2026-10-05

## Descrição

O texto lido aparece completo antes de qualquer ação. Um link só abre quando o usuário toca em "Abrir link", no
navegador dele, e só se começar com http:// ou https://. Outros esquemas (javascript:, intent://, file://,
content://, …) nunca são abertos: o usuário pode copiar ou compartilhar o texto. O app nunca abre nada sozinho.

## Exemplos

- Dado o texto "https://exemplo.com", quando o app avalia o link, então ele pode ser aberto por toque.
- Dado o texto "javascript:alert(1)" ou "intent://x", quando o app avalia o link, então ele não pode ser aberto.

## Exceções

Nenhuma.

## Testes que cobrem

- tests/aceite/37-leitor-de-qr/LeitorDeCodigosTest.kt
