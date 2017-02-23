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
import co.aurasphere.botmill.core.datastore.adapter.AdapterType;
import co.aurasphere.botmill.core.datastore.adapter.DataAdapter;
import co.aurasphere.botmill.core.datastore.adapter.HsqlAdapter;
import co.aurasphere.botmill.core.datastore.adapter.MapAdapter;
import co.aurasphere.botmill.core.datastore.adapter.MongoDBAdapter;
import co.aurasphere.botmill.core.datastore.adapter.RdbmsAdapter;
import co.aurasphere.botmill.core.datastore.model.KeyValuePair;
import co.aurasphere.botmill.core.datastore.model.KeyValuePairBuilder;
import co.aurasphere.botmill.core.datastore.model.Session;
import co.aurasphere.botmill.core.internal.util.ConfigurationUtils;

/**
 * The Class Bot.
 */
public abstract class Bot implements BotDefinition {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(Bot.class);
	
	/** The data adapter. */
	DataAdapter dataAdapter;
	
	/**
	 * Instantiates a new bot.
	 */
	public Bot() {
		//	check the data adapter type from the properties.
		//	map, hsql, mongodb, rdbms
		String dataAdapterType = ConfigurationUtils.getConfiguration().getProperty("data.adapter.type");
		if(dataAdapterType.equals("map")) {
			defineDataAdapter(AdapterType.MAP);
		}else if(dataAdapterType.equals("mongodb")){
			defineDataAdapter(AdapterType.MONGODB);
		}else if(dataAdapterType.equals("hsql")) {
			defineDataAdapter(AdapterType.HSQL);
		}else if(dataAdapterType.equals("rdbms")) {
			defineDataAdapter(AdapterType.RBDMS);
		}
	}
	
	/**
	 * Define data adapter.
	 *
	 * @param adapterType the adapter type
	 */
	private void defineDataAdapter(AdapterType adapterType) {
		switch (adapterType) {
		case MAP:
			this.dataAdapter = new MapAdapter();
			break;
		case MONGODB:
			this.dataAdapter = new MongoDBAdapter();
			break;
		case HSQL:
			this.dataAdapter = new HsqlAdapter();
			break;
		case RBDMS:
			this.dataAdapter = new RdbmsAdapter();
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
	protected Session buildSession(String identifier){
		return this.dataAdapter.buildSession(identifier);
	}
	
	/**
	 * Destroy session.
	 *
	 * @param identifier the identifier
	 */
	protected void destroySession(String identifier) {
		this.dataAdapter.destroySession(identifier);
	}
	
	/**
	 * Gets the session.
	 *
	 * @param identifier the identifier
	 * @return the session
	 */
	protected Session getSession(String identifier) {
		return this.dataAdapter.getSession(identifier);
	}
	
	/**
	 * Put data.
	 *
	 * @param identifier the identifier
	 * @param keyValuePair the key value pair
	 * @return the session
	 */
	protected Session putData(String identifier, KeyValuePair keyValuePair) {
		return this.dataAdapter.putData(identifier,keyValuePair);
	}
	
	/**
	 * Removes the data.
	 *
	 * @param identifier the identifier
	 * @param key the key
	 */
	protected void removeData(String identifier, String key) {
		this.dataAdapter.removeData(identifier,key);
	}
	
	/**
	 * Gets the data.
	 *
	 * @param identifier the identifier
	 * @param key the key
	 * @return the data
	 */
	protected KeyValuePair getData(String identifier, String key) {
		return this.dataAdapter.getData(identifier,key);
	}
	
	/**
	 * Adds the key value pair.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the key value pair
	 */
	protected KeyValuePair addKeyValuePair(String key, String value) {
		return KeyValuePairBuilder.getInstance().setKey(key).setValue(value).build();
	}
}
