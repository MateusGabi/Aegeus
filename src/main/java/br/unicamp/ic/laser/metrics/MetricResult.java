package br.unicamp.ic.laser.metrics;

public class MetricResult {
    private String serviceName;
    private String version;
    private String metricName;
    private Double metricValue;

    public MetricResult() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Double getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(Double metricValue) {
        this.metricValue = metricValue;
    }

    @Override
    public String toString() {
        return "MetricResult(metricName=" + this.getMetricName() + ", metricValue=" + this.getMetricValue() + ")";
    }
}
