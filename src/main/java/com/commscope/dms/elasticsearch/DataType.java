/**
 *
 * Copyright (c) 2015 Airvana LP. All rights reserved.
 * 
 */

package com.commscope.dms.elasticsearch;

import java.util.ArrayList;
import java.util.List;

public enum DataType {
	NUMERIC(FilterOperator.EQUALS, FilterOperator.NOT_EQUALS,
			FilterOperator.GREATER_THAN, FilterOperator.LESSER_THAN),
	STRING(FilterOperator.EQUALS, FilterOperator.NOT_EQUALS,
			FilterOperator.CONTAINS), BOOLEAN( FilterOperator.EQUALS,
			FilterOperator.NOT_EQUALS), DATE(FilterOperator.BETWEEN),VERSION(FilterOperator.EQUALS, FilterOperator.NOT_EQUALS,
					FilterOperator.CONTAINS,FilterOperator.GREATER_THAN,FilterOperator.GREATER_THAN_EQUAL,FilterOperator.LESSER_THAN,FilterOperator.LESS_THAN_EQUAL);

	private List<FilterOperator> operators = new ArrayList<FilterOperator>();

	private DataType(FilterOperator... operators) {
		for (FilterOperator operator : operators) {
			this.operators.add(operator);
		}
	}
	public List<FilterOperator> getOperators() {
		return operators;
	}

	public boolean eval(FilterOperator operator, String operand, Object value, Object toValue) {
		return false;
	}
}