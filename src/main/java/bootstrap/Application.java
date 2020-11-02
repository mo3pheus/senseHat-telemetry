package bootstrap;

import data.daemon.ScheduledExtractor;
import domain.ApplicationConfig;
import http.DataExtractor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Application extends Thread {
    private Logger             logger = LoggerFactory.getLogger(Application.class);
    private ApplicationConfig  applicationConfig;
    private DataExtractor      dataExtractor;
    private ScheduledFuture<?> executorService;

    public Application(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
        this.dataExtractor     = new DataExtractor(applicationConfig);
    }

    @Override
    public void run() {
        super.run();
        logger.info("Application config  - mqtt topic name = " + applicationConfig.getMqttTransmissionTopic());
        try {
            try {
                this.executorService = Executors.newSingleThreadScheduledExecutor()
                        .scheduleAtFixedRate(new ScheduledExtractor(dataExtractor, applicationConfig),
                                0l,
                                applicationConfig.getCollectionIntervalSeconds(),
                                TimeUnit.SECONDS);
            } catch (MqttException e) {
                logger.error("Exception in sending telemetry data to mqtt", e);
            }
        } catch (Exception e) {
            logger.error("Could not execute scheduled extract.", e);
        }
    }
}
