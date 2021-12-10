package com.springmvc.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springmvc.authorization.MyDBAuthenticationService;

//import com.springmvc.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * @Bean public UserDetailsService userDetailsService() { return new
	 * UserDetailsServiceImpl(); }
	 */
	
	@Autowired
	@Qualifier("myDBAuthenticationService")
	private MyDBAuthenticationService myDBAuthenticationService;
    
	
	@Bean
	public BCryptPasswordEncoder passwordEcoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEcoder());
		authProvider.setUserDetailsService(userDetailsService());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(myDBAuthenticationService);

		//auth.inMemoryAuthentication().withUser("employee").password("employee").authorities("ROLE_EMPLOYEE");
		//auth.inMemoryAuthentication().withUser("manager").password("manager").authorities("ROLE_MANAGER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable(); // luc nao cung phai co ko la nhay vo 403

		http.authorizeRequests().antMatchers("/orderList", "/order", "/accountInfo")
				.access("hasAnyRole('EMPLOYEE', 'MANAGER')"); // moi url-pattern cach

		http.authorizeRequests().antMatchers("/product","/accountList", "/account").access("hasRole('MANAGER')");
		

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");


		// cau hinh cho login form
		http.authorizeRequests().and().formLogin()
				// neu ko khai bao se bao loi
				.loginProcessingUrl("/j_spring_security_check") // ???
				.loginPage("/login")
				.defaultSuccessUrl("/accountInfo") 
				.failureUrl("/login?error=true") 
				.usernameParameter("userName")
				.passwordParameter("password") 
				.and().logout().logoutUrl("/logout") 
				.logoutSuccessUrl("/"); 
	}

}
