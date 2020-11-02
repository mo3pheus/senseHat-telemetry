package bootstrap;

import domain.ApplicationConfig;
import domain.GlobalConstants;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.EnhancedPatternLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Driver {
    public static final String SEPARATOR =
            "==============================================================";

    public static Logger logger = LoggerFactory.getLogger(Driver.class);

    public static void main(String[] args) {
        try {
            ApplicationConfig applicationConfig = new ApplicationConfig(args);
            configureLogging(applicationConfig.getLogDirectory(), applicationConfig.getLogLevel());
            (new Application(applicationConfig)).start();
        } catch (ArgumentParserException e) {
            e.printStackTrace();
            System.exit(GlobalConstants.SYSTEM_EXIT_CONFIG_FAILURE);
        }
    }

    public static String configureLogging(String logDirectory, String logLevel) {
        DailyRollingFileAppender dailyRollingFileAppender = new DailyRollingFileAppender();

        String logFilename = "";
        switch (logLevel) {
            case "DEBUG": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.DEBUG_INT));
            }
            case "WARN": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.WARN_INT));
            }
            case "ERROR": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.ERROR_INT));
            }
            default: {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.INFO_INT));
            }
            break;
        }

        logFilename = logDirectory + GlobalConstants.LOG_FILE_NAME;

        System.out.println("Log files written out at " + logFilename);
        dailyRollingFileAppender.setFile(logFilename);
        dailyRollingFileAppender.setLayout(new EnhancedPatternLayout("%d [%t] %-5p %c - %m%n"));

        dailyRollingFileAppender.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(dailyRollingFileAppender);
        return dailyRollingFileAppender.getFile();
    }

    public static Properties getProjectProperties(String propertiesFilePath) throws IOException {
        logger.info("Properties file specified at location = " + propertiesFilePath);
        FileInputStream projFile   = new FileInputStream(propertiesFilePath);
        Properties      properties = new Properties();
        properties.load(projFile);
        return properties;
    }
}
