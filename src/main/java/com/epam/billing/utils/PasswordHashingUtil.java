package com.epam.billing.utils;

import com.epam.billing.exeption.AppException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class PasswordHashingUtil {            //toDo Class need to be refactored according to other has method
    private static final int ITERATIONS = 10;
    private static final int SALT_LENGTH = 16;
    private static final int DESIRED_KEY_LINE = 256;

    public static String getSaltedHash(String password) throws AppException {  //toDo DB 'password' attribute should be varchar more 30 symbols
        byte[] salt = null;
        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(SALT_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            throw new AppException("ERR_CANNOT_GET_SALT", e);
        }
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    private static String hash(String password, byte[] salt) throws AppException {
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey key = f
                    .generateSecret(new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, DESIRED_KEY_LINE));
            return Base64.encodeBase64String(key.getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AppException("ERR_CANNOT_GET_HASH", e);
        }
    }

    public static boolean check(String password, String stored) throws AppException {

        String[] saltAndPass = stored.split("\\$");
        if (saltAndPass.length != 2) {
            throw new AppException("The stored password haven't form 'salt$hash'");
        }
        String inputHash = hash(password, Base64.decodeBase64(saltAndPass[0]));
        return inputHash.equals(saltAndPass[1]);
    }
}
