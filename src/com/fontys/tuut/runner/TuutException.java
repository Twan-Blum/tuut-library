/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.runner;

/**
 *
 * @author Twan Blum
 */
public class TuutException extends Exception
{
    public TuutException(String message) {
        super(message);
    }
    public TuutException(String message, Throwable cause) {
        super(message, cause);
    }    
}
