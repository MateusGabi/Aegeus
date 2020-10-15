package br.unicamp.ic.laser.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import br.unicamp.ic.laser.cli.CommandLineInterfaceApp;
import br.unicamp.ic.laser.metrics.ServiceInterfaceDataCohesion;
import br.unicamp.ic.laser.metrics.StrictServiceImplementationCohesion;
import br.unicamp.ic.laser.model.IServiceDescriptor;
import br.unicamp.ic.laser.model.IServiceDescriptorBuilder;
import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.ServiceDescriptor;

public class Main {
    public static void main(String[] args) {
        CommandLineInterfaceApp commandLineInterfaceApp = new CommandLineInterfaceApp();
        commandLineInterfaceApp.run(args);
    }
}
