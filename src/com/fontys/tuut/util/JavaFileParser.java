/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.util;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.File;
import java.io.IOException;

/**
 * Get full class name
 * 
 * @author Twan Blum
 */
public class JavaFileParser
{  
    /**
     * Get class name from file
     * 
     * @param file
     * @return
     * @throws IOException 
     */
    public static String getClassName(File file) throws IOException {
        if (!file.isFile()) {
            throw new IOException("failed to get class name from file, argument is not a java source file");
        }
        
        StringBuilder stringBuilder = new StringBuilder();
        
        try {
            CompilationUnit cu = JavaParser.parse(file);
            ClassVisitorAdapter cva = new ClassVisitorAdapter();
            cva.visit(cu, stringBuilder);
        } catch (com.github.javaparser.ParseException ex) {
            throw new IOException("failed to get class name from file", ex);
        }
        
        return stringBuilder.toString();
    }
    
    private static class ClassVisitorAdapter extends VoidVisitorAdapter
    {
        @Override
        public void visit(EnumDeclaration e, Object arg) {
            if (arg instanceof StringBuilder == false) {
               return;
            }

            if (((StringBuilder) arg).length() > 0) {
                ((StringBuilder) arg).append('.');
            }

            ((StringBuilder) arg).append(e.getName());
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration c, Object arg) {
            if (arg instanceof StringBuilder == false) {
               return;
            }

            if (((StringBuilder) arg).length() > 0) {
                ((StringBuilder) arg).append('.');
            }

            ((StringBuilder) arg).append(c.getName());
        }

        @Override
        public void visit(PackageDeclaration n, Object arg) {
            if (arg instanceof StringBuilder == false) {
                return;
            }

            ((StringBuilder) arg).append(n.getName().toString());
        }
    }
}