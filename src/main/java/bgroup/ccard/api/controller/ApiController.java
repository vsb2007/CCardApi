package bgroup.ccard.api.controller;

import bgroup.ccard.api.Service.CardService;
import bgroup.ccard.api.apiInputModel.BalanceRequest;
import bgroup.ccard.api.apiInputModel.TransactionsRequest;
import bgroup.ccard.api.apiModel.Balance;
import bgroup.ccard.api.apiModel.Transactions;
import bgroup.ccard.api.mapper.CardBalanceMapper;
import bgroup.ccard.api.mapper.CardTransactionsMapper;
import bgroup.ccard.api.model.CardBalance;
import bgroup.ccard.api.model.CardState;
import bgroup.ccard.api.model.CardTransactions;
import bgroup.ccard.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static bgroup.ccard.api.controller.HelpFunctions.getRightShortNumber;

@RestController
public class ApiController {
    static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    CardService cardService;
    @Autowired
    CardTransactionsMapper cardTransactionsMapper;

    @RequestMapping(value = {"api/user/balance"}, method = RequestMethod.POST)
    public Balance userBalace(@RequestBody BalanceRequest card) {

        CardBalance cardBalance = cardService.getCardBalance(card);
        if (cardBalance != null)
            return new Balance("ok", "", cardBalance.getBalance());
        else return new Balance("error", "ничего не найдено", 0.0);
    }

    @RequestMapping(value = {"api/user/transactions"}, method = RequestMethod.POST)
    public Transactions getTransactions(
            @RequestBody TransactionsRequest transactionsRequest
    ) {
        Transactions transactions = cardService.getCardTransactionsList(transactionsRequest);
       return transactions;
    }
}