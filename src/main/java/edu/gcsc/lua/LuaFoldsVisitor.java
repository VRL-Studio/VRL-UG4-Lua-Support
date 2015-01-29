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

import java.util.ArrayList;
import java.util.List;

import edu.gcsc.lua.grammar.LuaBaseVisitor;
import edu.gcsc.lua.grammar.LuaParser;
import edu.gcsc.lua.grammar.LuaParser.BlockContext;

class LuaFoldsVisitor extends LuaBaseVisitor<Void> {
	private final List<FoldProposal> folds = new ArrayList<FoldProposal>();

	public static class FoldProposal {
		private int startLine, endLine, startChar, endChar;

		public int getEndChar() {
			return endChar;
		}

		public int getEndLine() {
			return endLine;
		}

		public int getStartChar() {
			return startChar;
		}

		public int getStartLine() {
			return startLine;
		}
	}

	public List<FoldProposal> getFolds() {
		return folds;
	}

	@Override
	public Void visitBlock(BlockContext ctx) {
		if (LuaParseTreeUtil.getParentRuleContext(ctx, LuaParser.RULE_block,
				BlockContext.class) != null
				&& ctx.getParent() != null
				&& ctx.getParent().getStart() != null
				&& ctx.getParent().getStop() != null) {
			FoldProposal fold = new FoldProposal();
			fold.startLine = ctx.getParent().getStart().getLine() - 1;
			fold.startChar = ctx.getParent().getStart().getCharPositionInLine();

			fold.endLine = ctx.getParent().getStop().getLine() - 1;
			fold.endChar = ctx.getParent().getStop().getCharPositionInLine();
			folds.add(fold);
		}
		return super.visitBlock(ctx);
	}
}