/*
 * MIT License
 *
 * Copyright (c) 2017 BotMill.io
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
package co.aurasphere.botmill.core.datastore.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;


/**
 * The Class Session.
 */
public class Session implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */

	private String id;

	/** The identifier. */
	private String identifier;
	
	/** The key value pair. */
	@SerializedName("keyvaluepair")
	private List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the key value pair.
	 *
	 * @return the key value pair
	 */
	public List<KeyValuePair> getKeyValuePairs() {
		return keyValuePairs;
	}

	/**
	 * Sets the key value pair.
	 *
	 * @param keyValuePairs the new key value pair
	 */
	public void setKeyValuePair(List<KeyValuePair> keyValuePairs) {
		this.keyValuePairs = keyValuePairs;
	}
	
	/**
	 * Adds the key value pair.
	 *
	 * @param keyValuePair the key value pair
	 */
	public void addKeyValuePair(KeyValuePair keyValuePair) {
		for(KeyValuePair kp:this.keyValuePairs) {
			if(kp.getKey().equals(keyValuePair.getKey())) {
				kp.setValue(keyValuePair.getValue());
				return;
			}
		}
		this.keyValuePairs.add(keyValuePair);
	}

	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Sets the identifier.
	 *
	 * @param identifier the new identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
