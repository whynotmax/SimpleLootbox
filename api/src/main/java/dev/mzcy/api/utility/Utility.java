package dev.mzcy.api.utility;

import lombok.experimental.UtilityClass;

/**
 * Utility class providing utility methods.
 */
@UtilityClass
public class Utility {

    /**
     * Check if a value is in a range
     *
     * @param value the value to check
     * @param min   the minimum value
     * @param max   the maximum value
     * @return true if the value is in the range, false otherwise
     */
    public boolean inRange(Number value, Number min, Number max) {
        return value.doubleValue() >= min.doubleValue() && value.doubleValue() <= max.doubleValue();
    }

}
