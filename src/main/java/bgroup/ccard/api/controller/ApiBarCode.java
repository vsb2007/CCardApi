package bgroup.ccard.api.controller;

import bgroup.ccard.api.apiModel.Balance;
import bgroup.ccard.api.apiModel.BarCode;
import bgroup.ccard.api.mapper.BarCodeMapper;
import bgroup.ccard.api.model.BarCodeDetails;
import bgroup.ccard.api.model.RC5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static bgroup.ccard.api.controller.HelpFunctions.getRightShortNumber;

/**
 * Created by VSB on 11.10.2017.
 * ccardApi
 */
@RestController
@RequestMapping("api/user/barcode")
public class ApiBarCode {
    @Autowired
    BarCodeMapper barCodeMapper;

    @RequestMapping(method = RequestMethod.POST)
    public BarCode getBarCode(@RequestParam(value = "card_number", defaultValue = "null") String cardNumber) {
        cardNumber = getRightShortNumber(cardNumber);

        if (barCodeMapper == null) return null;
        BarCodeDetails barCodeDetails = barCodeMapper.getBarCodeDetailsByNumber(cardNumber);
        RC5 rc5 = new RC5(cardNumber);
        String barCode = rc5.getEncryptBarCodeString(barCodeDetails);
        if (barCode == null) return new BarCode("error", "barcode not found", null);

        return new BarCode("ok", null, barCode);
    }
}
