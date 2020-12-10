package br.unicamp.ic.laser.metrics;

import br.unicamp.ic.laser.model.IServiceDescriptor;
import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.ServiceDescriptor;
import br.unicamp.ic.laser.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The measurement procedure consists of comparing the sets of parameter types for each
 * service operation so∈ SO (sis) in a pair-wise manner, and then placing the operations
 * with common parameter types into a set of Common operations. The cardinality of this
 * set is then divided by a total number of discrete parameter types for this service.
 * <p>
 * SIDC (s) = |Common(Param(so ∈ SO(sis )| / totalParamTypes
 */
public class ServiceInterfaceDataCohesion extends AbstractMetric {

    public ServiceInterfaceDataCohesion() {
        this.setMetricName("ServiceInterfaceDataCohesion");
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


            this.getResult().setMetricValue(intersectTypesSize / (totalOfParamsType * 1.0));
        }
    }
}
