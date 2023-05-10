package br.unicamp.ic.laser.model;

import java.util.List;

public interface IServiceDescriptor {
    String getServiceName();

    void setServiceName(String name);

    String getServiceVersion();

    void setServiceVersion(String version);

    List<Operation> getServiceOperations();

    void setServiceOperations(List<Operation> serviceOperations);

    void writeToMsd(String filename);
}
