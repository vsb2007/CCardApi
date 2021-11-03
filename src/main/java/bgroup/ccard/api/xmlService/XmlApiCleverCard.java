package bgroup.ccard.api.xmlService;

import bgroup.ccard.api.apiInputModel.FioSetRequest;
import bgroup.ccard.api.configuration.EnvVariable;
import bgroup.ccard.api.controller.HelpFunctions;
import bgroup.ccard.api.xmlModel.XmlSetInfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.text.SimpleDateFormat;

/**
 * Created by VSB on 06.12.2017.
 * cleverClients
 */

public class XmlApiCleverCard {
    static final Logger logger = LoggerFactory.getLogger(XmlApiCleverCard.class);

    public boolean setCardProperties(FioSetRequest fioSetRequest) {
        try {
            String cardNumberString = HelpFunctions.getRightShortNumber(fioSetRequest.getCard_number());
            if (cardNumberString == null) return false;
            String requestString = new XmlSetInfoRequest(
                    cardNumberString
                    , fioSetRequest.getName()
                    , fioSetRequest.getSecond_name()
                    , fioSetRequest.getLast_name()
                    , ((fioSetRequest.getBirthday() != null) ? (new SimpleDateFormat("yyyy-MM-dd").format(fioSetRequest.getBirthday())) : null)
                    , fioSetRequest.getPhone()
                    , fioSetRequest.getSex()
                    , "setinfo", EnvVariable.getXmlApiKey()
            ).toRequestString();
            ResponseEntity<String> response = FuncXmlApiCleverCard.getResponse(null, requestString);

            String xmlString = null;
            if (response != null) {
                //logger.info(response.getBody());
                xmlString = response.getBody();
                //  logger.info("xmlString:{}", xmlString);
            } else {
                //logger.info("ничего не ответили");
            }

            if (xmlString != null) {
                try {
                    Document document = getDocument(xmlString);
                    if (document == null) return false;
                    Element eElement = document.getDocumentElement();
                    String resp = eElement.getElementsByTagName("result").item(0).getTextContent();
                    //logger.debug("resp:{}", resp);
                    if (resp != null && resp.equals("0")) return true;
                } catch (Exception e) {
                    logger.error("XML: какая-то не такая ошибка:{}", e.toString());
                }
                //logger.info(eElement.getElementsByTagName("result").item(0).getTextContent());
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return false;
        }
        //logger.info(xmlString);
        return false;
    }

    private Document getDocument(String xmlString) {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }
}
