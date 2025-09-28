package com.wantuil.jep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Antes do JEP 511 — Sem "import module" (saídas ilustrativas).
 *
 * Abordagem tradicional: quando precisamos de muitas classes relacionadas,
 * escrevemos diversos imports por pacote (ou usamos wildcard por pacote), ou
 * optamos por nomes totalmente qualificados nos pontos de uso.
 */
public final class Jep511AntesExample {

    public static void run() {
        System.out.println("[JEP 511 | ANTES] Múltiplos imports por pacote / wildcard / nomes qualificados.");

        // 1) Muitos imports explícitos por pacote
        System.out.println("\n[1] Muitos imports explícitos por pacote (exemplo):\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n" +
                "import java.util.Set;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.HashSet;\n");

        // 2) Uso de wildcard por pacote
        System.out.println("[2] Wildcard por pacote (exemplo):\n" +
                "import java.util.*;\n" +
                "// Pode esconder de onde cada tipo vem e não reflete a granulação por módulo.\n");

        // 3) Nomes totalmente qualificados no ponto de uso
        System.out.println("[3] Nomes completamente qualificados (exemplo):\n" +
                "java.util.List<String> xs = java.util.List.of(\"a\", \"b\");\n" +
                "java.util.Map<String,Integer> m = new java.util.HashMap<>();\n");

        // Execução real simbólica (apenas para termos uma saída concreta)
        List<String> nomes = List.of("Ana", "Bruno");
        Set<String> conjunto = new HashSet<>(nomes);
        Map<String, Integer> tamanhoPorNome = new HashMap<>();
        nomes.forEach(n -> tamanhoPorNome.put(n, n.length()));
        List<String> lista = new ArrayList<>(conjunto);

        System.out.println("[JEP 511 | ANTES] nomes = " + nomes);
        System.out.println("[JEP 511 | ANTES] conjunto (HashSet) = " + conjunto);
        System.out.println("[JEP 511 | ANTES] tamanhoPorNome (HashMap) = " + tamanhoPorNome);
        System.out.println("[JEP 511 | ANTES] lista (ArrayList, a partir do conjunto) = " + lista);

        // 4) Observações
        System.out.println("\n[4] Observações:\n" +
                "- Em bases grandes, repetimos muitos imports, especialmente quando usamos tipos\n" +
                "  de vários pacotes do mesmo módulo.\n" +
                "- Wildcards por pacote ajudam, mas não mapeiam 1-para-1 a organização por módulo.\n" +
                "- Nomes qualificados reduzem imports, mas ficam verbosos no código.\n");
    }

    public static void main(String[] args) {
        run();
    }
}
