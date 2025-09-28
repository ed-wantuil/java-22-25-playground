package com.wantuil.jep;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Antes do JEP 510 – sem API padrão de KDF.
 *
 * Exemplo de derivação de chave via HKDF (HMAC-SHA256) implementado "na unha":
 *  - hkdfExtract(): PRK = HMAC(salt, IKM)
 *  - hkdfExpand():  OKM a partir de PRK, info e tamanho desejado
 *
 * Riscos: código verboso e sujeito a erros sutis (limites de bloco, resets, concat, etc.).
 */
public final class Jep510AntesExample {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try {
            // Entradas (simuladas)
            byte[] ikm  = "segredo-base".getBytes(StandardCharsets.UTF_8); // Input Keying Material
            byte[] salt = "sal-exemplo".getBytes(StandardCharsets.UTF_8);
            byte[] info = "contexto/hkdf-demo".getBytes(StandardCharsets.UTF_8);

            // HKDF-Extract
            byte[] prk = hkdfExtract(salt, ikm);

            // HKDF-Expand para 32 bytes (AES-256)
            int outLen = 32;
            byte[] okm = hkdfExpand(prk, info, outLen);

            SecretKey aesKey = new SecretKeySpec(okm, "AES");

            System.out.println("[JEP 510 | ANTES] Algoritmo: " + aesKey.getAlgorithm());
            System.out.println("[JEP 510 | ANTES] Tamanho: " + (aesKey.getEncoded().length * 8) + " bits");
            System.out.println("[JEP 510 | ANTES] Chave (Base64): " +
                    Base64.getEncoder().encodeToString(aesKey.getEncoded()));
        } catch (Throwable t) {
            System.err.println("[JEP 510 | ANTES] Erro: " + t);
        }
    }

    // ========== Implementação mínima de HKDF (HMAC-SHA256) ==========
    private static byte[] hkdfExtract(byte[] salt, byte[] ikm) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(salt, "HmacSHA256"));
        return mac.doFinal(ikm); // PRK
    }

    private static byte[] hkdfExpand(byte[] prk, byte[] info, int outLen) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(prk, "HmacSHA256"));

        int hashLen = mac.getMacLength();              // 32 para SHA-256
        int n = (int) Math.ceil((double) outLen / hashLen);
        byte[] okm = new byte[outLen];

        byte[] t = new byte[0];
        int pos = 0;
        for (int i = 1; i <= n; i++) {
            mac.reset();
            mac.update(t);
            if (info != null) mac.update(info);
            mac.update((byte) i);
            t = mac.doFinal();
            int bytesToCopy = Math.min(hashLen, outLen - pos);
            System.arraycopy(t, 0, okm, pos, bytesToCopy);
            pos += bytesToCopy;
        }
        return okm;
    }
}
