# Operação do ScreenFakeCam

Aplicativo Android distribuído como APK assinado pelo GitHub Releases; não há servidor, banco, migração, domínio
ou endpoint de saúde. Responsável operacional: Bruno dos Santos Vaz, dono do repositório. CI verde não demonstra
que alguém instalou e usou um APK: cada registro de homologação deve indicar a evidência real e seus limites.

| Situação | Procedimento |
| --- | --- |
| Construir candidata, homologar e publicar | [Entrega do APK](entrega.md) |
| Regressão depois de publicar | [Retorno de versão](voltar-versao.md) |
| Falha ou suspeita de vazamento | [Incidente](incidente.md) |
| Custódia, recuperação e rotação da assinatura | [Assinatura e segredos](assinatura.md) |
| Imagens e fotos locais, backup e exclusão | [Inventário de dados](../dados/inventario.md) |
| Portões antes da produção | [Checklist de produção](checklist-producao.md) |

Os procedimentos foram confrontados inicialmente com a v0.3.1. A produção atual v0.3.2 possui
[recibo próprio](../validacao/032-producao.md) de candidata/CI/assinatura/SBOM, homologação real no emulador e
promoção dos mesmos bytes. A conferência documental não executa recuperação de chave nem retorno em aparelho. Registre cada exercício futuro com data,
Android, versão/hash e resultado, sem imagens pessoais nem valores de segredo.
