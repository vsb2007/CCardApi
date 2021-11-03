package bgroup.ccard.api.xmlModel;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by VSB on 25.01.2018.
 * cleverClients
 */
public class XmlFindRequest {
    /**
     * CARD_NUM=1234567
     * TYPE=find
     * KEY=MD5(CARD_NUM + SECRET_WORD)
     */
    private String cardNumber;
    private String type;
    private String key;

    public XmlFindRequest() {
    }

    public XmlFindRequest(String cardNumber, String type, String key) {
        this.cardNumber = cardNumber;
        this.type = type;
        this.key = key;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "TransactionsRequest{" +
                "cardNumber='" + cardNumber + '\'' +
                ", type='" + type + "'" +
                ", key='" + key + "'" +
                "}";
    }

    public String toXmlString() {
        return null;
    }

    public String toRequestString() {
        return "CARD_NUM=" + cardNumber + "&TYPE=" + type + "&KEY=" + DigestUtils.md5Hex(cardNumber + key);
    }
}
