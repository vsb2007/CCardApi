package bgroup.ccard.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VSB on 11.10.2017.
 * ccardApi
 */
public class HelpFunctions {
    public static String getRightShortNumber(String cardNumber) {
        if (cardNumber == null) return null;
        //char[] card = cardNumber.toCharArray();
        String card = null;
        if (cardNumber.length() == 8) {
            if (!cardNumber.substring(0, 2).equals("01")) return null;
            card = cardNumber.substring(2, 8);
            return getNumberString(card);
        } else if (cardNumber.length() == 9) {
            card = cardNumber.substring(2, 9);
            return getNumberString(card);
        } else if (cardNumber.length() == 10) {
            card = cardNumber.substring(2, 10);
            return getNumberString(card);
        }
        return null;
    }

    private static String getNumberString(String card) {
        try {
            int i = Integer.parseInt(card);
            return i + "";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFromDate(Date date, String pattern) {
        if (date == null || pattern == null) return null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        } catch (Exception e) {

        }
        return null;
    }

    public static Date getDateFromString(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateString);
        } catch (Exception e) {
        }
        return null;
    }
}
