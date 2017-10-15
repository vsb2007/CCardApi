package bgroup.ccard.api.apiInputModel;

/**
 * Created by VSB on 15.10.2017.
 * ccardApi
 */
public class TransactionsRequest {
    private String card_number;
    private String start_date;
    private String end_date;
    private String station;

    public TransactionsRequest() {
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
