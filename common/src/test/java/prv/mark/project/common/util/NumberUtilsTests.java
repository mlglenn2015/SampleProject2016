package prv.mark.project.common.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import java.math.BigDecimal;

import static org.junit.Assert.*;


/**
 * Junit tests for the {@link NumberUtils} class.
 *
 * Created by mlglenn on 11/15/2016.
 */
public class NumberUtilsTests extends AbstractAppTransactionalTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(NumberUtilsTests.class);

    @Test
    public void defaultTest() {
        LOGGER.debug("NumberUtilsTests.defaultTest()");
    }

    @Test
    public void testMyToBigDecimal() {
        LOGGER.debug("NumberUtilsTests.testMyToBigDecimal()");
        BigDecimal bigDecimalVal = NumberUtils.myToBigDecimal(new String());
        assertNotNull(bigDecimalVal);
        assertEquals(bigDecimalVal.toString(), "0.00");

        bigDecimalVal = NumberUtils.myToBigDecimal("X.XX");
        assertNotNull(bigDecimalVal);
        assertEquals(bigDecimalVal.toString(), "0.00");

        bigDecimalVal = NumberUtils.myToBigDecimal("12");
        assertNotNull(bigDecimalVal);
        assertEquals(bigDecimalVal.toString(), "12.000000000000");
    }

    @Test
    public void testBigDecimalWithDouble() {
        Double doubleVal = new Double(123456789);
        BigDecimal bigDecimal = NumberUtils.myToBigDecimal(doubleVal);
        assertNotNull(bigDecimal);
        LOGGER.debug("Value is {}", bigDecimal);
    }

    @Test
    public void testBigDecimalWithFloat() {
        Float floatVal = new Float(123456789.00);
        BigDecimal bigDecimal = NumberUtils.myToBigDecimal(floatVal);
        assertNotNull(bigDecimal);
        LOGGER.debug("Value is {}", bigDecimal);
    }

    @Test
    public void testBigDecimalWithLong() {
        Long longVal = new Long(123456789);
        BigDecimal bigDecimal = NumberUtils.myToBigDecimal(longVal);
        assertNotNull(bigDecimal);
        LOGGER.debug("Value is {}", bigDecimal);
    }

    @Test
    public void testBigDecimalWithInteger() {
        Integer intVal = new Integer(123456789);
        BigDecimal bigDecimal = NumberUtils.myToBigDecimal(intVal);
        assertNotNull(bigDecimal);
        LOGGER.debug("Value is {}", bigDecimal);
    }

    @Test
    public void testToInt() {
        String val = "123";
        assertTrue(NumberUtils.isNumber(val));
        BigDecimal bigDecimal = NumberUtils.myToBigDecimal(NumberUtils.toInt(val));
        assertNotNull(bigDecimal);
        LOGGER.debug("Value is {}", bigDecimal);
    }

    @Test
    public void testToLong() {
        String val = "456";
        assertTrue(NumberUtils.isNumber(val));
        BigDecimal bigDecimal = NumberUtils.myToBigDecimal(NumberUtils.toLong(val));
        assertNotNull(bigDecimal);
        LOGGER.debug("Value is {}", bigDecimal);
    }

    @Test
    public void testToLongFromInteger() {
        Integer val = new Integer(456);
        Long longVal = 456L;
        Long longValue = NumberUtils.myToLong(val);
        assertEquals(longValue, longVal);
        LOGGER.debug("Value is {}", longValue);
    }

    @Test
    public void testLong() {
        long val = 456;
        assertTrue(NumberUtils.isPositiveNumber(val));
    }

    @Test
    public void testMyToFloat() {
        float floatVal = NumberUtils.myToFloat(new String());
        assertNotNull(floatVal);
        assertTrue(0.0 == floatVal);

        String inputVal = "";
        floatVal = NumberUtils.myToFloat(inputVal);
        assertNotNull(floatVal);
        assertTrue(0.0 == floatVal);

        inputVal = "1.0";
        floatVal = NumberUtils.myToFloat(inputVal);
        assertNotNull(floatVal);
        assertTrue(1.0 == floatVal);
    }

    @Test
    public void testMyToInt() {
        int intVal = NumberUtils.myToInt(new String());
        assertNotNull(intVal);
        assertTrue(0 == intVal);

        String inputVal = "";
        intVal = NumberUtils.myToInt(inputVal);
        assertNotNull(intVal);
        assertTrue(0 == intVal);

        inputVal = "1";
        intVal = NumberUtils.myToInt(inputVal);
        assertNotNull(intVal);
        assertTrue(1 == intVal);
    }

    @Test
    public void testMyToLong() {
        String inputVal = "";
        long longVal = NumberUtils.myToLong(inputVal);
        assertNotNull(longVal);
        assertTrue(0L == longVal);

        inputVal = "0";
        longVal = NumberUtils.myToLong(inputVal);
        assertNotNull(longVal);
        assertTrue(0L == longVal);

        Integer intVal = new Integer("0");
        longVal = NumberUtils.myToLong(intVal);
        assertNotNull(longVal);
        assertTrue(0L == longVal);

        //longVal = NumberUtils.myToLong(new Integer("X"));
        //assertNotNull(longVal);
        //assertTrue(0L == longVal);

        intVal = new Integer("1");
        longVal = NumberUtils.myToLong(intVal);
        assertNotNull(longVal);
        assertTrue(1L == intVal);
    }

    @Test
    public void testIsPositiveNumber() {
        long val = 456;
        assertTrue(NumberUtils.isPositiveNumber(val));
        val = -456;
        assertFalse(NumberUtils.isPositiveNumber(val));
    }
}
