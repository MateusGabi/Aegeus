package br.unicamp.ic.laser.metrics;

import br.unicamp.ic.laser.model.IServiceDescriptor;

public abstract class AbstractMetric implements IMetric {
    private String metricName;
    private IServiceDescriptor serviceDescriptor;
    private MetricResult result;

    public AbstractMetric() {
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public IServiceDescriptor getServiceDescriptor() {
        return serviceDescriptor;
    }

    public void setServiceDescriptor(IServiceDescriptor serviceDescriptor) {
        this.serviceDescriptor = serviceDescriptor;
    }

    public MetricResult getResult() {
        return result;
    }

    public void setResult(MetricResult result) {
        this.result = result;
    }
}
