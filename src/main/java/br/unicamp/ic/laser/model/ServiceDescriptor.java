package br.unicamp.ic.laser.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Description on a SOA Service.
 *  
 * @author Mateus Gabi Moreira
 * @version 1.0.0
 */
public class ServiceDescriptor {
	
	private String serviceName;
	private List<Operation> serviceOperations;
	
	public ServiceDescriptor() {
		this.serviceName = "UnkownService";
		this.serviceOperations = new ArrayList<Operation>();
	}
	
	public ServiceDescriptor(String name, List<Operation> operationsList) {
		super();
		this.serviceName = name;
		this.serviceOperations = operationsList;
	}

	public String getServiceName() {
		return serviceName;
	}

	public List<Operation> getServiceOperations() {
		return serviceOperations;
	}	
	
}
