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
import java.util.Arrays;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import co.aurasphere.botmill.core.datastore.model.KeyValuePair;
import co.aurasphere.botmill.core.datastore.model.Session;
import co.aurasphere.botmill.core.internal.util.ConfigurationUtils;

/**
 * The Class MongoDBAdapter.
 */
public class MongoDBAdapter extends BotDataAdapter<MongoOperations> {

	/** The mongodb ops. */

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#setup()
	 */
	public void setup() {
		
		MongoCredential credential = MongoCredential.createCredential(
				ConfigurationUtils.getConfiguration().getProperty("mongodb.username"), 
				ConfigurationUtils.getConfiguration().getProperty("mongodb.database"), 
				ConfigurationUtils.getConfiguration().getProperty("mongodb.password").toCharArray());
		ServerAddress serverAddress = new ServerAddress(
				ConfigurationUtils.getConfiguration().getProperty("mongodb.server"), 
				Integer.valueOf(ConfigurationUtils.getConfiguration().getProperty("mongodb.port")));
		MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));
		SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClient, ConfigurationUtils.getConfiguration().getProperty("mongodb.database"));

		MongoTemplate mongoTemplate = new MongoTemplate(simpleMongoDbFactory);
		this.source = (MongoOperations) mongoTemplate;
	}
	
	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#buildSession(java.lang.String)
	 */
	public Session buildSession(String identifier) {

		Query q = new Query(Criteria.where("identifier").is(identifier));
		Session sessionResult = this.source.findOne(q, Session.class);

		if (sessionResult == null) {
			Session session = new Session();
			session.setIdentifier(identifier);
			session.setKeyValuePair(new ArrayList<KeyValuePair>());
			this.source.save(session);

			Query newSessionQ = new Query(Criteria.where("identifier").is(identifier));
			Session newSession = this.source.findOne(newSessionQ, Session.class);
			return newSession;
		}

		return sessionResult;
	}

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#destroySession(java.lang.String)
	 */
	public void destroySession(String identifier) {
		Query q = new Query(Criteria.where("identifier").is(identifier));
		this.source.remove(q, Session.class);
	}

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#putData(java.lang.String, co.aurasphere.botmill.core.datastore.model.KeyValuePair)
	 */
	public Session putData(String identifier, KeyValuePair keyValuePair) {
		// create a session if none exist.
		buildSession(identifier);

		Query q = new Query(Criteria.where("identifier").is(identifier));
		Session sessionResult = this.source.findOne(q, Session.class);
		sessionResult.addKeyValuePair(keyValuePair);
		this.source.save(sessionResult);
		return sessionResult;
	}

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#removeData(java.lang.String, java.lang.String)
	 */
	public void removeData(String identifier, String key) {
		Query q = new Query(Criteria.where("identifier").is(identifier).and("KeyValuePairs.key").is(key));
		this.source.remove(q, Session.class);
	}

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#getSession(java.lang.String)
	 */
	public Session getSession(String identifier) {
		Query q = new Query(Criteria.where("identifier").is(identifier));
		Session sessionResult = this.source.findOne(q, Session.class);
		return sessionResult;
	}

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#getData(java.lang.String, java.lang.String)
	 */
	public KeyValuePair getData(String identifier, String key) {
		Query q = new Query(Criteria.where("identifier").is(identifier).and("KeyValuePairs.key").is(key));
		Session sessionResult = this.source.findOne(q, Session.class);
		return sessionResult.getKeyValuePairs().get(0);
	}

}
