package com.wantuil.jep;

/**
 * Antes do JEP 506 – exemplo com InheritableThreadLocal.
 *
 * Contexto: para “propagar” dados a threads-filhas, usava-se ThreadLocal ou
 * InheritableThreadLocal. Isso pode causar problemas: necessidade de limpeza
 * manual (remove), risco de vazamento em pools, e mutabilidade acidental.
 */
public final class Jep506AntesExample {

    // Simula um "contexto" (ex.: request-id / locale) que deveria acompanhar chamadas.
    private static final InheritableThreadLocal<String> CONTEXTO =
            new InheritableThreadLocal<>();

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        // Define um valor de contexto no "escopo atual"
        CONTEXTO.set("req-123");

        System.out.println("[JEP 506 | ANTES] Contexto na thread principal: " + CONTEXTO.get());

        Thread child = new Thread(() -> {
            // InheritableThreadLocal copia o valor do pai no momento da criação
            System.out.println("[JEP 506 | ANTES] (filha) Contexto herdado: " + CONTEXTO.get());

            // Mutabilidade: alguém pode alterar o valor localmente…
            CONTEXTO.set("req-123-MODIFICADO");
            System.out.println("[JEP 506 | ANTES] (filha) Contexto modificado: " + CONTEXTO.get());

            // Boa prática, mas frequentemente esquecida:
            CONTEXTO.remove();
        });

        child.start();

        try {
            child.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[JEP 506 | ANTES] Thread interrompida: " + e);
        }

        // O valor da thread principal permanece definido se não for limpo:
        System.out.println("[JEP 506 | ANTES] Contexto ainda presente no pai (pode vazar em pools): " + CONTEXTO.get());

        // Limpeza manual necessária para evitar retenção/vazamentos
        CONTEXTO.remove();
    }
}
