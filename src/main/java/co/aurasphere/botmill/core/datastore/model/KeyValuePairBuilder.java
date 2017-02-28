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
package co.aurasphere.botmill.core.datastore.model;




/**
 * The Class TextMessageBuilder.
 * 
 * @author Alvin P. Reyes
 */
public class KeyValuePairBuilder {
	
	/** The text message. */
	private static KeyValuePair keyValuePair;

	/** The instance. */
	private static KeyValuePairBuilder instance;
	
	/**
	 * Gets the single instance of TextMessageBuilder.
	 *
	 * @return single instance of TextMessageBuilder
	 */
	public static KeyValuePairBuilder getInstance() {
		if (instance == null) {
			instance = new KeyValuePairBuilder();
		}
		keyValuePair = new KeyValuePair();
		return instance;
	}
	
	/**
	 * Instantiates a new text message builder.
	 */
	public KeyValuePairBuilder() {
		keyValuePair = new KeyValuePair();
	}
	
	/**
	 * Sets the to.
	 *
	 * @param key the key
	 * @return the text message builder
	 */
	public KeyValuePairBuilder setKey(String key) {
		keyValuePair.setKey(key);
		return this;
	}
	
	/**
	 * Sets the body.
	 *
	 * @param value the value
	 * @return the text message builder
	 */
	public KeyValuePairBuilder setValue(Object value) {
		keyValuePair.setValue(value);
		return this;
	}
	
	/**
	 * Builds the.
	 *
	 * @return the key value pair
	 */
	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.kik.intf.Buildable#build()
	 */
	public KeyValuePair build() {
		return keyValuePair;
	}
}
