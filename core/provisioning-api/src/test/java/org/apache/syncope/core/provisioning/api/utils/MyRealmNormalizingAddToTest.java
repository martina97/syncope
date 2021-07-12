package org.apache.syncope.core.provisioning.api.utils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;



@RunWith(Parameterized.class)
public class MyRealmNormalizingAddToTest {

    private boolean expectedResult;
    private Set<String> realms;
    private String realm1;
    private String realm2;
    private String newRealm;

    public MyRealmNormalizingAddToTest(boolean expectedResult,String realm1, String realm2, String newRealm){
        this.expectedResult = expectedResult;
        this.realm1 = realm1;
        this.realm2 = realm2;
        this.newRealm = newRealm;
    }

    @Parameterized.Parameters
    public static Collection<?> getParameter() {


        return Arrays.asList(new Object[][] {

                //SE newRealm INIZIA CON UNO DEI DUE REALM, LA FUNZIONE RITORNA FALSE, E QUINDI newRealm NON VIENE AGGIUNTO A realms
                {false, "realm1", "realm2", "realm123"}, // alla fine realms = [realm2, realm1]
                {true, "realm123", "realm2", "realm1"}, //ritorna true, e realm123 viene sostituito da realm1, alla fine realms = [realm2, realm1]
                {false, null, null, null},   //in questo caso vengono sostituiti realm1 e realm2 con "", quindi infine si avra' che realms=[]

                //  {false, "realm1", "realm1", "realm1"},//se ne aggiungo due uguali, realms=[realm1], e la funzione ritorna false perche' newRealm.startsWith(realm1)
                // {false, "realm1", "realm2", "realm1"},   //ritorna false, perche' non viene aggiunto
                //{false, null, "realm2", "realm1"},   //non ha senso metterne uno null e uno no, tanto la funzione normalizingAddTo scorre tutti i realms
                // {false, null, null, "realm1"},   //non ha senso metterne uno null e uno no, tanto la funzione normalizingAddTo scorre tutti i realms
                // {true, "realm1", "realm2", ""},   //in questo caso vengono sostituiti realm1 e realm2 con "", quindi infine si avra' che realms=[]
                // {false, "realm1", "realm2", null},   //in questo caso vengono sostituiti realm1 e realm2 con "", quindi infine si avra' che realms=[]

        });
    }

    @Before
    public void before(){
        realms = new HashSet<>();

        if (realm1 != null & realm2 != null) {
            realms.add(realm1);
            realms.add(realm2);
        }

    }

    @After
    public void after(){
        realms.clear();
    }


    @Test
    public void normalizingAddToTest(){

        // all'inizio ho che realms = {realm2,realm123}, ora cancello realm123 e realms == [realm2, realm1], e la
        // funzione mi restituisce true, il che vuol dire che ho sostituito realm1 a realm123

       boolean result;
        if (!realms.isEmpty() && newRealm != null) {
            result = RealmUtils.normalizingAddTo(realms, newRealm);
            System.out.println(realms);
        }
        else {
            result = false;
        }
        Assert.assertEquals(expectedResult,result);
    }



}