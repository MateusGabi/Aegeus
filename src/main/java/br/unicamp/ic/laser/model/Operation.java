package br.unicamp.ic.laser.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Operation {
	private String path;
	private String name;
	private List<String> paramList;
	private List<String> usingTypesList;
	private String responseType;
}
