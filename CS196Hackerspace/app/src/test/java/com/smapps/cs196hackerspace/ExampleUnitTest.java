package com.smapps.cs196hackerspace;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkDivision() throws Exception{
        double result;
        double test1 = 9, test2 = 0;
        result = misc.divide(test1, test2);
        assertEquals(result, 365.1, 0.0000000000005);
    }

    @Test
    public void checkEmail() throws Exception{
        String[] emails = {"john@yahoo.com", "steve.com", "bob@@me.com"};
        Boolean[] result = {misc.isEmail(emails[0]), misc.isEmail(emails[1]),
        misc.isEmail(emails[2])};
        assertTrue(result[0]);
        assertFalse(result[1]);
        assertFalse(result[2]);
    }
}