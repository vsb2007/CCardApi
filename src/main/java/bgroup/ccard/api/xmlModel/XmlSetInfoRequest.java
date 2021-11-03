package bgroup.ccard.api.xmlModel;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by VSB on 25.01.2018.
 * cleverClients
 */
public class XmlSetInfoRequest {
    static final Logger logger = LoggerFactory.getLogger(XmlSetInfoRequest.class);
    /**
     * CARD_NUM=1234567
     * TYPE=setinfo
     * KEY=MD5(CARD_NUM + SECRET_WORD)
     * CARD_NUM,FNAME,MNAME,LNAME,BDATE,PHONE
     */
    private String cardNumber;
    private String fName;
    private String mName;
    private String lName;
    private String bDate;
    private String phone;
    private Integer sex;
    private String type;
    private String key;

    public XmlSetInfoRequest() {
    }

    public XmlSetInfoRequest(String cardNumber, String fName, String mName, String lName, String bDate, String phone, Integer sex, String type, String key) {
        this.cardNumber = cardNumber;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.bDate = bDate;
        this.phone = phone;
        this.sex = sex;
        this.type = type;
        this.key = key;
    }

    @Override
    public String toString() {
        return "XmlSetInfoRequest{" +
                "cardNumber='" + cardNumber + '\'' +
                ", fName='" + fName + '\'' +
                ", mName='" + mName + '\'' +
                ", lName='" + lName + '\'' +
                ", bDate='" + bDate + '\'' +
                ", phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public String toXmlString() {
        return null;
    }

    //CARD_NUM,FNAME,MNAME,LNAME,BDATE,PHONE
    public String toRequestString() {

        /*
                return "CARD_NUM=" + cardNumber

                + "&TYPE=" + type
                + "&KEY=" + DigestUtils.md5Hex(cardNumber + key)
                + "&FNAME=" + ((fName != null) ? fName : "")
                + "&MNAME=" + ((mName != null) ? mName : "")
                + "&LNAME=" + ((lName != null) ? lName : "")
                + "&BDATE=" + ((bDate != null) ? bDate : "")
                + "&PHONE=" + ((phone != null) ? phone : "");
                 */
        StringBuilder str = new StringBuilder();
        str.append("CARD_NUM=" + cardNumber);
        str.append("&TYPE=" + type);
        str.append("&KEY=" + DigestUtils.md5Hex(cardNumber + key));
        if (fName != null && !fName.equals("")) {
            str.append("&FNAME=" + fName);
        }
        if (mName != null && !mName.equals("")) {
            str.append("&MNAME=" + mName);
        }
        if (lName != null && !lName.equals("")) {
            str.append("&LNAME=" + lName);
        }
        if (bDate != null && !bDate.equals("")) {
            str.append("&BDATE=" + bDate);
        } else {
            str.append("&BDATE=1970-01-01");
        }

        if (phone != null && !phone.equals(""))
            str.append("&PHONE=" + phone);
        if (sex != null)
            str.append("&SEX=" + sex);

        return str.toString();
    }
}
