package br.unicamp.ic.laser.metrics;

import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.Parameter;
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
        Parameter parameter = new Parameter();
        parameter.setName("limit");
        operationA.setParamList(Arrays.asList(parameter));

        Operation operationB = new Operation();
        operationB.setName("/pets/{age}");
        Parameter parameter1 = new Parameter();
        parameter1.setName("limit");

        Parameter parameter2 = new Parameter();
        parameter2.setName("age");
        operationB.setParamList(Arrays.asList(parameter1, parameter2));

        LackOfMessageLevelCohesion instance = new LackOfMessageLevelCohesion();

        double inputDataSimilarity = instance.inputDataSimilarity(operationA, operationB);

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
        Parameter parameter = new Parameter();
        parameter.setName("limit");
        operationA.setParamList(Arrays.asList(parameter));


        Operation operationB = new Operation();
        operationB.setName("/pets/{age}");
        operationB.setResponseType("Pet");
        Parameter parameter1 = new Parameter();
        parameter1.setName("limit");

        Parameter parameter2 = new Parameter();
        parameter2.setName("age");
        operationB.setParamList(Arrays.asList(parameter1, parameter2));

        ServiceDescriptor serviceDescriptor = new ServiceDescriptor("Service A", Arrays.asList(operationA, operationB));

        LackOfMessageLevelCohesion instance = new LackOfMessageLevelCohesion();
        instance.setServiceDescriptor(serviceDescriptor);

        instance.evaluate();
        MetricResult result = instance.getResult();

        Assert.assertEquals(0.25, result.getMetricValue(), 0.0);

    }

    @Test
    /**
     * https://github.com/spinnaker/clouddriver/blob/c9c9608b58d9c48ccd6545c9acdb632ebaa67c1f/clouddriver-web/src/main/groovy/com/netflix/spinnaker/clouddriver/controllers/admin/EntityTagsAdminController.java
     */
    public void should_not_NaN() {
        Operation operation1 = new Operation();
        operation1.setName("reindex");
        operation1.setResponseType("void");

        Operation operation2 = new Operation();
        operation2.setName("delta");
        operation2.setResponseType("Map");

        Operation operation3 = new Operation();
        operation3.setName("/reconcile");
        operation3.setResponseType("Map");

        Parameter parameter1 = new Parameter();
        parameter1.setType("Boolean");
        parameter1.setName("dryRun");

        Parameter parameter2 = new Parameter();
        parameter2.setType("String");
        parameter2.setName("cloudProvider");

        Parameter parameter3 = new Parameter();
        parameter3.setType("String");
        parameter3.setName("account");

        Parameter parameter4 = new Parameter();
        parameter4.setType("String");
        parameter4.setName("region");

        operation3.setParamList(Arrays.asList(parameter1, parameter2, parameter3, parameter4));

        Operation operation4 = new Operation();
        operation4.setName("/deleteByNamespace");
        operation4.setResponseType("Map");

        Parameter parameter5 = new Parameter();
        parameter5.setType("String");
        parameter5.setName("namespace");

        Parameter parameter6 = new Parameter();
        parameter6.setType("Boolean");
        parameter6.setName("dryRun");

        Parameter parameter7 = new Parameter();
        parameter7.setType("Boolean");
        parameter7.setName("deleteFromSource");

        operation4.setParamList(Arrays.asList(parameter5, parameter6, parameter7));

        Operation operation5 = new Operation();
        operation5.setName("/deleteByTag");
        operation5.setResponseType("Map");

        Parameter parameter8 = new Parameter();
        parameter8.setType("String");
        parameter8.setName("namespace");

        Parameter parameter9 = new Parameter();
        parameter9.setType("Boolean");
        parameter9.setName("dryRun");

        Parameter parameter10 = new Parameter();
        parameter10.setType("Boolean");
        parameter10.setName("deleteFromSource");

        operation5.setParamList(Arrays.asList(parameter8, parameter9, parameter10));

        ServiceDescriptor serviceDescriptor = new ServiceDescriptor("EntityTagsAdminController", Arrays.asList(operation1, operation2, operation3, operation4, operation5));

        LackOfMessageLevelCohesion instance = new LackOfMessageLevelCohesion();
        instance.setServiceDescriptor(serviceDescriptor);

        instance.evaluate();
        MetricResult result = instance.getResult();

        Assert.assertEquals(0.5833333333333334, result.getMetricValue(), 0.003);
    }
}
