package bgroup.ccard.api.Service;

import bgroup.ccard.api.apiInputModel.*;
import bgroup.ccard.api.apiModel.SimpleResponse;
import bgroup.ccard.api.mapper.CardBalanceMapper;
import bgroup.ccard.api.mapper.FioMapper;
import bgroup.ccard.api.mapper.PhoneMapper;
import bgroup.ccard.api.mapper.SurnameMapper;
import bgroup.ccard.api.model.*;
import bgroup.ccard.api.xmlService.XmlApiCleverCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static bgroup.ccard.api.controller.HelpFunctions.*;

/**
 * Created by VSB on 23.04.2019.
 * ccardApi
 */
@Service
public class FioService {
    @Autowired
    FioMapper fioMapper;
    @Autowired
    CardBalanceMapper cardBalanceMapper;
    @Autowired
    CardService cardService;
    @Autowired
    PhoneMapper phoneMapper;
    @Autowired
    SurnameMapper surnameMapper;
    static final Logger logger = LoggerFactory.getLogger(FioService.class);

    public FioModel getFio(PhoneRequest card) {
        String cardNumber = getRightShortNumber(card.getCard_number());
        if (cardNumber != null && cardNumber.length() <= 6) {
            /*CardState cardStateInClever = cardBalanceMapper.findCardSateByNumber(cardNumber);
            if (cardStateInClever != null && (cardStateInClever.getState() == null || cardStateInClever.getState() == 0)) {
                return getFioFromClever(cardNumber);
            } else {
                return getFioFromPc(cardNumber);
            }*/
            return getFioFromPc(cardNumber);
        } else if (cardNumber != null && cardNumber.length() == 8) {
            return getFioFromPc(cardNumber);
        }
        return null;
    }

    private FioModel getFioFromPc(String cardNumber) {
        CardInfo cardInfo = cardService.getCardInfoFromPC(cardNumber);
        //logger.info("cardInfo:{}", cardInfo.toString());
        return new FioModel(cardInfo.getFirstName()
                , cardInfo.getPatronymic()
                , cardInfo.getLastName()
                , null
                , cardInfo.getPhoneNumber()
                , getStringFromDate(cardInfo.getBirthDate(), "yyyy-MM-dd"));
    }

    private FioModel getFioFromClever(String cardNumber) {
        return fioMapper.getFioByCardNumber(cardNumber);
    }

    public Boolean setFio(FioSetRequest fioSetRequest) {
        String cardNumber = getRightShortNumber(fioSetRequest.getCard_number());

        if (cardNumber != null && cardNumber.length() <= 6) {
            /*CardState cardStateInClever = cardBalanceMapper.findCardSateByNumber(cardNumber);
            if (cardStateInClever != null && (cardStateInClever.getState() == null || cardStateInClever.getState() == 0)) {
                return new XmlApiCleverCard().setCardProperties(fioSetRequest);
            } else {
                return setFioInPc(fioSetRequest);
            }*/
            return setFioInPc(fioSetRequest);
        } else if (cardNumber != null && cardNumber.length() == 8) {
            return setFioInPc(fioSetRequest);
        }

        return false;
    }

    private Boolean setFioInPc(FioSetRequest fioSetRequest) {

        return cardService.setFioInPc(fioSetRequest);
    }

    public PhoneModel getPhoneByCardNumber(PhoneRequest card) {
        String cardNumber = getRightShortNumber(card.getCard_number());
        if (cardNumber != null && cardNumber.length() <= 6) {
            /*CardState cardStateInClever = cardBalanceMapper.findCardSateByNumber(cardNumber);
            if (cardStateInClever != null && (cardStateInClever.getState() == null || cardStateInClever.getState() == 0)) {
                return getPhoneFromClever(cardNumber);
            } else {
                return getPhoneFromPc(cardNumber);
            }*/
            return getPhoneFromPc(cardNumber);
        } else if (cardNumber != null && cardNumber.length() == 8) {
            return getPhoneFromPc(cardNumber);
        }
        return null;
    }

    private PhoneModel getPhoneFromPc(String cardNumber) {
        CardInfo cardInfo = cardService.getCardInfoFromPC(cardNumber);
        PhoneModel phoneModel = null;
        if (cardInfo == null) return null;
        phoneModel = new PhoneModel();
        phoneModel.setPhone(cardInfo.getPhoneNumber());
        return phoneModel;
    }

    private PhoneModel getPhoneFromClever(String cardNumber) {
        return phoneMapper.getPhoneByCardNumber(cardNumber);
    }

    public SurnameModel getSurnameByCardNumber(SurnameRequest card) {
        String cardNumber = getRightShortNumber(card.getCard_number());
        if (cardNumber != null && cardNumber.length() <= 6) {
           /* CardState cardStateInClever = cardBalanceMapper.findCardSateByNumber(cardNumber);
            if (cardStateInClever != null && (cardStateInClever.getState() == null || cardStateInClever.getState() == 0)) {
                return getSurnameFromClever(cardNumber);
            } else {
                return getSurnameFromPc(cardNumber);
            }*/
            return getSurnameFromPc(cardNumber);
        } else if (cardNumber != null && cardNumber.length() == 8) {
            return getSurnameFromPc(cardNumber);
        }
        return null;
    }

    private SurnameModel getSurnameFromPc(String cardNumber) {
        CardInfo cardInfo = cardService.getCardInfoFromPC(cardNumber);
        SurnameModel surnameModel = null;
        if (cardInfo == null) return null;
        surnameModel = new SurnameModel();
        surnameModel.setSurname(cardInfo.getLastName());
        return surnameModel;
    }

    private SurnameModel getSurnameFromClever(String cardNumber) {
        return surnameMapper.getSurnameByCardNumber(cardNumber);
    }

    public SimpleResponse registrationLk(RegistrationLkRequest registrationLkRequest) {
        //logger.info(registrationLkRequest.toString());
        String result = cardService.registrationLk(registrationLkRequest);
        //logger.error("result:{}", result);
        SimpleResponse simpleResponse = null;
        if (result != null && result.length() == 0) {
            simpleResponse = new SimpleResponse("ok", null);
        } else if (result == null) {
            simpleResponse = new SimpleResponse("error", "Что-то не так с входными данными");
        } else {
            simpleResponse = new SimpleResponse("error", result);
        }
        return simpleResponse;
    }

    public SimpleResponse authorization(AuthorizationRequest authorizationRequest) {
        String[] result = cardService.authorization(authorizationRequest);
        SimpleResponse simpleResponse = null;
        if (result == null) {
            return simpleResponse = new SimpleResponse("error", "Ошибка входных данных");
        }
        if ((result[0] != null && result[0].length() == 0) || result[0] == null) {
            if (result[1] != null && result[1].length() != 0) {
                simpleResponse = new SimpleResponse("ok", null, result[1]);
            } else {
                simpleResponse = new SimpleResponse("ok", null);
            }
        } else {
            simpleResponse = new SimpleResponse("error", result[0]);
        }
        return simpleResponse;
    }

    public SimpleResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        String[] result = cardService.changePassword(changePasswordRequest);
        SimpleResponse simpleResponse = null;
        if (result == null) {
            return simpleResponse = new SimpleResponse("error", "Ошибка входных данных");
        }
        if ((result[0] != null && result[0].length() == 0) || result[0] == null) {
            simpleResponse = new SimpleResponse("ok", null);
        } else {
            simpleResponse = new SimpleResponse("error", result[0]);
        }
        return simpleResponse;
    }
}
