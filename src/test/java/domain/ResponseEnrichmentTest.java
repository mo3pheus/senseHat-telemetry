package domain;

import http.ResponseUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseEnrichmentTest {
    private Response response;
    private Response expectedResponse;
    private String   temperatureData;
    private String   humidityData;

    @Before
    public void setUp() throws Exception {
        response         = new Response();
        temperatureData  = "{\n" +
                "  \"temperature\": 46.86040496826172, \n" +
                "  \"ts\": 1604281487530\n" +
                "}\n";
        humidityData     = "{\n" +
                "  \"humidity\": 22.862525939941406, \n" +
                "  \"ts\": 1604281494358\n" +
                "}\n";
        expectedResponse = new Response();
        expectedResponse.setTemperatureData(new TemperatureData(temperatureData));
        expectedResponse.setHumidityData(new HumidityData(humidityData));
    }

    @Test
    public void testDataEnrichment() throws Exception {
        Logger logger = LoggerFactory.getLogger(ResponseEnrichmentTest.class);
        ResponseUtil.enrichResponseData(response, temperatureData, "temperature", logger);
        ResponseUtil.enrichResponseData(response, humidityData, "humidity", logger);
        Assert.assertEquals(response.getTemperatureData().getTemperature(), expectedResponse.getTemperatureData().getTemperature());
        Assert.assertEquals(response.getHumidityData().getHumidity(), expectedResponse.getHumidityData().getHumidity());
    }
}
