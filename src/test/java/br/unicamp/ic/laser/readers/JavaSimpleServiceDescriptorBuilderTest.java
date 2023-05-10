package br.unicamp.ic.laser.readers;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import br.unicamp.ic.laser.model.IServiceDescriptor;
import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.Parameter;

public class JavaSimpleServiceDescriptorBuilderTest {
	
	private boolean fileContent;
	private JavaSimpleServiceDescriptorBuilder instance;
	
	@Before
	public void setUp() {
		this.instance = new JavaSimpleServiceDescriptorBuilder();
		this.fileContent = true;
	}

	@Test
	public void testShouldThrowIfFilePathNull() {
		try {
			IServiceDescriptor serviceDescriptor = this.instance.build(null);
			assertFalse(true);
		} catch (IOException e) {
			assertEquals("Filepath should not be null or empty.", e.getMessage());
		}
	}
	
	@Test
	public void testShouldThrowIfFilePathEmpty() {
		try {
			IServiceDescriptor serviceDescriptor = this.instance.build("");
			assertFalse(true);
		} catch (IOException e) {
			assertEquals("Filepath should not be null or empty.", e.getMessage());
		}		
	}
	
	@Test
	public void testShouldGenerateServiceDescriptor() throws IOException {
		IServiceDescriptor serviceDescriptor = this.instance.build("src/test/resources/SpringExample.java");
		
		assertEquals("CapabilitiesController", serviceDescriptor.getServiceName());
		assertEquals(3, serviceDescriptor.getServiceOperations().size());
		
		// FIRST OPERATION
		Operation operation = serviceDescriptor.getServiceOperations().get(0); 
		assertEquals("CapabilitiesController::getDeploymentMonitors", operation.getName());
		assertEquals("List<Object>", operation.getResponseType());
		
		List<Parameter> params = operation.getParamList();
		assertEquals(0, params.size());
		
		List<String> usingTypesList = operation.getUsingTypesList();
		assertEquals(1, usingTypesList.size());
		assertEquals("OrcaServiceSelector", usingTypesList.get(0));
		
		// SECOND OPERATION
		operation = serviceDescriptor.getServiceOperations().get(1); 
		assertEquals("CapabilitiesController::getExpressionCapabilities", operation.getName());
		assertEquals("Map", operation.getResponseType());
		
		params = operation.getParamList();
		assertEquals(0, params.size());
		
		usingTypesList = operation.getUsingTypesList();
		assertEquals(1, usingTypesList.size());
		assertEquals("OrcaServiceSelector", usingTypesList.get(0));
		
		// THIRD OPERATION
		operation = serviceDescriptor.getServiceOperations().get(2); 
		assertEquals("CapabilitiesController::getQuietPeriodState", operation.getName());
		assertEquals("Map", operation.getResponseType());
		
		params = operation.getParamList();
		assertEquals(0, params.size());
		
		usingTypesList = operation.getUsingTypesList();
		assertEquals(1, usingTypesList.size());
		assertEquals("Optional", usingTypesList.get(0));
	}

	@Test
	public void testShouldGenerateServiceDescriptorSingleOperation() throws IOException {
		IServiceDescriptor serviceDescriptor = this.instance.build("src/test/resources/SpringExampleOneOperation.java");
		
		assertEquals("FooController", serviceDescriptor.getServiceName());
		assertEquals(1, serviceDescriptor.getServiceOperations().size());
		
		// FIRST OPERATION
		Operation operation = serviceDescriptor.getServiceOperations().get(0); 
		assertEquals("FooController::getFools", operation.getName());
		assertEquals("List<Object>", operation.getResponseType());
		
		List<Parameter> params = operation.getParamList();
		assertEquals(0, params.size());
		
		List<String> usingTypesList = operation.getUsingTypesList();
		assertEquals(1, usingTypesList.size());
		assertEquals("FooSelector", usingTypesList.get(0));
	}
	
	@Test
	public void testShouldGenerateServiceDescriptorWithParams() throws IOException {
		IServiceDescriptor serviceDescriptor = this.instance.build("src/test/resources/SpringWithParams.java");
		
		assertEquals("GCBController", serviceDescriptor.getServiceName());
		assertEquals(2, serviceDescriptor.getServiceOperations().size());
		
		// FIRST OPERATION
		Operation operation = serviceDescriptor.getServiceOperations().get(0); 
		assertEquals("GCBController::getAs", operation.getName());
		assertEquals("List<String>", operation.getResponseType());
		
		List<Parameter> params = operation.getParamList();
		assertEquals(0, params.size());
		
		List<String> usingTypesList = operation.getUsingTypesList();
		assertEquals(1, usingTypesList.size());
		assertEquals("IS", usingTypesList.get(0));
		
		// SECOND OPERATION
		operation = serviceDescriptor.getServiceOperations().get(1); 
		assertEquals("GCBController::getGCBTs", operation.getName());
		assertEquals("List<GCBT>", operation.getResponseType());
		
		params = operation.getParamList();
		assertEquals(1, params.size());
		assertEquals("account", params.get(0).getName());
		assertEquals("String", params.get(0).getType());
		
		usingTypesList = operation.getUsingTypesList();
		assertEquals(2, usingTypesList.size());
		assertEquals("BS", usingTypesList.get(0));
		assertEquals("String", usingTypesList.get(1));
	}
}
