package userdetails.authentication;

import java.security.Principal;

import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

import userdetails.token.KeycloakUserDetailsAuthenticationToken;

public class keycloakUserDetailsAuthenticationProvider extends KeycloakAuthenticationProvider {
	
	 private UserDetailsService userDetailsService;
	 
	 @Override
	    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) super.authenticate(authentication);
	        String username;
	        UserDetails userDetails;

	        if (token == null) {
	            return null;
	        }

	        username = this.resolveUsername(token);
	        userDetails = userDetailsService.loadUserByUsername(username);
	        
	        return  new KeycloakUserDetailsAuthenticationToken(userDetails, token.getAccount(), token.isInteractive());
	    }
	 
	 protected String resolveUsername(KeycloakAuthenticationToken token) {

	        Assert.notNull(token, "KeycloakAuthenticationToken required");
	        Assert.notNull(token.getAccount(), "KeycloakAuthenticationToken.getAccount() cannot be return null");
	        OidcKeycloakAccount account = token.getAccount();
	        Principal principal = account.getPrincipal();

	        return principal.getName();
	    }

	    @Required
	    public void setUserDetailsService(UserDetailsService userDetailsService) {
	        this.userDetailsService = userDetailsService;
	    }

}
