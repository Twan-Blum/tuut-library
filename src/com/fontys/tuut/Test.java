/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut;

import java.util.ArrayList;
import java.util.List;
import javassist.CtClass;

/**
 *
 * @author Twan Blum
 */
public class Test
{
    /**
     * Test class name
     */
    private final String  name;
    
    /**
     * Compiled time error class
     */
    private final CtClass error;
    
    /**
     * Test behaviors
     */
    private final List<TestBehavior> testBehaviors = new ArrayList<>();
    
    public Test(String name, CtClass error) {
        this.name  = name;
        this.error = error;
    }
    
    public String getClassName() {
        return this.name;
    }


    public List<TestBehavior> getTestBehaviors() {
        return this.testBehaviors;
    }
    
}
