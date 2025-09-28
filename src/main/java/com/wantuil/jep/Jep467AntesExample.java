package com.wantuil.jep;

/**
 * Antes do JEP 467 — Javadoc em HTML tradicional
 * <p>
 * Historicamente, a documentação Java (Javadoc) era escrita usando
 * marcação baseada em HTML. É totalmente funcional, porém muitas vezes menos
 * natural para quem está acostumado a escrever documentação em Markdown.
 * </p>
 *
 * <h2>Pontos demonstrados aqui</h2>
 * <ul>
 *   <li>Títulos e listas usando tags HTML (<code>&lt;h2&gt;</code>, <code>&lt;ul&gt;</code>)</li>
 *   <li>Blocos de código com <code>&lt;pre&gt;</code> e <code>&lt;code&gt;</code></li>
 *   <li>Links usando <code>&lt;a href&gt;</code></li>
 * </ul>
 *
 * <h3>Exemplo de código (HTML):</h3>
 * <pre><code>
 * // Um pequeno exemplo de uso
 * Jep467AntesExample exemplo = new Jep467AntesExample();
 * exemplo.run();
 * </code></pre>
 *
 * <p>
 * Link: <a href="https://openjdk.org/jeps/467">JEP 467: Markdown Documentation Comments</a>
 * </p>
 */
public final class Jep467AntesExample {

    /**
     * Executa a demonstração "antes" do JEP 467.
     */
    public static void run() {
        System.out.println("[JEP 467 | ANTES] Comentários de documentação usando HTML clássico no Javadoc.");
        System.out.println("[JEP 467 | ANTES] A renderização ocorre ao gerar a documentação com o javadoc.");
    }

    /**
     * Permite executar este exemplo diretamente.
     */
    public static void main(String[] args) {
        run();
    }
}
