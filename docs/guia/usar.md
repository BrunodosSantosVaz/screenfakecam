# Como usar o ScreenFakeCam

## Instalar

1. No celular Android (8.0 ou mais novo), abra a página de
   [Releases](https://github.com/BrunodosSantosVaz/screenfakecam/releases/latest) e baixe o arquivo
   `screenfakecam-vX.Y.Z-android.apk` da versão mais recente.
2. Abra o arquivo baixado. Na primeira vez, o Android pede para permitir a instalação de apps do navegador ou do
   gerenciador de arquivos: permita só para essa instalação.
3. Para atualizar, baixe e instale a versão nova por cima: as versões são assinadas com a mesma chave.

## Usar o visor

1. Toque em **Escolher imagem** e escolha uma imagem no seletor de fotos do Android.
2. A imagem aparece parada no visor. Use **1×, 2× e 4×** ou dois dedos para o zoom (até 4×) e arraste para
   enquadrar; o enquadramento nunca sai da imagem.
   Com teclado ou D-pad, use **Tab** para dar foco ao visor (contorno âmbar): as **setas** movem a imagem;
   **+ / -** passam pelos níveis 1×, 2× e 4×. Também funcionam **Shift + =** e as teclas do teclado numérico.
3. Aperte o **obturador** (o círculo branco embaixo) para tirar a foto do que o visor mostra. A foto tem a
   resolução da imagem decodificada, não a da tela. O lado maior decodificado é limitado ao dobro do lado
   maior da tela, em pixels; arquivos grandes podem perder resolução ao carregar. O arquivo original nunca é alterado.
   - Android 10 ou mais novo: a foto vai para a galeria, na pasta **Pictures/ScreenFakeCam**.
   - Android 8 ou 9: o Android pergunta onde salvar (o app não pede permissão de armazenamento).
4. Na tela **Foto salva**: **Compartilhar** abre a lista de apps do Android; **Tirar outra** volta ao visor. No visor,
   **Última** mostra a última foto de novo.
5. **Voltar** (ou o gesto de voltar do Android) retorna à tela inicial e descarta a imagem.

## Ler QR code ou código de barras

1. Na tela inicial, toque em **Ler QR ou código de barras** e escolha a imagem (um print, uma foto recebida).
2. A tela **Código lido** mostra o tipo (QR code, EAN-13…) e o texto completo. **Copiar** copia o texto;
   **Compartilhar** envia para outro app; **Abrir link** aparece só para endereços `http://` ou `https://` e abre no
   seu navegador. Confira o endereço antes de tocar: um QR pode levar a um site enganoso.
3. Código pequeno ou longe na imagem: abra a imagem no visor (**Enquadrar no visor** ou **Escolher imagem**), dê zoom
   no código e toque em **Ler código** no topo do visor.

O app não usa internet nem mantém histórico persistente de imagens ou leituras. Fotos salvas ficam na galeria até
você as remover. Compartilhar e Abrir link entregam o conteúdo ao app externo escolhido, que tem sua própria
política de dados e pode usar internet. Consulte o [inventário de dados locais](../dados/inventario.md).
