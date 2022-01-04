package ifrn.projeto.finolanche.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("site/formPrato").hasRole("USU_LANCHONETE")
		.antMatchers("site/listarPratos").hasRole("USU_CADASTRADO")
		.antMatchers("site/detalhes").hasRole("USU_CADASTRADO")
		.anyRequest().authenticated().and().formLogin().loginPage("/login")
		.permitAll().and().logout().logoutSuccessUrl("/login?logout").permitAll();
		
	}
	
}
