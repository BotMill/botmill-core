package co.aurasphere.botmill.core.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.aurasphere.botmill.core.datastore.adapter.MapAdapter;
import co.aurasphere.botmill.core.datastore.model.KeyValuePairBuilder;

public class DataAdapterTest {
	
	private static final Logger logger = LoggerFactory.getLogger(DataAdapterTest.class);
	
	@Test
	public void testMap() {
		
		MapAdapter mapAdapter = new MapAdapter();
		mapAdapter.buildSession("123");
		mapAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("key1").setValue("value").build());
		mapAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("key2").setValue("value1").build());

		mapAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("email").setValue("alvin@reyes.com").build());
		mapAdapter.putData("123", KeyValuePairBuilder.getInstance().setKey("email").setValue("alvin@reyes.com").build());
		assertEquals("alvin@reyes.com", mapAdapter.getData("123", "email").getValue().toString());
	}
}
