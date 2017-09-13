package bgroup.ccard.api.controller;

import bgroup.ccard.api.apiModel.Balance;
import bgroup.ccard.api.apiModel.Greeting;


import bgroup.ccard.api.apiModel.Transactions;
import bgroup.ccard.api.mapper.CardBalanceMapper;
import bgroup.ccard.api.mapper.CardTransactionsMapper;
import bgroup.ccard.api.model.CardBalance;
import bgroup.ccard.api.model.CardTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class ApiController {
    static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    CardBalanceMapper cardBalanceMapper;
    @Autowired
    CardTransactionsMapper cardTransactionsMapper;

    @RequestMapping(value = {"greeting"})
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping(value = {"user/balance"}, method = RequestMethod.GET)
    public Balance userBalace(@RequestParam(value = "card_number", defaultValue = "null") String cardNumber) {
        CardBalance cardBalance = cardBalanceMapper.findCardBalanceByNumber(cardNumber);
        if (cardBalance != null)
            return new Balance("ok", "", cardBalance.getBalance());
        else return new Balance("error", "ничего не найдено", 0.0);
    }

    @RequestMapping(value = {"user/transactions"}, method = RequestMethod.GET)
    public Transactions getTransactions(
            @RequestParam(value = "card_number", defaultValue = "null") String cardNumber,
            @RequestParam(value = "start_date", defaultValue = "null") String startDate,
            @RequestParam(value = "end_date", defaultValue = "null") String endDate
    ) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateStart = sdf.parse(startDate);
            dateEnd = sdf.parse(endDate);
        } catch (ParseException e) {

        }
        if (dateStart == null || dateEnd == null) {
            List<CardTransactions> cardTransactionsList = cardTransactionsMapper.findCardTransactionsByNumber(cardNumber);
            if (cardTransactionsList != null && cardTransactionsList.size() == 0) {
                return new Transactions("error", "ничего не найдено", cardTransactionsList);
            } else if (cardTransactionsList != null) {
                return new Transactions("ok", "", cardTransactionsList);
            }
        } else {
            List<CardTransactions> cardTransactionsList = null;
            try {
                cardTransactionsList = cardTransactionsMapper.findCardTransactionsByNumberAndDate(cardNumber, startDate, endDate);
            } catch (Exception e) {
                return null;
            }
            try {
                if (cardTransactionsList != null && cardTransactionsList.size() == 0) {
                    return new Transactions("error", "ничего не найдено", cardTransactionsList);
                } else if (cardTransactionsList != null) {
                    return new Transactions("ok", "", cardTransactionsList);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}