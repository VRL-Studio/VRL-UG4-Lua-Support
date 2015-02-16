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

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.gcsc.lua.CaretInfo;
import edu.gcsc.lua.CompletionInfo;
import edu.gcsc.lua.Completions;
import edu.gcsc.lua.FunctionParameter;
import edu.gcsc.lua.Logging;
import edu.gcsc.lua.LuaCompletionProvider;
import edu.gcsc.lua.LuaResource;
import edu.gcsc.lua.LuaResourceLoader;
import edu.gcsc.lua.LuaResourceLoaderFactory;
import edu.gcsc.lua.LuaSyntaxAnalyzer;
import edu.gcsc.lua.LuaSyntaxInfo;
import edu.gcsc.lua.resources.FileResourceLoader;
import edu.gcsc.lua.visitors.LuaCompletionVisitor;
import edu.gcsc.vrl.lua.autocompletion.RegClassDescription;
import edu.gcsc.vrl.lua.autocompletion.RegFunctionDescription;
import edu.gcsc.vrl.lua.autocompletion.UG4CompletionsLoader;
import edu.gcsc.vrl.lua.autocompletion.UGLoadScriptVisitor;

public class UG4LuaAutoCompletionProvider extends LuaCompletionProvider {

	UG4CompletionsLoader ug4loader = new UG4CompletionsLoader();
	List<CompletionInfo> staticCompletions = new ArrayList<CompletionInfo>();
	LuaSyntaxInfo staticSyntaxInfo = new LuaSyntaxInfo();

	public void setUg4Root(String ug4Root) {
		UGResourceLoader.setUg4Root(ug4Root);
	}

	public void setCurrentDir(String dir) {
		UGResourceLoader.setCurrentDir(dir);
	}

	protected void loadUg4CompletionsTxt(String file) {

		try {
			System.out.println("Loading UG4 completions file: " + file);
			staticCompletions.clear();
			ug4loader.load(new FileInputStream(file));
			LuaResource res = new LuaResource("file:" + file);
			// for now the syntax info wants at least a dummy resource, not
			// pretty
			staticSyntaxInfo
					.setResourceLoaderFactory(new LuaResourceLoaderFactory(
							FileResourceLoader.class));
			staticSyntaxInfo.setResource(res);
			for (RegFunctionDescription fd : ug4loader.getFunctions()) {
				CompletionInfo info = CompletionInfo.newFunctionInstance(res,
						fd.getName(), fd.getHtml(), fd.getLine(), 0, false);
				info.setRelevance(3000);
				int params = fd.getParameterCount();
                info.setParameter(
						new ArrayList<FunctionParameter>());
				for (int i = 0; i < params; i++) {
					info.getParameter().add(
							new FunctionParameter(fd.getParameterName(i)));
				}
				staticCompletions.add(info);
			}
			for (RegClassDescription cls : ug4loader.getClasses().values()) {
				CompletionInfo info = CompletionInfo.newKeyWordInstance(
						cls.getName(), cls.getHtml());
				info.setRelevance(100);
				staticCompletions.add(info);
			}
		} catch (Exception e) {
			System.out.println("Loading UG4 completions file failed: "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	protected void fillResourceLoaders(
			List<Class<? extends LuaResourceLoader>> loaders) {
		super.fillResourceLoaders(loaders);
		loaders.add(UGResourceLoader.class);
	}

	@Override
	protected void fillVisitors(List<LuaCompletionVisitor> visitors) {
		visitors.add(new UGLoadScriptVisitor());
		super.fillVisitors(visitors);
	}

	@Override
	protected void fillCompletions(LuaSyntaxAnalyzer root,
			Map<LuaResource, LuaSyntaxInfo> includes, Completions completions,
			String alreadyEntered, CaretInfo info) {
		Logging.debug("Adding static completions in UG4LuaAutoCompletionProvider:101 -> "
				+ staticCompletions.size());

		for (CompletionInfo ci : staticCompletions) {
			completions.addCompletion(ci, staticSyntaxInfo);
		}
		// TODO: add to statics
		for (String prop : UGResourceLoader.createUGLoadScriptCompletions()) {
			CompletionInfo ci = CompletionInfo.newKeyWordInstance(prop,
					"Load UG shell library script.");
			completions.addCompletion(ci, staticSyntaxInfo);
		}

		for (String var : getTypeMap().keySet()) {
			if (var.startsWith(alreadyEntered)
					|| alreadyEntered.startsWith(var)) {
				String type = getTypeMap().get(var);
				if (ug4loader.getClasses().containsKey(type)) {
					RegClassDescription cd = ug4loader.getClasses().get(type);
					List<RegFunctionDescription> fds = new ArrayList<RegFunctionDescription>(
							cd.getMemberfunctions());
					for (RegClassDescription parent : cd.getClassHierachy()) {
						if (parent == null)
							System.out
									.println("Broken type hierarchy in class description for "
											+ cd.getName());
						else
							fds.addAll(parent.getMemberfunctions());
					}
					for (RegFunctionDescription fd : fds) {
						CompletionInfo ci = CompletionInfo.newFunctionInstance(
								staticSyntaxInfo.getResource(),
								var + ":" + fd.getName(), fd.getHtml(), 0, 0,
								false);
						ci.setRelevance(7000);
						completions.addCompletion(ci, staticSyntaxInfo);
						// fc.setShortDescription(cd.getName() + ":"
						// + fd.getSignature());
						// TODO:
						// fc.setIcon(IconLib.instance().getMemberFunctionIcon());
					}
				}
			}
		}
	}
}
