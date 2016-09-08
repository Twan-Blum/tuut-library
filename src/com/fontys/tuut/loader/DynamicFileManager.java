/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.loader;

import java.io.IOException;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

/**
 * File manager used by the java compiler.
 * 
 * Instead of writing byte code to a class file, this file manager writes byte code
 * to a ByteArrayOutputStream provided by the DynamicFileObject. This means that all
 * java classes are compiled in memory and no actually class files are created on
 * the file system.
 * 
 * @author Twan Blum
 */
public class DynamicFileManager extends ForwardingJavaFileManager<JavaFileManager>
{
    private final DynamicClassLoader    classLoader;
    private final DynamicFileObjectPool objectPool;

    /**
     * Constructor 
     *
     * @param fileManager
     * @param classLoader 
     */
    public DynamicFileManager(JavaFileManager fileManager, DynamicClassLoader classLoader) {
        super(fileManager);

        this.classLoader = classLoader;
        this.objectPool  = classLoader.getObjectPool();
    }

    @Override
    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String name, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        try {
            if (this.objectPool.hasObject(name) == false) {
                this.objectPool.setObject(name, new DynamicFileObject(name));
            }

            return this.objectPool.getObject(name);
        } catch (Exception ex) {
            throw new IOException("Could not load dynamic file object", ex);
        }
    }

    @Override
    public ClassLoader getClassLoader(JavaFileManager.Location location) {
        return this.classLoader;
    }
    
}
