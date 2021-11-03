package bgroup.ccard.api.model;

/**
 * Created by VSB on 11.09.2017.
 * ccardApi
 */
public class CardState {
    private String cardNumber;
    private Integer state;

    public CardState(String cardNumber, Integer state) {
        this.cardNumber = cardNumber;
        this.state = state;
    }

    public CardState() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
