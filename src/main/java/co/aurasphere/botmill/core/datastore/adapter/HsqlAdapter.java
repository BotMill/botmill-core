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
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.aurasphere.botmill.core.datastore.model.KeyValuePair;
import co.aurasphere.botmill.core.datastore.model.Session;
import co.aurasphere.botmill.core.internal.util.ConfigurationUtils;
import co.aurasphere.botmill.core.internal.util.JsonUtils;

/**
 * The Class HSQLAdapter.
 */
public class HsqlAdapter extends BotDataAdapter<Connection> {

	/*
	 * (non-Javadoc)
	 * 
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

			// check if table exists.
			DatabaseMetaData dbMeta = this.source.getMetaData();
			ResultSet tableMeta = dbMeta.getTables(null, null,
					ConfigurationUtils.getConfiguration().getProperty("hsql.table.prefix") + "_SESSION", null);

			if (!tableMeta.next()) {
				// check if the table exist, if not create one.
				this.source.prepareStatement(
						"" + "CREATE TABLE " + ConfigurationUtils.getConfiguration().getProperty("hsql.table.prefix")
								+ "_SESSION (IDENTIFIER VARCHAR(255), KEYVALUEPAIR TEXT)")
						.executeQuery();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("Driver not found in classpath");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.botmill.core.datastore.adapter.DataAdapter#buildSession(
	 * java.lang.String)
	 */
	public Session buildSession(String identifier) {
		// 	check if it already has a session entry.
		try {
			PreparedStatement preparedStatement = this.source.prepareStatement(
					"SELECT * FROM " + 
					ConfigurationUtils.getConfiguration().getProperty("hsql.table.prefix") + 
					"_SESSION "
					+ "WHERE "
					+ "	IDENTIFIER = ?");
			preparedStatement.setString(1, identifier);
			
			ResultSet rs = preparedStatement.executeQuery();
			Session session = new Session();
			if(rs.getRow() == 0) { // meaning, no record. Create one.
				PreparedStatement createRecord = this.source.prepareStatement(
						"INSERT INTO " + 
						ConfigurationUtils.getConfiguration().getProperty("hsql.table.prefix") + 
						"_SESSION(IDENTIFIER) "
						+ "VALUES(?) ");
				createRecord.setString(1, identifier);
				createRecord.executeQuery();
				buildSession(identifier);
				
			}else {
				session.setIdentifier(rs.getString(0));
				
				if(!rs.getString(1).equals("")) {
					session.addKeyValuePair(JsonUtils.fromJson(rs.getString(1), KeyValuePair.class));
				}
	
				return session;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.botmill.core.datastore.adapter.DataAdapter#destroySession(
	 * java.lang.String)
	 */
	public void destroySession(String identifier) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.botmill.core.datastore.adapter.DataAdapter#putData(java.
	 * lang.String, co.aurasphere.botmill.core.datastore.model.KeyValuePair)
	 */
	public Session putData(String identifier, KeyValuePair keyValuePair) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.botmill.core.datastore.adapter.DataAdapter#removeData(java.
	 * lang.String, java.lang.String)
	 */
	public void removeData(String identifier, String key) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.botmill.core.datastore.adapter.DataAdapter#getSession(java.
	 * lang.String)
	 */
	public Session getSession(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.botmill.core.datastore.adapter.DataAdapter#getData(java.
	 * lang.String, java.lang.String)
	 */
	public KeyValuePair getData(String identifier, String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
