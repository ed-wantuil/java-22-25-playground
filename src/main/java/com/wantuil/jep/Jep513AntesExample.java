package com.wantuil.jep;

/**
 * Antes do “JEP 513” – exemplo ilustrativo.
 *
 * Observação: historicamente, a chamada a {@code super()} precisava ser
 * a **primeira instrução** do construtor. Por isso, qualquer lógica
 * (validação, logs, etc.) vinha somente depois.
 */
public final class Jep513AntesExample {

    String texto;

    Jep513AntesExample(String texto) {
        // super() deve ser a PRIMEIRA instrução no construtor (como era antes):
        super();

        // Qualquer validação acontecia depois do super():
        if (texto == null) {
            System.out.println("O texto deve ser infomado");
        }

        this.texto = texto;
    }

    public static void run() {
        Jep513AntesExample jep513AntesExample = new Jep513AntesExample("Apenas um teste");
    }

    public static void main(String[] args) {
        run();
    }
}
