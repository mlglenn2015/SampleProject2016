package prv.mark.project.common.util;

import java.math.BigDecimal;

/**
 *
 * Created by mlglenn on 10/24/2016.
 */
public final class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {


    /**
     *
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimal(final Double value) {
        if (value == null) {
            return new BigDecimal(0.00);
        }
        BigDecimal returnVal = new BigDecimal(value);
        return returnVal;
    }

    /**
     *
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimal(final Float value) {
        if (value == null) {
            return new BigDecimal(0.00);
        }
        BigDecimal returnVal = new BigDecimal(value);
        return returnVal;
    }

    /**
     *
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimal(final Long value) {
        if (value == null) {
            return new BigDecimal(0.00);
        }
        BigDecimal returnVal = new BigDecimal(value);
        return returnVal;
    }

    /**
     *
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimal(final Integer value) {
        if (value == null) {
            return new BigDecimal(0.00);
        }
        BigDecimal returnVal = new BigDecimal(value);
        return returnVal;
    }

    /**
     * Converts a passed in {@link String} to an integer.
     *
     * @param str {@link String} (final) representing the input string
     * @return {@link Integer} containing the integer numeric value, or a 0 if it fails
     */
    public static int toInt(final String str) {

        return org.apache.commons.lang3.math.NumberUtils.toInt(str, 0);
    }

    /**
     * Converts a {@link String} passed in to a Long.
     *
     * @param str {@link String} s representing the input string
     * @return {@link Long} containing the Long numeric value, or a 0 if it fails
     */
    public static long toLong(final String str) {

        return org.apache.commons.lang3.math.NumberUtils.toLong(str, 0L);
    }

    /**
     * Checks whether a {@link String} a valid Java number.
     *
     * @param str {@link String} the <code>String</code> to check
     * @return <code>true</code> if the string is a correctly formatted number
     */
    public static boolean isNumber(final String str) {
        return org.apache.commons.lang3.math.NumberUtils.isNumber(str);
    }


    /**
     * Determines whether a given number is > 0.
     *
     * @param nbr (final long)
     * @return <code>true</code> if the number is > 0
     */
    public static boolean isPositiveNumber(final long nbr) {
        if ((nbr > 0) && (nbr % 1 == 0)) {
            return true;
        }
        return false;
    }

}
