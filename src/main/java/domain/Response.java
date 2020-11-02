package domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Response implements Serializable {
    @Getter
    @Setter
    private HumidityData humidityData;

    @Getter
    @Setter
    private TemperatureData temperatureData;

    @Getter
    @Setter
    private AccelerometerData accelerometerData;

    @Getter
    @Setter
    private GyroscopeData gyroscopeData;
}
