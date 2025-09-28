package com.wantuil.jep;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Antes do JEP 485 — Como fazíamos sem Gatherers
 *
 * Demonstra uma alternativa pré-JEP 485 usando apenas operações tradicionais
 * de Stream (limit + collect com acumulador), mantendo coleções temporárias e
 * lógica manual para formar janelas de tamanho fixo.
 */
public final class Jep485AntesExample {

    public static void run() {
        System.out.println("[JEP 485 | ANTES] Agrupando manualmente em janelas de 3 sem Gatherers");

        List<List<Integer>> janelas =
                Stream.iterate(0, i -> i + 1)
                        .limit(6) // precisamos pré-materializar elementos suficientes
                        .collect(ArrayList::new, (grupos, n) -> {
                            if (grupos.isEmpty() || grupos.getLast().size() == 3)
                                grupos.add(new ArrayList<>());
                            grupos.getLast().add(n);
                        }, (a, b) -> { throw new UnsupportedOperationException(); });

        System.out.println("[JEP 485 | ANTES] Janelas de tamanho 3 (2 primeiras): " + janelas);
    }

    public static void main(String[] args) {
        run();
    }
}
