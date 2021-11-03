package bgroup.ccard.api.apiInputModel;


public class AuthorizationRequest {
    private String card_number;
    private String user_pass;

    public AuthorizationRequest() {
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    @Override
    public String toString() {
        return "RegistrationLkRequest{" +
                "card_number='" + card_number + '\'' +
                ", user_pass='" + user_pass + '\'' +
                '}';
    }
}
