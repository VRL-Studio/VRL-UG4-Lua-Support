package edu.gcsc.vrl.langsupport.ug4lua;

import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;

import java.io.Serializable;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
@ComponentInfo(name="UG4 Lua Editor", category="VRL/Language/Lua", description = "Lua Editor for the simulation system UG4")
public class UG4LuaEditor implements Serializable {

    private static final long serialVersionUID = 1L;

    @OutputInfo(style = "silent", name = " ")
    public String run(@ParamInfo(name=" ", style="ug4lua-code") String code) {
        return code;
    }

}