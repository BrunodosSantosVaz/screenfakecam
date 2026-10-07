# Teto de decodificação — bug #81

RN-0004 vigente limita a imagem decodificada ao dobro do lado maior da tela. MainActivity passa esse limite em
pixels ao AndroidPictureLoader. O algoritmo anterior escolhia a maior potência de dois cuja redução ainda deixava
lado>=maxSide; isso invertia a condição do teto. Nenhuma RN ou aceite congelado mudou nesta correção.

## Evidência antes do runtime

Commit de regressão 34a191e, sete cenários AndroidPictureLoaderTest com PNGs fictícios via ContentResolver/Robolectric
Android 35. Dois falharam: PNG 10000×800/maxSide 4800 decodificou largura 5000; PNG 1600×1200/maxSide 500 retornou 800×600
em vez de 400×300. PNG ímpar 1001×801/maxSide 500 já ficou dentro do limite nesse decodificador. Imagem no limite,
imagem pequena, orientação EXIF e arquivo ausente passaram. Não houve tolerância extra nem teste de APK físico.

## Correção mínima e limite

A amostra continua potência de dois, agora escolhida até ceil(lado/sample)<=maxSide. A condição usa
(lado-1)/sample>=maxSide, equivalente para dimensões positivas, evitando ignorar o resto da divisão em imagens
ímpares. Imagem exatamente no limite preserva resolução. A orientação EXIF só troca os eixos, mantendo o lado maior.
A amostragem pode produzir imagem menor que o teto; não há promessa de preencher exatamente duas vezes a tela.

Fonte primária: [BitmapFactory.Options.inSampleSize](https://developer.android.com/reference/android/graphics/BitmapFactory.Options#inSampleSize),
que descreve subsampling para poupar memória e uso de potências de dois. Escolher uma amostra não inteira ou 3 não
permite impor um teto confiável, pois o decodificador arredonda para potência de dois. Mantivemos o mecanismo existente,
sem dependência, escala posterior ou mudança do obturador.

A correção precisa de revisão independente, CI exata e candidata com bugs 70/72/81; produção/homologação são etapas
posteriores. Testes locais de carregamento não substituem instalação do APK assinado e execução real dos critérios.

Validação local depois do fix: stack completa passou em 1m37, 62 testes/17 suítes sem falhas ou erros, incluindo os
16 aceites congelados. Lint/ktlint, compilação, arquitetura, Kover 80% e assembleRelease passaram. O APK local serve
para verificar build; não é a candidata assinada da esteira nem prova de homologação.
