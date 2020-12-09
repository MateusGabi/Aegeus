package br.unicamp.ic.laser.exporters;

import br.unicamp.ic.laser.metrics.MetricResult;

import java.util.List;

public class CsvExporter implements IExporter {
    @Override
    public void export(List<MetricResult> metricResults) {
        if (metricResults.size() == 0) {
            // not implemented
        }

        metricResults.forEach(metricResult ->
            System.out.println(metricResult.getServiceName() + ","
                    + metricResult.getVersion() + ","
                    + metricResult.getMetricName() + ","
                    + metricResult.getMetricValue())
        );
    }
}
