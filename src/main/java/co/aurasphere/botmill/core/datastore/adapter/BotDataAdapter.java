/*
 * 
 */
package co.aurasphere.botmill.core.datastore.adapter;

/**
 * The Class DataAdapter.
 *
 * @param <T> the generic type
 */
public abstract class BotDataAdapter<T> implements DataAdapter {
	
	/** The source. */
	T source;
	
	/**
	 * Instantiates a new data adapter.
	 */
	public BotDataAdapter() {
		this.setup();
	}
}
