package bgroup.ccard.api.apiModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Balance {
    static final Logger logger = LoggerFactory.getLogger(Balance.class);

    private String status;
    private String error_message;
    private Double balance;

    public Balance(String status, String error_message, Double balance) {
        this.status = status;
        this.error_message = error_message;
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public String getError_message() {
        return error_message;
    }

    public Double getBalance() {
        return balance;
    }
}
