package domain;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Serializable;

public class HumidityData implements Serializable {
    @Getter
    @Setter
    private Double humidity;

    @Getter
    @Setter
    private long timestamp;

    public HumidityData(String jsonData) throws ParseException {
        Object     obj        = (new JSONParser()).parse(jsonData);
        JSONObject jsonObject = (JSONObject) obj;
        this.humidity  = (Double) jsonObject.get("humidity");
        this.timestamp = (Long) jsonObject.get("ts");
    }
}
