package com.ibm.sbt.test.js.connections.files.api;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.ibm.commons.util.io.json.JsonJavaObject;
import com.ibm.sbt.automation.core.test.connections.BaseFilesTest;
import com.ibm.sbt.automation.core.test.pageobjects.JavaScriptPreviewPage;
import com.ibm.sbt.services.client.connections.files.File;
import com.ibm.sbt.services.client.connections.files.FileServiceException;

public class GetPublicFiles extends BaseFilesTest {

	static final String SNIPPET_ID = "Social_Files_API_GetPublicFiles";

	private List<File> files;

	@Before
	public void init() {
		if (environment.isSmartCloud()) {
			return;
		}
		// to make sure there is at least one public file
		createFile();
		addSnippetParam("sample.fileId", fileEntry.getId());
		try {
			files = fileService.getPublicFiles(new HashMap<String, String>());
		} catch (FileServiceException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@After
	public void destroy() {
		if (environment.isSmartCloud()) {
			return;
		}
		deleteFileAndQuit();
	}

	@Override
	protected boolean isEnvironmentValid() {
		// TODO disabled as there may be an OAuth problem
		return !environment.isSmartCloud();
	}

	@Test
	public void testGetPublicFiles() {
		if (environment.isSmartCloud()) {
			return;
		}
		JavaScriptPreviewPage previewPage = executeSnippet(SNIPPET_ID);
		try {
			@SuppressWarnings({ "rawtypes" })
			List jsonList = previewPage.getJsonList();
			Assert.assertEquals(jsonList.size(), files.size());
			for (int i = 0; i < jsonList.size(); i++) {
				JsonJavaObject fileJsonObj = (JsonJavaObject) jsonList.get(i);
				Assert.assertTrue("snippet loaded file not found in list", existsFileWithLabel(fileJsonObj.getString("getLabel")));
			}
		} catch (Exception ex) {
			fail(previewPage.getJson().getJsonObject("cause").getString("message"), ex);
		}
	}

	private boolean existsFileWithLabel(String label) {
		for (File entry : files) {
			if (label == null) {
				if (entry.getLabel() == null)
					return true;
				else
					continue;
			}
			if (entry.getLabel().equals(label)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public WebElement waitForResult(int timeout) {
		if(files == null){
			return super.waitForResult(timeout);
		}
		return waitForJsonList(files.size(), timeout);
	}

}
