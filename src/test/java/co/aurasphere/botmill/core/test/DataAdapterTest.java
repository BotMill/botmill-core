package co.aurasphere.botmill.core.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.aurasphere.botmill.core.datastore.adapter.MapAdapter;
import co.aurasphere.botmill.core.datastore.adapter.MongoDBAdapter;
import co.aurasphere.botmill.core.datastore.model.KeyValuePairBuilder;
import co.aurasphere.botmill.core.internal.util.ConfigurationUtils;
import jdk.nashorn.internal.ir.annotations.Ignore;


/**
 * The Class DataAdapterTest.
 */
public class DataAdapterTest {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DataAdapterTest.class);
	
	/**
	 * Test map.
	 */
	@Test
	@Ignore
	public void testMap() {
		
		MapAdapter mapAdapter = new MapAdapter();
		mapAdapter.buildSession("123");
		mapAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("key1").setValue("value").build());
		mapAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("key2").setValue("value1").build());

		mapAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("email").setValue("alvin@reyes.com").build());
		mapAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("email").setValue("alvin@reyes.com").build());
		assertEquals("alvin@reyes.com", mapAdapter.getData("123", "email").getValue().toString());
	}
	
	/**
	 * Test mongo db.
	 */
	@Test
	@Ignore
	public void testMongoDb() {
		ConfigurationUtils.loadConfigurationFile();
		MongoDBAdapter mongoDbAdapter = new MongoDBAdapter();
		mongoDbAdapter.buildSession("123");
		mongoDbAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("email").setValue("alvin@reyes.com").build());
		
		assertEquals("alvin@reyes.com", mongoDbAdapter.getData("123", "email").getValue().toString());
	}
}
