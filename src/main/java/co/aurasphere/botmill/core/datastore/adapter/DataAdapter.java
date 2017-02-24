/*
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
 */
package co.aurasphere.botmill.core.datastore.adapter;

import co.aurasphere.botmill.core.datastore.model.KeyValuePair;
import co.aurasphere.botmill.core.datastore.model.Session;


/**
 * The Interface DataAdapter.
 */
public interface DataAdapter {
	/**
	 * Setup.
	 */
	void setup();

	/**
	 * Builds the session.
	 *
	 * @param identifier
	 *            the identifier
	 * @return the session
	 */
	Session buildSession(String identifier);

	/**
	 * Destroy session.
	 *
	 * @param identifier
	 *            the identifier
	 */
	void destroySession(String identifier);

	/**
	 * Gets the session.
	 *
	 * @param identifier
	 *            the identifier
	 * @return the session
	 */
	Session getSession(String identifier);

	/**
	 * Put data.
	 *
	 * @param identifier
	 *            the identifier
	 * @param keyValuePair
	 *            the key value pair
	 * @return the session
	 */
	Session putData(String identifier, KeyValuePair keyValuePair);

	/**
	 * Removes the data.
	 *
	 * @param identifier
	 *            the identifier
	 * @param key
	 *            the key
	 */
	void removeData(String identifier, String key);

	/**
	 * Gets the data.
	 *
	 * @param identifier
	 *            the identifier
	 * @param key
	 *            the key
	 * @return the data
	 */
	KeyValuePair getData(String identifier, String key);
}
