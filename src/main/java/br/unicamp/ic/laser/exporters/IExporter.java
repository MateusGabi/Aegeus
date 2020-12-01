package br.unicamp.ic.laser.exporters;

import br.unicamp.ic.laser.metrics.MetricResult;

import java.util.List;

public interface IExporter {
    public void export(List<MetricResult> metricResults);
}
