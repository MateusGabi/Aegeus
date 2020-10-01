package br.unicamp.ic.laser.readers;

import br.unicamp.ic.laser.model.IServiceDescriptor;

import java.io.IOException;

/**
 * Input file of different formats and creates a Service Descriptor object.
 */
public interface IInputFile {
    IServiceDescriptor build(String filePath) throws IOException;
}
