package br.unicamp.ic.laser.metrics;

import br.unicamp.ic.laser.model.IServiceDescriptor;
import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.utils.Utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LackOfMessageLevelCohesion implements IMetric {
    @Override
    public Double evaluate(IServiceDescriptor serviceDescriptor) {

        if (serviceDescriptor == null || serviceDescriptor.getServiceOperations() == null) {
            return 0.0;
        }

        if (serviceDescriptor.getServiceOperations().size() == 0) {
            return 0.0;
        }

        // set of all operations type
        long totalOfParamsType = serviceDescriptor.getServiceOperations()
                .stream()
                .map(o -> o.getParamList())
                .filter(Objects::nonNull)
                .flatMap(types -> types.stream())
                .distinct()
                .count();

        // pairs of operations
        int intersectTypesSize = Utils
                .pairs(serviceDescriptor.getServiceOperations().stream().map(o -> o.getParamList()).collect(Collectors.toList())).stream()
                .map((pair) -> {
                    List<String> typesIntoFirstOperation = pair.get(0);
                    List<String> typesIntoSecondOperation = pair.get(1);

                    List<String> intersectElements = typesIntoFirstOperation.stream()
                            .filter(typesIntoSecondOperation::contains).collect(Collectors.toList());

                    return intersectElements;
                }).flatMap(types -> types.stream())
                .collect(Collectors.toSet()).size();

        double inputDataSimilarity = intersectTypesSize / (totalOfParamsType * 1.0);

        // calculate outputDataSimilarity
        // set of all operations type
        long totalOfOutputType = serviceDescriptor.getServiceOperations()
                .stream()
                .map(o -> o.getResponseType())
                .filter(Objects::nonNull)
                .distinct()
                .count();

        // pairs of operations
        int intersectOutputTypesSize = Utils
                .pairs(serviceDescriptor.getServiceOperations().stream().map(o -> o.getResponseType()).collect(Collectors.toList())).stream()
                .map((pair) -> {
                    String response1 = pair.get(0);
                    String response2 = pair.get(1);

                    if (response1.equals(response2)) {
                        return response1;
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
                .size();

        double outputDataSimilarity = intersectOutputTypesSize / (totalOfOutputType * 1.0);

        return inputDataSimilarity + outputDataSimilarity;
    }
}
