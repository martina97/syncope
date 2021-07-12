package org.apache.syncope.core.provisioning.api.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.syncope.common.lib.SyncopeConstants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@RunWith(Parameterized.class)
public class MyParseDateFormatUtilsTest {
    private static final Calendar calendar = Calendar.getInstance();
    private static final Date date = calendar.getTime();

    private boolean expectedResult;
    private String source;
    private String conversionPattern;

    public MyParseDateFormatUtilsTest(boolean expectedResult, String source, String conversionPattern){
        this.expectedResult = expectedResult;
        this.source = source;
        this.conversionPattern = conversionPattern;
    }

    @Parameterized.Parameters
    public static Collection<?> getParameter() {


        return Arrays.asList(new Object[][] {

                {true, new SimpleDateFormat("dd-MM-yyyy").format(date), "dd-MM-yyyy"} ,  //costanti di default
                {false, "aaa", "dd-MM-yyyy"} ,

                {false, new SimpleDateFormat("dd-MM-yyyy").format(date),SyncopeConstants.DEFAULT_DATE_PATTERN } , //uso un pattern per creare la data e un altro per fare il parse

               // {false, new SimpleDateFormat("dd-MM-yyyy").format(date), ""} ,  //ritorna ParseException: Unable to parse the date: 06-07-2021
                {false, new SimpleDateFormat("dd-MM-yyyy").format(date), null} ,
                {false, null, null},
                {false, new SimpleDateFormat("dd-MM-yyyy").format(date), "aaa"} , //ritorna IllegalArgumentException: Format 'c' not supported

        });
    }


    @Test
    public void parseDateTest(){

        try {
            Assert.assertEquals(DateUtils.parseDate(source,conversionPattern), FormatUtils.parseDate(source, conversionPattern));
        } catch (ParseException e) {
            //e.printStackTrace();

            Assert.assertEquals(e.getMessage(), "Unable to parse the date: " + source);
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();

            if (conversionPattern != "ciao") {
                Assert.assertEquals(e.getMessage(), "Date and Patterns must not be null");
            }
            else {
                Assert.assertEquals(e.getMessage(), "Format '" +conversionPattern.charAt(0) + "' not supported");
            }

        } catch (NullPointerException e) {
            //e.printStackTrace();
            Assert.assertEquals(expectedResult, false);
            // ci entro quando il pattern e' null
            System.out.println();
        }

    }

}