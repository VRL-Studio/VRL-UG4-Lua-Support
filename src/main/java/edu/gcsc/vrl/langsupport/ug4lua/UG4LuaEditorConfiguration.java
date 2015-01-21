/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.langsupport.ug4lua;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionCellRenderer;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;

import edu.gcsc.lua.LuaFoldParser;
import eu.mihosoft.vrl.lang.visual.EditorConfiguration;
import eu.mihosoft.vrl.reflection.VisualCanvas;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
class UG4LuaEditorConfiguration implements EditorConfiguration {

	private UG4LuaAutoCompletionProvider prov;
	private AutoCompletion ac;

	@Override
	public void init(VisualCanvas vc) {
		prov = new UG4LuaAutoCompletionProvider();
		ac = new AutoCompletion(prov);
	}

	@Override
	public void configure(RSyntaxTextArea textArea) {

		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LUA);
		textArea.setCodeFoldingEnabled(true);
		textArea.setAntiAliasingEnabled(true);

		FoldParserManager.get().addFoldParserMapping(
				SyntaxConstants.SYNTAX_STYLE_LUA, new LuaFoldParser());

		ac.setShowDescWindow(true);
		ac.install(textArea);
		ac.setListCellRenderer(new CompletionCellRenderer());

		ac.setTriggerKey(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,
				KeyEvent.CTRL_DOWN_MASK | KeyEvent.ALT_DOWN_MASK));
	}

	@Override
	public String getLanguage() {
		return "ug4lua";
	}

}
