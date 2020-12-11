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
        IServiceDescriptor serviceDescriptor = new ServiceDescriptor();

        TypeSolver typeSolver = new CombinedTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);

        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

        CompilationUnit compilationUnit = StaticJavaParser.parse(new File(filePath));

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

            currentMethodDeclaration.getParameters().forEach(parameter -> {
                Parameter parameter1 = new Parameter();
                parameter1.setName(parameter.getNameAsString());
                parameter1.setType(parameter.getTypeAsString());

                paramsList.add(parameter1);
            });

            operation.setParamList(paramsList);

            List<String> usingTypes = new ArrayList<>();

            currentMethodDeclaration.findAll(NameExpr.class).forEach(ae -> {
                try {
                    ResolvedType resolvedType = ae.calculateResolvedType();
                    usingTypes.add(resolvedType.toString());
                } catch (UnsolvedSymbolException ex) {
                    // ignore
                    String[] splitedMessage = ex.getMessage().split(" ");
                    String[] blockedWords = new String[] { "Unsolved", "symbol", "in", ":", "Solving" };
                    Optional<String> a = Arrays.stream(splitedMessage).distinct().filter(it -> {
                        return Arrays.stream(blockedWords).noneMatch(i -> i.equals(it));
                    }).findFirst();

                    if (a.isPresent()) {
                        usingTypes.add(a.get());
                    }
                }
            });

            operation.setUsingTypesList(usingTypes);

            // last line
            operations.add(operation);

        }
        serviceDescriptor.setServiceOperations(operations);

        return serviceDescriptor;
    }
}
