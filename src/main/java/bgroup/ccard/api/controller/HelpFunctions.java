package bgroup.ccard.api.controller;

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
            card = cardNumber.substring(2, 8);
            try {
                int i = Integer.parseInt(card);
                return i + "";
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
