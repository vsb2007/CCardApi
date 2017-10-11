package bgroup.ccard.api.apiModel;

/**
 * Created by VSB on 11.10.2017.
 * ccardApi
 */
public class BarCode {
    private String status;
    private String error_message;
    private String barcode;

    public BarCode(String status, String error_message, String barcode) {
        this.status = status;
        this.error_message = error_message;
        this.barcode = barcode;
    }

    public String getStatus() {
        return status;
    }

    public String getError_message() {
        return error_message;
    }

    public String getBarcode() {
        return barcode;
    }
}
