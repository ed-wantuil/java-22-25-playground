package com.wantuil.jep;

/**
 * Depois do “JEP 513” – exemplo prático.
 *
 * Demonstra que o construtor pode executar lógica **antes** de chamar `super()`
 * (isto é, o `super()` não precisa mais ser a primeira instrução do construtor).
 * Ainda assim, a chamada a `super()` deve acontecer em algum ponto antes de o
 * construtor terminar.
 */
public final class Jep513DepoisExample {

    private final String texto;

    public Jep513DepoisExample(String texto) {
        // Lógica de validação e inicialização ANTES do super():
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("[JEP 513 | DEPOIS] O texto deve ser informado");
        }
        System.out.println("[JEP 513 | DEPOIS] Preparando instância com texto válido...");

        //A chamada explícita ao super() foi mantida, mas agora pode vir DEPOIS:
        super();

        // Inicializações do próprio objeto
        this.texto = texto;
        System.out.println("[JEP 513 | DEPOIS] Instância criada com sucesso.");
    }

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try {
            Jep513DepoisExample exemplo = new Jep513DepoisExample("Apenas um teste");
            System.out.println("[JEP 513 | DEPOIS] Texto: " + exemplo.texto);
        } catch (IllegalArgumentException e) {
            System.err.println("[JEP 513 | DEPOIS] Erro de validação: " + e.getMessage());
        } catch (Throwable t) {
            System.err.println("[JEP 513 | DEPOIS] Erro inesperado: " + t);
        }
    }
}
