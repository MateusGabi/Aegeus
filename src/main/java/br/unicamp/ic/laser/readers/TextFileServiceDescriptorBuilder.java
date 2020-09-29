package br.unicamp.ic.laser.readers;

import br.unicamp.ic.laser.model.IServiceDescriptorBuilder;
import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.ServiceDescriptor;
import br.unicamp.ic.laser.model.Statements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextFileServiceDescriptorBuilder implements IServiceDescriptorBuilder {

    @Override
    public ServiceDescriptor build(String fileName) {
        ServiceDescriptor serviceDescriptor = new ServiceDescriptor();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<String> statements = stream
                    .map(str -> str.replace(" ", "")) // remove blank spaces
                    .map(str -> str.replace(",", "")) // remove commas
                    .collect(Collectors.toList());

            Operation lastOperation = new Operation();
            for (int i = 0; i < statements.size(); i++) {
                String statement = statements.get(i);

                switch (whichKindOfStatementIs(statement)) {
                    case OPERATION_NAME:
                        lastOperation.setName(statement);
                        break;
                    case OPERATION_USE_OF_TYPE:
                        lastOperation.getUsingTypesList().add(statement);
                        break;
                    case SERVICE_NAME:
                        serviceDescriptor.setServiceName(statement);
                        break;
                    case BLANK:
                        serviceDescriptor.getServiceOperations().add();
                        lastOperation = new Operation();
                        break;
                    case OPERATION_PARAM:
                        lastOperation.getParamList().add(statement);
                    default:
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return serviceDescriptor;
    }

    private Statements whichKindOfStatementIs(String line) {
        if (line.contains("::")) {
            return Statements.OPERATION_NAME;
        } else if (line == "") {
            return Statements.BLANK;
        } else if (line.startsWith("*")) {
            return Statements.OPERATION_PARAM;
        } else if (line.startsWith("service=")) {
            return Statements.SERVICE_NAME;
        }
        return Statements.OPERATION_USE_OF_TYPE;
    }
}
