package bgroup.ccard.api.model;

/**
 * Created by VSB on 11.10.2017.
 * ccardApi
 */
public class BarCodeDetails {
    private String cardNumber;
    private String orgCode;
    private String clientCode;

    public BarCodeDetails() {
    }

    public BarCodeDetails(String cardNumber, String orgCode, String clientCode) {
        this.cardNumber = cardNumber;
        this.orgCode = orgCode;
        this.clientCode = clientCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    @Override
    public String toString() {
        return "BarCodeDetails{" +
                "cardNumber='" + cardNumber + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", clientCode='" + clientCode + '\'' +
                '}';
    }
}
