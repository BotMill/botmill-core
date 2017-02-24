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

public class BotMillSession {
	
	private static final Logger logger = LoggerFactory.getLogger(BotMillSession.class);
	
	private static DataAdapter dataAdapter;
	private static BotMillSession instance;
	
	public static BotMillSession getInstance() {
		if (instance == null) {
			//	Making sure we only have one instance.
			instance = new BotMillSession();
		}
		return instance;
	}
	
	private BotMillSession() {
		//	check the data adapter type from the properties.
		//	map, hsql, mongodb, rdbms
		String dataAdapterType = ConfigurationUtils.getConfiguration().getProperty("data.adapter.type");
		if(dataAdapterType.equals("map")) {
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

	public Session buildSession(String identifier){
		return dataAdapter.buildSession(identifier);
	}
	
	public void destroySession(String identifier) {
		dataAdapter.destroySession(identifier);
	}

	public Session getSession(String identifier) {
		return dataAdapter.getSession(identifier);
	}
	
	public Session putSessionData(String identifier, KeyValuePair keyValuePair) {
		return dataAdapter.putData(identifier,keyValuePair);
	}
	
	public void removeSessionData(String identifier, String key) {
		dataAdapter.removeData(identifier,key);
	}
	
	public KeyValuePair getSessionData(String identifier, String key) {
		return dataAdapter.getData(identifier,key);
	}
	
	public KeyValuePair addKeyValuePair(String key, String value) {
		return KeyValuePairBuilder.getInstance().setKey(key).setValue(value).build();
	}
}
