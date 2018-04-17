/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.langsupport.ug4lua;

import edu.gcsc.lua.Logging;
import edu.gcsc.lua.TextAreaManager;
import edu.gcsc.lua.resources.JTextComponentResourceLoader;
import edu.gcsc.vrl.langsupport.lua.InputLuaCodeType;
import edu.gcsc.vrl.langsupport.lua.LuaEditor;
import edu.gcsc.vrl.langsupport.lua.LuaEditorConfiguration;
import edu.gcsc.vrl.langsupport.lua.LuaInterpreter;
import eu.mihosoft.vrl.io.ConfigurationFile;
import eu.mihosoft.vrl.system.InitPluginAPI;
import eu.mihosoft.vrl.system.PluginAPI;
import eu.mihosoft.vrl.system.PluginDependency;
import eu.mihosoft.vrl.system.PluginIdentifier;
import eu.mihosoft.vrl.system.VPluginAPI;
import eu.mihosoft.vrl.system.VPluginConfigurator;

/**
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class UG4LuaPluginConfigurator extends VPluginConfigurator {


    UG4LuaAutoCompletionProvider prov = new UG4LuaAutoCompletionProvider();

    public UG4LuaPluginConfigurator() {
        // specify the plugin name and version
        setIdentifier(new PluginIdentifier("VRL-UG4-Lua-Support", "0.1"));

        // optionally allow other plugins to use the api of this plugin
        // you can specify packages that shall be
        // exported by using the exportPackage() method:
        //
        exportPackage("edu.gcsc.vrl.langsupport.lua");
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

        // addDependency(new PluginDependency("VRL-Lua-Support", "0.2", "x"));

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

            vapi.addEditorConfiguration(new LuaEditorConfiguration());
            vapi.addTypeRepresentation(InputLuaCodeType.class);

            vapi.addEditorConfiguration(new UG4LuaEditorConfiguration(prov));
            vapi.addTypeRepresentation(InputUG4LuaCodeType.class);

            vapi.addComponent(LuaEditor.class);
            vapi.addComponent(UG4LuaEditor.class);
            vapi.addComponent(LuaInterpreter.class);
        }
    }

    @Override
    public void unregister(PluginAPI api) {
        configuration = null;
    }

    @Override
    public void init(final InitPluginAPI iApi) {
        ConfigurationFile conf = iApi.getConfiguration();

        Logging.debug("start UG4LuaPluginConfigurator.init");
        configuration.setProvider(prov);
        configuration.setConfigurationFile(conf);
        configuration.load();

        Logging.debug("end UG4LuaPluginConfigurator.init");
    }

    private static UG4LuaPluginConfiguration configuration = new UG4LuaPluginConfiguration();

    public static UG4LuaPluginConfiguration config() {
        return configuration;
    }

    public static class UG4LuaPluginConfiguration {
        String ugBaseDir, ugCompletionsTxt;

        ConfigurationFile conf;
        UG4LuaAutoCompletionProvider prov;

        final String ugBaseDirProperty = "edu.gcsc.vrl.langsupport.ug4lua.UGBASEDIR";
        final String ugCompletionsTxtProperty = "edu.gcsc.vrl.langsupport.ug4lua.UGCOMPLETIONSTXT";

        public String getUgBaseDir() {
            return ugBaseDir;
        }

        public void setProvider(UG4LuaAutoCompletionProvider prov) {
            this.prov = prov;
        }

        public String getUgCompletionsTxt() {
            return ugCompletionsTxt;
        }

        public void setUgBaseDir(String ugBaseDir) {
            try {
                UGResourceLoader.setUg4Root(ugBaseDir);
                JTextComponentResourceLoader.getTextAreaManager().free();
                if (this.ugBaseDir != null) { // should only be null at init
                    conf.setProperty(ugBaseDirProperty, ugBaseDir);
                    conf.save();
                }
                this.ugBaseDir = ugBaseDir;
            } catch (Exception e) {
                Logging.error("Error setting UG base folder: " + e.getMessage(), e);
            }
        }

        public void setUgCompletionsTxt(String ugCompletionsTxt) {
            try {
                prov.loadUg4CompletionsTxt(ugCompletionsTxt);
                if (this.ugCompletionsTxt != null) { // it should only be null
                    // at init
                    conf.setProperty(ugCompletionsTxtProperty, ugCompletionsTxt);
                    conf.save();
                }
                this.ugCompletionsTxt = ugCompletionsTxt;
            } catch (Exception e) {
                Logging.error("Error loading ugCompletions.txt: "
                        + e.getMessage(), e);
            }
        }

        public void setConfigurationFile(ConfigurationFile conf) {
            this.conf = conf;
        }

        public void load() {
            Logging.debug("Loading configuration from config.xml");
            if (!conf.containsProperty(ugBaseDirProperty)) {
                conf.setProperty(ugBaseDirProperty, "");
                Logging.debug("Adding empty entry for " + ugBaseDirProperty
                        + " config.xml");
                conf.save();
            }
            if (!conf.containsProperty(ugCompletionsTxtProperty)) {
                conf.setProperty(ugCompletionsTxtProperty, "");
                Logging.debug("Adding empty entry for "
                        + ugCompletionsTxtProperty + " config.xml");
                conf.save();
            }
            setUgBaseDir(conf.getProperty(ugBaseDirProperty));
            setUgCompletionsTxt(conf.getProperty(ugCompletionsTxtProperty));
        }
    }
}
