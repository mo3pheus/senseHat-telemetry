package client;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.EnhancedPatternLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

@Ignore
public class MqttClientTest {

    private Logger logger = LoggerFactory.getLogger(MqttClientTest.class);

    /**
     * This method needs to be called for configuring console logging to monitor the program operation.
     *
     * @param debug - boolean - if true, the logger will collect debug level information. This usually generates
     *              voluminous logs only required for debugging purposes. Know what you are doing and use this
     *              sparingly!
     */
    public static void configureConsoleLogging(boolean debug) {
        ConsoleAppender ca = new ConsoleAppender();
        if (!debug) {
            ca.setThreshold(Level.toLevel(Priority.INFO_INT));
        } else {
            ca.setThreshold(Level.toLevel(Priority.DEBUG_INT));
        }
        ca.setLayout(new EnhancedPatternLayout("%-6d [%25.35t] %-5p %40.80c - %m%n"));
        ca.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(ca);
    }

    class TrustEveryoneManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) {
        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    @Before
    public void setup() {
        configureConsoleLogging(false);
    }

    @Test
    public void testMqttClient() {
        try {
            MqttClient telemetryBeacon = new MqttClient("tcp://localhost:1883", "senseHat");

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(false);
            options.setMaxInflight(3);
            options.setKeepAliveInterval(300);

            telemetryBeacon.connect(options);

            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(1);

            String payload = "Hello World.";

            mqttMessage.setPayload(payload.getBytes());
            telemetryBeacon.publish("senseHat/telemetry/2", mqttMessage);
        } catch (Exception e) {
            logger.error("Could not publish message.", e);
        }
    }
}
