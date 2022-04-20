/**
 *
 * Copyright (c) 2015 Airvana LP. All rights reserved.
 * 
 */

package com.commscope.dms.elasticsearch.exception;

/**
 * @(#) ElkSearchException.java
 *
 */


@SuppressWarnings("serial")
public class ElkSearchException extends Exception {

	//@Autowired
	//MessageUtils msgSource;
	
	// Error code which denotes a particular error.
	// The detail message is retrieved from property file based on this code.  
	private String errorCode;
	
	// error message in case unknown exception
	private String errorMsg;
	
	private Object[] args;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String getMessage(){
		
		return this.getErrorMsg();		
	}
	
	public String getErrorMsg() {
		
		String result = null;
		
		if(this.errorMsg != null)
			return this.errorMsg;
			
		return result;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	public ElkSearchException() {
		super();
	}

	public ElkSearchException(String errorCode, Object[] args) {
		
		super(errorCode);
		this.errorCode = errorCode;
		this.args = args;
	}
	
	public ElkSearchException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public ElkSearchException(Throwable t) {
		super(t);
	}
	
	public ElkSearchException(String errorCode) {
		this.errorCode = errorCode;
	}

	public ElkSearchException(String message, Throwable cause) {
		super(message, cause);
		this.errorMsg = message;
	}
	
}
