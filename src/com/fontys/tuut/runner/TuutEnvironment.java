/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.runner;

import com.fontys.tuut.loader.DynamicFileObjectPool;
import javassist.ClassPool;

/**
 *
 * @author Twan
 */
public class TuutEnvironment
{
    private final static TuutEnvironment INSTANCE = new TuutEnvironment();
   
    private ClassPool classPool;
    private DynamicFileObjectPool objectPool;
    private ClassLoader classLoader;
    
    private TuutEnvironment() {    
    }
    
    public static TuutEnvironment getInstance() {
        return INSTANCE;
    }
    
    public void setClassPool(ClassPool classPool) {
        this.classPool = classPool;
    }
    
    public ClassPool getClassPool() {
        return this.classPool;
    }

    public void setObjectPool(DynamicFileObjectPool objectPool) {
        this.objectPool = objectPool;
    }
    
    public DynamicFileObjectPool getObjectPool() {
        return this.objectPool;
    }
    
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }
}
