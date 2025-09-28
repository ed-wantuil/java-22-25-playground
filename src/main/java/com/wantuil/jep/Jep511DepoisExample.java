package com.wantuil.jep;

import module java.base;

/**
 * Depois do JEP 511 — import module (conceitual + código executável).
 *
 * Observação: A sintaxe 'import module' pode não estar disponível na ide atual.
 */
public final class Jep511DepoisExample {

    public static void run() {
        System.out.println("[JEP 511 | DEPOIS] import module — visão geral e exemplo executável.");

        // 1) Ideia central e snippet com a nova sintaxe
        System.out.println("\n[1] Snippet (conceitual; pode exigir JDK com suporte ao JEP 511):\n" +
                "----------------------------------------------\n" +
                "import module java.base;\n" +
                "// Agora tipos públicos exportados por java.base (p.ex., java.util.List,\n" +
                "// java.time.LocalDate) ficam disponíveis sem imports por pacote.\n");

        // 2) Exemplo real executável (usando imports tradicionais para manter compatibilidade)
        List<String> nomes = List.of("Ana", "Bruno", "Carla");
        LocalDate hoje = LocalDate.now();
        Set<String> conjunto = new HashSet<>(nomes);
        Map<String, Integer> tamanhoPorNome = new HashMap<>();
        nomes.forEach(n -> tamanhoPorNome.put(n, n.length()));
        List<String> lista = new ArrayList<>(conjunto);

        System.out.println("[JEP 511 | DEPOIS][Execução] hoje = " + hoje);
        System.out.println("[JEP 511 | DEPOIS][Execução] nomes = " + nomes);
        System.out.println("[JEP 511 | DEPOIS][Execução] conjunto (HashSet) = " + conjunto);
        System.out.println("[JEP 511 | DEPOIS][Execução] tamanhoPorNome (HashMap) = " + tamanhoPorNome);
        System.out.println("[JEP 511 | DEPOIS][Execução] lista (ArrayList a partir do conjunto) = " + lista);

        // 3) Exportações transitivas (conceitual)
        System.out.println("\n[2] Exportações transitivas (conceitual):\n" +
                "Se o módulo B declara 'requires transitive A', então:\n" +
                "    import module B;\n" +
                "também disponibiliza os tipos exportados por A.\n\n" +
                "Snippet:\n" +
                "module A { exports a.api; }\n" +
                "module B { requires transitive A; }\n" +
                "// Em um código cliente:\n" +
                "import module B; // torna a.api.* visível via transitividade\n");

        // 4) Conflitos e ambiguidade (conceitual)
        System.out.println("[3] Conflitos de nomes (conceitual):\n" +
                "Se dois módulos exportarem tipos com o mesmo nome simples (ex.: Date),\n" +
                "a resolução segue regras semelhantes às de import tradicional.\n" +
                "Você pode optar por:\n" +
                "  - Usar o nome completamente qualificado (ex.: java.util.Date).\n" +
                "  - Adicionar imports mais específicos para desambiguar.\n");

        // 5) Misturar com imports tradicionais (conceitual)
        System.out.println("[4] Misturar com imports tradicionais (conceitual):\n" +
                "import module java.base;\n" +
                "import java.time.LocalDate;  // import específico continua válido\n\n" +
                "LocalDate hoje2 = LocalDate.now();\n");

        // 6) Classpath vs. módulos (conceitual)
        System.out.println("[5] Classpath vs. módulos: o código pode estar no classpath, mas a resolução\n" +
                "do 'import module' ocorre sobre módulos conhecidos (JDK e bibliotecas modularizadas\n" +
                "disponíveis na configuração de build/execução).\n");

        System.out.println("[JEP 511 | DEPOIS] Fim da demonstração.\n");
    }

    public static void main(String[] args) {
        run();
    }
}
