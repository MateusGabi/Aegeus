package br.unicamp.ic.laser.readers;

import br.unicamp.ic.laser.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TextFileServiceDescriptorBuilder implements IServiceDescriptorBuilder {

    @Override
    public ServiceDescriptor build(String fileName) throws IOException {
        ServiceDescriptor serviceDescriptor = new ServiceDescriptor();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            // i need this variable to know which operation it is add params and types;
            ArrayList<Integer> arrayOfOperationsIndex = new ArrayList<>();
            stream
                    .map(this::sanitize)
                    .forEach((statement) -> {
                        Statements statementKind = whichKindOfStatementIs(statement);

                        switch (statementKind) {
                            case SERVICE_NAME:
                                serviceDescriptor.setServiceName(statement);
                                break;
                            case OPERATION_NAME:
                                Operation operation = new Operation();
                                operation.setName(statement);

                                // increment array of operations index
                                int size = arrayOfOperationsIndex.size();
                                arrayOfOperationsIndex.add(size++);

                                // add operation
                                serviceDescriptor.getServiceOperations().add(operation);
                                break;
                            case OPERATION_PARAM:
                                Parameter parameter = new Parameter();
                                String paramName = statement.replace("*", "").split(":")[0];
                                String paramType = statement.replace("*", "").split(":")[1];
                                parameter.setName(paramName);
                                parameter.setType(paramType);
                                serviceDescriptor.getServiceOperations().get(arrayOfOperationsIndex.size() - 1).getParamList().add(parameter);
                                break;
                            case OPERATION_USE_OF_TYPE:
                                serviceDescriptor.getServiceOperations().get(arrayOfOperationsIndex.size() - 1).getUsingTypesList().add(statement);
                                break;
                        }
                    });


        }

        return serviceDescriptor;
    }

    private String sanitize(String statement) {
        return statement.replace(" ", "") // remove blank spaces
                .replace(",", ""); // remove commas
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
        } else if (line.startsWith("-")) {
            return Statements.OPERATION_OUTPUT;
        }
        return Statements.OPERATION_USE_OF_TYPE;
    }
}
