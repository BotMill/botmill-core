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
package co.aurasphere.botmill.core.internal.util;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.jasypt.encryption.pbe.PBEStringCleanablePasswordEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.aurasphere.botmill.core.BotDefinition;
import co.aurasphere.botmill.core.annotation.Bot;
import co.aurasphere.botmill.core.annotation.BotConfiguration;
import co.aurasphere.botmill.core.annotation.BotEncryption;
import co.aurasphere.botmill.core.internal.exception.BotMillConfigurationException;

/**
 * Utility class for handling BotMill configuration.
 * 
 * @author Donato Rimenti
 * @author Alvin Reyes
 */
public class ConfigurationUtils {

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationUtils.class);

	/**
	 * The BotMill configuration.
	 */
	private static Properties configuration = new Properties();

	/** The encrypted configuration. */
	private static EncryptableProperties encryptedConfiguration;

	/** The bot definition instance list **/
	private static List<BotDefinition> botDefinitionInstances;

	/**
	 * The name of the BotMill properties file with the platform configuration.
	 * It must be placed on the classpath. If you have a Maven project, just
	 * make sure to place it in the resources folder.
	 */
	private final static String CONFIG_PATH = "botmill.properties";

	/**
	 * Instantiates a new ConfigurationUtils.
	 */
	private ConfigurationUtils() {
	}

	public static void loadBotConfig() {
		// Gets all the subclasses of bot definition.
		Reflections ref = new Reflections();
		
		Set<Class<?>> botConfigs = ref.getTypesAnnotatedWith(BotConfiguration.class);

		if(botConfigs.isEmpty()) {
			return;
		}
		// Tries to load and instantiate the bot definitions.
		for (Class<?> configClass : botConfigs) {

			try {
				configClass.newInstance();
			} catch (ClassCastException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads all classes extending {@link BotDefinition} in the classpath.
	 */
	public static void loadBotDefinitions() {

		// Gets all the subclasses of bot definition.
		Reflections ref = new Reflections();
		Set<Class<? extends BotDefinition>> botDefinitions = ref.getSubTypesOf(BotDefinition.class);
		botDefinitionInstances = new ArrayList<BotDefinition>();
		if (botDefinitions.isEmpty()) {
			logger.warn(
					"No bot definition found on the classpath. Make sure to have at least one class implementing the BotDefinition interface.");
		}

		// Tries to load and instantiate the bot definitions.
		for (Class<? extends BotDefinition> defClass : botDefinitions) {

			// Check if it's annotated too.
			if (!defClass.isAnnotationPresent(Bot.class)) {
				continue;
			}

			// If the class is abstract, skips it.
			if (Modifier.isAbstract(defClass.getModifiers())) {
				continue;
			}

			try {
				BotDefinition instance = defClass.newInstance();
				botDefinitionInstances.add(instance);
				instance.defineBehaviour();
			} catch (ClassCastException e) {
				logger.error("Class [{}] does not implement co.aurasphere.botmill.common.BotDefinition.", defClass, e);
				throw new BotMillConfigurationException(
						"Class [ " + defClass + " ] does not implement co.aurasphere.botmill.common.BotDefinition.", e);
			} catch (Exception e) {
				logger.error("Error during instantiation of class [{}].", defClass, e);
				throw new BotMillConfigurationException("Error during instantiation of class [ " + defClass + " ].", e);
			}
		}
	}

	/**
	 * Load encrypted configuration properties.
	 */
	public static void loadEncryptedConfigurationProperties() {

		Reflections ref = new Reflections();
		Set<Class<?>> botEncryptions = ref.getTypesAnnotatedWith(BotEncryption.class);
		for (Class<?> botEncryption : botEncryptions) {
			try {
				botEncryption.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads the configuration BotMill configuration properties file. In order
	 * for this to work, you need a botmill.properties file on the classpath. If
	 * you have a Maven project, just make sure to place it in the resources
	 * folder.
	 */
	public static void loadConfigurationFile() {
		try {
			if (configuration == null) {
				configuration = new Properties();
			}
			configuration.load(ConfigurationUtils.class.getClassLoader().getResourceAsStream(CONFIG_PATH));
		} catch (Exception e) {
			logger.error("Error while loading BotMill properties file ({})", CONFIG_PATH, e);
		}
	}

	/**
	 * Loads the Encrypted configuration BotMill configuration properties file.
	 * In order for this to work, you need a botmill.properties file on the
	 * classpath. If you have a Maven project, just make sure to place it in the
	 * resources folder.
	 *
	 * @param encryptor
	 *            the encryptor.
	 * @param classpathProperties
	 *            the classpath properties
	 */
	public static void loadEncryptedConfigurationFile(PBEStringCleanablePasswordEncryptor encryptor,
			String classpathProperties) {
		try {
			if (encryptedConfiguration == null) {
				encryptedConfiguration = new EncryptableProperties(encryptor);
			}
			encryptedConfiguration
					.load(ConfigurationUtils.class.getClassLoader().getResourceAsStream(classpathProperties));
		} catch (Exception e) {
			logger.error("Error while loading BotMill properties file ({})", CONFIG_PATH, e);
		}
	}

	/**
	 * Loads the Encrypted configuration BotMill configuration properties file.
	 * In order for this to work, you need a botmill.properties file on the
	 * classpath. If you have a Maven project, just make sure to place it in the
	 * resources folder.
	 *
	 * @param encryptor
	 *            the encryptor
	 */
	public static void setEncryptedPropertiesEncryptor(PBEStringCleanablePasswordEncryptor encryptor) {
		try {
			if (encryptedConfiguration == null) {
				encryptedConfiguration = new EncryptableProperties(encryptor);
			}
			encryptedConfiguration.load(ConfigurationUtils.class.getClassLoader().getResourceAsStream(CONFIG_PATH));
		} catch (Exception e) {
			logger.error("Error while loading BotMill properties file ({})", CONFIG_PATH, e);
		}
	}

	/**
	 * Gets the {@link #configuration}.
	 *
	 * @return the {@link #configuration}.
	 */
	public static Properties getConfiguration() {
		return configuration;
	}

	/**
	 * Gets the {@link #encryptedConfiguration}.
	 *
	 * @return the {@link #encryptedConfiguration}.
	 */
	public static EncryptableProperties getEncryptedConfiguration() {
		return encryptedConfiguration;
	}

	/**
	 * Sets the {@link #configuration}.
	 *
	 * @param configuration
	 *            the {@link #configuration} to set.
	 */
	public static void setConfiguration(Properties configuration) {
		ConfigurationUtils.configuration = configuration;
	}

	/**
	 * Sets the {@link #encryptedConfiguration}.
	 *
	 * @param encryptedConfiguration
	 *            the new encrypted configuration
	 */
	public static void setEncryptedConfiguration(EncryptableProperties encryptedConfiguration) {
		ConfigurationUtils.encryptedConfiguration = encryptedConfiguration;
	}

	/**
	 * Gets the bot definitions.
	 *
	 * @return the bot definitions
	 */
	public static List<BotDefinition> getBotDefinitionInstances() {
		return botDefinitionInstances;
	}

	/**
	 * Sets the Bot Definition Instances
	 * 
	 * @param botDefinitionInstances
	 *            the list of bot definition instances
	 */
	public static void setBotDefinitionInstance(List<BotDefinition> botDefinitionInstances) {
		ConfigurationUtils.botDefinitionInstances = botDefinitionInstances;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConfigurationUtils []";
	}

}
