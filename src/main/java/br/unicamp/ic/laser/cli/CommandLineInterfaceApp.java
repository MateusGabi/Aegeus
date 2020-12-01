package br.unicamp.ic.laser.cli;

import br.unicamp.ic.laser.metrics.ServiceInterfaceDataCohesion;
import br.unicamp.ic.laser.metrics.StrictServiceImplementationCohesion;
import br.unicamp.ic.laser.model.IServiceDescriptor;
import br.unicamp.ic.laser.model.IServiceDescriptorBuilder;
import br.unicamp.ic.laser.model.ServiceDescriptor;
import br.unicamp.ic.laser.readers.JavaGrpcReflectionServiceDescriptorBuilder;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

public class CommandLineInterfaceApp implements ICommandLineInterfaceApp {
    @Override
    public void run(String[] args) {

        CommandLine line = parseArguments(args);

        if (line.hasOption("filename")) {
            String filename = line.getOptionValue("filename");
            String parser = line.getOptionValue("p");

//            String FILE_PATH = "/home/mgm/Documents/Unicamp/Master/analysis/sitewhere/in/label-generation-service.7ce7cac632a46847b9a24d97e796ca5967ed6ac7.txt";

            System.out.println("Starting application");

            ServiceDescriptor.Builder serviceDescriptorBuilder = null;

            if (line.hasOption('p') && parser.equals("javagrpc")) {
                System.out.println("Using Java GRPC Parser");
                serviceDescriptorBuilder = new ServiceDescriptor.Builder(new JavaGrpcReflectionServiceDescriptorBuilder());
            }
            else {
                System.out.println("Using default parser");
                serviceDescriptorBuilder = new ServiceDescriptor.Builder();
            }

            IServiceDescriptor serviceDescriptor = null;
            try {
                serviceDescriptor = serviceDescriptorBuilder.build(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Service Name: " + serviceDescriptor.getServiceName());

            System.out.println("Operations: " + serviceDescriptor.getServiceOperations().size());

            System.out.println("ServiceInterfaceDataCohesion: " + new ServiceInterfaceDataCohesion().evaluate(serviceDescriptor));
            System.out.println("StrictServiceImplementationCohesion: " + new StrictServiceImplementationCohesion().evaluate(serviceDescriptor));


        } else {
            printAppHelp();
        }

    }

    /**
     * Parses application arguments
     *
     * @param args application arguments
     * @return <code>CommandLine</code> which represents a list of application
     * arguments.
     */
    private CommandLine parseArguments(String[] args) {
        Options options = getOptions();
        CommandLine line = null;

        CommandLineParser parser = new DefaultParser();

        try {
            line = parser.parse(options, args);
        } catch (ParseException ex) {

            System.err.println("Failed to parse command line arguments");
            System.err.println(ex.toString());

            printAppHelp();

            System.exit(1);
        }

        return line;
    }

    /**
     * Prints application help
     */
    private void printAppHelp() {
        Options options = getOptions();

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Aegeus", options, true);
    }

    /**
     * Generates application command line options
     *
     * @return application <code>Options</code>
     */
    private Options getOptions() {
        Options options = new Options();

        options.addOption("f", "filename", true, "file name to load data from");
        options.addOption("p", true, "parser");

        return options;
    }
}
