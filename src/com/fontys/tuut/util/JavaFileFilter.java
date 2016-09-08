/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.util;

import java.io.File;
import java.io.FileFilter;

/**
 * Java file filter with some static helper functions
 * 
 * @author Twan Blum
 */
public class JavaFileFilter implements FileFilter 
{
    private final static String EXTENSION    = ".java";
    private final static String SUFFIX_TEST  = "Test" + EXTENSION;
    private final static String SUFFIX_ERROR = "TestError" + EXTENSION;
    
    @Override
    public boolean accept(File pathname) {
        return (!pathname.isDirectory()) ? JavaFileFilter.isSourceFile(pathname) : true;
    }
    
    /**
     * Check if file has .java extension
     * 
     * @param  file
     * @return true when file ends with .java, false otherwise
     */
    public static boolean isSourceFile(File file) {
        return (file.isFile() && file.toString().endsWith(EXTENSION));
    }
    
    /**
     * Check if file is a test file
     * 
     * @param  file
     * @return true when file ends with Test.java false otherwise
     */
    public static boolean isTestFile(File file) {
        return (file.isFile() && file.toString().endsWith(SUFFIX_TEST));
    }
    
    /**
     * Check if file is a error test file
     * 
     * @param  file
     * @return true when file ends with TestError.java false otherwise
     */
    public static boolean isErrorFile(File file) {
        return (file.isFile() && file.toString().endsWith(SUFFIX_ERROR));
    }

}
