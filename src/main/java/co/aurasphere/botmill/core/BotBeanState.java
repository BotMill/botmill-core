package co.aurasphere.botmill.core;

public enum BotBeanState {
	
	SINGLETON, // return the single instance
	PROTOTYPE // returns a new instance of the bot for each request
}
