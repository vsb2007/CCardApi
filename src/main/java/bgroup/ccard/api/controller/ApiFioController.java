package bgroup.ccard.api.controller;

import bgroup.ccard.api.Service.FioService;
import bgroup.ccard.api.apiInputModel.*;
import bgroup.ccard.api.apiModel.*;
import bgroup.ccard.api.model.FioModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by VSB on 11.10.2017.
 * ccardApi
 */
@RestController
public class ApiFioController {
    static final Logger logger = LoggerFactory.getLogger(ApiFioController.class);

    @Autowired
    FioService fioService;

    @RequestMapping(value = {"api/user/view"}, method = RequestMethod.POST)
    public Fio getFio(@RequestBody PhoneRequest card) {

        Fio nullFio = new Fio("error", "fio not found", null);
        if (fioService == null) return nullFio;
        FioModel fioModel = null;
        try {
            fioModel = fioService.getFio(card);
            //logger.info("fio:{}", fioModel.toString());
        } catch (Exception e) {
            logger.error(e.toString());
            return nullFio;
        }
        if (fioModel == null) return nullFio;

        return new Fio("ok", null, fioModel);
    }

    @RequestMapping(value = {"api/user/edit"}, method = RequestMethod.POST)
    public SimpleResponse setFio(@RequestBody FioSetRequest fioSetRequest) {
        Boolean isSetInfo = fioService.setFio(fioSetRequest);
        if (isSetInfo)
            return new SimpleResponse("0", "ok");
        return new SimpleResponse("-1", "Ошибка");
    }

    @RequestMapping(value = {"api/user/registration"}, method = RequestMethod.POST)
    public SimpleResponse registration(@RequestBody RegistrationLkRequest registrationLkRequest) {
        SimpleResponse result = fioService.registrationLk(registrationLkRequest);
        return result;
    }

    @RequestMapping(value = {"api/user/authorization"}, method = RequestMethod.POST)
    public SimpleResponse authorization(@RequestBody AuthorizationRequest authorizationRequest) {
        SimpleResponse result = fioService.authorization(authorizationRequest);
        return result;
    }

    @RequestMapping(value = {"api/user/changepassword"}, method = RequestMethod.POST)
    public SimpleResponse changepassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        SimpleResponse result = fioService.changePassword(changePasswordRequest);
        return result;
    }
}
