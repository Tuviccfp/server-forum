package com.platformtest.app.exception;

public class IdNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IdNotFound(String msg) {
		super(msg);
	}

}
