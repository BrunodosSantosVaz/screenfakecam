# Testes de aceite

Um teste por critério de aceite de cada épico, com o ID da regra de negócio no nome (`rn0001…`), no pacote Kotlin
`aceite` e numa pasta por épico (`tests/aceite/<n>-<slug>/`). Compilados e rodados com os testes do módulo `app`
(`bigbang.toml` comandos.testes_aceite). Travados: só o PR de teste do épico cria linhas aqui; a tarefa só retira a
marca `@Pendente` dos próprios testes (`bb aceite liberar`).
