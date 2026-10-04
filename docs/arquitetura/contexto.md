# ScreenFakeCam — Contexto (C4, nível 1)

```mermaid
C4Context
  title Contexto — ScreenFakeCam
  Person(usuario, "Usuário", "Escolhe uma imagem guardada, enquadra e tira a foto, ou lê um QR/código de barras")
  System(sistema, "ScreenFakeCam", "Câmera cujo visor é uma imagem guardada, com obturador manual e leitor de QR")
  System_Ext(android, "Android", "Seletor de fotos, galeria (MediaStore), compartilhamento e navegador")
  Rel(usuario, sistema, "Usa")
  Rel(sistema, android, "Lê a imagem escolhida, grava a foto, compartilha o texto lido", "APIs do Android")
```

| Elemento | Responsabilidade |
| --- | --- |
| Usuário | Qualquer pessoa com Android 8.0+, sem cadastro |
| Android | Entrega só a imagem que o usuário escolheu; recebe a foto na galeria; abre links no navegador do usuário |

Sem servidor e sem internet: nada sai do aparelho.
