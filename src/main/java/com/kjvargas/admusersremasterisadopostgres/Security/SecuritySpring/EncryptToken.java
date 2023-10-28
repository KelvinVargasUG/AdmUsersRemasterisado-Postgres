package com.kjvargas.admusersremasterisadopostgres.Security.SecuritySpring;

import org.springframework.security.rsa.crypto.RsaSecretEncryptor;
import org.springframework.stereotype.Component;

@Component
public class EncryptToken {

    private RsaSecretEncryptor encryptor;


    private String privateKey="-----BEGIN RSA PRIVATE KEY-----" +
            "MIIEpAIBAAKCAQEA1rGKzA3y2Aw/..." +
            "... (contenido de la clave privada) ..." +
            "...Jy5g3yF6m2f8cP9sJViLWA/JMc8lqkJA" +
            "-----END RSA PRIVATE KEY-----";

    public String encryptToken(String token) {
        try {
            this.encryptor = new RsaSecretEncryptor(this.privateKey);
            return this.encryptor.encrypt(token);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al cifrar el token";
        }
    }

    public String decryptToken(String encryptedToken) {
        try {
            this.encryptor = new RsaSecretEncryptor(this.privateKey);
            return this.encryptor.decrypt(encryptedToken);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al descifrar el token";
        }
    }
}
