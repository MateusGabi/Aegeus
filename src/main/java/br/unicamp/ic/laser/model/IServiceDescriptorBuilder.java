package br.unicamp.ic.laser.model;

import java.io.File;

public interface IServiceDescriptorBuilder {
    ServiceDescriptor build(String filePath);
}
