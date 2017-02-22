/*
 * 
 */
package co.aurasphere.botmill.core.datastore.adapter;

import co.aurasphere.botmill.core.datastore.model.KeyValuePair;
import co.aurasphere.botmill.core.datastore.model.Session;

// TODO: Auto-generated Javadoc
/**
 * The Class DataAdapter.
 */
public abstract class DataAdapter<T> {
	
	T data;
	
	/**
	 * Instantiates a new data adapter.
	 */
	public DataAdapter() {
		this.setup();
	}
	
	/**
	 * Setup.
	 */
	public abstract void setup();
	
	/**
	 * Builds the session.
	 *
	 * @param identifier the identifier
	 * @return the session
	 */
	public abstract Session buildSession(String identifier);
	
	/**
	 * Destroy session.
	 *
	 * @param identifier the identifier
	 */
	public abstract void destroySession(String identifier);
	
	/**
	 * Gets the session.
	 *
	 * @param identifier the identifier
	 * @return the session
	 */
	public abstract Session getSession(String identifier);
	
	/**
	 * Put data.
	 *
	 * @param identifier the identifier
	 * @param keyValuePair the key value pair
	 * @return the session
	 */
	public abstract Session putData(String identifier, KeyValuePair keyValuePair);
	
	/**
	 * Removes the data.
	 *
	 * @param identifier the identifier
	 * @param key the key
	 */
	public abstract void removeData(String identifier, String key);
	
	/**
	 * Gets the data.
	 *
	 * @param identifier the identifier
	 * @param key the key
	 * @return the data
	 */
	public abstract KeyValuePair getData(String identifier, String key);

}
