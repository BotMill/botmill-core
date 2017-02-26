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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import co.aurasphere.botmill.core.datastore.model.KeyValuePair;
import co.aurasphere.botmill.core.datastore.model.Session;
import co.aurasphere.botmill.core.internal.util.ConfigurationUtils;


/**
 * The Class HSQLAdapter.
 */
public class HsqlAdapter extends BotDataAdapter<Connection> {
	
	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#setup()
	 */
	public void setup() {
		try {
			Class.forName(ConfigurationUtils.getConfiguration().getProperty("hsql.driver"));
			Connection c = DriverManager.getConnection(
					ConfigurationUtils.getConfiguration().getProperty("hsql.conn.url"), 
					ConfigurationUtils.getConfiguration().getProperty("hsql.username"), 
					ConfigurationUtils.getConfiguration().getProperty("hsql.password"));
			this.source = c;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("Driver not found in classpath");
		}
	}

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#buildSession(java.lang.String)
	 */
	public Session buildSession(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#destroySession(java.lang.String)
	 */
	public void destroySession(String identifier) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#putData(java.lang.String, co.aurasphere.botmill.core.datastore.model.KeyValuePair)
	 */
	public Session putData(String identifier, KeyValuePair keyValuePair) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#removeData(java.lang.String, java.lang.String)
	 */
	public void removeData(String identifier, String key) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#getSession(java.lang.String)
	 */
	public Session getSession(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see co.aurasphere.botmill.core.datastore.adapter.DataAdapter#getData(java.lang.String, java.lang.String)
	 */
	public KeyValuePair getData(String identifier, String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
