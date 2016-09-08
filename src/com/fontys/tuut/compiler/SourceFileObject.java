/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.compiler;

import java.net.URI;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

/**
 * Java object file used as compilation unit
 * 
 * @author Twan Blum
 */
public class SourceFileObject extends SimpleJavaFileObject 
{
    private final String code;

    public SourceFileObject(String name, String code) {
        super(URI.create("string:///" + name), JavaFileObject.Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
    
}
