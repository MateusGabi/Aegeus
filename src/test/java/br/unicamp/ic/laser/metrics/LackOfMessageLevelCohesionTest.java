package br.unicamp.ic.laser.metrics;

import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.ServiceDescriptor;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class LackOfMessageLevelCohesionTest {
    @Test
    public void should_not_return_null() {
        LackOfMessageLevelCohesion instance = new LackOfMessageLevelCohesion();
        instance.setServiceDescriptor(new ServiceDescriptor());
        instance.evaluate();

        Assert.assertNotNull(instance.getResult());
    }

    @Test
    /**
     * @see https://github.com/restful-ma/rama-cli/blob/master/docs/metrics/LackOfMessageLevelCohesion.md
     */
    public void example_of_locmes_from_RAMACLI_should_input_data_similarity() {
        Operation operationA = new Operation();
        operationA.setName("/pets");
        operationA.setParamList(Arrays.asList("limit"));

        Operation operationB = new Operation();
        operationB.setName("/pets/{age}");
        operationB.setParamList(Arrays.asList("limit", "age"));

//        ServiceDescriptor serviceDescriptor = new ServiceDescriptor("Service A", Arrays.asList(operationA, operationB));
//
        LackOfMessageLevelCohesion instance = new LackOfMessageLevelCohesion();
//        instance.setServiceDescriptor(serviceDescriptor);
//        instance.evaluate();

        double inputDataSimilarity = instance.inputDataSimilarity(operationA, operationB);

//        Assert.assertNotNull(instance.getResult());
        Assert.assertEquals(0.5, inputDataSimilarity, 0.0);
    }

    @Test
    /**
     * @see https://github.com/restful-ma/rama-cli/blob/master/docs/metrics/LackOfMessageLevelCohesion.md
     */
    public void example_of_locmes_from_RAMACLI_should_output_data_similarity() {
        Operation operationA = new Operation();
        operationA.setName("/pets");
        operationA.setResponseType("Pet");

        Operation operationB = new Operation();
        operationB.setName("/pets/{age}");
        operationB.setResponseType("Pet");

        LackOfMessageLevelCohesion instance = new LackOfMessageLevelCohesion();

        double inputDataSimilarity = instance.outputDataSimilarity(operationA, operationB);

        Assert.assertEquals(1.0, inputDataSimilarity, 0.0);
    }

    @Test
    /**
     * @see https://github.com/restful-ma/rama-cli/blob/master/docs/metrics/LackOfMessageLevelCohesion.md
     */
    public void example_of_locmes_from_RAMACLI_should_total() {
        Operation operationA = new Operation();
        operationA.setName("/pets");
        operationA.setResponseType("Pet");
        operationA.setParamList(Arrays.asList("limit"));


        Operation operationB = new Operation();
        operationB.setName("/pets/{age}");
        operationB.setResponseType("Pet");

        operationB.setParamList(Arrays.asList("limit", "age"));

        ServiceDescriptor serviceDescriptor = new ServiceDescriptor("Service A", Arrays.asList(operationA, operationB));

        LackOfMessageLevelCohesion instance = new LackOfMessageLevelCohesion();
        instance.setServiceDescriptor(serviceDescriptor);

        instance.evaluate();
        MetricResult result = instance.getResult();

        Assert.assertEquals(0.25, result.getMetricValue(), 0.0);

    }
}
