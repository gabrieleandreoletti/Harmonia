package org.elis.security;

import org.elis.model.Role;
import org.elis.repository.jpa.JpaCustomerRepository;
import org.elis.security.filter.MyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class CustomSecurity {
	private final MyFilter myFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		// return NoOpPaaswordEncoder.getInstance();

	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class);
		httpSecurity.csrf(t -> t.disable());
		httpSecurity.sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		httpSecurity.authorizeHttpRequests(t -> t.requestMatchers("/all/**").permitAll()
				.requestMatchers("/base/**").hasAnyRole(Role.BASE.toString(), Role.ADMIN.toString(), Role.ARTIST.toString(),Role.PREMIUM.toString())
				.requestMatchers("/premium/**").hasAnyRole(Role.PREMIUM.toString(), Role.ADMIN.toString())
				.requestMatchers("/artist/**").hasAnyRole(Role.ARTIST.toString(), Role.ADMIN.toString())
				.requestMatchers("/admin/**").hasRole(Role.ADMIN.toString()).anyRequest().permitAll());
		return httpSecurity.build();
	}

}
