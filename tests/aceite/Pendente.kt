package aceite

import org.junit.AssumptionViolatedException
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Every acceptance test lives in package `aceite`, so `@Pendente` needs no import: releasing a mark
 * (`bb aceite liberar`) only removes the mark line and leaves no unused import behind.
 *
 * Pending acceptance test of a task (bigbang.toml testes.marca_pendente): the line reads
 * `@Pendente // pendente da tarefa #N` and `bb aceite liberar N` removes it when the task is done.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Pendente

/**
 * Strict expected failure: a pending test that fails is reported as skipped; one that passes FAILS, so a forgotten
 * mark never hides a working feature. Every acceptance test class declares `@get:Rule val pendentes = PendingRule()`.
 */
class PendingRule : TestRule {
    override fun apply(
        base: Statement,
        description: Description,
    ): Statement =
        object : Statement() {
            override fun evaluate() {
                if (description.getAnnotation(Pendente::class.java) == null) return base.evaluate()
                try {
                    base.evaluate()
                } catch (failure: Throwable) {
                    throw AssumptionViolatedException("pendente: falhou como esperado (${failure.message})")
                }
                throw AssertionError(
                    "${description.methodName}: passou, mas está marcado @Pendente; retire a marca com bb aceite liberar",
                )
            }
        }
}
