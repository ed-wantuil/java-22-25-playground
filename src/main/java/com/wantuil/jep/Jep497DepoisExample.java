package com.wantuil.jep;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.NamedParameterSpec;
import java.util.Base64;

/**
 * Depois do JEP 497 – ML-DSA (assinatura resistente a quantum).
 *
 * Usa a implementação padrão do JDK 24+:
 *  - KeyPairGenerator "ML-DSA" com NamedParameterSpec.ML_DSA_65
 *  - Signature "ML-DSA" para assinar e verificar
 */
public final class Jep497DepoisExample {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try {
            // 1) Gera par ML-DSA (nível 65 — equivalente ao "Dilithium3")
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("ML-DSA");
            kpg.initialize(NamedParameterSpec.ML_DSA_65); // também há ML_DSA_44 e ML_DSA_87
            KeyPair kp = kpg.generateKeyPair();

            // 2) Mensagem
            byte[] msg = "Assinatura pós-quântica (ML-DSA)".getBytes(StandardCharsets.UTF_8);

            // 3) Assina
            Signature sig = Signature.getInstance("ML-DSA");
            sig.initSign(kp.getPrivate());
            sig.update(msg);
            byte[] assinatura = sig.sign();

            // 4) Verifica
            Signature verify = Signature.getInstance("ML-DSA");
            verify.initVerify(kp.getPublic());
            verify.update(msg);
            boolean ok = verify.verify(assinatura);

            System.out.println("[JEP 497 | DEPOIS] Assinatura válida? " + ok);
            System.out.println("[JEP 497 | DEPOIS] Parâmetro: ML_DSA_65");
            System.out.println("[JEP 497 | DEPOIS] Assinatura (Base64): " +
                    Base64.getEncoder().encodeToString(assinatura));
        } catch (Throwable t) {
            System.err.println("[JEP 497 | DEPOIS] Erro: " + t);
        }
    }
}
