package com.commscope.dms.elasticsearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	List<FilterExpression> expressions = new ArrayList<FilterExpression>();
	Integer pageNum;
	Integer pageSize;
	String sortField;
	Boolean reverseOrder;
	// second option
	Map<String, String> filterMap = new HashMap<String, String>();

	public Map<String, String> getFilterMap() {
		return filterMap;
	}

	public void setFilterMap(Map<String, String> filterMap) {
		this.filterMap = filterMap;
	}

	public List<FilterExpression> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<FilterExpression> expressions) {
		this.expressions = expressions;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public Boolean getReverseOrder() {
		return reverseOrder;
	}

	public void setReverseOrder(Boolean reverseOrder) {
		this.reverseOrder = reverseOrder;
	}

}
