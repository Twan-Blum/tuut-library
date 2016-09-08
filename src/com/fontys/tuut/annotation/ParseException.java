/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.annotation;

/**
 * Parse exception
 * 
 * @author Twan Blum
 */
public class ParseException extends Exception
{
    public ParseException(String message) {
        super(message);
    }
    
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }  
}
