package bgroup.ccard.api.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VSB on 12.09.2017.
 * ccardApi
 */
public class CardTransactions {
    private Integer id;
    private Date date;
    private String operation;
    private Double amount;
    private String station;

    /*
            "date":"1996-12-19T16:39:57-08:00", //rfc3339
                    "operation":"admission",
                    "amount":210,
                    "station":"AZS 31",
                    */


    public CardTransactions(Integer id, Date dateDate, String operation, Double amount, String station) {
        this.id = id;
        //this.date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(dateDate);
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
}
