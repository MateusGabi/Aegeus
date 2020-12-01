package br.unicamp.ic.laser.exporters;

import br.unicamp.ic.laser.metrics.MetricResult;

import java.util.List;

public class ConsoleExporter implements IExporter {
    @Override
    public void export(List<MetricResult> metricResults) {
        System.out.println("Using Console Exporter");

        if (metricResults.size() == 0) {
           // not implemented
        }
        metricResults.forEach(metricResult -> System.out.println(metricResult.getMetricName()+ ": "+metricResult.getMetricValue()));
    }
}
