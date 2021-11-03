package bgroup.ccard.api.configuration;

import org.springframework.http.HttpMethod;

/**
 * Created by VSB on 12.12.2017.
 * cleverClients
 */

public class EnvVariable {
    private static String apiUrl;
    private static String apiAuth;
    private static String apiSurname;
    private static String apiUserName;
    private static String apiPassword;
    private static String xmlApiKey;
    private static String xmlApiUrl;
    private static HttpMethod xmlApiMethod;
    private static String xmlApiUrlRead;
    private static String xmlApiUrlWrite;
    private static String xmlApiKeyPc;
    private static String cardPrefix;
    private static String cardMifarPrefix;
    private static String salt;

    public static HttpMethod getXmlApiMethod() {
        return xmlApiMethod;
    }

    public static void setXmlApiMethod(String method) {
        if (method.equals("GET"))
            EnvVariable.xmlApiMethod = HttpMethod.GET;
        else EnvVariable.xmlApiMethod = HttpMethod.POST;
    }

    public static String getXmlApiUrl() {
        return xmlApiUrl;
    }

    public static void setXmlApiUrl(String xmlApiUrl) {
        EnvVariable.xmlApiUrl = xmlApiUrl;
    }

    public static String getXmlApiKey() {
        return xmlApiKey;
    }

    public static void setXmlApiKey(String xmlApiKey) {
        EnvVariable.xmlApiKey = xmlApiKey;
    }

    public static String getApiUrl() {
        return apiUrl;
    }

    public static void setApiUrl(String apiUrl) {
        EnvVariable.apiUrl = apiUrl;
    }

    public static String getApiAuth() {
        return apiAuth;
    }

    public static void setApiAuth(String apiAuth) {
        EnvVariable.apiAuth = apiAuth;
    }

    public static String getApiSurname() {
        return apiSurname;
    }

    public static void setApiSurname(String apiSurname) {
        EnvVariable.apiSurname = apiSurname;
    }

    public static String getApiUserName() {
        return apiUserName;
    }

    public static void setApiUserName(String apiUserName) {
        EnvVariable.apiUserName = apiUserName;
    }

    public static String getApiPassword() {
        return apiPassword;
    }

    public static void setApiPassword(String apiPassword) {
        EnvVariable.apiPassword = apiPassword;
    }

    public static String getXmlApiUrlRead() {
        return xmlApiUrlRead;
    }

    public static void setXmlApiUrlRead(String xmlApiUrlRead) {
        EnvVariable.xmlApiUrlRead = xmlApiUrlRead;
    }

    public static String getXmlApiUrlWrite() {
        return xmlApiUrlWrite;
    }

    public static void setXmlApiUrlWrite(String xmlApiUrlWrite) {
        EnvVariable.xmlApiUrlWrite = xmlApiUrlWrite;
    }

    public static String getCardPrefix() {
        return cardPrefix;
    }

    public static void setCardPrefix(String cardPrefix) {
        EnvVariable.cardPrefix = cardPrefix;
    }

    public static String getXmlApiKeyPc() {
        return xmlApiKeyPc;
    }

    public static void setXmlApiKeyPc(String xmlApiKeyPc) {
        EnvVariable.xmlApiKeyPc = xmlApiKeyPc;
    }

    public static String getSalt() {
        return salt;
    }

    public static void setSalt(String salt) {
        EnvVariable.salt = salt;
    }

    public static String getCardMifarPrefix() {
        return cardMifarPrefix;
    }

    public static void setCardMifarPrefix(String cardMifarPrefix) {
        EnvVariable.cardMifarPrefix = cardMifarPrefix;
    }
}
