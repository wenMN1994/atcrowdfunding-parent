package com.dragon.atcrowdfunding.exception;
/**
 * 
 * <p>Title: LoginException</p>  
 * <p>Description: </p>  
 * @author Dragon.Wen
 * @date 2019年11月28日
 */
public class LoginException extends RuntimeException {
	public LoginException() {}
	
	public LoginException(String message) {
		super(message);
	}
}
