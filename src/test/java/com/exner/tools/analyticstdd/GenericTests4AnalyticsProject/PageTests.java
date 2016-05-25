package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.DOMElementForSelectorExistenceTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.DataLayerElementDelayedExistenceTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.DataLayerElementDelayedValueTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.DataLayerElementExistenceTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.DataLayerElementValueTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.JQueryLoadedTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.JQueryVersionBelowTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.JQueryMinVersionTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.analytics.AnalyticsCodeHasLoadedTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.analytics.AnalyticsCodeLibTypeTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.analytics.AnalyticsCodeMinVersionTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.analytics.AnalyticsTagForReportSuiteFiredTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.core.VisitorIDServiceLoadedTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.core.VisitorIDServiceVersionBelowTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.core.VisitorIDServiceMinVersionTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm.DTMIsInDebugModeTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm.DTMLoadedTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm.DataElementDelayedValueTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm.DataElementValueTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm.EventBasedRuleExistenceTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm.EventBasedRuleHasRunTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm.PageLoadRuleExistenceTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.dtm.RuleHasRunTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.target.GlobalMboxExistenceTestCase;
import com.exner.tools.analyticstdd.GenericTests4AnalyticsProject.tests.adobe.target.MBoxJSLoadedTestCase;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

public class PageTests extends TestSuite {
	private final static Logger LOGGER = Logger.getLogger(PageTests.class.getName());

