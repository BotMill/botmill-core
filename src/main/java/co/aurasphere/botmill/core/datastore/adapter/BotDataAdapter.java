/*
 * 
 */
package co.aurasphere.botmill.core.datastore.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DataAdapter.
 *
 * @param <T> the generic type
 */
public abstract class BotDataAdapter<T> implements DataAdapter {
	
	protected static final Logger logger = LoggerFactory.getLogger(BotDataAdapter.class);
	
	/** The source. */
	T source;
	
	/**
	 * Instantiates a new data adapter.
	 */
	public BotDataAdapter() {
		this.setup();
	}
	
	public T getDataSource() {
		return source;
	}
}
