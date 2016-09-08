/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.tester;

import com.fontys.tuut.loader.DynamicClassLoader;
import com.fontys.tuut.loader.DynamicFileObjectPool;
import com.fontys.tuut.runner.TuutEnvironment;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Runs test with the dynamic class loader so changes made to the behavior
 * of a class are available for the unit test.
 * 
 * @author Twan Blum
 */
public class TestClassRunner extends BlockJUnit4ClassRunner
{   
    /**
     * Constructor
     * 
     * @param  c test class
     * @throws InitializationError 
     */
    public TestClassRunner(Class<?> c) throws InitializationError {
        super(loadClass(c));
    }

    /**
     * Load class from dynamic class loader
     * 
     * @param clazz
     * @return
     * @throws InitializationError 
     */
    private static Class<?> loadClass(Class<?> c) throws InitializationError {  
        try {
            ClassLoader loader = new DynamicClassLoader(
                TuutEnvironment.getInstance().getClassLoader(),
                TuutEnvironment.getInstance().getClassPool(),
                TuutEnvironment.getInstance().getObjectPool()
            );
            return Class.forName(c.getName(), true, loader);
        } catch (ClassNotFoundException e) {
            throw new InitializationError(e);
        }
    }
}