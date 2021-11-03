package bgroup.ccard.api.xmlService;

import bgroup.ccard.api.configuration.EnvVariable;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;


/**
 * Created by VSB on 06.12.2017.
 * cleverClients
 */

public class FuncXmlApiCleverCard {

    static final Logger logger = LoggerFactory.getLogger(FuncXmlApiCleverCard.class);

    private static final MediaType JSON = MediaType.APPLICATION_JSON;
    private static final MediaType XML = MediaType.APPLICATION_XML;
    //    private static CustomHttpSessionStatus customHttpSessionStatus;
    private static String sessionId;
    private static final String apiUrl = EnvVariable.getXmlApiUrl();
    //private static final String apiAuth = EnvVariable.getApiAuth();
    private static final HttpMethod METHOD = EnvVariable.getXmlApiMethod();
    private static HttpComponentsClientHttpRequestFactory requestFactory;

    public static ResponseEntity<String> getResponse(String urlRequest, String requestString) {

        String url = apiUrl;
        if (urlRequest != null)
            url += urlRequest;
        logger.info(url);
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(Charset.forName("windows-1251")));
            response = restTemplate.exchange(url + "?" + requestString, METHOD, entity, String.class);
            //response.setCharacterEncoding()
        } catch (Exception e) {
            logger.error(e.toString());
        }

        return response;
    }

    private static HttpComponentsClientHttpRequestFactory getRequestFactory() {
        //SSLContext sslContext = null;
        //sslContext = getSslContext(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom()
                //      .setSSLContext(sslContext)
                //    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return requestFactory;
    }


}
