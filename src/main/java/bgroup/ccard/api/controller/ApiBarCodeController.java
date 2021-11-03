package bgroup.ccard.api.controller;

import bgroup.ccard.api.Service.BarCodeService;
import bgroup.ccard.api.apiInputModel.BalanceRequest;
import bgroup.ccard.api.apiInputModel.BarCodeRequest;
import bgroup.ccard.api.apiModel.BarCode;
import bgroup.ccard.api.mapper.BarCodeMapper;
import bgroup.ccard.api.model.BarCodeDetails;
import bgroup.ccard.api.model.RC5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    static final Logger logger = LoggerFactory.getLogger(ApiBarCodeController.class);

    @Autowired
    BarCodeService barCodeService;

    @RequestMapping(method = RequestMethod.POST)
    public BarCode getBarCode(@RequestBody BarCodeRequest card) {
        String barCode = barCodeService.getBarCodeString(card);
        BarCode nullBarCode = new BarCode("error", "barcode not found", null);
        if (barCode == null) return nullBarCode;
        return new BarCode("ok", null, barCode);
    }
}
