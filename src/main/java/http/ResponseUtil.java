package http;

import domain.*;
import org.apache.http.HttpResponse;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResponseUtil {
    public static String getResponseAsString(HttpResponse httpResponse, Logger logger) {
        BufferedReader reader          = null;
        StringBuilder  responseBuilder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String dataLine = null;
            while ((dataLine = reader.readLine()) != null) {
                responseBuilder.append(dataLine);
            }
        } catch (IOException io) {
            logger.error("IOException", io);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                logger.error("IOException", e);
            }
        }
        return responseBuilder.toString();
    }

    public static void enrichResponseData(Response response, String data, String dataField, Logger logger) throws ParseException {
        switch (dataField) {
            case "temperature": {
                response.setTemperatureData(new TemperatureData(data));
            }
            break;
            case "humidity": {
                response.setHumidityData(new HumidityData(data));
            }
            break;
            case "gyroscope": {
                response.setGyroscopeData(new GyroscopeData(data));
            }
            break;
            case "accelerometer": {
                response.setAccelerometerData(new AccelerometerData(data));
            }
            break;
            default: {
                logger.error("Could not identify field", dataField);
            }
        }
    }
}
