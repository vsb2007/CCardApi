package bgroup.ccard.api.apiInputModel;


public class RegistrationLkRequest {
    private String card_number;
    private String phone;

    public RegistrationLkRequest() {
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "RegistrationLkRequest{" +
                "card_number='" + card_number + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
