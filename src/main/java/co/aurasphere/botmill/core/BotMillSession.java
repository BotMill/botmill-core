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
package co.aurasphere.botmill.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.aurasphere.botmill.core.datastore.adapter.DataAdapterType;
import co.aurasphere.botmill.core.datastore.adapter.DataAdapter;
import co.aurasphere.botmill.core.datastore.adapter.DataAdapterFactory;
import co.aurasphere.botmill.core.datastore.model.KeyValuePair;
import co.aurasphere.botmill.core.datastore.model.KeyValuePairBuilder;
import co.aurasphere.botmill.core.datastore.model.Session;
import co.aurasphere.botmill.core.internal.util.ConfigurationUtils;


/**
 * The Class BotMillSession.
 */
public class BotMillSession {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(BotMillSession.class);
	
	/** The data adapter. */
	private static DataAdapter dataAdapter;
	
	/** The instance. */
	private static BotMillSession instance;
	
	/**
	 * Gets the single instance of BotMillSession.
	 *
	 * @return single instance of BotMillSession
	 */
	public static BotMillSession getInstance() {
		if (instance == null) {
			//	Making sure we only have one instance.
			instance = new BotMillSession();
		}
		return instance;
	}
	
	/**
	 * Instantiates a new bot mill session.
	 */
	private BotMillSession() {
		//	check the data adapter type from the properties.
		//	map, hsql, mongodb, rdbms
		String dataAdapterType = ConfigurationUtils.getEncryptedConfiguration().getProperty("data.adapter.type");
		if(dataAdapterType == null || dataAdapterType.equals("map")) {
			defineDataAdapter(DataAdapterType.MAP);
		}else if(dataAdapterType.equals("mongodb")){
			defineDataAdapter(DataAdapterType.MONGODB);
		}else if(dataAdapterType.equals("hsql")) {
			defineDataAdapter(DataAdapterType.HSQL);
		}else if(dataAdapterType.equals("rdbms")) {
			defineDataAdapter(DataAdapterType.RBDMS);
		}else {
			logger.info("No Data Adapter Type specified on the botmill.properties, defaulting to Map");
			defineDataAdapter(DataAdapterType.MAP);
		}
	}
	
	/**
	 * Define data adapter.
	 *
	 * @param adapterType the adapter type
	 */
	private void defineDataAdapter(DataAdapterType adapterType) {
		switch (adapterType) {
		case MAP:
			dataAdapter = DataAdapterFactory.getMapAdapter();
			break;
		case MONGODB:
			dataAdapter = DataAdapterFactory.getMongoDbAdapter();
			break;
		case HSQL:
			dataAdapter = DataAdapterFactory.getHsqlAdapter();
			break;
		case RBDMS:
			dataAdapter = DataAdapterFactory.getRdbmsAdapter();
			break;
		default:
			logger.error("Data Adapter not defined.");
		}
	}

	/**
	 * Builds the session.
	 *
	 * @param identifier the identifier
	 * @return the session
	 */
	public Session buildSession(String identifier){
		return dataAdapter.buildSession(identifier);
	}
	
	/**
	 * Destroy session.
	 *
	 * @param identifier the identifier
	 */
	public void destroySession(String identifier) {
		dataAdapter.destroySession(identifier);
	}

	/**
	 * Gets the session.
	 *
	 * @param identifier the identifier
	 * @return the session
	 */
	public Session getSession(String identifier) {
		return dataAdapter.getSession(identifier);
	}
	
	/**
	 * Put session data.
	 *
	 * @param identifier the identifier
	 * @param keyValuePair the key value pair
	 * @return the session
	 */
	public Session putSessionData(String identifier, KeyValuePair keyValuePair) {
		return dataAdapter.putData(identifier,keyValuePair);
	}
	
	/**
	 * Removes the session data.
	 *
	 * @param identifier the identifier
	 * @param key the key
	 */
	public void removeSessionData(String identifier, String key) {
		dataAdapter.removeData(identifier,key);
	}
	
	/**
	 * Gets the session data.
	 *
	 * @param identifier the identifier
	 * @param key the key
	 * @return the session data
	 */
	public KeyValuePair getSessionData(String identifier, String key) {
		return dataAdapter.getData(identifier,key);
	}
	
	/**
	 * Adds the key value pair.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the key value pair
	 */
	public KeyValuePair addKeyValuePair(String key, String value) {
		return KeyValuePairBuilder.getInstance().setKey(key).setValue(value).build();
	}
}
