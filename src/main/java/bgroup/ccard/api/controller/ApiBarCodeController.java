package bgroup.ccard.api.controller;

import bgroup.ccard.api.apiInputModel.BalanceRequest;
import bgroup.ccard.api.apiInputModel.BarCodeRequest;
import bgroup.ccard.api.apiModel.BarCode;
import bgroup.ccard.api.mapper.BarCodeMapper;
import bgroup.ccard.api.model.BarCodeDetails;
import bgroup.ccard.api.model.RC5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static bgroup.ccard.api.controller.HelpFunctions.getRightShortNumber;

/**
 * Created by VSB on 11.10.2017.
 * ccardApi
 */
@RestController
@RequestMapping("api/user/barcode")
public class ApiBarCodeController {
    @Autowired
    BarCodeMapper barCodeMapper;

    @RequestMapping(method = RequestMethod.POST)
    public BarCode getBarCode(@RequestBody BarCodeRequest card) {
        String cardNumber = getRightShortNumber(card.getCard_number());
        BarCode nullBarCode = new BarCode("error", "barcode not found", null);
        if (barCodeMapper == null) return nullBarCode;
        BarCodeDetails barCodeDetails = null;
        try {
            barCodeDetails = barCodeMapper.getBarCodeDetailsByNumber(cardNumber);
        } catch (Exception e) {
            return nullBarCode;
        }
        if (barCodeDetails == null) return nullBarCode;
        RC5 rc5 = new RC5(cardNumber);
        String barCode = rc5.getEncryptBarCodeString(barCodeDetails);
        if (barCode == null) return nullBarCode;

        return new BarCode("ok", null, barCode);
    }
}