/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.langsupport.lua;

import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.Serializable;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
@ComponentInfo(name = "LuaJ Interpreter", category = "VRL/Language/Lua")
public class LuaInterpreter implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient Globals globals;

    public LuaInterpreter() {
    }

    public void run(@ParamInfo(name = "code", style = "silent") String code) {

        LuaValue value = getGlobals().load(code);
        value.call();
    }

    public void set(@ParamInfo(name = "name") String name, @ParamInfo(name = "value") String value) {
        getGlobals().set(name, value);
    }

    public void set(@ParamInfo(name = "name") String name, @ParamInfo(name = "value") int value) {
        getGlobals().set(name, value);
    }

    public void set(@ParamInfo(name = "name") String name, @ParamInfo(name = "value") double value) {
        getGlobals().set(name, value);
    }

    public String getString(@ParamInfo(name = "name") String name) {
        return getGlobals().get(name).checkstring().tojstring();
    }

    public int getInt(@ParamInfo(name = "name") String name) {
        return getGlobals().get(name).checkint();
    }

    public double getDouble(@ParamInfo(name = "name") String name) {
        return getGlobals().get(name).checkdouble();
    }

    /**
     * @return the globals
     */
    public Globals getGlobals() {

        if (globals == null) {
            globals = JsePlatform.standardGlobals();
        }

        return globals;
    }
}