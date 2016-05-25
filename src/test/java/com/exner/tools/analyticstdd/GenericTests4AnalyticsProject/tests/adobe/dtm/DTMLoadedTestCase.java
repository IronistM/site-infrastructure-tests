package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.Tools;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.WebDriverBasedTestCase;

public class DTMLoadedTestCase extends WebDriverBasedTestCase {
	
	public DTMLoadedTestCase(String pageURL) {
		super(pageURL);
		setName(Tools.DTM + " loaded - " + pageURL);
	}

	@Override
	protected void runTest() throws Throwable {
		// check whether DTM has been loaded on the page
		Object response = _jsExecutor
				.executeScript("if (typeof _satellite !== 'undefined') { return true } else { return false }");

		// make sure the element exists
		if (Boolean.class.isAssignableFrom(response.getClass())) {
			assertTrue(Tools.DTM + " must load", (Boolean) response);
		} else {
			fail(Tools.DTM + " not loaded");
		}

	}

}
