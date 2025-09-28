package com.wantuil.jep;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyAgreement;
import java.util.Base64;

/**
 * Antes do JEP 496 – sem ML-KEM.
 *
 * Troca de segredo com X25519 (ECDH) + HKDF (implementado manualmente)
 * para derivar uma chave AES-256.
 *
 * Riscos: código verboso, propenso a erros sutis (HKDF), e sem resistência
 * quântica (X25519/ECDH não é PQC).
 */
public final class Jep496AntesExample {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try {
            // === Simula "Receiver" (servidor) gerando par X25519
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("X25519");
            KeyPair receiver = kpg.generateKeyPair();

            // === Simula "Sender" (cliente) gerando par efêmero X25519
            KeyPair sender = kpg.generateKeyPair();

            // --- Sender calcula segredo compartilhado (ECDH) ---
            byte[] sharedSender = ecdh(sender.getPrivate(), receiver.getPublic());

            // --- Receiver calcula o mesmo segredo compartilhado (ECDH) ---
            byte[] sharedReceiver = ecdh(receiver.getPrivate(), sender.getPublic());

            // === Ambos executam HKDF-SHA256 para derivar AES-256 (32 bytes)
            byte[] info = "contexto/jep496-antes".getBytes(StandardCharsets.UTF_8);
            byte[] salt = "sal-exemplo".getBytes(StandardCharsets.UTF_8);

            byte[] prkS = hkdfExtract(salt, sharedSender);
            byte[] keyS = hkdfExpand(prkS, info, 32);

            byte[] prkR = hkdfExtract(salt, sharedReceiver);
            byte[] keyR = hkdfExpand(prkR, info, 32);

            SecretKey aesS = new SecretKeySpec(keyS, "AES");
            SecretKey aesR = new SecretKeySpec(keyR, "AES");

            System.out.println("[JEP 496 | ANTES] Chaves iguais? " +
                    java.util.Arrays.equals(aesS.getEncoded(), aesR.getEncoded()));
            System.out.println("[JEP 496 | ANTES] AES-256 (Base64): " +
                    Base64.getEncoder().encodeToString(aesS.getEncoded()));
        } catch (Throwable t) {
            System.err.println("[JEP 496 | ANTES] Erro: " + t);
        }
    }

    private static byte[] ecdh(java.security.PrivateKey priv, java.security.PublicKey pub) throws Exception {
        KeyAgreement ka = KeyAgreement.getInstance("X25519");
        ka.init(priv);
        ka.doPhase(pub, true);
        return ka.generateSecret(); // bytes do segredo ECDH
    }

    // ======= HKDF (HMAC-SHA256) mínimo =======
    private static byte[] hkdfExtract(byte[] salt, byte[] ikm) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(salt, "HmacSHA256"));
        return mac.doFinal(ikm); // PRK
    }

    private static byte[] hkdfExpand(byte[] prk, byte[] info, int outLen) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(prk, "HmacSHA256"));
        int hashLen = mac.getMacLength();
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
