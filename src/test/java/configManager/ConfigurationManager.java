package configManager;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationManager {
	public static Configuration configuration() {
		return ConfigCache.getOrCreate(Configuration.class);
	}
}
