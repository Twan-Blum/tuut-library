/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut;

import javassist.CtConstructor;

/**
 * Method annotation info
 * 
 * @author Twan Blum
 */
public class TestConstructor implements TestBehavior
{
    private int    weight;
    private String description;
    
    private CtConstructor error;
    
    public TestConstructor() {}
    
    public TestConstructor(int weight, String description, CtConstructor constructor) {
        this.weight      = weight;
        this.description = description;
        this.error       = constructor;
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
     * Set constructor error
     * 
     * @param constructor 
     */
    public void setError(CtConstructor constructor) {
        this.error = constructor;
    }
    
    /**
     * Get constructor error
     * 
     * @return 
     */
    @Override
    public CtConstructor getError() {
        return this.error;
    }
}