	public static Test suite(PageTestDefinition pageTestDefinition) {
		TestSuite suite = new TestSuite("PageTests " + pageTestDefinition.getPageURL());

		final String pageURL = pageTestDefinition.getPageURL();

		// test basic page environment
		for (Iterator<String> iterator = pageTestDefinition.getDataLayerElementsThatMustExist().iterator(); iterator
				.hasNext();) {
			String dataLayerElementName = (String) iterator.next();
			suite.addTest(new DataLayerElementExistenceTestCase(pageURL, dataLayerElementName));
		}
		for (Iterator<StringLongTuple> iterator = pageTestDefinition.getDataLayerElementsThatMustExistAfterDelay()
				.iterator(); iterator.hasNext();) {
			StringLongTuple element = (StringLongTuple) iterator.next();
			suite.addTest(new DataLayerElementDelayedExistenceTestCase(pageURL, element.getName(), element.getDelay()));
		}
		for (Iterator<Map<String, String>> iterator = pageTestDefinition.getDataLayerElementsThatMustHaveSpecificValue()
				.iterator(); iterator.hasNext();) {
			Map<String, String> element = (Map<String, String>) iterator.next();
			suite.addTest(new DataLayerElementValueTestCase(pageURL, element.get("name"), element.get("value")));
		}
		for (Iterator<StringStringLongTuple> iterator = pageTestDefinition
				.getDataLayerElementsThatMustHaveSpecificValueAfterDelay().iterator(); iterator.hasNext();) {
			StringStringLongTuple element = (StringStringLongTuple) iterator.next();
			suite.addTest(new DataLayerElementDelayedValueTestCase(pageURL, element.getName(), element.getValue(),
					element.getDelay()));
		}
		for (Iterator<String> iterator = pageTestDefinition.getElementsByDOMSelectorThatMustExist().iterator(); iterator
				.hasNext();) {
			suite.addTest(new DOMElementForSelectorExistenceTestCase(pageURL, iterator.next()));
		}

		// test page infrastructure (DTM, Analytics, Target, ... setup)
		if (pageTestDefinition.isjQueryLoaded()) {
			suite.addTest(new JQueryLoadedTestCase(pageURL));
		}
		String jQueryMinVersion = pageTestDefinition.getjQueryMinVersion();
		if (null != jQueryMinVersion && !jQueryMinVersion.isEmpty()) {
			suite.addTest(new JQueryMinVersionTestCase(pageURL, jQueryMinVersion));
		}
		String jQueryMaxVersion = pageTestDefinition.getjQueryVersionBefore();
		if (null != jQueryMaxVersion && !jQueryMaxVersion.isEmpty()) {
			suite.addTest(new JQueryVersionBelowTestCase(pageURL, jQueryMaxVersion));
		}
		if (pageTestDefinition.isVisitorIDServiceLoaded()) {
			suite.addTest(new VisitorIDServiceLoadedTestCase(pageURL));
		}
		String visitorIDServiceMinVersion = pageTestDefinition.getVisitorIDServiceMinVersion();
		if (null != visitorIDServiceMinVersion && !visitorIDServiceMinVersion.isEmpty()) {
			suite.addTest(new VisitorIDServiceMinVersionTestCase(pageURL, visitorIDServiceMinVersion));
		}
		String visitorIDServiceMaxVersion = pageTestDefinition.getVisitorIDServiceVersionBefore();
		if (null != visitorIDServiceMaxVersion && !visitorIDServiceMaxVersion.isEmpty()) {
			suite.addTest(new VisitorIDServiceVersionBelowTestCase(pageURL, visitorIDServiceMaxVersion));
		}
		if (pageTestDefinition.isDtmLoaded()) {
			suite.addTest(new DTMLoadedTestCase(pageURL));
		}
		if (pageTestDefinition.isDtmInDebugMode()) {
			suite.addTest(new DTMIsInDebugModeTestCase(pageURL));
		}
		if (pageTestDefinition.isTargetJSLoaded()) {
			suite.addTest(new MBoxJSLoadedTestCase(pageURL));
		}
		String globalMboxName = pageTestDefinition.getGlobalMboxName();
		if (null != globalMboxName && !globalMboxName.isEmpty()) {
			suite.addTest(new GlobalMboxExistenceTestCase(pageURL, globalMboxName));
		}
		if (pageTestDefinition.isAnalyticsJSLoaded()) {
			suite.addTest(new AnalyticsCodeHasLoadedTestCase(pageURL));
		}
		String libType = pageTestDefinition.getAnalyticsJSLibType();
		if (null != libType && !libType.isEmpty()) {
			suite.addTest(new AnalyticsCodeLibTypeTestCase(pageURL, libType));
		}
		String analyticsJSMinVersion = pageTestDefinition.getAnalyticsJSMinVersion();
		if (null != analyticsJSMinVersion && !analyticsJSMinVersion.isEmpty()) {
			suite.addTest(new AnalyticsCodeMinVersionTestCase(pageURL, analyticsJSMinVersion));
		}

		// test DTM-specific page basics
		for (Iterator<Map<String, String>> iterator = pageTestDefinition.getDataElementsThatMustHaveASpecificValue()
				.iterator(); iterator.hasNext();) {
			Map<String, String> element = (Map<String, String>) iterator.next();
			suite.addTest(new DataElementValueTestCase(pageURL, element.get("name"), element.get("value")));
		}
		for (Iterator<StringStringLongTuple> iterator = pageTestDefinition
				.getDataElementsThatMustHaveSpecificValueAfterDelay().iterator(); iterator.hasNext();) {
			StringStringLongTuple element = (StringStringLongTuple) iterator.next();
			suite.addTest(new DataElementDelayedValueTestCase(pageURL, element.getName(), element.getValue(),
					element.getDelay()));
		}
		for (Iterator<String> iterator = pageTestDefinition.getPageLoadRulesThatMustExist().iterator(); iterator
				.hasNext();) {
			String plr = (String) iterator.next();
			suite.addTest(new PageLoadRuleExistenceTestCase(pageURL, plr));
		}
		for (Iterator<String> iterator = pageTestDefinition.getEventBasedRulesThatMustExist().iterator(); iterator
				.hasNext();) {
			String rule = iterator.next();
			suite.addTest(new EventBasedRuleExistenceTestCase(pageURL, rule));
		}
		for (Iterator<String> iterator = pageTestDefinition.getPageLoadRulesThatMustHaveRun().iterator(); iterator
				.hasNext();) {
			String rule = (String) iterator.next();
			suite.addTest(new RuleHasRunTestCase(pageURL, rule));
		}

		// test page interactions
		for (Iterator<Map<String, String>> iterator = pageTestDefinition.getEventBasedRulesThatMustFire()
				.iterator(); iterator.hasNext();) {
			Map<String, String> test = iterator.next();
			suite.addTest(new EventBasedRuleHasRunTestCase(pageURL, test.get("name"), test.get("triggerType"),
					test.get("triggerElement")));
		}

		// test stuff for Analyst/Marketer
		List<String> rsids = pageTestDefinition.getReportSuiteIDsThatMustReceiveTags();
		for (Iterator<String> iterator = rsids.iterator(); iterator.hasNext();) {
			String rsid = iterator.next();
			suite.addTest(new AnalyticsTagForReportSuiteFiredTestCase(pageURL, rsid));
		}

		TestSetup ts = new TestSetup(suite) {
			protected void tearDown() throws Exception {
				LOGGER.log(Level.CONFIG, "Tearing down test for " + pageURL);
			}
		};

		return ts;
	}

}
