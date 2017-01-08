package org.shenkar.auval.codesamples;

import com.mta.sharedutils.Util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class) // << work around a bug
public class ExampleUnitTest {

    /**
     * demonstrate how a passed test looks
     * @throws Exception
     */
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    /**
     * demonstrate how a failed test looks
     * @throws Exception
     */
    @Test
    public void addition_isWrong() throws Exception {
        assertEquals(4, 2 + 3);
    }

    /**
     * example of how to test some logic from my main code
     * (which is generic java)
     *
     * @throws Exception
     */
    @Test
    public void testStringBuilderPoolMethod() throws Exception {

        // with release
        for (int i = 0; i < 50; i++) {
            StringBuilder stringBuilder = Util.acquireStringBuilder();
            assertNotNull(stringBuilder);
            stringBuilder.append("234sdf");
            assertEquals(stringBuilder.length(), 6);
            Util.releaseStringBuilder(stringBuilder);
        }

        // without release - we will outgrow the pool size
        for (int i = 0; i < 50; i++) {
            StringBuilder stringBuilder = Util.acquireStringBuilder();
            assertNotNull(stringBuilder);
            stringBuilder.append("234sdf");
            assertEquals(stringBuilder.length(), 6);
            // commented out >> Util.releaseStringBuilder(stringBuilder);
        }
        // don't worry about not releasing the StringBuilder: the test does not affect anything external...

    }


}