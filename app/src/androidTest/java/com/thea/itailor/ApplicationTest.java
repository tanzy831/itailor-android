package com.thea.itailor;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    public void testCreate() {
        createApplication();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        terminateApplication();
    }
}