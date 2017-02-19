/**
 * 
 * MIT License
 *
 * Copyright (c) 2017 BotMill.io
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
package co.aurasphere.botmill.datastore.model;

import java.io.Serializable;

/**
 * The Class KeyValuePair.
 */
public class KeyValuePair implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	private String id;
	
	/** The key. */
	private String key;
	
	/** The value. */
	private Object value;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	
	
	
}
