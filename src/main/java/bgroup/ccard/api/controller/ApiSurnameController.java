package bgroup.ccard.api.controller;

import bgroup.ccard.api.Service.FioService;
import bgroup.ccard.api.apiInputModel.SurnameRequest;
import bgroup.ccard.api.apiModel.Surname;
import bgroup.ccard.api.mapper.SurnameMapper;
import bgroup.ccard.api.model.SurnameModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static bgroup.ccard.api.controller.HelpFunctions.getRightShortNumber;

/**
 * Created by VSB on 11.10.2017.
 * ccardApi
 */
@RestController
@RequestMapping("api/user/surname")
public class ApiSurnameController {
    @Autowired
    FioService fioService;

    @RequestMapping(method = RequestMethod.POST)
    public Surname getSurname(@RequestBody SurnameRequest surname) {
        Surname nullSurname = new Surname("error", "surname not found", null);
        SurnameModel surnameModel = null;
        try {
            surnameModel = fioService.getSurnameByCardNumber(surname);
        } catch (Exception e) {
            return nullSurname;
        }
        if (surnameModel == null) return nullSurname;
        if (surnameModel.getSurname() == null || surnameModel.getSurname().equals("")) return nullSurname;

        return new Surname("ok", null, surnameModel.getSurname());
    }
}
