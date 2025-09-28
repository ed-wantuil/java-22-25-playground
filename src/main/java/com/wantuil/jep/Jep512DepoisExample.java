/**
 * Depois do JEP 512
 * o código pode ser muito mais curto e direto ao ponto.
 */
void main() {
    IO.println("[JEP 512 | DEPOIS] Demonstração conceitual do recurso.");
    IO.println("[JEP 512 | DEPOIS] Abaixo, trechos de código e explicações em texto para manter compatibilidade.");

    var stringList = List.of("Exemplo (conceitual) do 'depois':",
            "Código aqui usaria a nova sintaxe/funcionalidade do JEP 512,",
            "possivelmente simplificando imports ou acessos a tipos de módulos.",
            "...");

    stringList.forEach(texto -> IO.println("// " + texto));
}