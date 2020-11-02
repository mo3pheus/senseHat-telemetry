package http;

import domain.ApplicationConfig;
import domain.Response;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DataExtractor {
    private ApplicationConfig applicationConfig;
    private Logger            logger = LoggerFactory.getLogger(DataExtractor.class);
    private String[]          extractionFields;

    public DataExtractor(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
        this.extractionFields  = applicationConfig.getSenseDataFields().split(",");
    }

    public Response getSenseHatResponse() {
        Response response = new Response();
        for (String field : extractionFields) {
            String extractionUrl = applicationConfig.getSenseDataUrl() + "/" + field;
            try {
                String data = extractData(extractionUrl);
                ResponseUtil.enrichResponseData(response, data, field, logger);
            } catch (IOException | ParseException e) {
                logger.error("Data extraction failed for field = " + field, e);
            }
        }
        return response;
    }

    private String extractData(String url) throws IOException {
        String                response            = null;
        CloseableHttpClient   closeableHttpClient = getHttpClient();
        HttpGet               httpGet             = new HttpGet(url);
        CloseableHttpResponse httpResponse        = null;
        try {
            httpResponse = closeableHttpClient.execute(httpGet);
            logger.debug("Response from micro-vault = " + httpResponse.getStatusLine().getReasonPhrase());
            logger.debug("Response from micro-vault = " + httpResponse.getStatusLine().getStatusCode());
            response = ResponseUtil.getResponseAsString(httpResponse, logger);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            EntityUtils.consume(httpResponse.getEntity());
        }
        return response;
    }

    private CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE).build();
        return httpClient;
    }
}
