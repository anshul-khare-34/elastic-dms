package com.commscope.dms.elasticsearch;

import java.io.Serializable;

public class FilterExpression implements Serializable {
	private static final long serialVersionUID = 1L;

	private String columnName;

	private Object value;

	private Object toValue;

	private FilterOperator operator;

	private FilterCondition filterCondition;
	private DataType dataType;
	public FilterExpression() {
		
	}
	public FilterExpression(String columnName, Object value, FilterOperator operator, FilterCondition filterCondition, DataType dataType){
		this.columnName = columnName;
		this.value = value;
		this.operator = operator;
		this.filterCondition = filterCondition;
		this.dataType = dataType;
		}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getToValue() {
		return toValue;
	}

	public void setToValue(Object toValue) {
		this.toValue = toValue;
	}

	public FilterOperator getOperator() {
		return operator;
	}

	public void setOperator(FilterOperator operator) {
		this.operator = operator;
	}

	public FilterCondition getFilterCondition() {
		return filterCondition;
	}

	public void setFilterCondition(FilterCondition filterCondition) {
		this.filterCondition = filterCondition;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

}
