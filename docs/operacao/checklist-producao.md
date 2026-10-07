# Checklist de produção

Exigido pelo "Publicar em produção" (`bb checklist producao`). O ScreenFakeCam é um app Android sem servidor, sem
banco e sem login (perfil compilado): os itens de backend e de API não se aplicam.

- [ ] O backend sobe sem erro — verificação: `nao-se-aplica: app sem servidor`
- [ ] O front compila — verificação: `portao: candidata`
- [ ] As migrações rodam do zero e a partir da versão anterior — verificação: `nao-se-aplica: app sem banco de dados`
- [ ] O ambiente sobe pelo método de deploy do alvo — verificação: `nao-se-aplica: perfil compilado; o usuário instala o APK assinado da Release`
- [ ] Nenhum segredo no pacote do front — verificação: `portao: seguranca`
- [ ] Rotas privadas exigem autenticação — verificação: `nao-se-aplica: app sem servidor e sem login`
- [ ] CORS de produção configurado por ambiente — verificação: `nao-se-aplica: app sem servidor`
- [ ] Testes de limite de requisições presentes e passando — verificação: `nao-se-aplica: app sem endpoints (não usa internet, RN-0003)`
- [ ] /api/health responde sem autenticação — verificação: `nao-se-aplica: app sem servidor`
- [ ] O README documenta as variáveis de ambiente sem valores — verificação: `portao: builtin`
- [ ] A auditoria de segurança não tem achado crítico ou alto aberto — verificação: `portao: builtin`

## Procedimentos e evidências

As verificações acima são resolvidas pelo portão de cada entrega, não marcadas como executadas por esta tarefa de
documentação. O épico #71 é sem-release e não produz APK; a próxima candidata de correção deve preencher evidências
próprias de hash/assinatura/atestado, instalação e cenários efetivamente executados.

- [Entrega compilada](entrega.md): integra PRs revisados e CI no SHA exato; verifica APK/checksum/SBOM/atestado,
  homologa a candidata e promove os mesmos bytes sem recompilar.
- [Assinatura](assinatura.md): certificado público, nomes dos segredos sem valores, backup privado a comprovar,
  restauração em ambiente isolado e limites de troca de chave.
- [Retorno](voltar-versao.md): limite de downgrade Android e correção compatível com versão maior; nenhum retorno
  real ou recuperação de keystore foi exercitado por este épico documental.
- [Incidente](incidente.md) e [dados locais](../dados/inventario.md): relatos mínimos sem imagens pessoais,
  compartilhamento por ação do usuário, retenção e backup das fotos externas.

Rastreabilidade/documentação: [conferência do épico #71](../validacao/71-documentacao-producao.md).
