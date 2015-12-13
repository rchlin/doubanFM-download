package org.rcl.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Lin on 2015/6/20.
 */
public class PathTest {

    @Test
    public void testIsFileExist() throws Exception {
        boolean isExist = Path.isFileExist("C:\\Program Files\\Java\\jre7");
        assertEquals(true, isExist);
    }

    @Test
    public void testChildNumber() throws Exception {
        int count = Path.childNumber("C:\\Program Files\\Java\\jre7");
        assertEquals(9, count);
    }
}