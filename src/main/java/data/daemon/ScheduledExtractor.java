package data.daemon;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.ApplicationConfig;
import domain.Response;
import http.DataExtractor;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledExtractor implements Runnable {
    private ApplicationConfig applicationConfig;
    private Logger            logger = LoggerFactory.getLogger(ScheduledExtractor.class);
    private DataExtractor     dataExtractor;
    private IMqttClient       telemetryBeacon;

    public ScheduledExtractor(DataExtractor dataExtractor, ApplicationConfig applicationConfig) throws MqttException {
        this.dataExtractor     = dataExtractor;
        this.applicationConfig = applicationConfig;
        this.telemetryBeacon   = new MqttClient(applicationConfig.getMqttTransmissionEndpoint(), "senseHat");

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setMaxInflight(3);
        options.setKeepAliveInterval(300);
        options.setUserName(applicationConfig.getMqttUsername());
        options.setPassword(applicationConfig.getMqttPassword().toCharArray());
        telemetryBeacon.connect(options);

        logger.info("ScheduledExtractor configured correctly");
        logger.info("Data collection enabled for following fields => " + applicationConfig.getSenseDataFields());
    }

    @Override
    public void run() {
        Thread.currentThread().setName("scheduledExtractor");
        try {
            Response    response    = dataExtractor.getSenseHatResponse();
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(1);

            ObjectMapper objectMapper = new ObjectMapper();
            String       payload      = objectMapper.writeValueAsString(response);

            mqttMessage.setPayload(payload.getBytes());
            telemetryBeacon.publish(applicationConfig.getMqttTransmissionTopic(), mqttMessage);

            logger.info("Sense hat telemetry running normally. Data sent to " + applicationConfig.getMqttTransmissionTopic());
        } catch (Exception e) {
            logger.error("Could not execute scheduled extract.", e);
        }
    }
}
