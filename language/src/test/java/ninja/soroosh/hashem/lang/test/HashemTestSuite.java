package ninja.soroosh.hashem.lang.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HashemTestSuite {

    /**
     * Defines the base path of the test suite. Multiple base paths can be specified. However only
     * the first base that exists is used to lookup the test cases.
     */
    String[] value();

    /**
     * A class in the same project (or .jar file) that contains the {@link #value test case
     * directory}. If the property is not specified, the class that declares the annotation is used,
     * i.e., the test cases must be in the same project as the test class.
     */
    Class<?> testCaseDirectory() default HashemTestSuite.class;
}
