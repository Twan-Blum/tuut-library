/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.tester;

import java.util.ArrayList;
import java.util.List;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;

/**
 * Test class modifier is responsible for changing java byte code.
 * 
 * @author Twan Blum
 */
public class TestClassEditor
{
    private final CtClass ctClass;
    
    private final List<CtConstructor> constructors = new ArrayList<>();
    private final List<CtMethod>      methods      = new ArrayList<>();
    
    /**
     * Constructor
     * 
     * @param ctClass compiled time class to edit 
     */
    public TestClassEditor(CtClass ctClass) {
        this.ctClass = ctClass;
    }
    
    /**
     * Replace constructor
     * 
     * @param replacement    replacement constructor
     * @param revertible     flag that indicates if replacement should be revertible
     * @throws TestException if constructor could not be replaced
     */
    public void replaceConstructor(CtConstructor replacement, boolean revertible) throws TestException {
        try {
            CtConstructor original = this.ctClass.getDeclaredConstructor(replacement.getParameterTypes());
            
            if (this.ctClass.isFrozen()) {
                this.ctClass.defrost();
            }
            
            if (revertible) {
                this.constructors.add(CtNewConstructor.copy(original, this.ctClass, null));
            }
            
            original.setBody(replacement, null);
            this.ctClass.rebuildClassFile();
        } catch (CannotCompileException | NotFoundException ex) {
            throw new TestException("Failed to replace class constructor", ex);
        }
        
    }
    
    /**
     * Replace method

     * @param replacement    replacement method
     * @param revertible     flag that indicates if replacement should be revertible
     * @throws TestException if method could not be replaced
     */
    public void replaceMethod(CtMethod replacement, boolean revertible) throws TestException {
        try {
            CtMethod original = this.ctClass.getDeclaredMethod(replacement.getName(), replacement.getParameterTypes());

            if (this.ctClass.isFrozen()) {
                this.ctClass.defrost();
            }
            
            if (revertible) {
                this.methods.add(CtNewMethod.copy(original, this.ctClass, null));
            }

            original.setBody(replacement, null);
            this.ctClass.rebuildClassFile();
        } catch (NotFoundException | CannotCompileException ex) {
            throw new TestException("Failed to replace class method", ex);
        }
    }
    
    /**
     * Revert all revertible changes
     * 
     * @throws TestException if changes could not be reverted
     */
    public void revert() throws TestException {
        this.revertConstructors();
        this.revertMethods();
    }
    
    /**
     * Revert constructors
     * 
     * @throws TestException if changes could not be reverted 
     */
    public void revertConstructors() throws TestException {
        for (CtConstructor constructor : this.constructors) {
             this.replaceConstructor(constructor, false);
        }
 
        this.constructors.clear();
    }
    
    /**
     * Revert methods
     * 
     * @throws TestException if changes could not be reverted 
     */
    public void revertMethods() throws TestException {
        for (CtMethod method : this.methods) {
            this.replaceMethod(method, false);
        }

        this.methods.clear();
    } 
}
