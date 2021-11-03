package bgroup.ccard.api.apiInputModel;


public class ChangePasswordRequest {
    private String card_number;
    private String detailsKey;
    private String password;

    public ChangePasswordRequest() {
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getDetailsKey() {
        return detailsKey;
    }

    public void setDetailsKey(String detailsKey) {
        this.detailsKey = detailsKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
