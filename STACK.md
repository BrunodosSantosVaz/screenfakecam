# Contrato transitório de dependências para o validador confiado

A documentação oficial da tecnologia está na [Wiki](https://github.com/BrunodosSantosVaz/screenfakecam/wiki/Tecnologia). Este arquivo é entrada da Guarda da stack v1.5.5 ainda instalada na branch de destino; contém apenas a tabela consumida pelo código. A tarefa seguinte remove esta entrada após atualizar o destino para 2.0.0. Nenhum portão é desligado.

<!-- bb:dependencias:inicio -->
| Pacote | Ecossistema | Faixa de versão | Para quê | ADR |
| --- | --- | --- | --- | --- |
| androidx.core:core-ktx | maven | 1.x | Base do AndroidX | ADR-0001 |
| androidx.activity:activity-compose | maven | 1.x | Activity com Compose e seletor de fotos | ADR-0001 |
| androidx.lifecycle:lifecycle-viewmodel-compose | maven | 2.x | ViewModel nas telas | ADR-0001 |
| androidx.compose:compose-bom | maven | 2026.x | Versões alinhadas do Compose | ADR-0001 |
| androidx.compose.ui:ui | maven | BOM | Compose UI | ADR-0001 |
| androidx.compose.material3:material3 | maven | BOM | Componentes Material 3 | ADR-0001 |
| androidx.exifinterface:exifinterface | maven | 1.x | Orientação correta da imagem (EXIF) | ADR-0001 |
| com.google.zxing:core | maven | 3.5.x | Ler QR e código de barras da imagem, offline | ADR-0001 |
<!-- bb:dependencias:fim -->
