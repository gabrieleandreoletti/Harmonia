package org.elis.service.implementation;

import org.elis.repository.jpa.JpaCustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MyUserDetailService  implements UserDetailsService{
private final JpaCustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return customerRepository.findCustomerByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username: " + username + " non trovato"));
				
	}
}
