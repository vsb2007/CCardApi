package bgroup.ccard.api.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by VSB on 11.10.2017.
 * ccardApi
 */
public class RC5 {
    static final Logger logger = LoggerFactory.getLogger(RC5.class);
    private static final int w = 32;
    private static final int r = 12;
    private static final int b = 16;
    private static final int c = 4;
    private static final int t = 26;

    private long[] S = new long[t];
    private long P = 0xb7e15163L;
    private long Q = 0x9e3779b9L;

    private String cardNumber;

    private int[] snc_key = {0x21, 0xF4, 0x53, 0xA1, 0x9D, 0x02, 0x7C, 0x80, 0xBB, 0x46, 0x14, 0x6E, 0xA5, 0x38, 0x69, 0xD0};

    public RC5(String cardNumber) {
        this.cardNumber = cardNumber;
        setup(snc_key);
    }

    private void encrypt(long[] pt, long[] ct) {
        long i;
        long A = (pt[0] + S[0]) & 0xFFFFFFFFL;
        long B = (pt[1] + S[1]) & 0xFFFFFFFFL;

        for (i = 1; i <= r; i++) {
            A = ((((A ^ B) << (B & (w - 1))) | ((A ^ B) >>> (w - (B & (w - 1))))) + S[(int) (2 * i)]) & 0xFFFFFFFFL;
            B = ((((B ^ A) << (A & (w - 1))) | ((B ^ A) >>> (w - (A & (w - 1))))) + S[(int) (2 * i) + 1]) & 0xFFFFFFFFL;
        }
        ct[0] = A & 0xFFFFFFFFL;
        ct[1] = B & 0xFFFFFFFFL;
    }

    public void setup(int[] K) {
        long i, j, k, u = w / 8, A, B;
        long[] L = new long[c];

        for (i = b - 1, L[c - 1] = 0; i != -1; i--) {
            L[(int) (i / u)] = ((L[(int) (i / u)] << 8) + K[(int) i]);
        }

        for (S[0] = P, i = 1; i < t; i++) {
            S[(int) i] = (S[(int) (i - 1)] + Q) & 0xFFFFFFFFL;
        }

        for (A = B = i = j = k = 0; k < 3 * t; k++, i = (i + 1) % t, j = (j + 1) % c) {
            A = S[(int) i] = ((((S[(int) i] + (A + B) & 0xFFFFFFFFL) << (3 & (w - 1))) | ((S[(int) i] + (A + B) & 0xFFFFFFFFL) >>> (w - (3 & (w - 1)))))) & 0xFFFFFFFFL;
            B = L[(int) j] = ((((L[(int) j] + (A + B) & 0xFFFFFFFFL) << ((A + B) & (w - 1))) | ((L[(int) j] + (A + B) & 0xFFFFFFFFL) >>> (w - ((A + B) & (w - 1)))))) & 0xFFFFFFFFL;
        }
    }

    private long[] convertBAArrayToLongArray(byte[] input) {
        long[] ret = new long[(input.length + 3) / 4];
        int n = 0;
        for (int i = 0; i < input.length; i++) {
            n = (input.length - i - 1) / 4;
            ret[n] = ret[n] << 8;
            ret[n] = ret[n] | (long) (input[i] & 0xff);
        }
        return ret;
    }

    private byte[] convertLongArrayToBAArray(long[] input) {
        byte[] ret = new byte[input.length * 4];
        int n = 0;
        for (int i = 0; i < (input.length * 4); i++) {
            n = i / 4;
            ret[input.length * 4 - i - 1] = (byte) (input[n] & 0xff);
            input[n] = input[n] >> 8;
        }
        return ret;
    }

    public String getEncryptBarCodeString(BarCodeDetails barCodeDetails) {
        //logger.info("cardnumber: {}", this.cardNumber);
        if (barCodeDetails == null) return null;
        StringBuilder cardBulder = new StringBuilder();
        cardBulder.append(String.format("%8s", barCodeDetails.getCardNumber()).replace(' ', '0'));
        cardBulder.append(String.format("%4s", barCodeDetails.getOrgCode()).replace(' ', '0'));
        cardBulder.append(String.format("%6s", barCodeDetails.getClientCode()).replace(' ', '0'));
        cardBulder.append("11");
        //logger.info("barString: {}", cardBulder.toString());
        String strCard = cardBulder.toString();
        BigInteger vIn = null;
        try {
            vIn = new BigInteger(strCard);
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
        byte[] bytes = Arrays.copyOf(vIn.toByteArray(), 8);
        long[] dataOut = new long[2];
        encrypt(convertBAArrayToLongArray(bytes), dataOut);
        BigInteger vOut = new BigInteger(1, convertLongArrayToBAArray(dataOut));
        String strCardOut = String.format("%20s", vOut.toString()).replace(' ', '0');
        String lunNumber = getLunNumber(cardBulder.toString());
        if (lunNumber == null) {
            return null;
        }
        return strCardOut + lunNumber;
    }

    public static String getLunNumber(String card) {
        if (card == null)
            return null;
        String digit;
        /* convert to array of int for simplicity */
        int[] digits = new int[card.length()];
        for (int i = 0; i < card.length(); i++) {
            digits[i] = Character.getNumericValue(card.charAt(i));
        }

        /* double every other starting from right - jumping from 2 in 2 */
        for (int i = digits.length - 1; i >= 0; i -= 2) {
            digits[i] += digits[i];

            /* taking the sum of digits grater than 10 - simple trick by substract 9 */
            if (digits[i] >= 10) {
                digits[i] = digits[i] - 9;
            }
        }
        int sum = 0;
        for (int i = 0; i < digits.length; i++) {
            sum += digits[i];
        }
        /* multiply by 9 step */
        sum = sum * 9;

        /* convert to string to be easier to take the last digit */
        digit = sum + "";
        return digit.substring(digit.length() - 1);
    }
}

