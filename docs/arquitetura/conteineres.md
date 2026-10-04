# ScreenFakeCam — Contêineres (C4, nível 2)

```mermaid
C4Container
  title Contêineres — ScreenFakeCam
  Person(usuario, "Usuário")
  System_Boundary(sistema, "ScreenFakeCam") {
    Container(ui, "Telas", "Jetpack Compose", "Visor com zoom e arraste, obturador, resultado do QR")
    Container(app, "Aplicação e domínio", "Kotlin", "Enquadramento, recorte da foto, decisão do que fazer com o código lido")
    Container(infra, "Infraestrutura", "Android, ZXing", "Seletor de fotos, MediaStore, decodificação de códigos")
  }
  System_Ext(android, "Android")
  Rel(usuario, ui, "Toca, arrasta, aperta o obturador")
  Rel(ui, app, "Chama")
  Rel(infra, app, "Implementa as portas")
  Rel(infra, android, "Lê e grava", "APIs do Android")
```

Um único APK, um módulo Gradle (`app`). As camadas são pacotes; Konsist confere que `domain` não depende de nada e
que `ui` não chama `infrastructure` direto.
