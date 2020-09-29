package br.unicamp.ic.laser.model;

import java.util.List;

public interface IServiceDescriptor {
    String getServiceName();
    List<Operation> getServiceOperations();
}
