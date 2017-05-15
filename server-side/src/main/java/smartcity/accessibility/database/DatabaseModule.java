package smartcity.accessibility.database;

import com.google.inject.AbstractModule;

/**
 * @author KaplanAlexander
 *
 */
public class DatabaseModule extends AbstractModule {
	@Override
	protected void configure() {
		ParseDatabase pd = ParseDatabase.get();
		bind(Database.class).toInstance(pd);
	}
}
