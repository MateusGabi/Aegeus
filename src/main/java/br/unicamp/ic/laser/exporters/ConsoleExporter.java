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
        metricResults.forEach(metricResult -> {
            System.out.println("SERVICE NAME: " + metricResult.getServiceName());
            System.out.println("VERSION: " + metricResult.getVersion());
            System.out.println("METRIC: " + metricResult.getMetricName());
            System.out.println("VALUE: " + metricResult.getMetricValue());
        });
    }
}
