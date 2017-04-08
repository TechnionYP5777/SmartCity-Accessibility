package smartcity.accessibility.database;

import com.google.inject.AbstractModule;

public class DatabaseModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Database.class).toInstance(ParseDatabase.get());
	}
}
