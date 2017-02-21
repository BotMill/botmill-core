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
package co.aurasphere.botmill.core.base;

import java.io.IOException;
import java.io.Reader;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.aurasphere.botmill.core.BotDefinition;
import co.aurasphere.botmill.core.internal.util.ConfigurationUtils;

/**
 * Base BotMill servlet with shared behavior.
 * 
 * @author Donato Rimenti
 */
public class BotMillServlet extends HttpServlet {

	/**
	 * The logger.
	 */
	protected static final Logger logger = LoggerFactory
			.getLogger(BotMillServlet.class);

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initializes the BotMill servlet. The initialization will try to load the
	 * classes implementing {@link BotDefinition} in the classpath. If the
	 * classes are found and correctly loaded, the method
	 * {@link BotDefinition#defineBehaviour()} is called for each configuration.
	 */
	@Override
	public void init() {
		ConfigurationUtils.loadBotDefinitions();
		ConfigurationUtils.loadConfigurationFile();
		logger.info("BotMill servlet started.");
	}
	
	/**
	 * Utility method that converts a Reader to a String.
	 *
	 * @param reader
	 *            the reader to convert.
	 * @return a String with the content of the reader.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected static String readerToString(Reader reader) throws IOException {
		char[] arr = new char[8 * 1024];
		StringBuilder buffer = new StringBuilder();
		int numCharsRead;
		while ((numCharsRead = reader.read(arr, 0, arr.length)) != -1) {
			buffer.append(arr, 0, numCharsRead);
		}
		return buffer.toString();
	}


}
