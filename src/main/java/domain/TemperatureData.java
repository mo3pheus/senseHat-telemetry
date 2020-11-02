package domain;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Serializable;

public class TemperatureData implements Serializable {
    @Getter
    @Setter
    private Double temperature;

    @Getter
    @Setter
    private long timestamp;

    public TemperatureData(String jsonData) throws ParseException {
        Object     obj        = (new JSONParser()).parse(jsonData);
        JSONObject jsonObject = (JSONObject) obj;
        this.temperature = (Double) jsonObject.get("temperature");
        this.timestamp   = (Long) jsonObject.get("ts");
    }
}
