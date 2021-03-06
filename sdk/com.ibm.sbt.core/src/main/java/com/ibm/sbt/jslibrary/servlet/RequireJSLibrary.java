/*
 * © Copyright IBM Corp. 2012
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package com.ibm.sbt.jslibrary.servlet;

import java.util.Map;

import com.ibm.commons.util.io.json.JsonObject;

/**
 * Library implementation for libraries which use RequireJS as an AMD loader
 * 
 * @author mwallace
 * @author cmanias
 */
public abstract class RequireJSLibrary extends AbstractLibrary {

	protected static final String		MODULE_BRIDGE			= "sbt/_bridge";						//$NON-NLS-1$
	protected static final String		MODULE_SBTX_WIDGET		= "sbtx/widget";						//$NON-NLS-1$
	protected static final String		MODULE_HAS				= "has";								//$NON-NLS-1$
	protected static final String		MODULE_REQUIRE_I18N		= "requirejs/i18n";					//$NON-NLS-1$
	protected static final String		MODULE_REQUIRE_TEXT		= "requirejs/text";					//$NON-NLS-1$

	protected static final String		PATH_SBTX				= "sbtx/js/sdk/sbtx";					//$NON-NLS-1$

	protected static final String		PATH_HAS				= "has";					//$NON-NLS-1$

	protected static final String		PATH_REQUIRE_I18N		= "requirejsPlugins/i18n"; //$NON-NLS-1$
	protected static final String		PATH_REQUIRE_TEXT		= "requirejsPlugins/text"; //$NON-NLS-1$
	// TODO Do these need to be dynamic
	protected static final String[][]	REGISTER_MODULES		= { { MODULE_SBT, PATH_SBT },
			{ MODULE_BRIDGE }									};

	protected static final String[][]	LIBRARY_MODULES			= { { MODULE_HAS, PATH_HAS },
			{ MODULE_REQUIRE_I18N, PATH_REQUIRE_I18N }, { MODULE_REQUIRE_TEXT, PATH_REQUIRE_TEXT } };

	private static final String[][]		REGISTER_EXT_MODULES	= { { MODULE_SBTX, PATH_SBTX },
			{ MODULE_SBTX_WIDGET }								};
	private static final String[]		REQUIRE_MODULES			= new String[0];

	private static final String			DEFINE_MODULE			= MODULE_CONFIG;

	static private final String		DEFINE_MODULE_LAYER		= MODULE_CONFIG_LAYER;

