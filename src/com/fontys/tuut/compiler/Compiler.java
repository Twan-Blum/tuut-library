/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.compiler;

import com.fontys.tuut.loader.DynamicFileManager;
import com.fontys.tuut.loader.DynamicClassLoader;
import com.fontys.tuut.loader.DynamicFileObject;
import com.fontys.tuut.loader.DynamicFileObjectPool;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

/**
 * In memory java compiler
 * 
 * Wrapper around javax java compiler to support in memory compilation.
 * 
 * @author Twan Blum
 */
public class Compiler
{    
    private final static Logger LOGGER = Logger.getLogger(Compiler.class.toString());
    
    private final static JavaCompiler    JAVACOMPILER    = ToolProvider.getSystemJavaCompiler();
    private final static JavaFileManager JAVAFILEMANAGER = JAVACOMPILER.getStandardFileManager(null, null, null);
    
    private final DynamicClassLoader classLoader;
    private final DynamicFileManager fileManager;
    
    /**
     * Constructor
     * 
     * @param classPool   Javassist class pool
     * @param objectPool  Dynamic object pool
     * @param classLoader class loader
     */
    public Compiler(ClassPool classPool, DynamicFileObjectPool objectPool, ClassLoader classLoader) {     
        this.classLoader = new DynamicClassLoader(classLoader, classPool, objectPool);
        this.fileManager = new DynamicFileManager(JAVAFILEMANAGER, this.classLoader);
    }

    
    /**
     * Compile class sources
     * 
     * @param  files map with name and source file
     * @throws CompileException when source files could not be compiled
     */
    public void compile(Map<String, File> files) throws CompileException {
        if (files.size() < 1) {
            throw new CompileException("Failed to compile, no compilable files found");
        }

        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();        
        List<JavaFileObject> compilationUnits = this.createCompilationUnits(files);

        CompilationTask task = JAVACOMPILER.getTask(
            null,                // Writer
            this.fileManager,    // JavaFileManager
            diagnosticCollector, // DiagnosticCollector
            null,                // List<String> (options)
            null,                // Iteratable<String> (classes)
            compilationUnits     // List<JavaFileObject>
        );

        if (!task.call()) {
            diagnosticCollector.getDiagnostics().stream().forEach((diagnostic) -> {
                LOGGER.log(Level.WARNING, diagnostic.getMessage(null));
            });
            throw new CompileException("Failed to compile source files"); 
        }
        
        this.appendClassPaths(this.classLoader.getObjectPool().getObjects());
    }
    
    /**
     * Creates compilation units from class name and java source file
     * 
     * @param  files
     * @return list of compilation units
     * @throws CompileException when java source file can not be read
     */
    private List<JavaFileObject> createCompilationUnits(Map<String, File> files) throws CompileException {
        List<JavaFileObject> compilationUnits = new ArrayList<>();
        
        try {
            for (Map.Entry entry : files.entrySet()) {
                File file     = (File) entry.getValue();
                String name   = file.getName();
                String source = new String(Files.readAllBytes(file.toPath()));
                
                compilationUnits.add(new SourceFileObject(name, source));
            }
        } catch (Exception ex) {
            throw new CompileException("Failed to add compilation unit", ex);
        } 
        
        return compilationUnits;
    }
    
    /**
     * Append compiled classes to class pool class path
     * 
     * @param objects 
     */
    private void appendClassPaths(Map<String, DynamicFileObject> objects) {
        ClassPool classPool = this.classLoader.getClassPool();
        
        objects.entrySet().stream().forEach((entry) -> {
            String name  = (String) entry.getKey();
            byte[] bytes = ((DynamicFileObject) entry.getValue()).getByteCode();

            classPool.appendClassPath(new ByteArrayClassPath(name, bytes));
        });
    }
    
}