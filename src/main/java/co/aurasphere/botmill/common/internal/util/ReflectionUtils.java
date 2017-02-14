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
package co.aurasphere.botmill.common.internal.util;

import java.lang.reflect.Modifier;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.aurasphere.botmill.common.BotDefinition;
import co.aurasphere.botmill.common.internal.exception.BotMillConfigurationException;

/**
 * Utility class for handling reflection inside BotMill.
 * 
 * @author Donato Rimenti
 */
public class ReflectionUtils {

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ReflectionUtils.class);

	/**
	 * Loads all classes extending {@link BotDefinition} in the classpath.
	 */
	public static void loadBotDefinitions() {

		// Gets all the subclasses of bot definition.
		Reflections ref = new Reflections();
		Set<Class<? extends BotDefinition>> botDefinitions = ref
				.getSubTypesOf(BotDefinition.class);
		if (botDefinitions.isEmpty()) {
			logger.warn("No bot definition found on the classpath. Make sure to have at least one class implementing the BotDefinition interface.");
		}

		// Tries to load and instantiate the bot definitions.
		for (Class<? extends BotDefinition> defClass : botDefinitions) {
			
			// If the class is abstract, skips it.
			if(Modifier.isAbstract(defClass.getModifiers())){
				continue;
			}
			
			try {
				BotDefinition instance = defClass.newInstance();
				instance.defineBehavior();
			} catch (ClassCastException e) {
				logger.error(
						"Class [{}] does not implement co.aurasphere.botmill.common.BotDefinition.",
						defClass, e);
				throw new BotMillConfigurationException(
						"Class [ "
								+ defClass
								+ " ] does not implement co.aurasphere.botmill.common.BotDefinition.",
						e);
			} catch (Exception e) {
				logger.error("Error during instantiation of class [{}].",
						defClass, e);
				throw new BotMillConfigurationException(
						"Error during instantiation of class [ " + defClass
								+ " ].", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReflectionUtils []";
	}

}
