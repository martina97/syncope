package org.apache.syncope.core.provisioning.api.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@RunWith(Parameterized.class)
public class MyParseNumberUtilsTest {
    private static final Calendar calendar = Calendar.getInstance();

    private static final Date date = calendar.getTime();

    private boolean expectedResult;
    private String source;
    private String conversionPattern;

    public MyParseNumberUtilsTest(boolean expectedResult, String source, String conversionPattern){
        this.expectedResult = expectedResult;
        this.source = source;
        this.conversionPattern = conversionPattern;
    }

    @Parameterized.Parameters
    public static Collection<?> getParameter() {

        return Arrays.asList(new Object[][] {


                //output = stringa corretta
                { true, "12345", "###,###"}, //ok


               // { false, " ", "###,###"}, //java.text.ParseException: Unparseable number: ""
                { false, "aaa123", "###,###"}, //java.text.ParseException: Unparseable number: "aaa123"
               // { false, null, "###,###"}, //java.lang.NullPointerException

                { false, "12345", "invalidPattern"},  //java.text.ParseException: Unparseable number: "12345"
                //{ true, "", "invalidPattern"}, //java.text.ParseException: Unparseable number: ""
                //{ true, "ciao", "invalidPattern"}, //java.text.ParseException: Unparseable number: "ciao"
                //{ true, null, "invalidPattern"}, //java.lang.NullPointerException --> posso toglierlo

                //danno tutte NullPointerException, quindi ne basta 1
               // { false, String.valueOf(date.getTime()), null},
                //{ true, "", null},
                //{ true, "ciao", null},
                { false, null, null},
        });
    }


    @Test
    public void parseNumberTest(){
        boolean result;

        try {
            Number number = FormatUtils.parseNumber(source,conversionPattern);
            result = number.equals(Long.valueOf(source));
            System.out.println("number == " + number);
            System.out.println("equals == " + Long.valueOf(source));
        } catch (Exception e) {
            //e.printStackTrace();
            result = false;
        }
        Assert.assertEquals(expectedResult,result);
    }

}