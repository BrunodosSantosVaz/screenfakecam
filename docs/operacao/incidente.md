# Responder a um incidente

Responsável: Bruno dos Santos Vaz. Use este procedimento para crash, arquivo/foto incorreto, regressão de instalação,
falha da candidata/produção ou suspeita de exposição de segredo/dado. O app é local; não há backend a reiniciar.

1. Registre hora, versão instalada, Android, último APK funcional, hash se disponível e passos com imagem sintética.
   Recolha somente log mínimo e anonimizado; não anexe fotos pessoais, texto sensível de QR, keystore ou senhas.
2. Falha funcional segue issue de bug e triagem com severidade. Vulnerabilidade segue o
   [relato privado](../../SECURITY.md); não publique exploração ou segredo numa issue aberta. Suspeita de segredo
   vazado exige avisar o dono, revogar/trocar a credencial apropriada e avaliar alcance; assinatura tem limites em
   [assinatura](assinatura.md). Apagar um log não torna o segredo novamente seguro.
3. Interrompa a promoção de uma candidata suspeita. Preserve versão/tag, hashes e links de runs para investigação;
   não sobrescreva APK, enfraqueça scanner nem declare check aprovado por ausência de evidência.
4. Reproduza em teste antes da correção, use branch oficial, revisão independente e CI no SHA exato. Se a regressão
   estiver em produção, siga [retorno de versão](voltar-versao.md); prefira correção com versão maior e mesma chave.
5. Homologue a candidata corrigida pelos cenários afetados e pelos critérios da entrega; publique somente com
   autorização vigente do dono e portões completos. Verifique os mesmos bytes na Release publicada.
6. Registre causa, impacto observado, versões afetadas/corrigidas, evidências reais e ação preventiva em memória e
   changelog. Atualize README/runbook se mudaram. Libere posse e conclua issues, branches e cartões pelo fluxo.

Uma falha que só ocorreu em teste local não demonstra exposição em produção. Um emulador não comprova comportamento
em todos os dispositivos físicos; delimite o resultado no relatório do incidente.
