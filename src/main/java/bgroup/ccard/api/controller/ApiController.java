package bgroup.ccard.api.controller;

import bgroup.ccard.api.apiInputModel.BalanceRequest;
import bgroup.ccard.api.apiInputModel.TransactionsRequest;
import bgroup.ccard.api.apiModel.Balance;
import bgroup.ccard.api.apiModel.Transactions;
import bgroup.ccard.api.mapper.CardBalanceMapper;
import bgroup.ccard.api.mapper.CardTransactionsMapper;
import bgroup.ccard.api.model.CardBalance;
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
    CardBalanceMapper cardBalanceMapper;
    @Autowired
    CardTransactionsMapper cardTransactionsMapper;

    @RequestMapping(value = {"api/user/balance"}, method = RequestMethod.POST)
    public Balance userBalace(@RequestBody BalanceRequest card) {
        String cardNumber = card.getCard_number();
        cardNumber = getRightShortNumber(cardNumber);
        CardBalance cardBalance = cardBalanceMapper.findCardBalanceByNumber(cardNumber);
        if (cardBalance != null)
            return new Balance("ok", "", cardBalance.getBalance());
        else return new Balance("error", "ничего не найдено", 0.0);
    }

    @RequestMapping(value = {"api/user/transactions"}, method = RequestMethod.POST)
    public Transactions getTransactions(
            @RequestBody TransactionsRequest transactionsRequest
    ) {
        String station = null;
        if (transactionsRequest.getStation() != null && !transactionsRequest.getStation().equals("")) {
            station = transactionsRequest.getStation();
        }
        logger.info("station: {}", station);
        String cardNumber = transactionsRequest.getCard_number();
        String startDate = transactionsRequest.getStart_date();
        String endDate = transactionsRequest.getEnd_date();
        //if (station.equals("null")) station = null;
        cardNumber = getRightShortNumber(cardNumber);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateStart = sdf.parse(startDate);
            dateEnd = sdf.parse(endDate);
            logger.info("date:{}", dateStart);
            logger.info("date:{}", dateEnd);
        } catch (Exception e) {
            logger.error(e.toString());
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
        try {
            if ((cardTransactionsList != null && cardTransactionsList.size() == 0) || cardTransactionsList == null) {
                logger.info("пусто");
                return new Transactions("error", "ничего не найдено", cardTransactionsList);
            } else if (cardTransactionsList != null) {
                logger.info("нашли кучу транзакций: {}",cardTransactionsList.size());
                return new Transactions("ok", "", cardTransactionsList);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}