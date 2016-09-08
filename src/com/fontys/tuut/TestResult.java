/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut;

import org.junit.runner.Result;

/**
 * Tuut test result
 * 
 * @author Twan Blum
 */
public class TestResult
{
    /**
     * Test class
     */
    private final Test test;
    
    /**
     * Test behavior
     */
    private final TestBehavior behavior;
    
    /**
     * JUnit test result
     */
    private final Result result;
    
    /**
     * Constructor
     * 
     * @param result
     * @param test
     * @param behavior 
     */
    public TestResult(Test test, TestBehavior behavior, Result result) {
        this.test     = test;
        this.behavior = behavior;
        this.result   = result;
    }
    
    public Test getTest() {
        return this.test;
    }
    
    public TestBehavior getTestBehavior() {
        return this.behavior;
    }
    
    public Result getResult() {
        return this.result;
    }
    
    /**
     * Test is successful when unit test fails
     * 
     * @return true if successful, otherwise false
     */
    public boolean wasSuccessful() {
        return !this.result.wasSuccessful();
    }
}
