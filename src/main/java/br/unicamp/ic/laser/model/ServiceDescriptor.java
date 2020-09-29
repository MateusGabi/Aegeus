package br.unicamp.ic.laser.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


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

		public ServiceDescriptor build(String file) {
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
	
}

class TextFileServiceDescriptorBuilder implements IServiceDescriptorBuilder {

	@Override
	public ServiceDescriptor build(String fileName) {
		ServiceDescriptor serviceDescriptor = new ServiceDescriptor();

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return serviceDescriptor;
	}
}
