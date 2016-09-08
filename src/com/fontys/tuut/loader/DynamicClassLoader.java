/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.loader;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;

/**
 * Dynamic class loader
 * 
 * @author Twan
 */
public class DynamicClassLoader extends ClassLoader
{
    private final static Logger LOGGER  = Logger.getLogger(DynamicClassLoader.class.getName());
    
    private final ClassPool classPool;
    private final DynamicFileObjectPool objectPool; 
    
    /**
     * Constructor using a custom class pool
     * 
     * @param classLoader
     * @param classPool
     * @param objectPool
     */
    public DynamicClassLoader(ClassLoader classLoader, ClassPool classPool, DynamicFileObjectPool objectPool) {
        super(classLoader);
        
        this.classPool  = classPool;
        this.objectPool = objectPool;
    }
    
    /**
     * Get class pool
     * 
     * @return 
     */
    public ClassPool getClassPool() {
        return this.classPool;
    }
    
    /**
     * Get object pool
     * 
     * @return 
     */
    public DynamicFileObjectPool getObjectPool() {
        return this.objectPool;
    }
    
    /**
     * Override method to support class loading from byte code.
     * 
     * @param  name class name to load
     * @return loaded class on success, null otherwise
     */
    @Override
    public Class<?> loadClass(String name) {
        if (this.objectPool.hasObject(name)) {
            try {
                byte[] bytes = this.classPool.get(name).toBytecode();
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException | CannotCompileException | NotFoundException ex) {
                LOGGER.log(Level.WARNING, "could not load class from byte code", ex);
            }
        }
        
        try {
            return super.loadClass(name);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "could not load class from class loader", ex);
        }
  
        return null;
    }
}