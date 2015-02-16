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

	public UG4LuaEditorConfiguration(UG4LuaAutoCompletionProvider prov) {
		this.prov = prov;
	}

	@Override
	public void init(VisualCanvas vc) {
		FoldParserManager.get().addFoldParserMapping(
				SyntaxConstants.SYNTAX_STYLE_LUA, new LuaFoldParser());
	}

	@Override
	public void configure(RSyntaxTextArea textArea) {
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LUA);
		textArea.setCodeFoldingEnabled(true);
		textArea.setAntiAliasingEnabled(true);

		AutoCompletion ac;
		ac = new AutoCompletion(prov);
		ac.install(textArea);
		ac.setListCellRenderer(new CompletionCellRenderer());
		ac.setShowDescWindow(true);

		ac.setTriggerKey(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,
				KeyEvent.CTRL_DOWN_MASK | KeyEvent.ALT_DOWN_MASK));
	}

	@Override
	public String getLanguage() {
		return "ug4lua";
	}

}
