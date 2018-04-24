/*
 * Copyright (C) 2013 Cumulocity GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of 
 * this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package c8y.win;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

import c8y.Hardware;

public class WindowsHardwareDriverTest {
	public static final String REFERENCE_HWFILE = "/hardware.txt";
	
	@Test
	public void InitializeFromReaderTest(){
		try (InputStream is = getClass().getResourceAsStream(REFERENCE_HWFILE);
			Reader reader = new InputStreamReader(is)) {
			driver.initializeFromReader(reader);
		} catch (IOException e){
			fail(e.getMessage());
		}
		assertEquals(referenceHw, driver.getHardware());
	}

	private Hardware referenceHw = new Hardware("Dell System XPS L502X", "1CMCZP1", "unknown");
	private WindowsHardwareDriver driver = new WindowsHardwareDriver();
}
