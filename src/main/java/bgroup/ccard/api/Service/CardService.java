package bgroup.ccard.api.Service;

import bgroup.ccard.api.apiInputModel.*;
import bgroup.ccard.api.apiModel.Transactions;
import bgroup.ccard.api.configuration.EnvVariable;
import bgroup.ccard.api.mapper.CardBalanceMapper;
import bgroup.ccard.api.mapper.CardTransactionsMapper;
import bgroup.ccard.api.model.CardBalance;
import bgroup.ccard.api.model.CardInfo;
import bgroup.ccard.api.model.CardState;
import bgroup.ccard.api.model.CardTransactions;
import bgroup.ccard.api.xmlService.XmlApiPC;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

import static bgroup.ccard.api.controller.HelpFunctions.*;
import static bgroup.ccard.api.model.AesCrypto.decrypt;
import static bgroup.ccard.api.model.AesCrypto.encrypt;

/**
 * Created by VSB on 02.04.2019.
 * ccardApi
 */
@Service
public class CardService {
    static final Logger logger = LoggerFactory.getLogger(CardService.class);
    @Autowired
    CardBalanceMapper cardBalanceMapper;
    @Autowired
    CardTransactionsMapper cardTransactionsMapper;
    @Autowired
    XmlApiPC xmlApiPC;
    @Autowired
    CardTransactionsService cardTransactionsService;

    public CardBalance getCardBalance(BalanceRequest card) {
        String cardNumber = card.getCard_number();
        cardNumber = getRightShortNumber(cardNumber);
        //logger.error("cardNumber:{}", cardNumber);
        CardBalance cardBalance = null;
        if (cardNumber != null && cardNumber.length() <= 6) {
            /*CardState cardState = cardBalanceMapper.findCardSateByNumber(cardNumber);
            if (cardState != null && (cardState.getState() == null || cardState.getState() == 0)) {
                cardBalance = cardBalanceMapper.findCardBalanceByNumber(cardNumber);
            } else {
                cardBalance = getCardBalanceFromPC(cardNumber);
            }*/
            cardBalance = getCardBalanceFromPC(cardNumber);
        } else if (cardNumber != null && cardNumber.length() == 8) {
            cardBalance = getCardBalanceFromPC(cardNumber);
        }

        return cardBalance;
    }

    private CardBalance getCardBalanceFromPC(String cardNumber) {
        CardBalance cardBalance = new CardBalance();
        cardBalance.setCardNumber(cardNumber);
        CardInfo cardInfo = getCardInfoFromPC(cardNumber);
        if (cardInfo == null) return null;
        cardBalance.setBalance(cardInfo != null ? cardInfo.getBalance() : null);
        return cardBalance;
    }

