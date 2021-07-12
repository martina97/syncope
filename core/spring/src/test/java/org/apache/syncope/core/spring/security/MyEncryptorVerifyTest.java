package org.apache.syncope.core.spring.security;

import org.apache.syncope.common.lib.types.CipherAlgorithm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class MyEncryptorVerifyTest {

    private static final Encryptor ENCRYPTOR = Encryptor.getInstance();

    private boolean expectedResult;
    private String password;
    private CipherAlgorithm cipherAlgorithm;
    private String encoded;

    public MyEncryptorVerifyTest(boolean expectedResult, String password, CipherAlgorithm cipherAlgorithm, String encoded){
        this.expectedResult = expectedResult;
        this.password = password;
        this.cipherAlgorithm = cipherAlgorithm;
        this.encoded = encoded;
    }


    public static String encryptPsswd(String value,CipherAlgorithm algo ) {
        String encPassword ;

        try {
            encPassword = ENCRYPTOR.encode(value, algo);

        } catch (Exception e) {
            //e.printStackTrace();
            encPassword = null;
        }
        return encPassword;
    }

    @Parameterized.Parameters
    public static Collection<?> getParameter() {

        return Arrays.asList(new Object[][] {

                //cambio encoded, ossia la criptatura della psswd
                {true, "password", CipherAlgorithm.AES, encryptPsswd("password", CipherAlgorithm.AES) },    //ENCRYPTED PW == 9Pav+xl+UyHt02H9ZBytiA==
                {false, "password", CipherAlgorithm.AES, encryptPsswd("passworddd", CipherAlgorithm.AES) }, //ENCRYPTED PW == tNHxIa2kL3b7Zbf1fRn23Q==
                //{false, "password", CipherAlgorithm.AES, encryptPsswd("", CipherAlgorithm.AES) }, //non serve. cìe' gia quello sopra

                //cambio algoritmo di cifratura
               {true, "password", CipherAlgorithm.BCRYPT, encryptPsswd("password", CipherAlgorithm.BCRYPT) },    //ENCRYPTED PW == $2a$10$wu3QcFg1IaxLZzD6Tlasae2bC7mWFsY2V1HwxPkNK6jxj6VJgBu9u
               {true, "password", CipherAlgorithm.SHA256, encryptPsswd("password", CipherAlgorithm.SHA256) },    //ENCRYPTED PW == $2a$10$wu3QcFg1IaxLZzD6Tlasae2bC7mWFsY2V1HwxPkNK6jxj6VJgBu9u

                {false, null, null, null}
        });
    }




    @Test
    public void encryptorVerifyTest(){

        boolean result;
        System.out.println("ENCRYPTED PW == " + encoded);
        result = ENCRYPTOR.verify(password, cipherAlgorithm, encoded);
        Assert.assertEquals(expectedResult, result);

    }

}