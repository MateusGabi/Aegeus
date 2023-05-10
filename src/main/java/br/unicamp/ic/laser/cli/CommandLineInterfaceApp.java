package br.unicamp.ic.laser.cli;

import br.unicamp.ic.laser.exporters.MetricsExporter;
import br.unicamp.ic.laser.metrics.MetricCalculator;
import br.unicamp.ic.laser.metrics.MetricResult;
import br.unicamp.ic.laser.model.IServiceDescriptor;
import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.Parameter;
import br.unicamp.ic.laser.model.ServiceDescriptor;
import br.unicamp.ic.laser.readers.JavaGrpcReflectionServiceDescriptorBuilder;
import br.unicamp.ic.laser.readers.JavaSimpleServiceDescriptorBuilder;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            } else if (line.hasOption('p') && parser.equals("java")) {
                serviceDescriptorBuilder = new ServiceDescriptor.Builder(new JavaSimpleServiceDescriptorBuilder());
            } else {
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
            // Problema: Embora tenha controllers/interface kafka/grpc diferentes, eu tenho que analisar de forma
            // "condensada" para que eu analise a interface do serviço como um todo;
            // uma das formas é colocar uma flag que o usuário bota na linha de comando e essa flag seja informada em
            // MatricCalculator::assess
            List<MetricResult> metricResults = new MetricCalculator().assess(serviceDescriptor);

            // export assessments
            new MetricsExporter().export(metricResults);

        } else if (line.hasOption("writemsdescriptor") &&
                line.hasOption("ms") && line.hasOption("p")) {
            System.out.println("Write Microservice Descriptor (.msd)\n");

            String parser = line.getOptionValue("p");
            String ms = line.getOptionValue("ms");
            String controllersPath = ms + "/analysis/SERVICES.out";

            System.out.println("Parser: " + parser);
            System.out.println("Microservice path: " + ms);
            System.out.println("Controllers file: " + controllersPath);


            ServiceDescriptor.Builder serviceDescriptorBuilder = null;

            if (line.hasOption('p') && parser.equals("javagrpc")) {
                serviceDescriptorBuilder = new ServiceDescriptor.Builder(new JavaGrpcReflectionServiceDescriptorBuilder());
            } else if (line.hasOption('p') && parser.equals("java")) {
                serviceDescriptorBuilder = new ServiceDescriptor.Builder(new JavaSimpleServiceDescriptorBuilder());
            } else {
                System.out.println("Using default parser");
                serviceDescriptorBuilder = new ServiceDescriptor.Builder();
            }

            // get first service in SERVICE.out file
            // open all line streaming
            try {
                List<String> lines = Files.readAllLines(Paths.get(controllersPath));
                for (String line1 : lines) {
                    String controllerPath = line1;
                    System.out.println(line1);

                    // get version replacing microservice path by empty string
                    String version = line1.replace(ms, "").split("/")[1];
                    System.out.println("Version: " + version);

//                    if (!version.equals("master")) break;

                    IServiceDescriptor serviceDescriptor = serviceDescriptorBuilder.build(controllerPath);
                    serviceDescriptor.setServiceVersion(version);

                    String filename = ms + "/analysis/VERSION_" + version.replace(".", "-") + ".msd";
                    serviceDescriptor.writeToMsd(filename);

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

//            IServiceDescriptor serviceDescriptor = null;
//            try {
//                serviceDescriptor = serviceDescriptorBuilder.build(filename);
//                serviceDescriptor.setServiceVersion(version);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        } else if (
                line.hasOption("assessmetricsinmsa") &&
                        line.hasOption("ms")) {
            System.out.println("Assess metrics in Microservice");
            String ms = line.getOptionValue("ms");

            String msAnalysisPath = ms + "/analysis/";

            System.out.println("Microservice path: " + ms);

            // get all .msd files in analysis folder
            File folder = new File(msAnalysisPath);
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".msd")) {
                    System.out.println("File: " + file.getName());
                    String filename = msAnalysisPath + file.getName();
                    IServiceDescriptor serviceDescriptor = new ServiceDescriptor();
                    serviceDescriptor.setServiceName("UNKOWN");
                    serviceDescriptor.setServiceVersion("UNKOWN");

                    List<String> lines = null;
                    try {
                        lines = Files.readAllLines(file.toPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Operation lastOperation = null;
                    for (String l : lines) {
                        if (l.startsWith("operation=")) {
                            // new operation
                            Operation operation = new Operation();
                            operation.setName(l.replace("operation=", ""));
                            lastOperation = operation;
                        } else if (l.startsWith("params=")) {
                            // params da operação
                            // TODO: diamonds não são suportados. Ex: Map<String, String>
                            String[] params = l.replace("params=", "").split(",");
                            for (String param : params) {
                                if (param.split(":").length != 2) {
                                    System.out.println("Invalid param: " + param);
                                    continue;
                                }

                                Parameter parameter = new Parameter();
                                parameter.setName(param.split(":")[0]);
                                parameter.setType(param.split(":")[1]);
                                assert lastOperation != null;
                                lastOperation.getParamList().add(parameter);
                            }
                        } else if (l.startsWith("output=")) {
                            assert lastOperation != null;
                            lastOperation.setResponseType(l.replace("output=", ""));
                        } else if (l.startsWith("using-types=")) {
                            assert lastOperation != null;
                            lastOperation.setUsingTypesList(Arrays.asList(l.replace("using-types=", "").split(",")));
                        }
                    }

                    List<MetricResult> metricResults = new MetricCalculator().assess(serviceDescriptor);
                    System.out.println(metricResults.stream().map(MetricResult::toString).collect(Collectors.joining("\n")));

                    // escrever na parte de baixo do file .msd
                    try {
                        Files.write(file.toPath(), ("\n" + metricResults.stream().map(MetricResult::toString).collect(Collectors.joining("\n"))).getBytes(), StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }


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
            System.err.println(ex);

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
        options.addOption("ms", true, "path to microservice analyzed. Ex: /home/mgm/.aegeus/repos/spinnaker-igor");
        options.addOption("writemsdescriptor", false, "Write an .msd of a Microservice.");
        options.addOption("assessmetricsinmsa", false, "Assess metrics in a Microservice Architecture given a -ms option.");

        return options;
    }
}
