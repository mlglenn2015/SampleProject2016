package prv.mark.project.common.util;

import org.junit.Test;
import prv.mark.project.testutils.junit.AbstractAppTransactionalTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class StringUtilsTests extends AbstractAppTransactionalTest {

    @Test
    public void defaultTest() {}

    /*@Test TODO
    public void testSafeString() {
        assertNotNull(StringUtils.safeString(null));
        assertEquals(StringUtils.safeString(null), StringUtils.EMPTY);
    }

    @Test
    public void testEmpty() {
        assertEquals("", StringUtils.EMPTY);
        assertEquals(new String(), StringUtils.EMPTY);
    }

    @Test
    public void testIsBlank() {
        assertTrue(StringUtils.isBlank(StringUtils.BLANK));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(StringUtils.isEmpty(null));
    }

    @Test
    public void testIsNotEmpty() {
        assertTrue(StringUtils.isNotEmpty(" f oo"));
    }

    @Test
    public void testRegEx() {
        assertTrue(StringUtils.testRegEx("\\d{16}", "0036003123456789"));
    }

    @Test
    public void testIsNumeric() {
        assertTrue(StringUtils.isNumeric("12345678"));
    }

    @Test
    public void testNegativeIsNumeric() {
        assertFalse(StringUtils.isNumeric("X"));
    }

    @Test
    public void testZeroPadStringFromInt() {
        assertNotNull(StringUtils.getZeroPadStringFromInt(3, 2, 2));
        assertEquals(StringUtils.getZeroPadStringFromInt(3, 2, 2), "03");
    }

    @Test
    public void testGetZeroPadString() {
        assertNotNull(StringUtils.getZeroPadString("ABC", 6, 'Z'));
        assertEquals(StringUtils.getZeroPadString("ABC", 6, 'Z'), "ZZZABC");
    }

    @Test
    public void testContainsIgnoreCase() {
        assertTrue(StringUtils.containsIgnoreCase("RACECAR", "ACE"));
    }

    @Test
    public void testNegativeContainsIgnoreCase() {
        assertFalse(StringUtils.containsIgnoreCase("RACECAR", "RAT"));
    }*/

}
