/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.annotation;

import com.fontys.tuut.Test;
import com.fontys.tuut.TestConstructor;
import com.fontys.tuut.TestMethod;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

/**
 * Error class annotation parser
 * 
 * @author Twan Blum
 */
public class Parser
{
    /**
     * Parse compiled-time class
     * 
     * @param  ctClass error class
     * @return Test
     * @throws ParseException when annotations could not be parsed
     */
    public static Test parse(CtClass ctClass) throws ParseException {
        try {
            Object testAnnotation = ctClass.getAnnotation(TUUTTest.class);
            Test test = toTest((TUUTTest) testAnnotation, ctClass);

            for (CtConstructor ctConstructor : ctClass.getDeclaredConstructors()) {
                TestConstructor constructor = parseTestConstructor(ctConstructor);
                
                if (constructor != null) {
                    test.getTestBehaviors().add(constructor);
                }
            }
            
            // create new test behavior for every method declaration
            for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
                TestMethod method = parseTestMethod(ctMethod);
                
                if (method != null) {
                    test.getTestBehaviors().add(method);    
                }
                
            }
            
            return test;
        } catch (ClassNotFoundException ex) {
            throw new ParseException("Failed to parse class", ex);
        }
    }
    
    private static Test parseTest(CtClass ctClass) throws ClassNotFoundException {
        Object annotation = ctClass.getAnnotation(TUUTTest.class);

        return toTest((TUUTTest) annotation, ctClass);
    }
    
    public static TestConstructor parseTestConstructor(CtConstructor ctConstructor) throws ClassNotFoundException {
        Object annotation = ctConstructor.getAnnotation(TUUTTestConstructor.class);
        
        if (annotation == null) {
            return null;
        }
        
        return toTestConstructor((TUUTTestConstructor) annotation, ctConstructor);
    }

    public static TestMethod parseTestMethod(CtMethod ctMethod) throws ClassNotFoundException {
        Object annotation = ctMethod.getAnnotation(TUUTTestMethod.class);
        
        if (annotation == null) {
            return null;
        }

        return toTestMethod((TUUTTestMethod) annotation, ctMethod);
    }
    
    public static Test toTest(TUUTTest t, CtClass c) {
        return new Test(t.value(), c);
    }
    
    public static TestConstructor toTestConstructor(TUUTTestConstructor t, CtConstructor c) {
        return new TestConstructor(t.weight(), t.description(), c);
    }
    
    public static TestMethod toTestMethod(TUUTTestMethod t, CtMethod m) {
       return new TestMethod(t.name(), t.weight(), t.description(), m); 
    }
}
