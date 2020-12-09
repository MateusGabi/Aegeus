package br.unicamp.ic.laser.exporters;

import br.unicamp.ic.laser.metrics.MetricResult;

import java.util.Arrays;
import java.util.List;

public class MetricsExporter {
    private List<IExporter> getExporters() {
        return Arrays.asList(
                new CsvExporter()
        );
    }

    public void export(List<MetricResult> results) {
       this.getExporters().forEach(iExporter -> iExporter.export(results));
    }
}
