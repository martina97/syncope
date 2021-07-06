package org.apache.syncope.core.provisioning.api.utils;

import org.apache.syncope.common.lib.SyncopeConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class provaFormatUtils {
    public static void main(String[] args) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String source = String.valueOf(date.getTime());
        Number number = FormatUtils.parseNumber(null,null);
        //System.out.println("source == " + source);
        System.out.println("number == " + number);


    }
}
