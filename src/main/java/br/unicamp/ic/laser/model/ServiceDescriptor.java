package br.unicamp.ic.laser.model;

import br.unicamp.ic.laser.readers.TextFileServiceDescriptorBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Description on a SOA Service.
 *
 * @author Mateus Gabi Moreira
 * @version 1.0.0
 */
public class ServiceDescriptor implements IServiceDescriptor {

    private String serviceName;
    private String serviceVersion;
    private List<Operation> serviceOperations;

    // TODO: become protected
    public ServiceDescriptor() {
        this.serviceName = "UnkownService";
        this.serviceOperations = new ArrayList<Operation>();
    }

    // TODO: become protected
    public ServiceDescriptor(String name, List<Operation> operationsList) {
        super();
        this.serviceName = name;
        this.serviceOperations = operationsList;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public void setServiceName(String name) {
        this.serviceName = name;
    }

    @Override
    public String getServiceVersion() {
        return serviceVersion;
    }

    @Override
    public void setServiceVersion(String version) {
        this.serviceVersion = version;
    }

    @Override
    public List<Operation> getServiceOperations() {
        return serviceOperations;
    }

    @Override
    public void setServiceOperations(List<Operation> serviceOperations) {
        this.serviceOperations = serviceOperations;
    }

    @Override
    public void writeToMsd(String filename) {
        System.out.println("Write .msd file: " + filename);

        // open file and write it
        File file = new File(filename);
        FileWriter myWriter = null;
        try {
            if (file.createNewFile()) {
                myWriter = new FileWriter(filename);
                myWriter.write(this.toString());
                myWriter.close();
            } else {
                // write bellow this file
                myWriter = new FileWriter(filename, true);
                myWriter.write("\n" + this);
                myWriter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Done.");
        }
    }

    @Override
    public String toString() {
        return "service=" + this.serviceName + "\n" +
                this.serviceOperations.stream().map(Operation::toString).reduce("", (acc, op) -> acc + "operation=" + op + "\n");
    }

    public static class Builder {

        private final IServiceDescriptorBuilder serviceDescriptorBuilder;

        public Builder(IServiceDescriptorBuilder serviceDescriptorBuilder) {
            this.serviceDescriptorBuilder = serviceDescriptorBuilder;
        }

        public Builder() {
            this.serviceDescriptorBuilder = new TextFileServiceDescriptorBuilder();
        }

        public IServiceDescriptor build(String file) throws IOException {
            return this.serviceDescriptorBuilder.build(file);
        }
    }
}

