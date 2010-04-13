package test.org.hrodberaht.springext.samplebean;

import org.springframework.stereotype.Component;

/**
 * Simple Java Utils
 *
 * @author Robert Alexandersson
 *         2010-apr-13 21:34:04
 * @version 1.0
 * @since 1.0
 */
@Component
public class SpringWiredSubServiceImpl implements SpringWiredSubService{
    @Override
    public String subServiceInfo() {
        return "SubService Information";
    }
}
