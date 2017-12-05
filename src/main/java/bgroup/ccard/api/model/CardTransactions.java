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

    public CardTransactions(Integer id, Integer idTr, Date dateDate, String operation, Double amount, String station) {
        this.id = id;
        this.idTr = idTr;
        this.date = dateDate;
        this.operation = operation;
        this.amount = amount;
        this.station = station;
    }

    public CardTransactions() {
    }

    public String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(date);
    }

    public Integer getId() {
        return id;
    }

    public String getOperation() {
        return operation;
    }

    public Double getAmount() {
        return amount;
    }

    public String getStation() {
        return station;
    }

    public Integer getIdTr() {
        return idTr;
    }

    public void setIdTr(Integer idTr) {
        this.idTr = idTr;
    }
}
