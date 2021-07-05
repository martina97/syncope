package org.apache.syncope.core.provisioning.api.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.syncope.common.lib.SyncopeConstants;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class MyParseDateFormatUtilsTest {
    //private final Calendar calendar = Calendar.getInstance();
    private static final Calendar calendar = Calendar.getInstance();
    //public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

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

        //Calendar calendar = Calendar.getInstance();

        return Arrays.asList(new Object[][] {

                {true, new SimpleDateFormat("dd-MM-yyyy").format(date), "dd-MM-yyyy"} ,  //costanti di default
                {true, "ciao", "dd-MM-yyyy"} ,
                {true, null, "dd-MM-yyyy"},
                {true, "", "dd-MM-yyyy"} ,
                {true, new SimpleDateFormat(SyncopeConstants.DEFAULT_DATE_PATTERN).format(date),SyncopeConstants.DEFAULT_DATE_PATTERN },
                {true, new SimpleDateFormat("dd-MM-yyyy").format(date),SyncopeConstants.DEFAULT_DATE_PATTERN } , //uso un pattern per creare la data e un altro per fare il parse


                {true, new SimpleDateFormat("dd-MM-yyyy").format(date), ""} ,  //costanti di default
                {true, new SimpleDateFormat("dd-MM-yyyy").format(date), null} ,  //costanti di default
                {true, new SimpleDateFormat("dd-MM-yyyy").format(date), "ciao"} ,  //costanti di default

        });
    }

    @Before
    public void before(){
        //this.cache = new ReadCache(UnpooledByteBufAllocator.DEFAULT, 10 * 1024);
    }
    @After
    public void after(){
        //cache.close();
    }


    @Test
    public void formatDateTest(){


        try {
            Assert.assertEquals(DateUtils.parseDate(source,conversionPattern), FormatUtils.parseDate(source, conversionPattern));
        } catch (ParseException e) {
            e.printStackTrace();

            Assert.assertEquals(e.getMessage(), "Unable to parse the date: " + source);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            if (conversionPattern != "ciao") {
                Assert.assertEquals(e.getMessage(), "Date and Patterns must not be null");
            }
            else {
                Assert.assertEquals(e.getMessage(), "Format '" +conversionPattern.charAt(0) + "' not supported");
            }

        } catch (NullPointerException e) {
            Assert.assertEquals(e.getMessage(), null);
            // ci entro quando il pattern e' null
            System.out.println();
        }


    }

}