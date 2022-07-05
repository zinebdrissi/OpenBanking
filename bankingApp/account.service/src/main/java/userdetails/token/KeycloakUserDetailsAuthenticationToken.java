package userdetails.token;

import java.util.Collection;

import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class KeycloakUserDetailsAuthenticationToken extends KeycloakAuthenticationToken {
	
	private UserDetails userDetails;
	
	public KeycloakUserDetailsAuthenticationToken(UserDetails userDetails, OidcKeycloakAccount account,
            boolean interactive
) {
       super(account, interactive);
       Assert.notNull(userDetails, "UserDetails required");
        this.userDetails = userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

}


