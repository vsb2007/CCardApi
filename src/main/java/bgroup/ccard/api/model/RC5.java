package bgroup.ccard.api.model;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;

/**
 * Created by VSB on 13.09.2017.
 * ccardApi
 */
import javax.crypto.spec.*;
import java.security.*;
import javax.crypto.*;


public class RC5 {
    private static String algorithm = "RC5";

    public static void main(String[] args) throws Exception {
        String cardNumber = "0100123456";
        String card = cardNumber.substring(2, 10);
        int i = Integer.parseInt(card);
        System.out.println(card + " " + i);
    }
}
