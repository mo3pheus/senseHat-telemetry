package domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccelerometerDataTest {
    private AccelerometerData accelerometerData;
    private String            jsonDataString;

    @Before
    public void setUp() throws Exception {
        jsonDataString = "{\n" +
                "\"accelerometer\": {\n" +
                "\"pitch\": 21.06143642841521,\n" +
                "\"roll\": 352.71120546212956,\n" +
                "\"yaw\": 14.071197143128844\n" +
                "},\n" +
                "\"ts\": 1604283424496\n" +
                "}";

        accelerometerData = new AccelerometerData(jsonDataString);
    }

    @Test
    public void testDataParsing() {
        Assert.assertEquals(21.06143642, accelerometerData.getPitch(), 0.001d);
        Assert.assertEquals(352.7112054, accelerometerData.getRoll(), 0.001d);
        Assert.assertEquals(14.0711971431, accelerometerData.getYaw(), 0.001d);
        Assert.assertTrue(accelerometerData.getTimestamp().equals(new Long(1604283424496l)));
        System.out.println("Accelerometer data parsing succeeded " + jsonDataString);
    }
}
