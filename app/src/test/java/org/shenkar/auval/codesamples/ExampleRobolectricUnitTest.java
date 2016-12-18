package org.shenkar.auval.codesamples;

import android.content.Context;

import com.mta.sharedutils.Util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants=BuildConfig.class, sdk=23) // << currently 23 is the maximum API robolectric supports
public class ExampleRobolectricUnitTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = RuntimeEnvironment.application;

        assertEquals("org.shenkar.auval.codesamples", appContext.getPackageName());
    }

    /**
     * example of how to test some logic from my main code
     *
     * @throws Exception
     */
    @Test
    public void testMyThreadUtil() throws Exception {

        // my method is only valid with Android API's, since it uses Looper
        @Util.THREAD_TYPE int threadType = Util.getThreadType();

        // tests are running on a main thread
        assertEquals(threadType, Util.TH_UI);
    }


}