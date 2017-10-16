package bgroup.ccard.api.controller;

import bgroup.ccard.api.apiInputModel.PhoneRequest;
import bgroup.ccard.api.apiModel.Phone;
import bgroup.ccard.api.mapper.PhoneMapper;
import bgroup.ccard.api.model.PhoneModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static bgroup.ccard.api.controller.HelpFunctions.getRightShortNumber;

/**
 * Created by VSB on 11.10.2017.
 * ccardApi
 */
@RestController
@RequestMapping("api/user/phone")
public class ApiPhoneController {
    @Autowired
    PhoneMapper phoneMapper;

    @RequestMapping(method = RequestMethod.POST)
    public Phone getPhone(@RequestBody PhoneRequest card) {
        String cardNumber = getRightShortNumber(card.getCard_number());
        Phone nullPhone = new Phone("error", "phone not found", null);
        if (phoneMapper == null) return nullPhone;
        PhoneModel phoneModel = null;
        try {
            phoneModel = phoneMapper.getPhoneByCardNumber(cardNumber);
        } catch (Exception e) {
            return nullPhone;
        }
        if (phoneModel == null) return nullPhone;
        if (phoneModel.getPhone() == null || phoneModel.getPhone().equals("")) return nullPhone;

        return new Phone("ok", null, phoneModel.getPhone());
    }
}
