package com.vyuhkrama.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests()
	        .antMatchers("/","/home").permitAll()
 	        .antMatchers("/admin/**").access("hasRole('ADMIN')")
 	        .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
 	        .antMatchers("/resources/**").permitAll()
 	        .and().formLogin().loginPage("/login")
 	        .defaultSuccessUrl("/homePage").failureUrl("/loginPage?error")
 	        .usernameParameter("username").passwordParameter("password")
 	        .and().logout().logoutSuccessUrl("/loginPage?logout")
 	        .and().exceptionHandling().accessDeniedPage("/Access_Denied");
	    }
	 	
	  @Autowired
	    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("ROLE_USER");
	        auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ROLE_ADMIN");
	        auth.inMemoryAuthentication().withUser("dba").password("root123").roles("ROLE_ADMIN","ROLE_DBA");
	    }
}
