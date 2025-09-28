package com.wantuil.jep;

import java.util.List;

/**
 * Antes do JEP 512
 *
 */
public final class Jep512AntesExample {

    public static void run() {
        System.out.println("[JEP 512 | ANTES] Alternativas tradicionais antes do recurso do JEP 512.");

        var stringList = List.of("Exemplo (conceitual) do 'antes':",
                "Código aqui mostra como resolveríamos sem o JEP 512,",
                "possivelmente com classes utilitárias, padrões existentes.",
                "ou escrevendo mais código boilerplate.",
                "...");

        stringList.forEach(texto -> IO.println("// " + texto));
    }

    public static void main(String[] args) {
        run();
    }
}
