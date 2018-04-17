///*
// /*
// * Copyright (c) 2014, Goethe University, Goethe Center for Scientific Computing (GCSC), gcsc.uni-frankfurt.de
// * All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *
// * * Redistributions of source code must retain the above copyright notice, this
// *   list of conditions and the following disclaimer.
// * * Redistributions in binary form must reproduce the above copyright notice,
// *   this list of conditions and the following disclaimer in the documentation
// *   and/or other materials provided with the distribution.
// *
// * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// * POSSIBILITY OF SUCH DAMAGE.
// */
//package edu.gcsc.vrl.langsupport.lua;
//
//import eu.mihosoft.vrl.system.InitPluginAPI;
//import eu.mihosoft.vrl.system.PluginAPI;
//import eu.mihosoft.vrl.system.PluginIdentifier;
//import eu.mihosoft.vrl.system.VPluginAPI;
//import eu.mihosoft.vrl.system.VPluginConfigurator;
//
//
//
///**
// *
// * @author Michael Hoffer <info@michaelhoffer.de>
// *
// */
//public class LuaPluginConfigurator extends VPluginConfigurator{
//
//    public LuaPluginConfigurator() {
//        //specify the plugin name and version
//       setIdentifier(new PluginIdentifier("VRL-Lua-Support", "0.2"));
//
//       // optionally allow other plugins to use the api of this plugin
//       // you can specify packages that shall be
//       // exported by using the exportPackage() method:
//       //
//       // exportPackage("com.your.package");
//
//       // describe the plugin
//       setDescription("Generic code-completion support for LUA scripts");
//
//       // copyright info
//       setCopyrightInfo("VRL-Lua-Support",
//               "(c) Goethe Center for Scientific Computing. Goethe University Frankfurt.",
//               "http://gcsc.uni-frankfurt.de", "BSD-2-Clause", "http://opensource.org/licenses/BSD-2-Clause");
//
//       // specify dependencies
//       // addDependency(new PluginDependency("VRL", "0.4.0", "0.4.0"));
//    }
//
//    @Override
//    public void register(PluginAPI api) {
//
//       // register plugin with canvas
//       if (api instanceof VPluginAPI) {
//           VPluginAPI vapi = (VPluginAPI) api;
//
//           // Register visual components:
//           //
//           // Here you can add additional components,
//           // type representations, styles etc.
//           //
//           // ** NOTE **
//           //
//           // To ensure compatibility with future versions of VRL,
//           // you should only use the vapi or api object for registration.
//           // If you directly use the canvas or its properties, please make
//           // sure that you specify the VRL versions you are compatible with
//           // in the constructor of this plugin configurator because the
//           // internal api is likely to change.
//           //
//           // examples:
//           //
//           // vapi.addComponent(MyComponent.class);
//           // vapi.addTypeRepresentation(MyType.class);
//
//           vapi.addEditorConfiguration(new LuaEditorConfiguration());
//
//           vapi.addTypeRepresentation(InputLuaCodeType.class);
//
//           vapi.addComponent(LuaEditor.class);
//           vapi.addComponent(LuaInterpreter.class);
//       }
//   }
//
//    @Override
//   public void unregister(PluginAPI api) {
//       // nothing to unregister
//   }
//
//    @Override
//    public void init(InitPluginAPI iApi) {
//       // nothing to init
//   }
// }
