/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fontys.tuut.loader;

import java.util.HashMap;
import java.util.Map;
import javassist.ClassPool;

/**
 * 
 * @author Twan Blum
 */
public class DynamicFileObjectPool
{
    /**
     * Registered file objects
     */
    private final Map<String, DynamicFileObject> objects = new HashMap<>();

    public boolean hasObject(String name) {
        return this.objects.containsKey(name);
    }
    
    public void setObject(String name, DynamicFileObject object) {
        this.objects.put(name, object);
    }
    
    public DynamicFileObject getObject(String name) throws Exception {
        if (!this.hasObject(name)) {
            return null;
        }
        
        return this.objects.get(name);
    }
    
    public Map<String, DynamicFileObject> getObjects() {
        return this.objects;
    }

    public void clear() {
        this.objects.clear();
    }
}
