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
package edu.gcsc.lua;

import java.io.BufferedReader;
import java.io.IOException;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.fife.io.DocumentReader;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParserNotice;

import edu.gcsc.lua.grammar.LuaBaseVisitor;
import edu.gcsc.lua.grammar.LuaParser.ChunkContext;

public class LuaErrorParser extends AbstractParser {

	@Override
	public ParseResult parse(RSyntaxDocument doc, String paramString) {
		BufferedReader reader = new BufferedReader(new DocumentReader(doc));
		StringBuffer buf = new StringBuffer();
		String line;

		final DefaultParseResult res = new DefaultParseResult(this);

		try {
			while ((line = reader.readLine()) != null) {
				buf.append(line);
				buf.append("\n");
			}
			ChunkContext ctx = LuaParseTreeUtil.parse(buf.toString());
			new LuaBaseVisitor<Void>() {
				@Override
				public Void visitErrorNode(ErrorNode node) {
					res.addNotice(createNotice("Error", node.getSymbol(),
							DefaultParserNotice.ERROR));
					return super.visitErrorNode(node);
				}
			}.visit(ctx);
			reader.close();
		} catch (RecognitionException e) {
			res.addNotice(createNotice(e.getMessage(), e.getOffendingToken(),
					DefaultParserNotice.ERROR));
		} catch (IOException e) {
			
		}

		return res;
	}

	ParserNotice createNotice(String message, Token node, int level) {
		DefaultParserNotice notice = new DefaultParserNotice(this, message,
				node.getLine()+1);
		notice.setLevel(level);
		return notice;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
