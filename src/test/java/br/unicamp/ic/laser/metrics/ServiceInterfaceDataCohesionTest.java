package br.unicamp.ic.laser.metrics;

import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.Parameter;
import br.unicamp.ic.laser.model.ServiceDescriptor;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ServiceInterfaceDataCohesionTest {
    @Test
    public void should_not_return_null() {
        ServiceInterfaceDataCohesion instance = new ServiceInterfaceDataCohesion();
        instance.setServiceDescriptor(new ServiceDescriptor());
        instance.evaluate();

        Assert.assertNotNull(instance.getResult());
    }

    @Test
    public void should_calc_two_services() {
        Operation operationA = new Operation();
        operationA.setName("Operation A");
        Parameter parameterA1 = new Parameter();
        parameterA1.setType("*String");
        Parameter parameterA2 = new Parameter();
        parameterA2.setType("*Double");
        operationA.setParamList(Arrays.asList(parameterA1, parameterA2));

        Operation operationB = new Operation();
        operationB.setName("Operation B");
        Parameter parameterB1 = new Parameter();
        parameterB1.setType("*String");
        operationB.setParamList(Arrays.asList(parameterB1));

        ServiceDescriptor serviceDescriptor = new ServiceDescriptor("Service A", Arrays.asList(operationA, operationB));

        ServiceInterfaceDataCohesion instance = new ServiceInterfaceDataCohesion();
        instance.setServiceDescriptor(serviceDescriptor);
        instance.evaluate();

        MetricResult result = instance.getResult();
        Assert.assertNotNull(result);
        Assert.assertEquals(0.5, result.getMetricValue(), 0.0);
    }
}
