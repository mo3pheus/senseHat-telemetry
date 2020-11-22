package domain;

import lombok.Getter;
import lombok.Setter;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.Serializable;

public class ApplicationConfig implements Serializable {
    @Getter
    @Setter
    private Integer collectionIntervalSeconds;
    @Getter
    @Setter
    private String  senseDataUrl;
    @Getter
    @Setter
    private String  senseDataFields;
    @Getter
    @Setter
    private String  mqttTransmissionTopic;
    @Getter
    @Setter
    private String  mqttTransmissionEndpoint;
    @Getter
    @Setter
    private String  logLevel;
    @Getter
    @Setter
    private String  logDirectory;
    @Getter
    @Setter
    private String  mqttUsername;
    @Getter
    @Setter
    private String  mqttPassword;

    private Namespace namespace;

    public ApplicationConfig(String[] args) throws ArgumentParserException {
        this.namespace                 = buildNamespace(args);
        this.collectionIntervalSeconds = namespace.getInt("collection.interval.seconds");
        this.senseDataFields           = namespace.getString("sensehat.data.fields");
        this.senseDataUrl              = namespace.getString("sensehat.data.url");
        this.mqttTransmissionEndpoint  = namespace.getString("mqtt.transmission.endpoint");
        this.mqttTransmissionTopic     = namespace.getString("mqtt.transmission.topic");
        this.mqttUsername              = namespace.getString("mqtt.username");
        this.mqttPassword              = namespace.getString("mqtt.password");
        this.logLevel                  = namespace.getString("log.level");
        this.logDirectory              = namespace.getString("log.directory");
    }

    private Namespace buildNamespace(String[] args) throws ArgumentParserException {
        ArgumentParser argumentParser = ArgumentParsers.newFor("SenseHat").build()
                .defaultHelp(true)
                .description("background data collection process.");
        argumentParser.addArgument("--collection.interval.seconds")
                .setDefault(60)
                .type(Integer.class)
                .help("Periodic interval for data collection in seconds");
        argumentParser.addArgument("--sensehat.data.fields")
                .setDefault("temperature, humidity, accelerometer, gyroscope, pressure")
                .help("Field names for data collection.");
        argumentParser.addArgument("--sensehat.data.url")
                .setDefault("http://localhost:5000/senseHat")
                .help("REST API endpoint for reading senseHat data");
        argumentParser.addArgument("--mqtt.transmission.topic")
                .setDefault("/SenseHat/Telemetry/1")
                .help("Name of the mqtt topic on which to transmit the data.");
        argumentParser.addArgument("--mqtt.transmission.endpoint")
                .setDefault("http://localhost:1883")
                .help("Local mqtt broker, assuming mosquitto implementation");
        argumentParser.addArgument("--log.level")
                .setDefault("INFO")
                .help("Severity level for log collection");
        argumentParser.addArgument("--log.directory")
                .setDefault("/var/log/senseHatTelemetry/")
                .help("Directory where application logs will be stored.");
        argumentParser.addArgument("--mqtt.username")
                .setDefault("username")
                .help("mqtt username to write to specified broker");
        argumentParser.addArgument("--mqtt.password")
                .setDefault("password")
                .help("mqtt password to write to specified broker");

        Namespace namespace = argumentParser.parseArgs(args);
        return namespace;
    }
}
