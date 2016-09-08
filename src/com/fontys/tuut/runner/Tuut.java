/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.runner;

import com.fontys.tuut.Test;
import com.fontys.tuut.TestResult;
import com.fontys.tuut.annotation.ParseException;
import com.fontys.tuut.annotation.Parser;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import com.fontys.tuut.util.JavaFileFilter;
import com.fontys.tuut.compiler.Compiler;
import com.fontys.tuut.compiler.CompileException;
import com.fontys.tuut.loader.DynamicFileObjectPool;
import com.fontys.tuut.tester.TestException;
import com.fontys.tuut.tester.Tester;
import com.fontys.tuut.util.JavaFileParser;
import java.util.HashMap;
import java.util.List;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 *
 * @author Twan
 */
public class Tuut
{
   
    
    private final Map<String, File> sourceFiles = new HashMap();
    private final Map<String, File> testFiles   = new HashMap();
    private final Map<String, File> errorFiles  = new HashMap();
    
    /**
     * Add path to test
     * 
     * @param path 
     * @throws TuutException when argument is not a directory
     */
    public void addPath(File path) throws TuutException {
        if (!path.isDirectory()) {
            throw new TuutException("Failed to add path, argument is not a directory");
        }

        for (File file : path.listFiles(new JavaFileFilter())) {
            if (file.isDirectory()) {
                this.addPath(file);
            } else {
                this.addFile(file);
            }
        }
    }

    /**
     * Add file to test
     * 
     * @param file 
     * @throws TuutException when argument is not a file
     */
    public void addFile(File file) throws TuutException {
        if (!JavaFileFilter.isSourceFile(file)) {
            throw new TuutException("Failed to add file, argument is not a valid java source file");
        }
  
        try {
            String name = JavaFileParser.getClassName(file);
            
            if (JavaFileFilter.isTestFile(file)) {
                this.testFiles.put(name, file);
            } else if (JavaFileFilter.isErrorFile(file)) {
                this.errorFiles.put(name, file);
            }
       
            this.sourceFiles.put(name, file);
        } catch (IOException ex) {
            throw new TuutException("Failed to get class name from java file", ex);
        }
    }
    
    /**
     * Run test
     * 
     * @return 
     * @throws TuutException 
     */
    public Map<Test, List<TestResult>> run() throws TuutException {
        Map<Test, List<TestResult>> results = new HashMap<>();
          
        try {
            TuutEnvironment environment = TuutEnvironment.getInstance();
            environment.setClassPool(new ClassPool(true));
            environment.setObjectPool(new DynamicFileObjectPool());
            environment.setClassLoader(environment.getClassPool().getClassLoader());


            Tester tester = new Tester(environment.getClassPool(), environment.getClassLoader(), environment.getObjectPool());
            Compiler compiler = new Compiler(environment.getClassPool(), environment.getObjectPool(), environment.getClassLoader());
            compiler.compile(this.sourceFiles);
 
            
            for (Map.Entry entry : this.errorFiles.entrySet()) {
                CtClass ctErrorClass = environment.getClassPool().get((String) entry.getKey());
                
                Test test = Parser.parse(ctErrorClass);
                results.put(test, tester.test(test));
            }
            
            return results;
//        } catch (CompileException | NotFoundException | TestException ex) {
//            throw new TuutException("Failed to execute test", ex);
        } catch (CompileException |TestException | NotFoundException | ParseException ex) {
            throw new TuutException("Failed to run test", ex);
        }  finally {
            //compiler.clean();
        }
    }
  
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException  {
        String path = "/Users/Twan/Projects/Tuut/examples/UnittestenTimeUitw";

        // remove timestamps from log format
        System.setProperty(
            "java.util.logging.SimpleFormatter.format", 
            "%4$s %2$s %5$s%6$s%n"
        );
        
        Map<Test, List<TestResult>> results;
        
        try{
            Tuut tuut = new Tuut();
            tuut.addPath(new File(path));
            results = tuut.run();
            
            printTestResults(results);
            
        } catch (TuutException ex) {
            // de groetjes van ruud!
            ex.printStackTrace(System.err);
        }
    }
    
    public static void printTestResults(Map<Test, List<TestResult>> results) {
         results.forEach((test, result) -> {
                System.out.println("=======================================================");
                System.out.println(test.getClassName());
                System.out.println("=======================================================");
                result.forEach(r -> {
                   
                    System.out.println((r.wasSuccessful() ? "PASSED" : "FAILED") + " weight:" + r.getTestBehavior().getWeight() + " description: " + r.getTestBehavior().getDescription());
//                    System.out.println("results:");
//      
//                    r.getResult().getFailures().forEach(failure -> {
//                        System.out.println("\tmessage: " + failure.getMessage());
//                        //System.out.println("trace:" + failure.getTrace());
//                    });
                    
                });
            });
    }
   
}
