package ifrn.projeto.finolanche.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	protected void configure (HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**").permitAll()
		.anyRequest().authenticated().and().formLogin().loginPage("/login")
		.permitAll().and().logout().logoutSuccessUrl("/login?logout").permitAll();
		
	}
	
}
