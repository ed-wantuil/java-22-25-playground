package com.wantuil.jep;

/**
 * # Depois do JEP 467 — Markdown Documentation Comments
 *
 * A partir do JEP 467 é possível escrever comentários de documentação
 * (Javadoc) usando sintaxe Markdown, o que traz uma experiência mais natural
 * para quem já documenta em Markdown.
 *
 * Principais pontos demonstrados aqui:
 *
 * - Títulos e listas em Markdown
 * - Código em bloco com cercas (code fences)
 * - Links em estilo Markdown
 * - Tabelas simples em Markdown
 *
 * Exemplo de código (em Markdown):
 *
 * ```java
 * // Um pequeno exemplo de uso
 * Jep467DepoisExample exemplo = new Jep467DepoisExample();
 * exemplo.run();
 * ```
 *
 * Links:
 * - [JEP 467: Markdown Documentation Comments](https://openjdk.org/jeps/467)
 *
 * Tabela (Markdown):
 *
 * | Recurso                  | Suporte |
 * |-------------------------|---------|
 * | Cabeçalhos (#, ##, ...) | Sim     |
 * | Listas                  | Sim     |
 * | Code fences ```         | Sim     |
 *
 * Observação: a renderização acontece quando você gera a documentação com o
 * comando `javadoc` de um JDK que suporte o JEP 467. Em tempo de compilação e
 * execução, estes comentários não afetam o bytecode.
 */
public final class Jep467DepoisExample {

    /**
     * Executa a demonstração "depois" do JEP 467.
     */
    public static void run() {
        System.out.println("[JEP 467 | DEPOIS] Comentários de documentação em Markdown prontos para o javadoc.");
        System.out.println("[JEP 467 | DEPOIS] Para ver o efeito, gere a documentação com o javadoc do JDK 25+.");
    }

    /**
     * Permite executar este exemplo diretamente.
     *
     * ```bash
     * java com.wantuil.jep.Jep467DepoisExample
     * ```
     */
    public static void main(String[] args) {
        run();
    }
}
