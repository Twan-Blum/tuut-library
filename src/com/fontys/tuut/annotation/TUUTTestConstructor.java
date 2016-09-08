/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Constructor annotation
 * 
 * @author Twan Blum
 */
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
public @interface TUUTTestConstructor
{ 
    /**
     * Test method description
     * 
     * @return test description 
     */
    public String description() default "";
    
    /**
     * Test method weight
     * 
     * @return test weight
     */
    public int weight() default 0;

}
