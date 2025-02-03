package dev.mzcy.api.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utility {

    public boolean inRange(Number value, Number min, Number max) {
        return value.doubleValue() >= min.doubleValue() && value.doubleValue() <= max.doubleValue();
    }

}
