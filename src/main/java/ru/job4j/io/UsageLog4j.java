package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        byte byteValue = 1;
        short shortValue = 2;
        int intValue = 3;
        long longValue = 4;
        float floatValue = 5.5f;
        double doubleValue = 6.6;
        char charValue = 'a';
        boolean booleanValue = true;
        LOG.debug(
                "byteValue: {}, shortValue: {}, intValue: {}, longValue: {}, floatValue: {}, doubleValue: {}, charValue: {}, booleanValue: {}",
                byteValue,
                shortValue,
                intValue,
                longValue,
                floatValue,
                doubleValue,
                charValue,
                booleanValue
        );
    }
}
