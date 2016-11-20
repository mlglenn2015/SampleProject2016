package prv.mark.project.common.util;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.Optional;
import java.util.regex.Pattern;


/**
 * String utility methods.
 * <p>
 * <p>
 * This class is an adapter for the {@link org.apache.commons.lang3.StringUtils} class from Apache Commons Lang.
 * </p>
 *
 * @author mlglenn
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);
    public static final String EMPTY = "";
    public static final String BLANK = " ";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String SLASH = "/";
    public static final String APPLICATION_EXCEPTION = "!!! APPLICATION_EXCEPTION !!!";


    /**
     * Return a boolean based on the given string is empty.
     *
     * @param str {@link java.lang.String} to evaluate
     * @return boolean
     */
    public static boolean isBlank(String str) {
        return org.apache.commons.lang3.StringUtils.isBlank(str);
    }

    /**
     * Return a boolean based on the given string is null.
     *
     * @param str {@link java.lang.String} to evaluate
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return org.apache.commons.lang3.StringUtils.isEmpty(str);
    }

    /**
     * Return the value of the given string, or an empty string if given string is empty or null.
     *
     * @param str {@link String} to evaluate
     * @return Empty {@link String}
     */
    public static String safeString(String str) {
        // More efficient to use Java 8 Optional.
        return Optional.ofNullable(str).orElse(EMPTY);
    }

    /**
     * @param patternStr The regex pattern to match
     * @param matchStr   The string compared to the regex pattern
     * @return boolean
     */
    public static boolean testRegEx(String patternStr, String matchStr) {
        boolean result = false;
        try {
            if (Pattern.matches(patternStr, matchStr))
                result = true;
        } catch (Exception e) {
            LOGGER.debug("Exception caught: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid argument.");
        }
        return result;
    }

    /**
     * For example, testStr with value of "3" when you want it padded as "03"
     *
     * @param testInt   The Integer value to pad
     * @param minDigits The minimum number of digits the padded string should be
     * @param maxDigits The maximum number of digits the padded string should be
     * @return java.lang.String
     */
    public static String getZeroPadStringFromInt(Integer testInt, int minDigits, int maxDigits) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(minDigits);
        nf.setMaximumIntegerDigits(maxDigits);
        return nf.format(testInt);
    }

    /**
     * Pad a String with a specified character for a specified length
     *
     * @param str     The string to pad
     * @param size    The total length of the returned string
     * @param padChar The character to use as padding
     * @return left-padded original string, or null
     */
    public static String getZeroPadString(String str, int size, char padChar) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, size, padChar);
    }

    /**
     * Test whether 1 string contains another
     *
     * @param container Enclosing string
     * @param content   String to search for
     * @return boolean
     */
    public static boolean containsIgnoreCase(String container, String content) {
        return org.apache.commons.lang3.StringUtils.containsIgnoreCase(container, content);
    }
}
