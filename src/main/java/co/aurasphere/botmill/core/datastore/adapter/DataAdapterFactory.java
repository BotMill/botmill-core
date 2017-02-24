package co.aurasphere.botmill.core.datastore.adapter;

public class DataAdapterFactory {
	
	public static MapAdapter getMapAdapter() {
		return new MapAdapter();
	}
	
	public static HsqlAdapter getHsqlAdapter() {
		return new HsqlAdapter();
	}
	
	public static MongoDBAdapter getMongoDbAdapter() {
		return new MongoDBAdapter();
	}
	
	public static RdbmsAdapter getRdbmsAdapter() {
		return new RdbmsAdapter();
	}
}
