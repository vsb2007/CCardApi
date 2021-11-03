package bgroup.ccard.api.model;

import java.util.Date;

/**
 * Created by VSB on 16.10.2017.
 * ccardApi
 */
public class FioModel {
    private String name;
    private String second_name;
    private String last_name;
    private String email;
    private String phone;
    private String birthday;

    public FioModel(String name, String second_name, String last_name, String email, String phone, String birthday) {
        this.name = name;
        this.second_name = second_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public FioModel() {
    }

    @Override
    public String toString() {
        return "FioModel{" +
                "name='" + name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}

