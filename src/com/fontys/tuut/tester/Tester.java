/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.tester;

import com.fontys.tuut.TestResult;
import com.fontys.tuut.Test;
import com.fontys.tuut.TestBehavior;
import com.fontys.tuut.TestConstructor;
import com.fontys.tuut.TestMethod;
import com.fontys.tuut.loader.DynamicClassLoader;
import com.fontys.tuut.loader.DynamicFileObjectPool;
import java.util.ArrayList;
import java.util.List;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ClassMemberValue;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

/**
 *
 * @author Twan Blum
 */
public class Tester
{
    private final static String TESTSUFFIX  = "Test";

    private final static Class RUNWITH         = RunWith.class;
    private final static Class TESTCLASSRUNNER = TestClassRunner.class;
    
    private final ClassPool classPool;
    private final ClassLoader classLoader;
    private final DynamicFileObjectPool objectPool;
    
    public Tester(ClassPool classPool, ClassLoader classLoader, DynamicFileObjectPool objectPool) {
        this.classPool = classPool;
        this.classLoader = classLoader;
        this.objectPool = objectPool;
    }
    
    /**
     * 
     * @param test
     * @return
     * @throws TestException 
     */
    public List<TestResult> test(Test test) throws TestException {
        List<TestResult> results = new ArrayList<>();
        
        try {
            CtClass ctImpl = this.classPool.get(test.getClassName());
            CtClass ctTest = this.classPool.get(test.getClassName() + TESTSUFFIX);

            ctTest  = this.addTestRunner(ctTest);
            
            DynamicClassLoader loader = new DynamicClassLoader(this.classLoader, this.classPool,this.objectPool);
            Class unitTest = loader.loadClass(ctTest.getName());
            
            
            if (!JUnitCore.runClasses(unitTest).wasSuccessful()) {
                throw new TestException("Failed initial unit test, test was unsuccessful");
            }

           
            
            TestClassEditor classEditor = new TestClassEditor(ctImpl);
            
            for (TestBehavior testBehavior : test.getTestBehaviors()) {

                if (testBehavior instanceof TestConstructor) {
                    this.replaceConstructorBehavior(classEditor, (TestConstructor) testBehavior);
                }

                if (testBehavior instanceof TestMethod) {
                    this.replaceMethodBehavior(classEditor, (TestMethod) testBehavior);
                }
                
                results.add(new TestResult(test, testBehavior, JUnitCore.runClasses(unitTest)));
                
                classEditor.revert();
            }
        } catch (NotFoundException  ex) {
            throw new TestException("failed to run test", ex);
        }
        
        return results;
    }
    
    /**
     * Replace constructor behavior
     * 
     * @param editor
     * @param constructor
     * @throws TestException 
     */
    public void replaceConstructorBehavior(TestClassEditor editor, TestConstructor constructor) throws TestException {
        CtConstructor c = (CtConstructor) constructor.getError();
        editor.replaceConstructor(c, true);
    }
    
    /**
     * Replace method behavior
     * 
     * @param editor
     * @param method
     * @throws TestException 
     */
    public void replaceMethodBehavior(TestClassEditor editor, TestMethod method) throws TestException {
        CtMethod m = (CtMethod) method.getError();
        m.setName(method.getName());
        editor.replaceMethod(m, true);
    }

    private CtClass addTestRunner(CtClass ctClass) {
        if (ctClass.isFrozen()) {
            ctClass.defrost();
        }
        
        ClassFile classFile = ctClass.getClassFile();
        ConstPool constPool = classFile.getConstPool();
        
        AnnotationsAttribute attribute = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation(RUNWITH.getName(), constPool);
     
        annotation.addMemberValue("value", new ClassMemberValue(TESTCLASSRUNNER.getName(), constPool));
        attribute.addAnnotation(annotation);
        classFile.addAttribute(attribute);
        
        ctClass.rebuildClassFile();
        
        return ctClass;
    }
   
}
