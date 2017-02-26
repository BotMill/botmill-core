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
package co.aurasphere.botmill.core.test;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.aurasphere.botmill.core.datastore.adapter.MapAdapter;
import co.aurasphere.botmill.core.datastore.adapter.MongoDBAdapter;
import co.aurasphere.botmill.core.datastore.model.KeyValuePairBuilder;
import co.aurasphere.botmill.core.internal.util.ConfigurationUtils;

/**
 * The Class DataAdapterTest.
 */
public class DataAdapterTest {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DataAdapterTest.class);
	
	/**
	 * Test map.
	 */
	@Test
	@Ignore
	public void testMap() {
		
		MapAdapter mapAdapter = new MapAdapter();
		mapAdapter.buildSession("123");
		mapAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("key1").setValue("value").build());
		mapAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("key2").setValue("value1").build());

		mapAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("email").setValue("alvin@reyes.com").build());
		mapAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("email").setValue("alvin@reyes.com").build());
		assertEquals("alvin@reyes.com", mapAdapter.getData("123", "email").getValue().toString());
	}
	
	/**
	 * Test mongo db.
	 */
	@Test
	@Ignore
	public void testMongoDb() {
		ConfigurationUtils.loadConfigurationFile();
		MongoDBAdapter mongoDbAdapter = new MongoDBAdapter();
		mongoDbAdapter.buildSession("1234");
		mongoDbAdapter.putData("1234", KeyValuePairBuilder.getInstance().setKey("email").setValue("alvin@reyes.com").build());
		
		assertEquals("alvin@reyes.com", mongoDbAdapter.getData("1234", "email").getValue().toString());
	}
}
