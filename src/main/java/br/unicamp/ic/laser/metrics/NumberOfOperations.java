package br.unicamp.ic.laser.metrics;

import br.unicamp.ic.laser.model.IServiceDescriptor;

public class NumberOfOperations implements IMetric {
    @Override
    public Double evaluate(IServiceDescriptor serviceDescriptor) {
        return serviceDescriptor.getServiceOperations().size() / 1.0;
    }
}