	/**
	 * Default constructor
	 */
	public RequireJSLibrary(String name) {
		// TODO remove hardcoded strings
		super(name, "", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.sbt.jslibrary.servlet.AbstractLibrary#enableDefineCheck(java. lang.String)
	 */
	@Override
	public boolean enableDefineCheck(String version) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.sbt.jslibrary.servlet.BaseLibrary#getRegisterModules(LibraryRequest)
	 */
	@Override
	protected String[][] getRegisterModules(LibraryRequest request) {
		return REGISTER_MODULES;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.sbt.jslibrary.servlet.AbstractLibrary#getRegisterModulesAmd(LibraryRequest)
	 */
	@Override
	protected String[][] getRegisterModulesAmd(LibraryRequest request) {
		return REGISTER_MODULES;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.sbt.jslibrary.servlet.BaseLibrary#getDefineModule()
	 */
	@Override
	protected String getDefineModule() {
		return DEFINE_MODULE;
	}

	@Override
	protected String getDefineModuleLayer() {
		return DEFINE_MODULE_LAYER;
	}

	@Override
	protected String[] getRequireModules() {
		return REQUIRE_MODULES;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.sbt.jslibrary.servlet.AbstractLibrary#getRegisterExtModules(LibraryRequest)
	 */
	@Override
	protected String[][] getRegisterExtModules(LibraryRequest request) {
		return REGISTER_EXT_MODULES;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.sbt.jslibrary.servlet.AbstractLibrary#getRequireModulesAmd()
	 */
	@Override
	protected String[] getRequireModulesAmd() {
		return REQUIRE_MODULES;
	}

	/*
	 * We redefine the isExtension boolean parameter. The meaning here is that there are extension modules pending to be added (so we need to append a comma) (non-Javadoc)
	 * @see com.ibm.sbt.jslibrary.servlet.AbstractLibrary#generateRegisterModules(java.lang.StringBuilder, int, com.ibm.sbt.jslibrary.servlet.LibraryRequest, java.lang.String[][], boolean)
	 */
	@Override
	protected void generateRegisterModules(StringBuilder sb, int indentationLevel, LibraryRequest request,
			String[][] registerModules, ModuleType type) {

		if (registerModules == null) {
			return;
		}
		int numModules = registerModules.length;
		for (int i = 0; i < numModules; i++) {
			String[] registerModule = registerModules[i];
			String moduleUrl = getModuleUrl(request, registerModule[1], type);
			indent(sb, indentationLevel).append("'").append(registerModule[0]).append("':'").append(moduleUrl).append("'");
			if (i < numModules - 1) {
				sb.append(",").append(newLine());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.sbt.jslibrary.servlet.AbstractLibrary#generateSbtConfigDefine(com.ibm.sbt.jslibrary.servlet.LibraryRequest, java.util.Map, com.ibm.commons.util.io.json.JsonObject, int)
	 */
	@Override
	protected StringBuilder generateSbtConfigDefine(LibraryRequest request,
			Map<String, JsonObject> endpoints, JsonObject properties, int indentationLevel)
			throws LibraryException {
		StringBuilder sb = super.generateSbtConfigDefine(request, endpoints, properties, indentationLevel);
		indent(sb, indentationLevel).append("require(['sbt/config'], function(){});").append(newLine());
		return sb;
	};

	/*
	 * (non-Javadoc)
	 * @see com.ibm.sbt.jslibrary.servlet.AbstractLibrary#generateRegisterModulePath (java.lang.String, java.lang.String)
	 */
	@Override
	protected String generateRegisterModulePath(LibraryRequest request, String moduleName, String moduleUrl) {
		return "";
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.sbt.jslibrary.servlet.AbstractLibrary#generateRequire(java.lang .String)
	 */
	@Override
	protected String generateRequire(String module) {
		StringBuilder sb = new StringBuilder();
		sb.append("require('").append(module).append("')");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.sbt.jslibrary.servlet.AbstractLibrary#generateModuleBlock(com.ibm.sbt.jslibrary.servlet.LibraryRequest, java.lang.String[][], java.lang.String[], int)
	 */
	@Override
	protected StringBuilder generateModuleBlock(LibraryRequest request, String[][] registerModules,
			String[][] registerExtModules, String[] requireModules, int indentationLevel) {
		StringBuilder sb = new StringBuilder();
		indent(sb, indentationLevel).append("requirejs.config({").append(newLine()); // begin requirejs.config
		indentationLevel += 1;
		// indent(sb, indentationLevel).append("enforceDefine: '").append(enforceDefine).append("',\n");
		// indent(sb, indentationLevel).append("waitSeconds: '").append(waitSeconds).append("',\n");
		// indent(sb, indentationLevel).append("baseUrl: '").append(PATH_BASEURL).append("',\n");
		indent(sb, indentationLevel).append("paths: {"); // begin paths

		// register the module paths and required modules
		indentationLevel++;
		sb.append(newLine());
		generateRegisterModules(sb, indentationLevel, request, registerModules, ModuleType.SBT_MODULE);
		if (registerExtModules != null) {
			sb.append(",").append(newLine());
			generateRegisterModules(sb, indentationLevel, request, registerExtModules, ModuleType.SBTX_MODULE);
		}
		sb.append(",").append(newLine());
		generateRegisterModules(sb, indentationLevel, request, LIBRARY_MODULES, ModuleType.JS_LIBRARY);

		generateRequireModules(sb, indentationLevel, requireModules);
		indentationLevel -= 1;
		indent(sb, indentationLevel).append("}").append(newLine()); // end paths

		indentationLevel -= 1;
		indent(sb, indentationLevel).append("});").append(newLine()); // end requirejs.config

		return sb;
	}
}
