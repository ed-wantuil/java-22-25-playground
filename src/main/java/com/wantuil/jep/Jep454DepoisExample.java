package com.wantuil.jep;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;

/**
 * Depois do JEP 454 (Foreign Function & Memory API) – exemplo prático.
 * Demonstra uma chamada para a função C "strlen" da libc usando o Linker nativo.
 */
public final class Jep454DepoisExample {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        // Texto de exemplo que será enviado como C string (UTF-8 com terminador nul)
        String texto = "Olá, JEP 454!";

        // Arena gerencia a alocação e liberação de memória nativa
        try (Arena arena = Arena.ofConfined()) {
            // Usa o lookup padrão do sistema fornecido pelo Linker nativo (portável e sem API restrita)
            Linker linker = Linker.nativeLinker();
            SymbolLookup lookup = linker.defaultLookup();

            // Localiza o símbolo da função strlen
            var strlenSymbol = lookup.find("strlen")
                    .orElseThrow(() -> new UnsatisfiedLinkError("Não foi possível localizar símbolo 'strlen'"));

            // Prepara o descritor da função: long strlen(const char*);
            FunctionDescriptor fd = FunctionDescriptor.of(
                    ValueLayout.JAVA_LONG,      // retorno (size_t) tratado como long em plataformas 64-bit
                    ValueLayout.ADDRESS         // parâmetro const char*
            );

            var strlenHandle = linker.downcallHandle(strlenSymbol, fd);

            // Aloca uma C string (UTF-8 + terminador \0) a partir da String Java
            MemorySegment cString = arena.allocateFrom(texto);

            // Invoca strlen
            long tamanho = (long) strlenHandle.invoke(cString);

            System.out.println("[JEP 454 | DEPOIS] Texto: " + texto);
            System.out.println("[JEP 454 | DEPOIS] Tamanho (strlen): " + tamanho);
        } catch (Throwable t) {
            // Captura Throwable por conta de MethodHandle.invoke, que pode lançar checked exceptions
            System.err.println("[JEP 454 | DEPOIS] Erro ao invocar strlen: " + t);
        }
    }
}
