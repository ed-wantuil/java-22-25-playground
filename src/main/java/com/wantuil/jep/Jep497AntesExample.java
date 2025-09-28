package com.wantuil.jep;

import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * Antes do JEP 497 – exemplo com ECDSA (não PQC).
 *
 * Assina/verifica usando EC secp256r1 + SHA256withECDSA.
 */
public final class Jep497AntesExample {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try {
            // 1) Gera par de chaves EC (secp256r1)
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
            kpg.initialize(new java.security.spec.ECGenParameterSpec("secp256r1"));
            KeyPair kp = kpg.generateKeyPair();

            // 2) Prepara dados
            byte[] msg = "Assinatura clássica (ECDSA)".getBytes(StandardCharsets.UTF_8);

            // 3) Assina
            Signature signer = Signature.getInstance("SHA256withECDSA");
            signer.initSign(kp.getPrivate());
            signer.update(msg);
            byte[] sig = signer.sign();

            // 4) Verifica
            Signature verifier = Signature.getInstance("SHA256withECDSA");
            verifier.initVerify(kp.getPublic());
            verifier.update(msg);
            boolean ok = verifier.verify(sig);

            System.out.println("[JEP 497 | ANTES] Assinatura válida? " + ok);
            System.out.println("[JEP 497 | ANTES] Algoritmo: SHA256withECDSA / curva secp256r1");
        } catch (Throwable t) {
            System.err.println("[JEP 497 | ANTES] Erro: " + t);
        }
    }
}
