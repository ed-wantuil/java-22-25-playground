package com.wantuil.jep;

import javax.crypto.KDF;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Depois do JEP 510 – API de KDF padronizada.
 *
 * Demonstra a derivação de uma chave AES-256 usando HKDF-SHA256:
 *  - Cria um KDF para "HKDF-SHA256"
 *  - Usa HKDFParameterSpec.ofExtract().addIKM(...).addSalt(...).thenExpand(info, len)
 *  - Chama deriveKey("AES", derivationSpec)
 *
 * Observação: no JDK 24 a API era "preview" (JEP 478); no JDK 25 foi finalizada (JEP 510).
 */
public final class Jep510DepoisExample {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try {
            // Entradas (simuladas)
            byte[] ikm  = "segredo-base".getBytes(StandardCharsets.UTF_8); // Input Keying Material
            byte[] salt = "sal-exemplo".getBytes(StandardCharsets.UTF_8);
            byte[] info = "contexto/hkdf-demo".getBytes(StandardCharsets.UTF_8);

            // 1) Seleciona o algoritmo de KDF
            KDF hkdf = KDF.getInstance("HKDF-SHA256"); // algoritmo padronizado

            // 2) Monta o "derivationSpec" para Extract-then-Expand (RFC 5869)
            var derivationSpec = HKDFParameterSpec.ofExtract()
                    .addIKM(ikm)
                    .addSalt(salt)
                    .thenExpand(info, 32); // 32 bytes => 256 bits (AES-256)

            // 3) Deriva SecretKey diretamente para o algoritmo desejado
            SecretKey aesKey = hkdf.deriveKey("AES", derivationSpec);

            System.out.println("[JEP 510 | DEPOIS] Algoritmo: " + aesKey.getAlgorithm());
            System.out.println("[JEP 510 | DEPOIS] Tamanho: " + (aesKey.getEncoded().length * 8) + " bits");
            System.out.println("[JEP 510 | DEPOIS] Chave (Base64): " +
                    Base64.getEncoder().encodeToString(aesKey.getEncoded()));
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException e) {
            System.err.println("[JEP 510 | DEPOIS] Erro de KDF: " + e.getMessage());
        } catch (Throwable t) {
            System.err.println("[JEP 510 | DEPOIS] Erro inesperado: " + t);
        }
    }
}
