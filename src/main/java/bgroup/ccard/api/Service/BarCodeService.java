package bgroup.ccard.api.Service;

import bgroup.ccard.api.apiInputModel.BarCodeRequest;
import bgroup.ccard.api.mapper.BarCodeMapper;
import bgroup.ccard.api.mapper.CardBalanceMapper;
import bgroup.ccard.api.model.BarCodeDetails;
import bgroup.ccard.api.model.CardInfo;
import bgroup.ccard.api.model.CardState;
import bgroup.ccard.api.model.RC5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static bgroup.ccard.api.controller.HelpFunctions.getRightShortNumber;

/**
 * Created by VSB on 23.04.2019.
 * ccardApi
 */
@Service
public class BarCodeService {
    @Autowired
    BarCodeMapper barCodeMapper;
    @Autowired
    CardBalanceMapper cardBalanceMapper;
    @Autowired
    CardService cardService;

    static final Logger logger = LoggerFactory.getLogger(BarCodeService.class);

    public String getBarCodeString(BarCodeRequest card) {
        String cardNumber = getRightShortNumber(card.getCard_number());
        if (cardNumber != null && cardNumber.length() <= 6) {
            /*CardState cardStateInClever = cardBalanceMapper.findCardSateByNumber(cardNumber);
            if (cardStateInClever != null && (cardStateInClever.getState() == null || cardStateInClever.getState() == 0)) {
                return getBarCodeStringFromClever(cardNumber);
            } else {
                return getBarCodeStringFromPc(cardNumber);
            }*/
            return getBarCodeStringFromPc(cardNumber);
        } else if (cardNumber != null && cardNumber.length() == 8) {
            return getBarCodeStringFromPc(cardNumber);
        }
        return null;
    }

    private String getBarCodeStringFromPc(String cardNumber) {
        CardInfo cardInfo = cardService.getCardInfoFromPC(cardNumber);
        if (cardInfo == null) return null;
        BarCodeDetails barCodeDetails = new BarCodeDetails();
        barCodeDetails.setCardNumber(cardNumber);
        barCodeDetails.setClientCode(cardInfo.getClientCode());
        barCodeDetails.setOrgCode(cardInfo.getOrgCode());
        return getBarCode(barCodeDetails);
    }

    public String getBarCodeStringFromClever(String cardNumber) {
        if (barCodeMapper == null) return null;
        BarCodeDetails barCodeDetails = null;
        try {
            barCodeDetails = barCodeMapper.getBarCodeDetailsByNumber(cardNumber);
        } catch (Exception e) {
            return null;
        }
        if (barCodeDetails == null) return null;
        return getBarCode(barCodeDetails);
    }

    private String getBarCode(BarCodeDetails barCodeDetails) {
        logger.debug("BarCodeDetails:{}",barCodeDetails.toString());
        RC5 rc5 = null;
        try {
            rc5 = new RC5(barCodeDetails.getCardNumber());
        } catch (Exception e) {
            return null;
        }
        String barCode = rc5.getEncryptBarCodeString(barCodeDetails);
        return barCode;
    }
}
