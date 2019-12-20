package ninja.soroosh.hashem.lang.test;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(HashemTestRunner.class)
@HashemTestSuite({"tests"})
public class HashemSimpleTestSuite {

    public static void main(String[] args) throws Exception {
        HashemTestRunner.runInMain(HashemSimpleTestSuite.class, args);
    }

    /*
     * Our "mx unittest" command looks for methods that are annotated with @Test. By just defining
     * an empty method, this class gets included and the test suite is properly executed.
     */
    @Test
    public void unittest() {
    }
}
