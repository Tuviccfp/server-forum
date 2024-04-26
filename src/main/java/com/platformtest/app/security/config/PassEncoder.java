package com.platformtest.app.security.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PassEncoder implements PasswordEncoder {

	private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	public static String encodePassword(String password) {
		return bCryptPasswordEncoder.encode(password);
	}
	
	public static boolean matches(String password, String rawPassword) {
		return bCryptPasswordEncoder.matches(password, rawPassword);
	} 

	public static String encode() {
		// TODO Auto-generated method stub
		return bCryptPasswordEncoder.encode("");
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String encode(CharSequence rawPassword) {
		// TODO Auto-generated method stub
		return null;
	}
}
