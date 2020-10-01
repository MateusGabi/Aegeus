package br.unicamp.ic.laser.readers;

import br.unicamp.ic.laser.model.IServiceDescriptor;
import br.unicamp.ic.laser.model.IServiceDescriptorBuilder;
import br.unicamp.ic.laser.model.ServiceDescriptor;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @link example 6 from https://www.hellojava.com/a/80798.html
 * @author Mateus Gabi Moreira
 */
public class JavaReflectionServiceDescriptorBuilder implements IServiceDescriptorBuilder {
    @Override
    public IServiceDescriptor build(String filePath) throws IOException {

//        File myFolder = new File(filePath);
//        URLClassLoader classLoader = new URLClassLoader(new URL[]{myFolder.toURI().toURL()}, ClassLoader.getSystemClassLoader());
//        Class<?> classe = null;
//        try {
//            classe = Class.forName("com.sitewhere.microservice.grpc.DeviceManagementImpl", false, classLoader);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }




        Path path = Paths.get(filePath);

        ByteClassLoader loader = new ByteClassLoader();
        loader.defineClass("com.sitewhere.microservice.grpc.DeviceManagementImpl", Files.readAllBytes(path));

        Class cls = null;
        try {
            cls = loader.loadClass("com.sitewhere.microservice.grpc.DeviceManagementImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(cls.getCanonicalName());

        return new ServiceDescriptor();
    }

}

class ByteClassLoader extends ClassLoader {

    public Class<?> defineClass(String name, byte[] classBytes) {
        return defineClass(name, classBytes, 0, classBytes.length);
    }

}
