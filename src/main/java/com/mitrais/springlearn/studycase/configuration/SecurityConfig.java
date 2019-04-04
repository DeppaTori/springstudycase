package com.mitrais.springlearn.studycase.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsService userDetailService;
	
	@Override
	  protected void configure(HttpSecurity http) throws Exception {
	      http
	          .authorizeRequests()
//	          .antMatchers("/admin/**").hasRole("ADMIN")
//	          .antMatchers("/user_list").hasRole("USER")
	          .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
	              .antMatchers("/","/login","/test").permitAll()
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
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authenticationProvider());
//	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
	
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//	    DaoAuthenticationProvider authProvider
//	      = new DaoAuthenticationProvider();
//	    authProvider.setUserDetailsService(userDetailService);
//	    authProvider.setPasswordEncoder(passwordEncoder());
//	    return authProvider;
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
