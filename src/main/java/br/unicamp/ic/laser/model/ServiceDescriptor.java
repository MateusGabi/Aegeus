package br.unicamp.ic.laser.model;

import br.unicamp.ic.laser.readers.TextFileServiceDescriptorBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Description on a SOA Service.
 *  
 * @author Mateus Gabi Moreira
 * @version 1.0.0
 */
public class ServiceDescriptor implements IServiceDescriptor {

	public static class Builder {

		private IServiceDescriptorBuilder serviceDescriptorBuilder;

		public Builder(IServiceDescriptorBuilder serviceDescriptorBuilder) {
			this.serviceDescriptorBuilder = serviceDescriptorBuilder;
		}

		public Builder() {
			this.serviceDescriptorBuilder = new TextFileServiceDescriptorBuilder();
		}

		public IServiceDescriptor build(String file) throws IOException {
			return this.serviceDescriptorBuilder.build(file);
		}
	}
	
	private String serviceName;
	private List<Operation> serviceOperations;

	// TODO: become protected
	public ServiceDescriptor() {
		this.serviceName = "UnkownService";
		this.serviceOperations = new ArrayList<Operation>();
	}

	// TODO: become protected
	public ServiceDescriptor(String name, List<Operation> operationsList) {
		super();
		this.serviceName = name;
		this.serviceOperations = operationsList;
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public List<Operation> getServiceOperations() {
		return serviceOperations;
	}

	@Override
	public void setServiceName(String name) {
		this.serviceName = name;
	}

	@Override
	public void setServiceOperations(List<Operation> serviceOperations) {
		this.serviceOperations = serviceOperations;
	}

	@Override
	public String toString() {
		return "ServiceDescriptor{" +
				"serviceName='" + serviceName + '\'' +
				", serviceOperations=" + serviceOperations +
				'}';
	}
}

