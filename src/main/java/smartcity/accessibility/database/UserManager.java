package smartcity.accessibility.database;
import java.net.URI;
import java.net.URISyntaxException;

import com.nimbusds.oauth2.sdk.*;
import com.nimbusds.oauth2.sdk.id.*;

/**
 * @author Kolikant
 *
 */
public class UserManager {
	
	public AuthorizationRequest AuthorisationRequest(){
		URI callback = null, authzEndpoint = null;
		try {
			authzEndpoint = new URI("http://aaasdasdasd.wixsite.com/mysite");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ClientID clientID = new ClientID("123");
		
		Scope scope = new Scope("read", "write");
		
		try {
			callback = new URI("https://client.com/callback");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new AuthorizationRequest.Builder(new ResponseType(ResponseType.Value.CODE), clientID).scope(scope)
				.state((new State())).redirectionURI(callback).endpointURI(authzEndpoint).build();
	}
	
	
}
