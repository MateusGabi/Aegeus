package br.unicamp.ic.laser.metrics;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.ServiceDescriptor;

public class StrictServiceImplementationCohesionMetricTest {

	@Test
	public void should_return_not_null() {
		ServiceDescriptor serviceDescriptor = new ServiceDescriptor();
		Double actual = new StrictServiceImplementationCohesion().evaluate(serviceDescriptor);

		assertNotNull(actual);
		assertEquals(0.0, actual, 0.0);
		assertEquals("UnkownService", serviceDescriptor.getServiceName());
	}

	@Test
	public void should_calc_two_services() {
		// Defining operation A
		Operation operationServiceA = new Operation();
		operationServiceA.setName("Service A");
		ArrayList<String> typesOfOperationsServiceA = new ArrayList<String>();
		typesOfOperationsServiceA.add("Apple");
		typesOfOperationsServiceA.add("Banana");
		typesOfOperationsServiceA.add("Throwable");

		operationServiceA.setUsingTypesList(typesOfOperationsServiceA);

		// Defining operation B
		Operation operationServiceB = new Operation();
		operationServiceB.setName("Service B");
		ArrayList<String> typesOfOperationsServiceB = new ArrayList<String>();
		typesOfOperationsServiceB.add("Fruit");
		typesOfOperationsServiceB.add("Banana");
		typesOfOperationsServiceB.add("Throwable");

		operationServiceB.setUsingTypesList(typesOfOperationsServiceB);

		// Defining operations
		ArrayList<Operation> operations = new ArrayList<Operation>();
		operations.add(operationServiceA);
		operations.add(operationServiceB);

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("Service", operations);
		Double actual = new StrictServiceImplementationCohesion().evaluate(serviceDescriptor);

		assertNotNull(actual);
		assertEquals(0.5, actual, 0.0);
	}

	@Test
	public void should_calc_two_services2() {
		// Defining operation A
		Operation operationServiceA = new Operation();
		operationServiceA.setName("Service A");
		ArrayList<String> typesOfOperationsServiceA = new ArrayList<String>();
		typesOfOperationsServiceA.add("Apple");
		typesOfOperationsServiceA.add("Banana");
		typesOfOperationsServiceA.add("Banana");
		typesOfOperationsServiceA.add("Throwable");

		operationServiceA.setUsingTypesList(typesOfOperationsServiceA);

		// Defining operation B
		Operation operationServiceB = new Operation();
		operationServiceB.setName("Service B");
		ArrayList<String> typesOfOperationsServiceB = new ArrayList<String>();
		typesOfOperationsServiceB.add("Fruit");
		typesOfOperationsServiceB.add("Banana");
		typesOfOperationsServiceB.add("Throwable");

		operationServiceB.setUsingTypesList(typesOfOperationsServiceB);

		// Defining operations
		ArrayList<Operation> operations = new ArrayList<Operation>();
		operations.add(operationServiceA);
		operations.add(operationServiceB);

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("Service A", operations);

		Double actual = new StrictServiceImplementationCohesion().evaluate(serviceDescriptor);

		Assert.assertNotNull(actual);
		Assert.assertEquals(0.5, actual, 0.0);
	}

	@Test
	public void should_calc_two_services1() {
		// Defining operation A
		Operation operationServiceA = new Operation();
		operationServiceA.setName("Service A");
		ArrayList<String> typesOfOperationsServiceA = new ArrayList<String>();
		typesOfOperationsServiceA.add("Apple");
		typesOfOperationsServiceA.add("Banana");

		operationServiceA.setUsingTypesList(typesOfOperationsServiceA);

		// Defining operation B
		Operation operationServiceB = new Operation();
		operationServiceB.setName("Service B");
		ArrayList<String> typesOfOperationsServiceB = new ArrayList<String>();
		typesOfOperationsServiceB.add("Coconut");

		operationServiceB.setUsingTypesList(typesOfOperationsServiceB);

		// Defining operations
		ArrayList<Operation> operations = new ArrayList<Operation>();
		operations.add(operationServiceA);
		operations.add(operationServiceB);

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("Service A", operations);

		Double actual = new StrictServiceImplementationCohesion().evaluate(serviceDescriptor);

		Assert.assertNotNull(actual);
		Assert.assertEquals(0.0, actual, 0.0);
	}

