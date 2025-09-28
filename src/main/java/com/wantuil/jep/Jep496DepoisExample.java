package com.wantuil.jep;

import javax.crypto.KEM;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.NamedParameterSpec;
import java.util.Base64;

/**
 * Depois do JEP 496 – ML-KEM no KEM API.
 *
 * Demonstra o uso do ML-KEM (padrão NIST FIPS 203) com o KEM API:
 *  - Receiver gera par de chaves ML-KEM (ex.: ML-KEM-768)
 *  - Sender encapsula e obtém (a) chave secreta e (b) mensagem de encapsulação
 *  - Receiver decapsula a mensagem e obtém a MESMA chave
 *
 * Algoritmos suportados: ML-KEM-512, ML-KEM-768, ML-KEM-1024 (nomes padronizados).
 * Requer JDK 24+ com implementação ML-KEM (JEP 496). :contentReference[oaicite:1]{index=1}
 */
public final class Jep496DepoisExample {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try {
            // === Receiver (servidor) gera par ML-KEM (parâmetro de segurança 768)
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("ML-KEM");
            kpg.initialize(NamedParameterSpec.ML_KEM_768); // nomes padronizados
            KeyPair receiver = kpg.generateKeyPair();

            // === Sender usa a pública do Receiver para encapsular
            KEM kem = KEM.getInstance("ML-KEM");
            KEM.Encapsulator enc = kem.newEncapsulator(receiver.getPublic());

            // Podemos pedir que a chave resultante já seja "AES" de 256 bits,
            // usando a cauda do segredo compartilhado (API do KEM permite from/to/algorithm).
            int secretSize = enc.secretSize();
            var encap = enc.encapsulate(secretSize - 32, secretSize, "AES");

            SecretKey senderKey = encap.key();          // chave simétrica do sender (AES-256)
            byte[] kemMessage = encap.encapsulation();  // mensagem de encapsulação para o receiver

            // === Receiver decapsula e obtém a MESMA chave AES-256
            KEM.Decapsulator dec = kem.newDecapsulator(receiver.getPrivate());
            SecretKey receiverKey = dec.decapsulate(kemMessage, dec.secretSize() - 32, dec.secretSize(), "AES");

            System.out.println("[JEP 496 | DEPOIS] Chaves iguais? " +
                    java.util.Arrays.equals(senderKey.getEncoded(), receiverKey.getEncoded()));
            System.out.println("[JEP 496 | DEPOIS] AES-256 (Base64): " +
                    Base64.getEncoder().encodeToString(senderKey.getEncoded()));
        } catch (Throwable t) {
            System.err.println("[JEP 496 | DEPOIS] Erro: " + t);
        }
    }
}
