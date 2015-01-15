/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.langsupport.ug4lua;

import eu.mihosoft.vrl.lang.visual.EditorConfiguration;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionCellRenderer;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
class LuaEditorConfiguration implements EditorConfiguration {

    @Override
    public void init(VisualCanvas vc) {
        
    }

    @Override
    public void configure(RSyntaxTextArea textArea) {
        
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LUA);
        textArea.setCodeFoldingEnabled(true);
        textArea.setAntiAliasingEnabled(true);
//
        
//        UG4LuaAutoCompletionProvider prov;
//        
//        FoldParserManager.get().addFoldParserMapping(
//                SyntaxConstants.SYNTAX_STYLE_LUA, new LuaFoldParser());
//

//        prov = new UG4LuaAutoCompletionProvider();
//
//        AutoCompletion ac = new AutoCompletion(prov);
//        ac.setShowDescWindow(true);
//        ac.install(textArea);
//        ac.setListCellRenderer(new CompletionCellRenderer());
//        
//        ac.setTriggerKey(KeyStroke.getKeyStroke(
//                KeyEvent.VK_SPACE,
//                KeyEvent.CTRL_DOWN_MASK | KeyEvent.ALT_DOWN_MASK));
    }

    @Override
    public String getLanguage() {
        return "ug4lua";
    }
    
}
