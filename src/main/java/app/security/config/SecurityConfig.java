package app.security.config;

import app.security.CustomUserDetailsService;
import app.security.handlers.CustomAuthenticationFailureHandler;
import app.security.handlers.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@ComponentScan("app")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private CustomUserDetailsService authenticationService;
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

	@Autowired
	public SecurityConfig(CustomUserDetailsService authenticationService,
						  CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
						  CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
		this.authenticationService = authenticationService;
		this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
		this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
	}


	/* настраивает обработку http запросов**/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.csrf().disable().addFilterBefore(filter, CsrfFilter.class);
		http.authorizeRequests().antMatchers("/").permitAll();
	}


	/* говорит что за авторизацией следит наша реализация UserDetailsService**/
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//The name of the configureGlobal method is not important. However,
		// it is important to only configure AuthenticationManagerBuilder in a class annotated with either @EnableWebSecurity
		auth.userDetailsService(authenticationService);
	}
}
