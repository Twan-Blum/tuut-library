/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.compiler;

/**
 * Compile exception
 * 
 * @author Twan Blum
 */
public class CompileException extends Exception
{
    public CompileException(String message) {
        super(message);
    }
    
    public CompileException(String message, Throwable cause) {
        super(message, cause);
    }
}
