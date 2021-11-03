package bgroup.ccard.api.xmlService;

import bgroup.ccard.api.configuration.EnvVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by VSB on 02.04.2019.
 * ccardApi
 */
@Service
public class XmlApiPC {
    static final Logger logger = LoggerFactory.getLogger(XmlApiPC.class);

    public Document getXmlResponse(String xml, String api) {
        Document document = null;
        try {
            String url = EnvVariable.getXmlApiUrlRead() + api;
            document = getDocument(url, xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    private Document getDocument(String xmlString) {
        /*xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                "<RequestDS>\n" +
                "<Card>\n" +
                "<CardNumber>1000003410000020</CardNumber>\n" +
                "<COD_A>4403</COD_A>\n" +
                "<COD_OWN>20</COD_OWN>\n" +
                "<ApplicationType>1</ApplicationType>\n" +
                "<ApplicationKey>73541</ApplicationKey>\n" +
                "<Balance>0</Balance>\n" +
                "<State>4</State>\n" +
                "</Card>\n" +
                "<Request>\n" +
                "<Command>getinfo</Command>\n" +
                "<Version>0</Version>\n" +
                "<POSCode>777</POSCode>\n" +
                "</Request>\n" +
                "</RequestDS>";*/
        Pattern p = Pattern.compile("<\\?xml.*");
        Matcher m = p.matcher(xmlString);
        String xml = xmlString;
        if (m.find()) {
            xml = xml.substring(m.start()).trim();
            //logger.info("xml:{}", xml);
        }
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            //logger.info("response:{}", xml);
            document = builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("UTF-8"))));
            //document = builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            /*if (document == null) {
                logger.error("document is null");
            } else {
                logger.info("not null:{}", document.toString());
                logger.info(document.getDocumentElement().getElementsByTagName("Details")
                        .item(0).getTextContent().toString());
                //document.getElementsByTagName("Details").item(0).getChildNodes().item(0).
                Element element = document.getDocumentElement();
                //element.getElementsByTagName("Details");
                //logger.info(element.getElementsByTagName("DetailsSelectName").item(0).getTextContent());
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return document;
    }

    public Document getXmlWriteResponse(String xml, String api) {
        Document document = null;
        try {
            String url = EnvVariable.getXmlApiUrlWrite() + api;
            document = getDocument(url, xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    private Document getDocument(String url, String xml) {
        Document document = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/xml");
        HttpEntity<String> request = new HttpEntity<String>(xml, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("utf-8")));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.OK) {
            if (response != null) {
                document = getDocument(response.getBody());
                //logger.info("response:{}", response.getBody());
            }
        }
        return document;
    }

    public Document getXmlRegistrationResponse(String xml, String api) {
        Document document = null;
        try {
            //String url = EnvVariable.getXmlApiUrlWrite() + api;
            String url = EnvVariable.getXmlApiUrlRead() + api;
            document = getDocumentRegistration(xml,url);
            //logger.info(document.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    private Document getDocumentRegistration(String xml, String url) {
        Document document = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/xml");
        headers.add("Content-length", "" + xml.length());
        headers.add("Return-type", "json");
        headers.add("Connection", "open");
        HttpEntity<String> request = new HttpEntity<String>(xml, headers);
        //logger.info("xml:{}", xml);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("utf-8")));
        //ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        //logger.info("xml:{}", xml);
        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.OK) {
            if (response != null) {
                //logger.info("response:{}", response.getBody());
                try {
                    //String str = new String(response.getBody().getBytes("ISO-8859-1"), "UTF-8");
                    //logger.info("str:{}", str);
                    document = getDocument(response.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return document;
    }

    public Document getDocumentAuth(String xml, String url) {
        Document document = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/xml");
        headers.add("Content-length", "" + xml.length());
        headers.add("Return-type", "json");
        headers.add("Connection", "open");
        HttpEntity<String> request = new HttpEntity<String>(xml, headers);
        RestTemplate restTemplate = new RestTemplate();
        //ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        //logger.info("xml:{}", xml);
        HttpStatus status = response.getStatusCode();
        if (status == HttpStatus.OK) {
            if (response != null) {
                //logger.info("response:{}", response.getBody());
                try {
                    String str = new String(response.getBody().getBytes("ISO-8859-1"), "UTF-8");
                    //logger.info("str:{}", str);
                    document = getDocument(str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return document;
    }

    public Document getXmlAuthResponse(String xml, String api) {
        Document document = null;
        try {
            //String url = EnvVariable.getXmlApiUrlWrite() + api;
            String url = EnvVariable.getXmlApiUrlRead() + api;
            document = getDocumentAuth(xml,url);
            //logger.info(document.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }
}
