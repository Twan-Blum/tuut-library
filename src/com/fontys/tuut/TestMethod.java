/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut;

import javassist.CtMethod;


/**
 * Method annotation info
 * 
 * All attributes should be strings so annotation parser can populate them.
 * 
 * @author Twan Blum
 */
public class TestMethod implements TestBehavior
{
    private String name;
    private int    weight;
    private String description;
    
    private CtMethod error;
    
    public TestMethod() {}
    
    public TestMethod(String name, int weight, String description, CtMethod method) {
        this.name        = name;
        this.weight      = weight;
        this.description = description;
        this.error       = method;
    }

    /**
     * Set method name to test
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get method name to test
     * 
     * @return 
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Set test weight
     * 
     * @param weight 
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    /**
     * Get test weight
     * 
     * @return 
     */
    @Override
    public int getWeight() {
        return this.weight;
    }
    
    /**
     * Set test description
     * 
     * @param description 
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get test description
     * 
     * @return 
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Set test error method
     * 
     * @param method 
     */
    public void setError(CtMethod method) {
        this.error = method;
    }
    
    /**
     * Get test method
     * 
     * @return 
     */
    @Override
    public CtMethod getError() {
        return this.error;
    }
}