	@Test
	public void should_calc_three_services() {
		// Defining operation A
		Operation operationServiceA = new Operation();
		operationServiceA.setName("Service A");
		ArrayList<String> typesOfOperationsServiceA = new ArrayList<String>();
		typesOfOperationsServiceA.add("Apple");
		typesOfOperationsServiceA.add("Banana");
		typesOfOperationsServiceA.add("Throwable");

		operationServiceA.setUsingTypesList(typesOfOperationsServiceA);

		// Defining operation B
		Operation operationServiceB = new Operation();
		operationServiceB.setName("Service B");
		ArrayList<String> typesOfOperationsServiceB = new ArrayList<String>();
		typesOfOperationsServiceB.add("Fruit");
		typesOfOperationsServiceB.add("Banana");
		typesOfOperationsServiceB.add("Throwable");

		operationServiceB.setUsingTypesList(typesOfOperationsServiceB);

		// Defining operation C
		Operation operationServiceC = new Operation();
		operationServiceC.setName("Service C");
		ArrayList<String> typesOfOperationsServiceC = new ArrayList<String>();
		typesOfOperationsServiceC.add("Coconut");
		typesOfOperationsServiceC.add("Throwable");

		operationServiceC.setUsingTypesList(typesOfOperationsServiceC);

		// Defining operation D
		Operation operationServiceD = new Operation();
		operationServiceD.setName("Service D");
		ArrayList<String> typesOfOperationsServiceD = new ArrayList<String>();
		typesOfOperationsServiceD.add("Apple");
		typesOfOperationsServiceD.add("Throwable");

		operationServiceB.setUsingTypesList(typesOfOperationsServiceD);

		// Defining operations
		ArrayList<Operation> operations = new ArrayList<Operation>();
		operations.add(operationServiceA);
		operations.add(operationServiceB);
		operations.add(operationServiceC);
		operations.add(operationServiceD);

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("Service A", operations);

		Double actual = new StrictServiceImplementationCohesion().evaluate(serviceDescriptor);

		Assert.assertNotNull(actual);
		Assert.assertEquals(0.8, actual, 0.0);
	}

	@Test
	public void example_figure_6_from_article() {
		// Defining operation A
		Operation operationServiceA = new Operation();
		operationServiceA.setName("Service A");
		ArrayList<String> typesOfOperationsServiceA = new ArrayList<String>();
		typesOfOperationsServiceA.add("A");
		typesOfOperationsServiceA.add("B");
		typesOfOperationsServiceA.add("C");
		typesOfOperationsServiceA.add("D");

		operationServiceA.setUsingTypesList(typesOfOperationsServiceA);

		// Defining operation B
		Operation operationServiceB = new Operation();
		operationServiceB.setName("Service B");
		ArrayList<String> typesOfOperationsServiceB = new ArrayList<String>();
		typesOfOperationsServiceB.add("A");
		typesOfOperationsServiceB.add("B");
		typesOfOperationsServiceB.add("C");
		typesOfOperationsServiceB.add("D");

		operationServiceB.setUsingTypesList(typesOfOperationsServiceB);

		// Defining operations
		ArrayList<Operation> operations = new ArrayList<Operation>();
		operations.add(operationServiceA);
		operations.add(operationServiceB);

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("Service A", operations);

		Double actual = new StrictServiceImplementationCohesion().evaluate(serviceDescriptor);

		Assert.assertNotNull(actual);
		Assert.assertEquals(1.0, actual, 0.0);
	}

	@Test
	public void example_figure_5_from_article() {
		// Defining operation A
		Operation operationServiceA = new Operation();
		operationServiceA.setName("EnrollStudent::getLibraryClearance");
		ArrayList<String> typesOfOperationsServiceA = new ArrayList<String>();
		typesOfOperationsServiceA.add("I");
		typesOfOperationsServiceA.add("B");
		typesOfOperationsServiceA.add("E");

		operationServiceA.setUsingTypesList(typesOfOperationsServiceA);

		// Defining operation B
		Operation operationServiceB = new Operation();
		operationServiceB.setName("EnrollStudent::checkPrerequisiteCourses");
		ArrayList<String> typesOfOperationsServiceB = new ArrayList<String>();
		typesOfOperationsServiceB.add("A");
		typesOfOperationsServiceB.add("B");
		typesOfOperationsServiceB.add("C");
		typesOfOperationsServiceB.add("D");
		typesOfOperationsServiceB.add("E");
		typesOfOperationsServiceB.add("F");

		operationServiceB.setUsingTypesList(typesOfOperationsServiceB);

		// Defining operation C
		Operation operationServiceC = new Operation();
		operationServiceC.setName("EnrollStudent::enrollStudentIntoCourse");
		ArrayList<String> typesOfOperationsServiceC = new ArrayList<String>();
		typesOfOperationsServiceC.add("H");
		typesOfOperationsServiceC.add("G");
		typesOfOperationsServiceC.add("F");

		operationServiceC.setUsingTypesList(typesOfOperationsServiceC);

		// Defining operations
		ArrayList<Operation> operations = new ArrayList<Operation>();
		operations.add(operationServiceA);
		operations.add(operationServiceB);
		operations.add(operationServiceC);

		ServiceDescriptor serviceDescriptor = new ServiceDescriptor("Service A", operations);

		Double actual = new StrictServiceImplementationCohesion().evaluate(serviceDescriptor);

		Assert.assertEquals(6 / (3.0 * 9), actual, 0.0);

	}
}
