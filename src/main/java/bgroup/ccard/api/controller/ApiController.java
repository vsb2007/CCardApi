package bgroup.ccard.api.controller;

import bgroup.ccard.api.apiModel.Balance;
import bgroup.ccard.api.apiModel.Transactions;
import bgroup.ccard.api.mapper.CardBalanceMapper;
import bgroup.ccard.api.mapper.CardTransactionsMapper;
import bgroup.ccard.api.model.CardBalance;
import bgroup.ccard.api.model.CardTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class ApiController {
    static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    CardBalanceMapper cardBalanceMapper;
    @Autowired
    CardTransactionsMapper cardTransactionsMapper;

    @RequestMapping(value = {"api/user/balance"}, method = RequestMethod.POST)
    public Balance userBalace(@RequestParam(value = "card_number", defaultValue = "null") String cardNumber) {
        cardNumber = getRightShortNumber(cardNumber);
        CardBalance cardBalance = cardBalanceMapper.findCardBalanceByNumber(cardNumber);
        if (cardBalance != null)
            return new Balance("ok", "", cardBalance.getBalance());
        else return new Balance("error", "ничего не найдено", 0.0);
    }

    private String getRightShortNumber(String cardNumber) {
        if (cardNumber == null) return null;
        //char[] card = cardNumber.toCharArray();
        String card = null;
        if (cardNumber.length() == 8) {
            card = cardNumber.substring(2, 8);
            try {
                int i = Integer.parseInt(card);
                return i + "";
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @RequestMapping(value = {"api/user/transactions"}, method = RequestMethod.POST)
    public Transactions getTransactions(
            @RequestParam(value = "card_number", defaultValue = "null") String cardNumber,
            @RequestParam(value = "start_date", defaultValue = "null") String startDate,
            @RequestParam(value = "end_date", defaultValue = "null") String endDate,
            @RequestParam(value = "station", defaultValue = "null") String station
    ) {
        if (station.equals("null")) station = null;
        System.out.println("\n\n" + cardNumber + "\n\n");
        cardNumber = getRightShortNumber(cardNumber);
        System.out.println("\n\n" + cardNumber + "\n\n");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateStart = sdf.parse(startDate);
            dateEnd = sdf.parse(endDate);
        } catch (ParseException e) {

        }
        List<CardTransactions> cardTransactionsList = null;
        if (station == null) {
            if (dateStart == null || dateEnd == null) {
                cardTransactionsList = cardTransactionsMapper.findCardTransactionsByNumber(cardNumber);
                if (cardTransactionsList != null && cardTransactionsList.size() == 0) {
                    return new Transactions("error", "ничего не найдено", cardTransactionsList);
                } else if (cardTransactionsList != null) {
                    return new Transactions("ok", "", cardTransactionsList);
                }
            } else {
                try {
                    cardTransactionsList = cardTransactionsMapper.findCardTransactionsByNumberAndDate(cardNumber, startDate, endDate);
                } catch (Exception e) {
                    return null;
                }
            }

        } else {
            if (station == null) {
                if (dateStart == null || dateEnd == null) {
                    cardTransactionsList = cardTransactionsMapper.findCardTransactionsByNumberAndStation(cardNumber, station);
                    if (cardTransactionsList != null && cardTransactionsList.size() == 0) {
                        return new Transactions("error", "ничего не найдено", cardTransactionsList);
                    } else if (cardTransactionsList != null) {
                        return new Transactions("ok", "", cardTransactionsList);
                    }
                } else {
                    try {
                        cardTransactionsList = cardTransactionsMapper.findCardTransactionsByNumberAndDateAndStation(cardNumber, station, startDate, endDate);
                    } catch (Exception e) {
                        return null;
                    }
                }
            }
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
        return null;
    }


}