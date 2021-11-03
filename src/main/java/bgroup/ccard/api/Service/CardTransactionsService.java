package bgroup.ccard.api.Service;

import bgroup.ccard.api.mapper.AzsMapper;
import bgroup.ccard.api.model.Azs;
import bgroup.ccard.api.model.CardTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by VSB on 22.04.2019.
 * ccardApi
 */
@Service
public class CardTransactionsService {
    static final Logger logger = LoggerFactory.getLogger(CardTransactionsService.class);
    @Autowired
    AzsMapper azsMapper;


    public CardTransactions getCardTransactions(int i, Element element) {
        CardTransactions cardTransactions = null;

        String bonusInString = element.getElementsByTagName("BonusIn").item(0).getTextContent();
        String bonusOutString = element.getElementsByTagName("BonusOut").item(0).getTextContent();
        String transactionDatetime = element.getElementsByTagName("TransactionDatetime").item(0).getTextContent();
        String azsCode = element.getElementsByTagName("COD_AZS").item(0).getTextContent();
        /*String azsName = element.getElementsByTagName("AZS_NAME") != null && element.getElementsByTagName("AZS_NAME").getLength() > 0
                ? element.getElementsByTagName("AZS_NAME").item(0).getTextContent() : null;*/
        Integer azsCodeInt = null;
        try {
            azsCodeInt = Integer.parseInt(azsCode);
        } catch (Exception e) {
            logger.error("AzsCode not integer:{}", azsCode);
        }
        String azsName = null;
        Map<Integer, Azs> azsMap = azsMapper.getAzsMap();
        try {
            if (azsCodeInt != null) {
                azsName = azsMap.get(azsCodeInt).getAzsName();
            }
        } catch (Exception e) {
            azsName = "" + azsCodeInt;
        }
        String dateString = element.getElementsByTagName("TransactionDatetime").item(0).getTextContent();
        String operation = null;
        Double amount = 0.0;
        Date transactionDate = null;
        try {
            Double amountIn = Double.parseDouble(bonusInString);
            Double amountOut = Double.parseDouble(bonusOutString);
            amount = amountIn != 0 && amountOut == 0 ? amountIn : amountOut;
            operation = amountIn != 0 && amountOut == 0 ? "admission" : "withdraw";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            transactionDate = simpleDateFormat.parse(dateString);
            azsName = new String(azsName.getBytes(ISO_8859_1), "UTF-8");

        } catch (Exception e) {
            return null;
        }

        cardTransactions = new CardTransactions();
        //cardTransactions.setIdTr(new Integer(i));
        cardTransactions.setAmount(amount);
        cardTransactions.setOperation(operation);
        cardTransactions.setStation(azsName != null ? azsName : azsCode);
        //cardTransactions.setId(new Integer(i));
        cardTransactions.setDate(transactionDate);

        return cardTransactions;
    }
}
