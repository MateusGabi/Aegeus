package br.unicamp.ic.laser.readers;

import br.unicamp.ic.laser.model.IServiceDescriptor;
import br.unicamp.ic.laser.model.IServiceDescriptorBuilder;
import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.ServiceDescriptor;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mateus Gabi Moreira
 * @link example 6 from https://www.hellojava.com/a/80798.html
 */
public class JavaGrpcReflectionServiceDescriptorBuilder implements IServiceDescriptorBuilder {
    @Override
    public IServiceDescriptor build(String filePath) throws IOException {
        IServiceDescriptor serviceDescriptor = new ServiceDescriptor();

        CompilationUnit compilationUnit = StaticJavaParser.parse(new File(filePath));

        System.out.println("Métodosshdsuhda");
        String clsName = compilationUnit.getType(0).getName().toString();
        MethodDeclaration firstMethod = compilationUnit.getType(0).getMethods().get(0);
        String firstMethodDeclaration = compilationUnit.getType(0).getMethods().get(0).getDeclarationAsString(true, true, true);
        System.out.println(firstMethod.getParameters());

        serviceDescriptor.setServiceName(clsName);

        List<Operation> operations = new ArrayList<>();

        TypeDeclaration cls = compilationUnit.getType(0);
        for (Object md : cls.getMethods()) {
            MethodDeclaration currentMethodDeclaration = ((MethodDeclaration) md);
            String methodName = currentMethodDeclaration.getName().toString();

            boolean isGrpc = isGrpcInterface(currentMethodDeclaration);

            if (!isGrpc) {
                break;
            }

            Operation operation = new Operation();
            operation.setName(clsName+"::"+methodName);
            operation.setResponseType(currentMethodDeclaration.getParameter(0).getTypeAsString());
            List<String> paramsList = new ArrayList<>();
            paramsList.add(currentMethodDeclaration.getParameter(1).getNameAsString());
            operation.setParamList(paramsList);

            operations.add(operation);

        }


        serviceDescriptor.setServiceOperations(operations);

        return serviceDescriptor;
    }

    private boolean isGrpcInterface(MethodDeclaration methodDeclaration) {
        return methodDeclaration.getParameters().size() == 2 && methodDeclaration.getParameter(1).getTypeAsString().contains("StreamObserver<");
    }
}
