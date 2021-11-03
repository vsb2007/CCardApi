package bgroup.ccard.api.model;

import java.util.Date;

/**
 * Created by VSB on 23.04.2019.
 * ccardApi
 */
public class CardInfo {
    private String cardNumber;
    private String orgCode;
    private String clientCode;
    private Double balance;
    private Integer state;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private Date birthDate;
    private Integer sex;

    public CardInfo(String cardNumber, String orgCode, String clientCode, Double balance, Integer state, String firstName, String lastName, String patronymic, String phoneNumber, Date birthDate, Integer sex) {
        this.cardNumber = cardNumber;
        this.orgCode = orgCode;
        this.clientCode = clientCode;
        this.balance = balance;
        this.state = state;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.sex = sex;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


    @Override
    public String toString() {
        return "CardInfo{" +
                "cardNumber='" + cardNumber + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", clientCode='" + clientCode + '\'' +
                ", balance=" + balance +
                ", state=" + state +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", sex=" + sex +
                '}';
    }
}
