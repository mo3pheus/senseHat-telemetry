package domain;

public class GlobalConstants {
    public static final String[] VALID_ENDPOINTS                 = {"temperature", "humidity", "accelerometer", "gyroscope", "pressure"};
    public static final int      SYSTEM_EXIT_CONFIG_FAILURE      = 2;
    public static final int      SYSTEM_EXIT_APPLICATION_FAILURE = 1;
    public static final String   LOG_FILE_NAME                   = "senseHat.telemetry.log";
}