    public CardInfo getCardInfoFromPC(String cardNumber) {
        String xml = getXmlString("getinfo", getCardNumberStringForPC(cardNumber));
        Document document = xmlApiPC.getXmlResponse(xml, "/card");
        if (document == null) return null;
        NodeList nodes = document.getElementsByTagName("Card");
        if (nodes.getLength() != 1) return null;
        Element element = (Element) nodes.item(0);
        String balanceString = element.getElementsByTagName("Balance").item(0).getTextContent();
        String codA = element.getElementsByTagName("COD_A").item(0).getTextContent();
        String codOwn = element.getElementsByTagName("COD_OWN").item(0).getTextContent();
        String stateString = element.getElementsByTagName("State").item(0).getTextContent();

        Double balance = null;
        Integer state = null;
        try {
            balance = Double.parseDouble(balanceString);
            state = Integer.parseInt(stateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String firstName = null;
        String lastName = null;
        String patronymic = null;
        String phoneNumber = null;
        Date birthDate = null;
        Integer sex = null;
        NodeList nodesRequisites = document.getElementsByTagName("Requisite");
        //logger.info("nodesRequisites.getLength():{}", nodesRequisites.getLength());
        try {
            for (int i = 0; i < nodesRequisites.getLength(); i++) {
                Element elementReq = (Element) nodesRequisites.item(i);
                String requisiteName = elementReq.getElementsByTagName("Name").item(0).getTextContent();
                String value = elementReq.getElementsByTagName("Value").item(0).getTextContent();
                //logger.info(requisiteName + ":" + value);
                if (requisiteName.equals("LastName")) {
                    lastName = value != null && !value.equals("null") ? value : null;
                }
                if (requisiteName.equals("FirstName")) {
                    firstName = value != null && !value.equals("null") ? value : null;
                }
                if (requisiteName.equals("Patronymic")) {
                    patronymic = value != null && !value.equals("null") ? value : null;
                }
                if (requisiteName.equals("Sex")) {
                    if (value != null)
                        try {
                            sex = Integer.parseInt(value);
                        } catch (Exception e) {
                        }
                }
                if (requisiteName.equals("PhoneNumber")) {
                    phoneNumber = value != null && !value.equals("null") ? value : null;
                }
                if (requisiteName.equals("BirthDate")) {
                    birthDate = getDateFromString(value, "yyyy-MM-dd");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CardInfo(cardNumber, codA, codOwn, balance, state, firstName, lastName, patronymic, phoneNumber, birthDate, sex);
    }

    private String getXmlString(String command, String foolGraphCardNumber) {
        String xml = null;
        if (command.equals("getinfo")) {
            xml = getInfoRequestString(command, foolGraphCardNumber);
        }
        if (command.equals("getsale")) {
            xml = getSaleRequestString(command, foolGraphCardNumber, null, null);
        }
        return xml;
    }


    private String getXmlString(String command, String foolGraphCardNumber, Date dateStart, Date dateEnd) {
        String xml = null;
        if (command.equals("getinfo")) {
            xml = getInfoRequestString(command, foolGraphCardNumber);
        }
        if (command.equals("getsale")) {
            xml = getSaleRequestString(command, foolGraphCardNumber, dateStart, dateEnd);
        }

        return xml;
    }

    private String getSaleRequestString(String command, String foolGraphCardNumber, Date startDate, Date endDate) {
        if (endDate == null) endDate = new Date();
        if (startDate == null) {
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            c.add(Calendar.DATE, -365);
            startDate = c.getTime();
        }

        String startDateString = getStringFromDate(startDate, "yyyy-MM-dd");
        String endDateString = getStringFromDate(endDate, "yyyy-MM-dd");
        /**
         * да, я знаю, что нужен StringBuilder
         */
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<RequestDS>\n" +
                "  <Request>\n" +
                "    <Command>" + command + "</Command>\n" +
                "    <Version>1</Version>\n" +
                "    <Certificate>" + EnvVariable.getXmlApiKeyPc() + "</Certificate>\n" +
                "    <POSCode>777</POSCode>\n" +
                "  </Request>\n" +
                "<Card>\n" +
                "  <CardNumber>" + foolGraphCardNumber + "</CardNumber>\n" +
                "  <StartDate>" + startDateString + "</StartDate>" +
                "  <EndDate>" + endDateString + "</EndDate>" +
                "</Card>\n" +
                "</RequestDS>";
        return xml;
    }

    private String getInfoRequestString(String command, String foolGraphCardNumber) {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<RequestDS>\n" +
                "  <Request>\n" +
                "    <Command>" + command + "</Command>\n" +
                "    <Version>0</Version>\n" +
                "    <Certificate>" + EnvVariable.getXmlApiKeyPc() + "</Certificate>\n" +
                "    <POSCode>777</POSCode>\n" +
                "  </Request>\n" +
                "<Card>\n" +
                "  <CardNumber>" + foolGraphCardNumber + "</CardNumber>\n" +
                "  <Flags>63</Flags>\n" +
                "</Card>\n" +
                "</RequestDS>";
        return xml;
    }

    private String getCardNumberStringForPC(String cardNumber) {
        StringBuilder cardBuilder = new StringBuilder();
        if (cardNumber.length() == 8 && cardNumber.charAt(0) == '2')
            cardBuilder.append(EnvVariable.getCardMifarPrefix());
        else cardBuilder.append(EnvVariable.getCardPrefix());
        cardBuilder.append(String.format("%8s", cardNumber).replace(' ', '0'));
        return cardBuilder.toString();
    }

    public Transactions getCardTransactionsList(TransactionsRequest transactionsRequest) {

        String cardNumber = transactionsRequest.getCard_number();

        //if (station.equals("null")) station = null;
        cardNumber = getRightShortNumber(cardNumber);

        Transactions transactions = null;
        if (cardNumber != null && cardNumber.length() <= 6) {
            /*CardState cardState = cardBalanceMapper.findCardSateByNumber(cardNumber);
            if (cardState != null && (cardState.getState() == null || cardState.getState() == 0)) {
                transactions = getCardTransactionsListFromClever(transactionsRequest, cardNumber);
            } else {
                transactions = getCardTransactionsListFromPc(transactionsRequest, cardNumber);
            }*/
            transactions = getCardTransactionsListFromPc(transactionsRequest, cardNumber);
        } else if (cardNumber != null && cardNumber.length() == 8) {
            transactions = getCardTransactionsListFromPc(transactionsRequest, cardNumber);
        }
        return transactions;
    }

    private Transactions getCardTransactionsListFromPc(TransactionsRequest transactionsRequest, String cardNumber) {
        String startDate = transactionsRequest.getStart_date();
        String endDate = transactionsRequest.getEnd_date();

        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateStart = getDateFromString(startDate, "yyyy-MM-dd");
            dateEnd = getDateFromString(endDate, "yyyy-MM-dd");
        } catch (Exception e) {
            //logger.debug("Даты не заданы:{}", e.toString());
        }
        String xml = getXmlString("getsale", getCardNumberStringForPC(cardNumber), dateStart, dateEnd);
        Document document = xmlApiPC.getXmlResponse(xml, "/sale");
        if (document == null) return null;
        NodeList nodes = document.getElementsByTagName("Sale");
        List<CardTransactions> cardTransactionsList = null;
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            CardTransactions cardTransactions = cardTransactionsService.getCardTransactions(i, element);
            if (cardTransactions != null) {
                if (cardTransactionsList == null) cardTransactionsList = new ArrayList<>();
                cardTransactionsList.add(cardTransactions);
            }
        }
        if (cardTransactionsList != null)
            Collections.sort(cardTransactionsList);
        setIdInCardTransactionsList(cardTransactionsList);
        return getTransactions(cardTransactionsList);
    }

    private void setIdInCardTransactionsList(List<CardTransactions> cardTransactionsList) {
        if (cardTransactionsList == null || cardTransactionsList.size() < 1) return;
        for (Integer i = 0; i < cardTransactionsList.size(); i++) {
            cardTransactionsList.get(i).setIdTr(i);
            cardTransactionsList.get(i).setId(i);
        }
    }

    private Transactions getTransactions(List<CardTransactions> cardTransactionsList) {
        try {
            if ((cardTransactionsList != null && cardTransactionsList.size() == 0) || cardTransactionsList == null) {
                //logger.info("пусто");
                return new Transactions("error", "ничего не найдено", cardTransactionsList);
            } else if (cardTransactionsList != null) {
                //logger.info("нашли кучу транзакций: {}", cardTransactionsList.size());
                return new Transactions("ok", "", cardTransactionsList);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private Transactions getCardTransactionsListFromClever(TransactionsRequest transactionsRequest, String cardNumber) {

        String startDate = transactionsRequest.getStart_date();
        String endDate = transactionsRequest.getEnd_date();

        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateStart = getDateFromString(startDate, "yyyy-MM-dd");
            dateEnd = getDateFromString(endDate, "yyyy-MM-dd");
        } catch (Exception e) {
            logger.error(e.toString());
        }
        List<CardTransactions> cardTransactionsList = null;
        String station = null;
        if (transactionsRequest.getStation() != null && !transactionsRequest.getStation().equals("")) {
            station = transactionsRequest.getStation();
        }
        //logger.info("station: {}", station);
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
        return getTransactions(cardTransactionsList);
    }

    public Boolean setFioInPc(FioSetRequest fioSetRequest) {
        String cardNumber = getRightShortNumber(fioSetRequest.getCard_number());
        CardInfo cardInfo = getCardInfoFromPC(cardNumber);
        try {
            cardInfo.setPhoneNumber(fioSetRequest.getPhone());
            cardInfo.setLastName(fioSetRequest.getLast_name());
            cardInfo.setFirstName(fioSetRequest.getName());
            cardInfo.setPatronymic(fioSetRequest.getSecond_name());
            cardInfo.setBirthDate(fioSetRequest.getBirthday());
            cardInfo.setSex(fioSetRequest.getSex());
        } catch (Exception e) {
            logger.error(e.toString());
            return false;
        }
        String xml = getXmlString("setinfo", getCardNumberStringForPC(cardNumber), cardInfo);
        Document document = xmlApiPC.getXmlWriteResponse(xml, "/card");
        if (document == null) return false;
        return true;
    }

    private String getXmlString(String command, String foolGraphCardNumber, CardInfo cardInfo) {
        if (cardInfo == null) {
            logger.error("card_info: is null");
            return null;
        } else {
            //logger.info("card_info:{}", cardInfo.toString());
        }

        String xml = null;
        if (command.equals("setinfo")) {
            xml = getSetInfoString(command, foolGraphCardNumber, cardInfo);
        }
        if (command.equals("Registration")) {
            xml = getRegistrationString(command, foolGraphCardNumber, cardInfo);
        }
        return xml;
    }

    public String registrationLk(RegistrationLkRequest registrationLkRequest) {
        String cardNumber = getRightShortNumber(registrationLkRequest.getCard_number());
        if (cardNumber == null) return null;
        String phoneNumber = registrationLkRequest.getPhone();
        CardInfo cardInfo = getCardInfoFromPC(cardNumber);
        if (cardInfo == null) return null;
        cardInfo.setPhoneNumber(phoneNumber);
        String xml = getXmlString("Registration", getCardNumberStringForPC(cardNumber), cardInfo);
        //logger.info("xml.length:{}", xml.length());
        Document document = xmlApiPC.getXmlRegistrationResponse(xml, "");
        String response = "Неопознанная ошибка";
        try {
            //logger.info(document.toString());
            //logger.info(document.getElementById("Details").toString());
            document.getDocumentElement().normalize();
            NodeList nList = document.getElementsByTagName("Details");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    if (element.getElementsByTagName("DetailsSelectName") != null
                            && element.getElementsByTagName("DetailsSelectName").item(0).getTextContent().equals("RegistrationState")) {
                        response = element.getElementsByTagName("DetailsValue").item(0).getTextContent();
                        break;
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return response;
    }

    private String getRegistrationString(String command, String foolGraphCardNumber, CardInfo cardInfo) {
        String phoneNumber = cardInfo.getPhoneNumber() != null ? cardInfo.getPhoneNumber() : "";

        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                //String xml = "<?xml version=\"1.0\"\n" +
                "<RequestDS>\n" +
                "<Request>\n" +
                "  <RequestKey>0</RequestKey>\n" +
                "  <sncAppCode>17</sncAppCode>\n" +
                "  <ShopRequestKey>0</ShopRequestKey>\n" +
                "  <SelectName>" + command + "</SelectName>\n" +
                "  <COD_AZS>1000001</COD_AZS>\n" +
                "  <COD_Q>0</COD_Q>\n" +
                "</Request>\n" +
                "<Details>\n" +
                "  <DetailsSelectName>NewLogin</DetailsSelectName>\n" +
                "  <DetailsValue>" + "01" + cardInfo.getCardNumber() + "</DetailsValue>\n" +
                "</Details>\n" +
                "<Details>\n" +
                "  <DetailsSelectName>Login</DetailsSelectName>\n" +
                "  <DetailsValue>" + cardInfo.getCardNumber() + "</DetailsValue>\n" +
                "</Details>\n" +
                "<Details>\n" +
                "  <DetailsSelectName>Data</DetailsSelectName>\n" +
                "  <DetailsValue>" + phoneNumber + "</DetailsValue>\n" +
                "</Details>\n" +
                "</RequestDS>";
        return xml;
    }

    private String getSetInfoString(String command, String foolGraphCardNumber, CardInfo cardInfo) {
        String firstName = cardInfo.getFirstName() != null ? cardInfo.getFirstName() : "";
        String lastName = cardInfo.getLastName() != null ? cardInfo.getLastName() : "";
        String patronymic = cardInfo.getPatronymic() != null ? cardInfo.getPatronymic() : "";
        String birthDate = cardInfo.getBirthDate() != null ? getStringFromDate(cardInfo.getBirthDate(), "yyyy-MM-dd") : "";
        String phoneNumber = cardInfo.getPhoneNumber() != null ? cardInfo.getPhoneNumber() : "";
        String sex = cardInfo.getSex() != null ? cardInfo.getSex().toString() : "";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<RequestDS>\n" +
                "  <Request>\n" +
                "    <Command>" + command + "</Command>\n" +
                "    <Version>1</Version>\n" +
                "    <Certificate>" + EnvVariable.getXmlApiKeyPc() + "</Certificate>\n" +
                "    <POSCode>777</POSCode>\n" +
                "  </Request>\n" +
                "<Requisite>\n" +
                "  <CardNumber>" + foolGraphCardNumber + "</CardNumber>\n" +
                "  <Name>FirstName</Name>\n" +
                "  <Value>" + firstName + "</Value>" +
                "</Requisite>\n" +
                "<Requisite>\n" +
                "  <CardNumber>" + foolGraphCardNumber + "</CardNumber>\n" +
                "  <Name>LastName</Name>\n" +
                "  <Value>" + lastName + "</Value>" +
                "</Requisite>\n" +
                "<Requisite>\n" +
                "  <CardNumber>" + foolGraphCardNumber + "</CardNumber>\n" +
                "  <Name>Patronymic</Name>\n" +
                "  <Value>" + patronymic + "</Value>" +
                "</Requisite>\n" +
                "<Requisite>\n" +
                "  <CardNumber>" + foolGraphCardNumber + "</CardNumber>\n" +
                "  <Name>BirthDate</Name>\n" +
                "  <Value>" + birthDate + "</Value>" +
                "</Requisite>\n" +
                "<Requisite>\n" +
                "  <CardNumber>" + foolGraphCardNumber + "</CardNumber>\n" +
                "  <Name>PhoneNumber</Name>\n" +
                "  <Value>" + phoneNumber + "</Value>" +
                "</Requisite>\n" +
                "<Requisite>\n" +
                "  <CardNumber>" + foolGraphCardNumber + "</CardNumber>\n" +
                "  <Name>Sex</Name>\n" +
                "  <Value>" + sex + "</Value>" +
                "</Requisite>\n" +
                "</RequestDS>";
        //logger.info("xml:{}", xml);
        return xml;
    }


    public String[] authorization(AuthorizationRequest authorizationRequest) {
        String cardNumber = getRightShortNumber(authorizationRequest.getCard_number());
        if (cardNumber == null) return null;
        CardInfo cardInfo = getCardInfoFromPC(cardNumber);
        if (cardInfo == null) return null;
        String salt = EnvVariable.getSalt();
        String inputPassword = authorizationRequest.getUser_pass();
        String xml = getXmlAuthString(cardInfo, salt, inputPassword);
        Document document = xmlApiPC.getXmlAuthResponse(xml, "");

        String response = "Неопознанная ошибка";
        String detailsKey = null;
        try {
            document.getDocumentElement().normalize();
            NodeList nList = document.getElementsByTagName("Details");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    if (element.getElementsByTagName("DetailsSelectName") != null
                            && element.getElementsByTagName("DetailsSelectName").item(0)
                            .getTextContent().equals("AuthorizationState")) {
                        response = element.getElementsByTagName("DetailsValue").item(0).getTextContent();
                        break;
                    } else if (element.getElementsByTagName("DetailsSelectName") != null
                            && element.getElementsByTagName("DetailsSelectName").item(0)
                            .getTextContent().equals("Parol")) {
                        detailsKey = element.getElementsByTagName("DetailsKey").item(0).getTextContent();
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        String[] array = {response, detailsKey};
        return array;
    }

    private String getXmlAuthString(CardInfo cardInfo, String salt, String inputPassword) {

        String xml = "" +
                "<?xml version=\"1.0\"?>\n" +
                "<RequestDS>\n" +
                "  <Request>\n" +
                "    <RequestKey>0</RequestKey>\n" +
                "    <sncAppCode>17</sncAppCode>\n" +
                "    <ShopRequestKey>0</ShopRequestKey>\n" +
                "    <SelectName>Authorization</SelectName>\n" +
                "    <COD_AZS>1000001</COD_AZS>\n" +
                "    <COD_Q>0</COD_Q>\n" +
                "  </Request>\n" +
                "  <Details>\n" +
                "    <DetailsSelectName>login</DetailsSelectName>\n" +
                "    <DetailsValue>" + "01" + cardInfo.getCardNumber() + "</DetailsValue>\n" +
                "  </Details>\n" +
                "  <Details>\n" +
                "    <DetailsSelectName>password</DetailsSelectName>\n" +
                "    <DetailsValue>" + DigestUtils.sha1Hex(salt + inputPassword) + "</DetailsValue>\n" +
                "  </Details>\n" +
                "  <Details>\n" +
                "    <DetailsSelectName>Roles</DetailsSelectName>\n" +
                "    <DetailsValue>0</DetailsValue>\n" +
                "  </Details>\n" +
                "  <Details>\n" +
                "    <DetailsSelectName>ImportData</DetailsSelectName>\n" +
                "    <DetailsValue/>\n" +
                "  </Details>\n" +
                "  <Details>\n" +
                "    <DetailsSelectName>NewLogin</DetailsSelectName>\n" +
                "    <DetailsValue/>\n" +
                "  </Details>\n" +
                "</RequestDS>";
        return xml;
    }

    public String[] changePassword(ChangePasswordRequest changePasswordRequest) {
        CardInfo card = getCardInfoFromPC(getRightShortNumber(changePasswordRequest.getCard_number()));
        if (card == null) return null;
        String requisiteKey = changePasswordRequest.getDetailsKey();
        String password = changePasswordRequest.getPassword();
        String xml = getXmlChangePasswordString(card, requisiteKey, password);
        Document document = xmlApiPC.getXmlAuthResponse(xml, "");
        String response = "Неопознанная ошибка";
        String detailsKey = null;
        try {
            document.getDocumentElement().normalize();
            NodeList nList = document.getElementsByTagName("Details");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    if (element.getElementsByTagName("DetailsSelectName") != null
                            && element.getElementsByTagName("DetailsSelectName").item(0)
                            .getTextContent().equals("ChangeLowesRequsites")) {
                        response = element.getElementsByTagName("DetailsValue").item(0).getTextContent();
                        break;
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        String[] array = {response};
        return array;
    }

    private String getXmlChangePasswordString(CardInfo cardInfo, String requisiteKey, String password) {
        String xml = null;
        try {

            xml = "" +
                    "<?xml version=\"1.0\"?>\n" +
                    "<RequestDS>\n" +
                    "<Request>" +
                    "  <RequestKey>0</RequestKey>" +
                    "  <sncAppCode>17</sncAppCode>" +
                    "  <ShopRequestKey>0</ShopRequestKey>" +
                    "  <SelectName>ChangePassword</SelectName>" +
                    "  <COD_AZS>1000001</COD_AZS>" +
                    "  <COD_Q>0</COD_Q>" +
                    "</Request > " +
                    "  <Details>\n" +
                    "    <DetailsSelectName>COD_OWN</DetailsSelectName>\n" +
                    "    <DetailsValue>" + cardInfo.getClientCode() + "</DetailsValue>\n" +
                    "  </Details>\n" +
                    "  <Details>\n" +
                    "    <DetailsSelectName>COD_A</DetailsSelectName>\n" +
                    "    <DetailsValue>" + cardInfo.getOrgCode() + "</DetailsValue>\n" +
                    "  </Details>\n" +
                    "  <Details>\n" +
                    "    <DetailsSelectName>role</DetailsSelectName>\n" +
                    "    <DetailsValue>4</DetailsValue>\n" +
                    "  </Details>\n" +
                    "  <Details>\n" +
                    "    <DetailsSelectName>requisiteValue</DetailsSelectName>\n" +
                    "    <DetailsValue>" + getPasswordEnc(password, requisiteKey) + "</DetailsValue>\n" +
                    "  </Details>\n" +
                    "  <Details>\n" +
                    "    <DetailsSelectName>requisiteKey</DetailsSelectName>\n" +
                    "    <DetailsValue>" + requisiteKey + "</DetailsValue>\n" +
                    "  </Details>\n" +
                    "</RequestDS>";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

    private String getPasswordEnc(String password, String requisiteKey) {
        try {
            String salt = EnvVariable.getSalt();
            String pKey = requisiteKey;
            //String auth = substr(substr(salt, 0, 16), 0, -pKey.length()) + pKey;

            //String auth = salt.substring(0, 16).substring(0, -pKey.length()) + pKey;
            String saltSubstring = salt.substring(0, 16);
            String auth = saltSubstring.substring(0, saltSubstring.length() - pKey.length()) + pKey;

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(auth.substring(0, 16));
            String iv = stringBuilder.reverse().toString();

            String key = auth; //"0123456789abcdef"; // 128 bit key
            String initVector = iv; //"abcdef9876543210"; // 16 bytes IV, it is recommended to use a different random IV for every message!

            String plain_text = password; //"plain text";
            String encrypted = encrypt(key, initVector, plain_text);
            //logger.info("encrypted:{}", encrypted);

            return encrypted.split(":")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
