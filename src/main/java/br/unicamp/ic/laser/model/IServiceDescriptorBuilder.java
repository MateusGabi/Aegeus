package br.unicamp.ic.laser.model;

import br.unicamp.ic.laser.readers.IInputFile;

import java.io.IOException;

public interface IServiceDescriptorBuilder extends IInputFile {
    IServiceDescriptor build(String filePath) throws IOException;
}
