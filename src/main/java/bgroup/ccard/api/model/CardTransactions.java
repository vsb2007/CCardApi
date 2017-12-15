package bgroup.ccard.api.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VSB on 12.09.2017.
 * ccardApi
 */
public class CardTransactions {
    private Integer id;
    private Integer idTr;
    private Date date;
    private String operation;
    private Double amount;
    private String station;

    public CardTransactions() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTr() {
        return idTr;
    }

    public void setIdTr(Integer idTr) {
        this.idTr = idTr;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
