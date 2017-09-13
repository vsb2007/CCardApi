package bgroup.ccard.api.model;

/**
 * Created by VSB on 11.09.2017.
 * ccardApi
 */
public class CardBalance {
    private String cardNumber;
    private Double balance;

    public CardBalance(String cardNumber, Double balance) {
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    public CardBalance() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
