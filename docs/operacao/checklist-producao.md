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
