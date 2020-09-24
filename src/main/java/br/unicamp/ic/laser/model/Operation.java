package br.unicamp.ic.laser.model;

import java.util.List;

public class Operation {
	private String path;
	private String name;
	private List<String> paramList;
	private List<String> usingTypesList;
	private String responseType;
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getParamList() {
		return paramList;
	}
	
	public void setParamList(List<String> paramList) {
		this.paramList = paramList;
	}
	
	public List<String> getUsingTypesList() {
		return usingTypesList;
	}
	
	public void setUsingTypesList(List<String> usingTypesList) {
		this.usingTypesList = usingTypesList;
	}
	
	public String getResponseType() {
		return responseType;
	}
	
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	
}
