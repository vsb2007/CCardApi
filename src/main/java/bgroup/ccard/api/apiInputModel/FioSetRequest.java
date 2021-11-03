package bgroup.ccard.api.apiInputModel;

import java.util.Date;

/**
 * Created by VSB on 15.10.2017.
 * ccardApi
 */
public class FioSetRequest {

    private String last_name;
    private String name;
    private String second_name;
    private Date birthday;
    private Integer sex;
    private String phone;
    private String email;
    private String vendorAuto;
    private String card_number;
    private Date dateFill;
    private String operatorNameCardOut;
    private Integer azs;

    public FioSetRequest() {
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVendorAuto() {
        return vendorAuto;
    }

    public void setVendorAuto(String vendorAuto) {
        this.vendorAuto = vendorAuto;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public Date getDateFill() {
        return dateFill;
    }

    public void setDateFill(Date dateFill) {
        this.dateFill = dateFill;
    }

    public String getOperatorNameCardOut() {
        return operatorNameCardOut;
    }

    public void setOperatorNameCardOut(String operatorNameCardOut) {
        this.operatorNameCardOut = operatorNameCardOut;
    }

    public Integer getAzs() {
        return azs;
    }

    public void setAzs(Integer azs) {
        this.azs = azs;
    }
}
