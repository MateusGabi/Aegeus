package br.unicamp.ic.laser.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import br.unicamp.ic.laser.metrics.ServiceInterfaceDataCohesion;
import br.unicamp.ic.laser.metrics.StrictServiceImplementationCohesion;
import br.unicamp.ic.laser.model.IServiceDescriptor;
import br.unicamp.ic.laser.model.IServiceDescriptorBuilder;
import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.ServiceDescriptor;

public class Main {
    public static void main(String[] args) {

        String FILE_PATH = "/home/mgm/Documents/Unicamp/Master/analysis/sitewhere/in/label-generation-service.7ce7cac632a46847b9a24d97e796ca5967ed6ac7.txt";

        System.out.println("Starting application");

        ServiceDescriptor.Builder serviceDescriptorBuilder = new ServiceDescriptor.Builder();
        IServiceDescriptor serviceDescriptor = null;
        try {
            serviceDescriptor = serviceDescriptorBuilder.build(FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Service Name: " + serviceDescriptor.getServiceName());

        System.out.println("Operations: " + serviceDescriptor.getServiceOperations().size());

//        serviceDescriptor.getServiceOperations().forEach(operation -> {
//            System.out.println("Operation name: " + operation.getName());
//            System.out.println("Params: " + operation.getParamList());
//            System.out.println("Types: " + operation.getUsingTypesList());
//        });

        System.out.println("ServiceInterfaceDataCohesion: " + new ServiceInterfaceDataCohesion().evaluate(serviceDescriptor));
        System.out.println("StrictServiceImplementationCohesion: " + new StrictServiceImplementationCohesion().evaluate(serviceDescriptor));
    }
}
