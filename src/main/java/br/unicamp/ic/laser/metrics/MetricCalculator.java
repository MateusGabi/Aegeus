package br.unicamp.ic.laser.metrics;

import br.unicamp.ic.laser.model.IServiceDescriptor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MetricCalculator {
    //    Add here all metrics instances
    private List<IMetric> getMetrics() {
        return Arrays.asList(
                new ServiceInterfaceDataCohesion(),
                new StrictServiceImplementationCohesion(),
                new LackOfMessageLevelCohesion()
        );
    }

    public List<MetricResult> assess(IServiceDescriptor serviceDescriptor) {
        List<IMetric> metrics = getMetrics();

        if (metrics.size() == 0) {
            return Arrays.asList();
        }

        List metricResults = metrics.stream().map(metric ->
                new MetricResult(serviceDescriptor.getServiceName(), serviceDescriptor.getServiceVersion(), metric.getClass().toString(), metric.evaluate(serviceDescriptor))).collect(Collectors.toList());

        return metricResults;
    }
}
