package com.platformtest.app.security.userdetails;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.platformtest.app.domain.User;
import com.platformtest.app.repository.UserRepository;

@Service
public class UserDetailsImp implements UserDetailsService {

	public UserDetailsImp() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Não foi possível encontrar seus dados");
		} 
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.get().getRole()));
		UserDetails returnUser = new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), authorities);
		return returnUser;
	}

}
