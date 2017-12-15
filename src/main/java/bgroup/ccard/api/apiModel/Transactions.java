package bgroup.ccard.api.apiModel;

import bgroup.ccard.api.model.CardTransactions;

import java.util.List;

/**
 * Created by VSB on 12.09.2017.
 * ccardApi
 */
public class Transactions {
    private String status;
    private String error_message;
    private List<CardTransactions> transactions;

    public Transactions() {
    }

    public Transactions(String status, String error_message, List<CardTransactions> transactions) {
        this.status = status;
        this.error_message = error_message;
        this.transactions = transactions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public List<CardTransactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<CardTransactions> transactions) {
        this.transactions = transactions;
    }
}
