package domain;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Serializable;

public class AccelerometerData implements Serializable {
    @Getter
    @Setter
    private Double pitch;

    @Getter
    @Setter
    private Double roll;

    @Getter
    @Setter
    private Double yaw;

    @Getter
    @Setter
    private Long timestamp;

    public AccelerometerData(String jsonData) throws ParseException {
        JSONObject jsonObject    = (JSONObject) new JSONParser().parse(jsonData);
        JSONObject accelerometer = (JSONObject) jsonObject.get("accelerometer");
        this.pitch     = (Double) accelerometer.get("pitch");
        this.roll      = (Double) accelerometer.get("roll");
        this.yaw       = (Double) accelerometer.get("yaw");
        this.timestamp = (Long) jsonObject.get("ts");
    }
}
