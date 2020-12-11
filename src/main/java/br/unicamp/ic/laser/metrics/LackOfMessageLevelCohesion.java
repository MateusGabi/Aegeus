package br.unicamp.ic.laser.metrics;

import br.unicamp.ic.laser.model.IServiceDescriptor;
import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.Parameter;
import br.unicamp.ic.laser.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class LackOfMessageLevelCohesion extends AbstractMetric {

    public LackOfMessageLevelCohesion() {
        this.setMetricName("LackOfMessageLevelCohesion");
    }

    @Override
    public void evaluate() {
        IServiceDescriptor serviceDescriptor = this.getServiceDescriptor();

        MetricResult metricResult = new MetricResult();
        metricResult.setMetricName(this.getMetricName());
        metricResult.setServiceName(serviceDescriptor.getServiceName());
        metricResult.setVersion(serviceDescriptor.getServiceVersion());
        metricResult.setMetricValue(0.0);

        this.setResult(metricResult);

        if (serviceDescriptor == null || serviceDescriptor.getServiceOperations() == null) {
            // do nothing
        }

        if (serviceDescriptor.getServiceOperations().size() == 0) {
            // do nothing
        }

        else {
            List<Operation> operations = serviceDescriptor.getServiceOperations();
            ArrayList<ArrayList<Operation>> operationPairs = Utils.pairs(operations);

            int numberOfPairs = operationPairs.size();

            ArrayList<Double> operationSimilarityList = new ArrayList<>();
            for (ArrayList<Operation> pair : operationPairs) {
                Operation firstOperation = pair.get(0);
                Operation secondOperation = pair.get(1);

                double operationSimilarity = (inputDataSimilarity(firstOperation, secondOperation) +
                        outputDataSimilarity(firstOperation, secondOperation)) / 2;

                operationSimilarityList.add(operationSimilarity);
            }

            double acc = 0.0;
            for (Double similarity : operationSimilarityList) {
                acc = acc + (1 - similarity);
            }

            this.getResult().setMetricValue(acc / numberOfPairs);
        }
    }

    public Double inputDataSimilarity(Operation firstOperation, Operation secondOperation) {
        HashSet<String> unionOfProperties = new HashSet<>();

        List<String> firstOperationParameterNames = Parameter.getParameterNames(firstOperation.getParamList());
        List<String> secondOperationParameterNames = Parameter.getParameterNames(secondOperation.getParamList());

        unionOfProperties.addAll(firstOperationParameterNames);
        unionOfProperties.addAll(secondOperationParameterNames);

        int sizeOfUnionOfProperties = unionOfProperties.size();

        List<String> commonProperties = firstOperationParameterNames.stream()
                .filter(secondOperationParameterNames::contains).collect(Collectors.toList());

        double inputDataSimilarity = commonProperties.size() / (sizeOfUnionOfProperties * 1.0);

        return inputDataSimilarity;
    }

    public Double outputDataSimilarity(Operation firstOperation, Operation secondOperation) {
        HashSet<String> unionOfProperties = new HashSet<>();
        unionOfProperties.add(firstOperation.getResponseType());
        unionOfProperties.add(secondOperation.getResponseType());

        return unionOfProperties.size() == 1 ? 1.0 : 0.0;
    }
}
