package bgroup.ccard.api.apiInputModel;

/**
 * Created by VSB on 15.10.2017.
 * ccardApi
 */
public class BalanceRequest {
    private String card_number;

    public BalanceRequest() {
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }
}
