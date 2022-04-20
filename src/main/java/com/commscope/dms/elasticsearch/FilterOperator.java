package com.commscope.dms.elasticsearch;

public enum FilterOperator {

	EQUALS, NOT_EQUALS,CONTAINS, NOT_CONTAINS, GREATER_THAN, LESSER_THAN, BETWEEN,GREATER_THAN_EQUAL,LESS_THAN_EQUAL,BOOLEAN,IN,IS_NOT_EMPTY;
	String sqlOperator;
	private FilterOperator() {
	}
	
	private FilterOperator(String sqlOperator) {
		this.sqlOperator = sqlOperator;
	}
	
	public String getSqlOperator() {
		return sqlOperator;
	}
}
