package com.OpenBanking.account.configuration;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.keycloak.adapters.springsecurity.filter.QueryParamPresenceRequestMatcher;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import userdetails.authentication.keycloakUserDetailsAuthenticationProvider;

@KeycloakConfiguration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Import({KeycloakSpringBootConfigResolver.class})
public class keycloakAccountConfiguration extends KeycloakWebSecurityConfigurerAdapter {
	
   	@Autowired    
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	
    	
    	KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
		 auth.authenticationProvider(keycloakAuthenticationProvider);
		
//		 .passwordEncoder(NoOpPasswordEncoder.getInstance())
//         .withUser("zineb").password("zineb").roles("admin")
//         .and()
//         .withUser("soukaina").password("soukaina").roles("user");
		 auth.authenticationProvider(keycloakAuthenticationProvider());
		
		
    }
    
    

    /**
     * Defines the session authentication strategy.
     */
  
   @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }
    
   @Bean
   public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
       return new KeycloakSpringBootConfigResolver();
   }

 
  
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    
       // super.configure(http);
    	 http
    	 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         .addFilterBefore(keycloakPreAuthActionsFilter(), LogoutFilter.class)
         .addFilterBefore(keycloakAuthenticationProcessingFilter(), X509AuthenticationFilter.class)
         .csrf().disable().cors().disable().
         authorizeHttpRequests()
         .antMatchers("/login*").permitAll()
         .antMatchers("/resources/**").permitAll()
         .antMatchers("/*.html").permitAll()
         .antMatchers("/accounts/**").hasRole("admin")
         .anyRequest()
         .authenticated()
         .and()
         .formLogin()
         ;		
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
      web
          .ignoring()
          .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    
    @Bean
    public FilterRegistrationBean<KeycloakPreAuthActionsFilter> keycloakPreAuthActionsFilterRegistrationBean(
        KeycloakPreAuthActionsFilter filter) {
      FilterRegistrationBean<KeycloakPreAuthActionsFilter> registrationBean = new FilterRegistrationBean<>(
          filter);
      registrationBean.setEnabled(false);
      return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<KeycloakAuthenticatedActionsFilter> keycloakAuthenticatedActionsFilterBean(
        KeycloakAuthenticatedActionsFilter filter) {
      FilterRegistrationBean<KeycloakAuthenticatedActionsFilter> registrationBean = new FilterRegistrationBean<>(
          filter);
      registrationBean.setEnabled(false);
      return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<KeycloakSecurityContextRequestFilter> keycloakSecurityContextRequestFilterBean(
        KeycloakSecurityContextRequestFilter filter) {
      FilterRegistrationBean<KeycloakSecurityContextRequestFilter> registrationBean = new FilterRegistrationBean<>(
          filter);
      registrationBean.setEnabled(false);
      return registrationBean;
    }
   
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
