package com.mitrais.springlearn.studycase.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mitrais.springlearn.studycase.service.MySecurityUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	MySecurityUserDetailService userDetailService;
	
	@Override
	  protected void configure(HttpSecurity http) throws Exception {
	      http
	          .authorizeRequests()
	          .antMatchers("/admin/**").hasRole("ADMIN")
	          .antMatchers("/user_list").hasRole("ADMIN")
	          .antMatchers("/user_list").hasRole("USER")
	          .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
	              .antMatchers("/","/login").permitAll()
	              .anyRequest().authenticated()
	              .and()
	          .formLogin()
	              .loginPage("/login")
	              .permitAll()
	              .and()
	          .logout()
	        //  .logoutSuccessUrl("/login")
	              .permitAll()
	              .and()
	              .exceptionHandling().accessDeniedPage("/access-denied");
	  }
	
	
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//		.withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
//	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
