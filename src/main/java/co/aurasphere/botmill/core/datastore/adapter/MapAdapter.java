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

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import co.aurasphere.botmill.core.datastore.model.KeyValuePair;
import co.aurasphere.botmill.core.datastore.model.Session;


/**
 * The Class MapAdapter.
 */
public class MapAdapter extends DataAdapter<ConcurrentMap<String, Session>> {

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#setup()
	 */
	@Override
	public void setup() {
		source = new ConcurrentHashMap<String, Session>();
	}
	
	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#buildSession(java.lang.String)
	 */
	@Override
	public Session buildSession(String identifier) {
		Session session = new Session();
		session.setIdentifier(identifier);
		session.setKeyValuePair(new ArrayList<KeyValuePair>());
		source.put(identifier, session);
		
		if(source.containsKey(identifier)) {
			return source.get(identifier);
		}
		return session;
	}
	

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#getSession(java.lang.String)
	 */
	@Override
	public Session getSession(String identifier) {
		if(source.containsKey(identifier)) {
			return source.get(identifier);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#destroySession(java.lang.String)
	 */
	@Override
	public void destroySession(String identifier) {
		if(source.containsKey(identifier)) {
			source.remove(identifier);
		}
	}
	
	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#putData(java.lang.String, co.aurasphere.botmill.core.datastore.model.KeyValuePair)
	 */
	@Override
	public Session putData(String identifier, KeyValuePair keyValuePair) {
		if(source.containsKey(identifier)) {
			source.get(identifier).addKeyValuePair(keyValuePair);
			return source.get(identifier);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#removeData(java.lang.String, java.lang.String)
	 */
	@Override
	public void removeData(String identifier, String key) {
		if(source.containsKey(identifier)) {
			for(KeyValuePair keyValuePair: source.get(identifier).getKeyValuePairs()) {
				if(keyValuePair.getKey().equals(key)) {
					source.get(identifier).getKeyValuePairs().remove(keyValuePair);
					break;
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#getData(java.lang.String, java.lang.String)
	 */
	@Override
	public KeyValuePair getData(String identifier, String key) {
		if(source.containsKey(identifier)) {
			for(KeyValuePair keyValuePair: source.get(identifier).getKeyValuePairs()) {
				if(keyValuePair.getKey().equals(key)) {
					return keyValuePair;
				}
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return source.toString();
	}

}
