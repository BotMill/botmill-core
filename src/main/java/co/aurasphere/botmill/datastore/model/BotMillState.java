package co.aurasphere.botmill.datastore.model;

public interface BotMillState {
	public Session buildSession(String identifier);
	public void destroySession(String identifier);
	public Session setVariable(String identifier, KeyValuePair keyValuePair);
	public KeyValuePair getVariable(String identifier, String key);
	
}
