/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.langsupport.ug4lua;

import eu.mihosoft.vrl.io.ConfigurationFile;
import eu.mihosoft.vrl.system.InitPluginAPI;
import eu.mihosoft.vrl.system.PluginAPI;
import eu.mihosoft.vrl.system.PluginDependency;
import eu.mihosoft.vrl.system.PluginIdentifier;
import eu.mihosoft.vrl.system.VPluginAPI;
import eu.mihosoft.vrl.system.VPluginConfigurator;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UG4LuaPluginConfigurator extends VPluginConfigurator {

	public UG4LuaPluginConfigurator() {
		// specify the plugin name and version
		setIdentifier(new PluginIdentifier("VRL-UG4-Lua-Support", "0.1"));

		// optionally allow other plugins to use the api of this plugin
		// you can specify packages that shall be
		// exported by using the exportPackage() method:
		//
		exportPackage("edu.gcsc.vrl.langsupport.ug4lua");

		// describe the plugin
		setDescription("LUA code completion for UG4 shell scripts.");

		// copyright info
		setCopyrightInfo(
				"VRL-UG4-Lua-Support",
				"(c) 2014 Goethe Center for Scientific Computing, Goethe University Frankfurt.",
				"http://gcsc.uni-frankfurt.de", "BSD 2-Clause",
				"http://opensource.org/licenses/BSD-2-Clause");

		// specify dependencies
		// addDependency(new PluginDependency("VRL", "0.4.0", "0.4.0"));

		addDependency(new PluginDependency("VRL-Lua-Support", "0.2", "x"));

	}

	@Override
	public void register(PluginAPI api) {

		// register plugin with canvas
		if (api instanceof VPluginAPI) {
			VPluginAPI vapi = (VPluginAPI) api;

			// Register visual components:
			//
			// Here you can add additional components,
			// type representations, styles etc.
			//
			// ** NOTE **
			//
			// To ensure compatibility with future versions of VRL,
			// you should only use the vapi or api object for registration.
			// If you directly use the canvas or its properties, please make
			// sure that you specify the VRL versions you are compatible with
			// in the constructor of this plugin configurator because the
			// internal api is likely to change.
			//
			// examples:
			//
			// vapi.addComponent(MyComponent.class);
			// vapi.addTypeRepresentation(MyType.class);

			vapi.addEditorConfiguration(new UG4LuaEditorConfiguration(
					configuration));
			vapi.addTypeRepresentation(InputUG4LuaCodeType.class);
		}
	}

	@Override
	public void unregister(PluginAPI api) {
		// nothing to unregister
	}

	@Override
	public void init(final InitPluginAPI iApi) {
		ConfigurationFile conf = iApi.getConfiguration();
		final String ugBaseDirProperty = "edu.gcsc.vrl.langsupport.ug4lua.UGBASEDIR";
		if (!conf.containsProperty(ugBaseDirProperty)) {
			conf.setProperty(ugBaseDirProperty, "");
			conf.save();
		}
		final String ugCompletionsTxtProperty = "edu.gcsc.vrl.langsupport.ug4lua.UGCOMPLETIONSTXT";
		if (!conf.containsProperty(ugCompletionsTxtProperty)) {
			conf.setProperty(ugCompletionsTxtProperty, "");
			conf.save();
		}
		configuration.setUgBaseDir(conf.getProperty(ugBaseDirProperty));
		configuration.setUgCompletionsTxt(conf
				.getProperty(ugCompletionsTxtProperty));

	}

	private UG4LuaPluginConfiguration configuration = new UG4LuaPluginConfiguration();

	public static class UG4LuaPluginConfiguration {
		String ugBaseDir, ugCompletionsTxt;

		public String getUgBaseDir() {
			return ugBaseDir;
		}

		public String getUgCompletionsTxt() {
			return ugCompletionsTxt;
		}

		public void setUgBaseDir(String ugBaseDir) {
			this.ugBaseDir = ugBaseDir;
		}

		public void setUgCompletionsTxt(String ugCompletionsTxt) {
			this.ugCompletionsTxt = ugCompletionsTxt;
		}
	}
}
