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

		// crsf(Cross Site Request Forgery) la ki thuat tan cong bang cach su dung chung
		// thuc cua ng su dung doi voi 1 website khac

		http.csrf().disable(); // luc nao cung phai co ko la nhay vo 403

	
		// nhung url-pattern ko can phai login -> admin/user/guest deu vao dc trang
		// login hay logout
		http.authorizeRequests().antMatchers("/orderList", "/order", "/accountInfo")
				.access("hasAnyRole('EMPLOYEE', 'MANAGER')"); // moi url-pattern cach

		http.authorizeRequests().antMatchers("/product").access("hasRole('MANAGER')");
		
		// khi ng dung da login voi vai tro user, nhung co y truy cap trang login khong
		// co vai tro se bi da ra
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// nhau boi ,
		// http.authorizeRequests().antMatchers("/user/list").access("hasRole('Admin')");//
		// deny user access admin
		// http.authorizeRequests().antMatchers("/user/**").access("hasAnyRole('Admin',
		// 'User')");// access

		// http.authorizeRequests().antMatchers("/delete/**").hasAuthority("Admin").antMatchers("/edit/**")
		// .hasAuthority("Admin");

		// cau hinh cho login form
		http.authorizeRequests().and().formLogin()
				// neu ko khai bao se bao loi
				.loginProcessingUrl("/j_spring_security_check") // ???
				.loginPage("/login")
				// goi den requestmapping /login.jsp, neu ko khai bao thi se su dung trang
				// login mac dinh
				.defaultSuccessUrl("/accountInfo") // khi dang nhao thanh cong
				// thi goi nhu trong ""
				.failureUrl("/login?error=true") // khi dang nhap sai thi
				// goi den requestmapping /login de nhap lai
				.usernameParameter("userName")
				// tham so nay nhan tu form login co input name=username
				.passwordParameter("password") // tham so nay nhan tu form login co input
				// name=password //cau hinh cho logout page. khi logout cua minh tra ve trang
				.and().logout().logoutUrl("/logout") // goi den requestmapping dinh /logout.jsp, neu ko khai bao thi se
														// su dung trang logout mac
				.logoutSuccessUrl("/"); // khi dang xuat thanh cong se goi den @requestmapping /logoutsuccessful
	}

}
