package com.wantuil.jep;

import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

/**
 * Depois do JEP 485 — Stream Gatherers (Stream.gather + java.util.stream.Gatherers)
 *
 * Demonstra o uso real de Gatherers em um pipeline de Stream, criando janelas
 * fixas (windowFixed) de tamanho 3 e imprimindo as duas primeiras janelas.
 * Este recurso torna o código mais legível e evita coleções temporárias
 * e lógica manual de agrupamento.
 */
public final class Jep485DepoisExample {

    public static void run() {
        System.out.println("[JEP 485 | DEPOIS] Stream.gather com Gatherers.windowFixed(3)");

        List<List<Integer>> janelas =
                Stream.iterate(0, i -> i + 1)
                        .gather(Gatherers.windowFixed(3))
                        .limit(2)   // janelas: [0,1,2], [3,4,5]
                        .toList();

        System.out.println("[JEP 485 | DEPOIS] Janelas de tamanho 3 (2 primeiras): " + janelas);
    }

    public static void main(String[] args) {
        run();
    }
}
