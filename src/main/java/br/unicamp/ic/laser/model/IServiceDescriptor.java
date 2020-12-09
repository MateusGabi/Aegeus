package br.unicamp.ic.laser.model;

import java.util.List;

public interface IServiceDescriptor {
    String getServiceName();
    String getServiceVersion();
    List<Operation> getServiceOperations();
    void setServiceName(String name);
    void setServiceVersion(String version);
    void setServiceOperations(List<Operation> serviceOperations);
}
