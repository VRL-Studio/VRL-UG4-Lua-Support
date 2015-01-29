package edu.gcsc.lua;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.junit.Test;

import edu.gcsc.lua.grammar.LuaLexer;
import edu.gcsc.lua.grammar.LuaParser;

public class LuaFoldVisitorTest {

	LuaFoldsVisitor prepare(String script) throws Exception {
		ANTLRInputStream str = new ANTLRInputStream(new StringReader(script));
		Lexer lx = new LuaLexer(str);
		CommonTokenStream tokStr = new CommonTokenStream(lx);
		LuaParser parser = new LuaParser(tokStr);
		LuaFoldsVisitor lfv = new LuaFoldsVisitor();
		lfv.visit(parser.chunk());
		return lfv;
	}

	@Test
	public void emptyFirstLines() throws Exception {
		LuaFoldsVisitor visitor = prepare("\n\n\nfunction test()\n  return 0\nend\n\n\n");
		assertEquals(1, visitor.getFolds().size());
	}
}
