/*
 * 
 * MIT License
 *
 * Copyright (c) 2016 BotMill.io
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */
package co.aurasphere.botmill.core.base;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import co.aurasphere.botmill.core.internal.util.ConfigurationUtils;

/**
 * The Class EncryptionTest.
 */
public class EncryptionTest {

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		new EncryptionSampleTest();
	}
	
	/**
	 * Test encryption.
	 */
	@Test
	public void testEncryption() {
		
		System.out.println(ConfigurationUtils.getEncryptedConfiguration().getProperty("kik.user.name"));
		System.out.println(ConfigurationUtils.getEncryptedConfiguration().getProperty("kik.api.key"));
		
		assertEquals("botmill", ConfigurationUtils.getEncryptedConfiguration().getProperty("kik.user.name"));
	}
	
	/**
	 * Test encryption custom.
	 */
	@Test
	public void testEncryptionCustom() {
		
		System.out.println(ConfigurationUtils.getEncryptedConfiguration().getProperty("kik.user.1"));
		System.out.println(ConfigurationUtils.getEncryptedConfiguration().getProperty("kik.api.2"));
		System.out.println(ConfigurationUtils.getEncryptedConfiguration().getProperty("data.strategy"));
		assertEquals("botmill", ConfigurationUtils.getEncryptedConfiguration().getProperty("kik.user.name"));
	}

}
