# Inventário dos dados locais

ScreenFakeCam v0.3.1, aplicativo Android sem servidor, conta, analytics, anúncios ou permissão de internet.
Não há banco de dados nem envio automático. A imagem pode conter dados pessoais; escolha e compartilhamento são
comandos do usuário. Este inventário descreve o fluxo técnico atual, sem declarar uma base legal para coleta futura.

| Dado | Finalidade e local | Retenção | Exclusão/saída |
| --- | --- | --- | --- |
| Imagem escolhida e URI | ContentResolver lê somente a imagem selecionada; bitmap decodificado em memória para visor/leitor | Sessão em memória, sem cópia/histórico persistente criado pelo app | Voltar descarta a imagem; encerrar o processo encerra a sessão. O original continua no provedor/galeria |
| Enquadramento e resultado do código | Estado em memória para recorte e exibição do texto | Sessão, sem histórico persistente | Descartar tela/sessão; copiar usa a área de transferência do Android, fora do controle de retenção do app |
| Foto nova JPEG e URI | Salvar o recorte solicitado na galeria (Android10+) ou documento escolhido (Android8/9) | Até o usuário excluir no destino | Apagar na galeria/gerenciador; remover também cópias em backups/serviços usados pelo próprio usuário |
| Prévia e URI da última foto | Memória do ViewModel para o atalho Última | Até outra foto substituir ou o ViewModel/processo encerrar; Voltar do visor não limpa essa prévia | Encerrar a sessão do app descarta o estado em memória; o JPEG externo permanece |
| Conteúdo compartilhado ou endereço aberto | Intent para app escolhido/navegador, somente por ação explícita | Depende do app externo | Aplicam-se as regras do destinatário; o navegador pode acessar internet |
| Log técnico de falha | Diagnóstico local; carregador registra classe da exceção sem URI/conteúdo da imagem | Conforme logs do Android; não há coletor remoto no app | Não publicar log bruto; relato usa mínimo anonimizado |

## Backup e restauração das fotos

O app tem `android:allowBackup=false`; não oferece backup/restauração de sessão ou de histórico. Isso não desliga
backup feito pela galeria, provedor de documentos ou serviço instalado pelo usuário. Fotos salvas são arquivos
externos: para preservá-las, copie as desejadas para destino de confiança ou use o backup escolhido pelo usuário;
verifique se a cópia abre antes de excluir o original. Para restaurar, recoloque as fotos no provedor/galeria e
escolha-as novamente pelo seletor. Não há restauração de estado de zoom nem lista de leituras no ScreenFakeCam.

Um mantenedor não precisa receber imagens reais para reproduzir um bug: use imagem sintética/QR de teste. Nova
coleta, persistência, telemetria ou integração exige revisão do produto, do fluxo de dados e deste inventário antes
da entrega. Consulte [RN-0003](../negocio/regras/RN-0003-sem-acesso-proibido.md) e [segurança](../../SECURITY.md).
