/*
 * 
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
 * 
 */
package co.aurasphere.botmill.core.datastore.adapter;


/**
 * A factory for creating DataAdapter objects.
 */
public class DataAdapterFactory {
	
	/**
	 * Gets the map adapter.
	 *
	 * @return the map adapter
	 */
	public static MapAdapter getMapAdapter() {
		return new MapAdapter();
	}
	
	/**
	 * Gets the hsql adapter.
	 *
	 * @return the hsql adapter
	 */
	public static HsqlAdapter getHsqlAdapter() {
		return new HsqlAdapter();
	}
	
	/**
	 * Gets the mongo db adapter.
	 *
	 * @return the mongo db adapter
	 */
	public static MongoDBAdapter getMongoDbAdapter() {
		return new MongoDBAdapter();
	}
	
	/**
	 * Gets the rdbms adapter.
	 *
	 * @return the rdbms adapter
	 */
	public static RdbmsAdapter getRdbmsAdapter() {
		return new RdbmsAdapter();
	}
}
