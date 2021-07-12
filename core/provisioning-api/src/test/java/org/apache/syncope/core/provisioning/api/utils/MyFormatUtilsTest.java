package org.apache.syncope.core.provisioning.api.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@RunWith(Parameterized.class)
public class MyFormatUtilsTest {
    private static final Calendar calendar = Calendar.getInstance();

    private boolean expectedResult;
    private Date date;
    private boolean lenient;
    private String conversionPattern;

    public MyFormatUtilsTest(boolean expectedResult, Date date, boolean lenient, String conversionPattern){
        this.expectedResult = expectedResult;
        this.date = date;
        this.lenient = lenient;
        this.conversionPattern = conversionPattern;
    }

    @Parameterized.Parameters
    public static Collection<?> getParameter() {

        return Arrays.asList(new Object[][] {

                //output = stringa corretta
                { true, calendar.getTime(), true, "dd/MM/yyyy"},
                //{ true, calendar.getTime(), false, "dd/MM/yyyy"},

                //IllegalArgumentException perche' il pattern non e' valido
                { false, calendar.getTime(), false, "invalidPattern"},
                //{ false, calendar.getTime(), false, "invalidPattern"},

                //output = stringa non parsata, quindi del tipo : 2021-07-03T14:58:31+0200
                // format non da errore se il pattern e' null, semplicemente non si effettua il format della data
                { true, calendar.getTime(), true, null},

                //NullPointerException a causa della data, quindi ne basta solo uno
                {false, null, true, "dd/MM/yyyy"},

                //{false, null, false, "dd/MM/yyyy"},

                //IllegalArgumentException --> pattern non valido
               // {false, null, true, "invalidPattern"},
               // {false, null, true, "invalidPattern"},

                //NullPointerException dovuto alla data, perche' pattern null va bene --> non servono questi parametri, perche' testo gia'
                // il caso in cui il pattern e' null
                //{true, null, true, null},
                //{true, null, true, null},



        });
    }


    @Test
    public void formatTest(){


        boolean result;

        String formatDate;


        if (conversionPattern == null) {
            System.out.println("sono quaaa");
            // se il pattern e' null, la funzione non fallisce ma ritorna 2021-07-03T16:04:21+0200
            formatDate = FormatUtils.format(date, lenient, conversionPattern);
            result = formatDate.equals(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(date));

        }
        else {
            try {
                formatDate = FormatUtils.format(date, lenient, conversionPattern);
                result = formatDate.equals(new SimpleDateFormat(conversionPattern).format(date));
            }
            catch (Exception e) {
                //e.printStackTrace();
                result = false;
            }

        }
        Assert.assertEquals(expectedResult, result);

    }

}