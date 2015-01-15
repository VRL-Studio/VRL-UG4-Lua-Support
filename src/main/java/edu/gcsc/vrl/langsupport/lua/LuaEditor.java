/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.langsupport.lua;

import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@ComponentInfo(name="Lua Editor", category="VRL/Language/Lua")
public class LuaEditor implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @OutputInfo(style = "silent", name = " ")
    public String run(@ParamInfo(name=" ", style="lua-code") String code) {
        return code;
    }
    
}