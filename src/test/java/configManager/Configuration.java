package configManager;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;

@LoadPolicy(LoadType.MERGE)
@Config.Sources({"classpath:service.now.app.config.properties"})
public interface Configuration extends Config{

	@Key("service.now.base.uri")
	String getBaseUri();

	@Key("service.now.base.path")
	String getBasePath();

	@Key("service.now.oauth.base.path")
	String getOAuthBasePath();

	@Key("service.now.username")
	String getUsername();

	@Key("service.now.password")
	String getPassword();
	
	@Key("service.now.grant.type")
	String getGrantType();

	@Key("service.now.client.id")
	String getCilentId();

	@Key("service.now.client.secret")
	String getCilentSecerts();

}
