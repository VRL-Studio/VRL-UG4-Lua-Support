package edu.gcsc.lua;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.VariableCompletion;
import org.junit.Test;

import edu.gcsc.lua.FifeCompletions;
import edu.gcsc.lua.LuaCompletionProvider;

public class FifeCompletionsTest {

	@Test
	public void testCreateCompletions() {
		FifeCompletions comps = new FifeCompletions(new LuaCompletionProvider());
		LuaSyntaxAnalyzer an = new LuaSyntaxAnalyzer();
		an.getDoxyGenMap().put("function", "<p>Some Info");

		CompletionInfo fi = CompletionInfo.newFunctionInstance(
				new LuaResource("test"), "function", 1, 1, true);
		fi.setParameter(Arrays.asList(new FunctionParameter("param1")));
		comps.addCompletion(fi, an);

		FunctionCompletion fc = (FunctionCompletion) comps.getCompletions()
				.iterator().next();
		assertEquals(1, fc.getParamCount());
		assertEquals("<p>Some Info<p>included from test, line 1",
				fc.getShortDescription());

		comps.getCompletions().clear();

		comps.addCompletion(CompletionInfo.newVariableInstance(
				new LuaResource("test"), "var", 2, 2, false), an);

		VariableCompletion vc = (VariableCompletion) comps.getCompletions()
				.iterator().next();
		assertEquals("var", vc.getInputText());

		comps.getCompletions().clear();
		comps.addCompletion(CompletionInfo.newKeyWordInstance("test", "test"), an);

		BasicCompletion bc = (BasicCompletion) comps.getCompletions().iterator().next();
		assertEquals("test", bc.getInputText());
	}

	@Test
	public void testCreateCompletionsIgnoreTable() {
		FifeCompletions comps = new FifeCompletions(new LuaCompletionProvider());
		LuaSyntaxAnalyzer an = new LuaSyntaxAnalyzer();
		an.getTables().put("var", new HashSet<String>());
		comps.addCompletion(CompletionInfo.newVariableInstance(new LuaResource("test"),
				"var", 2, 2, false), an);
		assertEquals(0, comps.getCompletions().size());
	}

}
