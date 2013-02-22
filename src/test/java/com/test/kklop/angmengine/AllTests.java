package com.test.kklop.angmengine;

import junit.framework.Test;
import junit.framework.TestSuite;
import android.test.suitebuilder.TestSuiteBuilder;

/**
 * 
 * @author Kevin Klopfenstein
 *
 */
public class AllTests extends TestSuite {
	public static Test suite() {
		return new TestSuiteBuilder(AllTests.class).includeAllPackagesUnderHere().build();
	}
}
