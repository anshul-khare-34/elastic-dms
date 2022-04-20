package com.commscope.dms.elasticsearch;

public class ElasticServiceStatusException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ElasticServiceStatusException() {
		super();
	}

	public ElasticServiceStatusException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ElasticServiceStatusException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElasticServiceStatusException(String message) {
		super(message);
	}

	public ElasticServiceStatusException(Throwable cause) {
		super(cause);
	}
	
}
