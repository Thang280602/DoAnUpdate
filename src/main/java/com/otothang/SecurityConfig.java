package com.otothang;

import com.otothang.Service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private CustomUserDetailService customUserDetailService;

//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//			http.csrf(csrf -> csrf.disable())
//					.authorizeHttpRequests((auth) -> auth.requestMatchers("/*").permitAll().
//							requestMatchers("/user/detail/**").permitAll().
//							requestMatchers("/user/AboutUs/**").permitAll().
//							requestMatchers("/user/listView/**").permitAll().
//							requestMatchers("/user/ListingClassic/**").permitAll().
//							requestMatchers("/user/index2/**").permitAll().
//							requestMatchers("/user/service/**").permitAll().
//							requestMatchers("/user/comingup/**").permitAll().
//							requestMatchers("/user/blog/**").permitAll().
//						requestMatchers("/admin/**").hasAuthority("ADMIN")
//							.anyRequest().authenticated())
//					.formLogin(login -> login.loginPage("/logon").loginProcessingUrl("/logon").usernameParameter("username")
//							.passwordParameter("password").defaultSuccessUrl("/admin", true))
//					.logout(logout -> logout.logoutUrl("/admin-logout").logoutSuccessUrl("/logon"))
//					.logout(logout -> logout.logoutUrl("/admin-logout").logoutSuccessUrl("/logon"))
//					.formLogin(login -> login.loginPage("/login").loginProcessingUrl("/login").usernameParameter("username")
//							.passwordParameter("password").defaultSuccessUrl("/", true))
//					.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/"));
//			return http.build();
//	}
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests(authorize -> authorize
						.requestMatchers("/*", "/user/detail/**", "/user/AboutUs/**", "/user/listView/**", "/user/ListingClassic/**",
								"/user/index2/**", "/user/service/**", "/user/comingup/**", "/user/blog/**").permitAll()
						.requestMatchers("/admin/**").hasAuthority("ADMIN")
						.anyRequest().authenticated()
				)
				.formLogin(login -> login
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.usernameParameter("username")
						.passwordParameter("password")
						.defaultSuccessUrl("/", true)
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
				)
				.exceptionHandling(exception -> exception
						.accessDeniedHandler((request, response, accessDeniedException) -> {
							response.sendRedirect("/access-denied");
						})
				);

		return http.build();
	}

	@Bean
	WebSecurityCustomizer securityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/static/**", "/assetsuser/**", "/assets/**","/uploads/**");
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
