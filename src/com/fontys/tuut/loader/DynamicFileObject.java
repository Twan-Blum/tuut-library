/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import javax.tools.SimpleJavaFileObject;

/**
 * Dynamic created class as file object.
 * 
 * @author Twan
 */
public class DynamicFileObject extends SimpleJavaFileObject
{
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    public DynamicFileObject(String className) throws Exception {
        super(new URI(className), Kind.CLASS);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return out;
    }

    public byte[] getByteCode() {
        return out.toByteArray();
    }
}