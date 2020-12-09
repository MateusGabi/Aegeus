package br.unicamp.ic.laser.cli;

import br.unicamp.ic.laser.exporters.ConsoleExporter;
import br.unicamp.ic.laser.exporters.MetricsExporter;
import br.unicamp.ic.laser.metrics.MetricCalculator;
import br.unicamp.ic.laser.metrics.MetricResult;
import br.unicamp.ic.laser.model.IServiceDescriptor;
import br.unicamp.ic.laser.model.ServiceDescriptor;
import br.unicamp.ic.laser.readers.JavaGrpcReflectionServiceDescriptorBuilder;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.List;

public class CommandLineInterfaceApp implements ICommandLineInterfaceApp {
    @Override
    public void run(String[] args) {

        CommandLine line = parseArguments(args);

        if (line.hasOption("filename")) {
            String filename = line.getOptionValue("filename");
            String parser = line.getOptionValue("p");
            String version = line.getOptionValue("v");

            ServiceDescriptor.Builder serviceDescriptorBuilder = null;

            if (line.hasOption('p') && parser.equals("javagrpc")) {
                serviceDescriptorBuilder = new ServiceDescriptor.Builder(new JavaGrpcReflectionServiceDescriptorBuilder());
            }
            else {
                System.out.println("Using default parser");
                serviceDescriptorBuilder = new ServiceDescriptor.Builder();
            }

            IServiceDescriptor serviceDescriptor = null;
            try {
                serviceDescriptor = serviceDescriptorBuilder.build(filename);
                serviceDescriptor.setServiceVersion(version);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // assess metrics
            List<MetricResult> metricResults = new MetricCalculator().assess(serviceDescriptor);

            // export assessments
            new MetricsExporter().export(metricResults);

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
        options.addOption("v", true, "service version");

        return options;
    }
}
