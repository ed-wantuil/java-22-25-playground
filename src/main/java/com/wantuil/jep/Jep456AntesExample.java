package com.wantuil.jep;

import java.io.StringReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Antes do JEP 456 — sem variáveis e padrões anônimos.
 *
 * Demonstra as alternativas tradicionais antes do JEP 456:
 *  - Usar nomes como "ignored" para parâmetros/variáveis não usados.
 *  - Usar default em switch ao invés de um padrão total _.
 *  - Em patterns (incluindo record patterns), nomear componentes não utilizados.
 */
public final class Jep456AntesExample {

    public record Ponto(int x, int y) {}

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("[ANTES DO JEP 456] Maneiras tradicionais sem _ anônimo.");

        // 1) Lambda: precisamos dar um nome ao parâmetro, mesmo sem usar
        var contador = new AtomicInteger();
        List<String> itens = List.of("a", "b", "c");
        itens.forEach(ignored -> contador.incrementAndGet());
        System.out.println("[ANTES DO JEP 456] Contador após lambda: " + contador.get());

        // 2) For-each: variável precisa de um nome
        int somaDeTres = 0;
        for (var ignored : List.of(1, 2, 3)) {
            somaDeTres++;
        }
        System.out.println("[ANTES DO JEP 456] Iterações contadas: " + somaDeTres);

        // 3) try-with-resources: recurso precisa de um nome
        try (var ignored = new StringReader("recurso")) {
            System.out.println("[ANTES DO JEP 456] try-with-resources com recurso nomeado.");
        } catch (Exception ignored) { // 4) catch com parâmetro nomeado
            System.out.println("[ANTES DO JEP 456] Exceção capturada e ignorada com variável nomeada.");
        }

        // 5) Switch: usamos default e ignoramos valores com nomes
        Object obj = Integer.valueOf(42);
        String desc = switch (obj) {
            case String s -> "string: " + s;
            case Integer ignored -> "inteiro (valor ignorado)"; // precisamos nomear
            default -> "qualquer outra coisa";
        };
        System.out.println("[ANTES DO JEP 456] Switch sem padrão anônimo: " + desc);

        // 6) Record pattern: componente não utilizado precisa de um nome
        Ponto p = new Ponto(10, 99);
        String txt = switch (p) {
            case Ponto(int x, int yIgnored) -> "x = " + x + ", y ignorado";
        };
        System.out.println("[ANTES DO JEP 456] Record pattern sem _: " + txt);
    }
}
