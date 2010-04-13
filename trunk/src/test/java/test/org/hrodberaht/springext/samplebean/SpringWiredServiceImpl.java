package test.org.hrodberaht.springext.samplebean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Simple Java Utils
 *
 * @author Robert Alexandersson
 *         2010-apr-13 20:59:17
 * @version 1.0
 * @since 1.0
 */
@Component
public class SpringWiredServiceImpl implements SpringWiredService{

    private String serviceText = null;

    @Autowired
    private SpringWiredSubService subService;

    @Override
    public void doSpringThing() {
        serviceText = "Test of Service";
    }

    @Override
    public String getSpringThing() {
        return serviceText;
    }

    @Override
    public String getSubServiceSpringThing() {
        return subService.subServiceInfo();
    }
}
