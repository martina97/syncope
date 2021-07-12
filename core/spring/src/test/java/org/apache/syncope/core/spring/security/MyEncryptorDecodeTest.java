package org.apache.syncope.core.spring.security;

import org.apache.syncope.common.lib.types.CipherAlgorithm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MyEncryptorDecodeTest {

    private static final Encryptor ENCRYPTOR = Encryptor.getInstance();

    private static final String PASSWORD_VALUE = "password";


    private boolean expectedResult;
    private String encoded;
    private CipherAlgorithm cipherAlgorithm;

    public MyEncryptorDecodeTest(boolean expectedResult, String encoded, CipherAlgorithm cipherAlgorithm){
        this.expectedResult = expectedResult;
        this.encoded = encoded;
        this.cipherAlgorithm = cipherAlgorithm;
    }


    public static String encryptPsswd(String value,CipherAlgorithm cipherAlgorithm ) {
        String encPassword;
        try {
            encPassword = ENCRYPTOR.encode(value, cipherAlgorithm);

        } catch (Exception e) {
            //e.printStackTrace();
            encPassword = null;
        }
        return encPassword;
    }

    @Parameterized.Parameters
    public static Collection<?> getParameter() {

        return Arrays.asList(new Object[][] {


                //output = stringa corretta
                { true, encryptPsswd("password", CipherAlgorithm.AES), CipherAlgorithm.AES},
                { false, "ciao", CipherAlgorithm.AES}, //javax.crypto.IllegalBlockSizeException: Input length must be multiple of 16 when decrypting with padded cipher
                //{ false, "ciakfodjfnclo==", CipherAlgorithm.AES}, //java.lang.IllegalArgumentException: Last unit does not have enough valid bits

                {true, null, null}, //se ho null, la funzione decode mi restituisce una decryption = null
                {false, encryptPsswd("password", CipherAlgorithm.SHA256), CipherAlgorithm.SHA256}, //per la coverage, fare dopo
                {false, encryptPsswd("password", CipherAlgorithm.BCRYPT ), CipherAlgorithm.BCRYPT }, //per la coverage, fare dopo

        });
    }



    @Test
    public void encryptorDecodeTest(){
        boolean result;

        try {
            if (encoded != null) {
                String decPassword = ENCRYPTOR.decode(encoded,cipherAlgorithm);
                System.out.println("encoded == " + encoded + "\tdecPassw == " + decPassword );
                result = decPassword.equals(PASSWORD_VALUE);
            }
            else {
                String decPassword = ENCRYPTOR.decode(encoded,cipherAlgorithm);
                System.out.println("encoded == " + encoded + "\tdecPassw == " + decPassword );
                result = decPassword == null;
            }

            } catch (Exception e ) {
                //e.printStackTrace();
                result = false;
            }

        Assert.assertEquals(expectedResult,result);
    }

}