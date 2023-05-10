package br.unicamp.ic.laser.readers;

import br.unicamp.ic.laser.model.*;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class JavaSimpleServiceDescriptorBuilder implements IServiceDescriptorBuilder {
    @Override
    public IServiceDescriptor build(String filePath) throws IOException {
    	if (filePath == null || filePath == "") {
    		throw new IOException("Filepath should not be null or empty.");
    	}
    	
    	
        IServiceDescriptor serviceDescriptor = new ServiceDescriptor();

        TypeSolver typeSolver = new CombinedTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);

        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

        System.out.println("creating compilation unit");
        CompilationUnit compilationUnit = StaticJavaParser.parse(new File(filePath));
        System.out.println("compilation unit created");

        String clsName = compilationUnit.getType(0).getName().toString();
        serviceDescriptor.setServiceName(clsName);

        List<Operation> operations = new ArrayList<>();

        TypeDeclaration cls = compilationUnit.getType(0);
        for (Object md : cls.getMethods()) {
            MethodDeclaration currentMethodDeclaration = ((MethodDeclaration) md);
            String methodName = currentMethodDeclaration.getName().toString();

            Operation operation = new Operation();
            operation.setName(clsName+"::"+methodName);
            operation.setResponseType(currentMethodDeclaration.getType().toString());

            List<Parameter> paramsList = new ArrayList<>();

            System.out.println("Getting parameters of " + clsName+"::"+methodName);
            currentMethodDeclaration.getParameters().forEach(parameter -> {
                System.out.println("Parameter " + parameter.getNameAsString() + " of type " + parameter.getTypeAsString() + " has found.");
                Parameter parameter1 = new Parameter();
                parameter1.setName(parameter.getNameAsString());
                parameter1.setType(parameter.getTypeAsString());

                paramsList.add(parameter1);
            });

            operation.setParamList(paramsList);

            List<String> usingTypes = new ArrayList<>();

            System.out.println("Getting types into method.");
            currentMethodDeclaration.findAll(NameExpr.class).forEach(ae -> {
                try {
                    ResolvedType resolvedType = ae.calculateResolvedType();
                    System.out.println("Type found: " + resolvedType.toString());
                    usingTypes.add(resolvedType.toString());
                } catch (UnsolvedSymbolException ex) {
                    // ignore
                    System.out.println("Ops! UnsolvedSymbolException.");
                    String[] splitedMessage = ex.getMessage().split(" ");
                    String[] blockedWords = new String[] { "Unsolved", "symbol", "in", ":", "Solving" };
                    Optional<String> a = Arrays.stream(splitedMessage).distinct().filter(it -> {
                        return Arrays.stream(blockedWords).noneMatch(i -> i.equals(it));
                    }).findFirst();

                    if (a.isPresent()) {
                        System.out.println("NAO SEIII " + a.get());
                        usingTypes.add(a.get());
                    }
                } catch (RuntimeException runtimeException) {
                    System.out.println("Runtime exception" + ae.getNameAsString());
                }
            });

            System.out.println("Setting types.");
            operation.setUsingTypesList(usingTypes);

            // last line
            System.out.println("Setting operation.");
            operations.add(operation);

        }
        System.out.println("Setting all operations.");
        serviceDescriptor.setServiceOperations(operations);

        System.out.println("Service Descriptor ok!");
        return serviceDescriptor;
    }
}
