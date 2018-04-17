/*
 * Copyright (c) 2014, Goethe University, Goethe Center for Scientific Computing (GCSC), gcsc.uni-frankfurt.de
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.gcsc.vrl.langsupport.ug4lua.lua;

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