package br.unicamp.ic.laser.model;

import br.unicamp.ic.laser.readers.IInputFile;

public interface IServiceDescriptorBuilder extends IInputFile {
    IServiceDescriptor build(String filePath);
}
