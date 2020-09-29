package br.unicamp.ic.laser.metrics;

import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.ServiceDescriptor;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ServiceInterfaceDataCohesionTest {
    @Test
    public void should_not_return_null() {
        ServiceInterfaceDataCohesion instance = new ServiceInterfaceDataCohesion();

        Assert.assertNotNull(instance.evaluate(new ServiceDescriptor()));
        Assert.assertNotNull(instance.evaluate(null));
    }

    @Test
    public void should_calc_two_services() {
        Operation operationA = new Operation();
        operationA.setName("Operation A");
        operationA.setParamList(Arrays.asList("*String", "*Double"));

        Operation operationB = new Operation();
        operationB.setName("Operation B");
        operationB.setParamList(Arrays.asList("*String"));

        ServiceDescriptor serviceDescriptor = new ServiceDescriptor("Service A", Arrays.asList(operationA, operationB));
        Double actual = new ServiceInterfaceDataCohesion().evaluate(serviceDescriptor);

        Assert.assertNotNull(actual);
        Assert.assertEquals(0.5, actual, 0.0);
    }
}
