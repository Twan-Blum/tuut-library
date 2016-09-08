/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut;

/**
 *
 * @author Twan Blum
 */
public interface TestBehavior
{
    /**
     * Get weight of test
     * 
     * @return test weight 
     */
    public int getWeight();
    
    /**
     * Get description of test
     * 
     * @return test description
     */
    public String getDescription();
    
    /**
     * Get error behavior of test
     * 
     * @return error behavior 
     */
    public Object getError();
}
