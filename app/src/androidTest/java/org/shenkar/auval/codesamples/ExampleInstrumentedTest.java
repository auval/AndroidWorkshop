package org.shenkar.auval.codesamples;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mta.sharedutils.Util;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

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
