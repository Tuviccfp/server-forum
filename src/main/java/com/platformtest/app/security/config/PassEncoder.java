package com.platformtest.app.security.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassEncoder {

	private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	public static String encodePassword(String password) {
		return bCryptPasswordEncoder.encode(password);
	}
	
	public static boolean matches(String password, String encodedString) {
		return bCryptPasswordEncoder.matches(password, encodedString);
	}
}
