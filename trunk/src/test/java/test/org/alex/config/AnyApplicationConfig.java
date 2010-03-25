package test.org.alex.config;

import org.alex.config.ConfigBase;
import org.alex.config.ConfigItem;
import org.alex.config.MasterConfig;
import org.alex.logging.SimpleLogger;

import java.text.ParseException;
import java.util.Date;


/**
 * Spring extensions
 *
 * @author Robert Alexandersson
 * @version 1.0
 * @since 1.0
 */
public class AnyApplicationConfig extends ConfigBase {
    private static final String DEFAULT_CONFIG = "classpath:/test/org/alex/config/basicConfig.properties";

    public AnyApplicationConfig() throws ParseException {
        this(DEFAULT_CONFIG);   
    }

    public AnyApplicationConfig(String propertyPath) throws ParseException {
        LOGGER = SimpleLogger.getInstance(AnyApplicationConfig.class);
        this.propertyPath = propertyPath == null ? DEFAULT_CONFIG : propertyPath;
        MasterConfig.registerConfig(this);
    }



    public interface ApplicationState {
        ConfigItem<Boolean> A_BOOLEAN = new ConfigItem<Boolean>(Boolean.class, "anyapp.aboolean");
        ConfigItem<String> A_STRING = new ConfigItem<String>(String.class, "anyapp.astring");
        ConfigItem<Date> A_DATE = new ConfigItem<Date>(Date.class, "anyapp.adate");
        ConfigItem<Integer> A_INTEGER = new ConfigItem<Integer>(Integer.class, "anyapp.ainteger");
        ConfigItem<Long> A_LONG = new ConfigItem<Long>(Long.class, "anyapp.along");
    }



    public static void initConfig() throws ParseException {
        new AnyApplicationConfig();
    }
    public static void initConfig(String resource) throws ParseException {
        new AnyApplicationConfig(resource);   
    }




}