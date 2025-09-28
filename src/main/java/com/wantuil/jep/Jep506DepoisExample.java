package com.wantuil.jep;

import java.lang.ScopedValue;

/**
 * Depois do JEP 506 – exemplo com ScopedValue.
 *
 * Demonstra o uso de Scoped Values para compartilhar dados imutáveis pelo
 * escopo dinâmico de execução (callees e threads-filhas criadas dentro do escopo),
 * sem precisar de limpeza manual ou mutabilidade incidental.
 */
public final class Jep506DepoisExample {

    // Chave de contexto imutável
    private static final ScopedValue<String> CONTEXTO = ScopedValue.newInstance();

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        // Estabelece um binding imutável para o escopo dinâmico a seguir
        ScopedValue.where(CONTEXTO, "req-456").run(() -> {
            System.out.println("[JEP 506 | DEPOIS] Contexto no escopo atual: " + CONTEXTO.get());

            // Threads-filhas criadas DENTRO do escopo “enxergam” o mesmo valor imutável
            Thread child = Thread.ofVirtual().start(() -> {
                System.out.println("[JEP 506 | DEPOIS] (filha) Contexto visível e imutável: " + CONTEXTO.get());

                // Não existe set(): não há como alterar o binding dentro do escopo
                // CONTEXTO.set(...) // <- inexistente por design
            });

            try {
                child.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[JEP 506 | DEPOIS] Thread interrompida: " + e);
            }

            // Ao sair do escopo, o binding é descartado automaticamente (sem remove()).
        });

        // Fora do escopo, o valor não está mais acessível:
        try {
            System.out.println("[JEP 506 | DEPOIS] Acesso fora do escopo: " + CONTEXTO.get());
        } catch (IllegalStateException e) {
            System.out.println("[JEP 506 | DEPOIS] Fora do escopo: sem binding ativo (ok).");
        }
    }
}
