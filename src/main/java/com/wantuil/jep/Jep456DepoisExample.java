package com.wantuil.jep;

import java.io.StringReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Depois do JEP 456 — Unnamed Variables & Patterns (final no Java 22+).
 *
 * Demonstra:
 *  - Variáveis anônimas: _, em lambda, catch, for-each e try-with-resources.
 *  - Padrões anônimos: _ em patterns (ex.: record pattern e switch).
 */
public final class Jep456DepoisExample {

    public record Ponto(int x, int y) {}

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("[JEP 456 | DEPOIS] Demonstração de variáveis e padrões anônimos (_).");

        // 1) Lambda com parâmetro anônimo
        var contador = new AtomicInteger();
        List<String> itens = List.of("a", "b", "c");
        itens.forEach(_ -> contador.incrementAndGet()); // parâmetro não é usado
        System.out.println("[JEP 456 | DEPOIS] Contador após lambda: " + contador.get());

        // 2) For-each com variável anônima
        int somaDeTres = 0;
        for (var _ : List.of(1, 2, 3)) { // não precisamos do elemento
            somaDeTres++; // apenas contamos as iterações
        }
        System.out.println("[JEP 456 | DEPOIS] Iterações contadas com for-each _: " + somaDeTres);

        // 3) try-with-resources com recurso anônimo
        try (var _ = new StringReader("recurso")) {
            System.out.println("[JEP 456 | DEPOIS] try-with-resources com recurso anônimo funcionando.");
        } catch (Exception _) { // 4) catch com parâmetro anônimo
            System.out.println("[JEP 456 | DEPOIS] Exceção capturada e ignorada com parâmetro anônimo.");
        }

        // 5) Switch com padrão anônimo (total pattern)
        Object obj = Integer.valueOf(42);
        String desc = switch (obj) {
            case String s -> "string: " + s;
            case Integer _ -> "inteiro (valor ignorado)"; // ignoramos o valor, só o tipo importa
            default -> "qualquer outra coisa"; // equivalente a case _
        };
        System.out.println("[JEP 456 | DEPOIS] Switch com padrão anônimo: " + desc);

        // 6) Record pattern com componente anônimo
        Ponto p = new Ponto(10, 99);
        String txt = switch (p) {
            case Ponto(int x, _) -> "x = " + x + ", y ignorado";
        };
        System.out.println("[JEP 456 | DEPOIS] Record pattern com _: " + txt);
    }
}
