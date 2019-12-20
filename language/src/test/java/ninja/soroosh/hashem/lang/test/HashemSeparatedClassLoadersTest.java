
package ninja.soroosh.hashem.lang.test;

import com.oracle.truffle.api.Truffle;
import ninja.soroosh.hashem.lang.HashemLanguage;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;
import java.util.Map;
import org.graalvm.polyglot.Engine;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class HashemSeparatedClassLoadersTest {
    private ClassLoader loader;

    @Before
    public void storeLoader() {
        loader = Thread.currentThread().getContextClassLoader();
    }

    @Test
    public void sdkAndTruffleLanguageAPIAndSLInSeparateClassLoaders() throws Exception {
        final ProtectionDomain sdkDomain = Engine.class.getProtectionDomain();
        Assume.assumeNotNull(sdkDomain);
        Assume.assumeNotNull(sdkDomain.getCodeSource());
        URL sdkURL = sdkDomain.getCodeSource().getLocation();
        Assume.assumeNotNull(sdkURL);

        URL truffleURL = Truffle.class.getProtectionDomain().getCodeSource().getLocation();
        Assume.assumeNotNull(truffleURL);

        URL slURL = HashemLanguage.class.getProtectionDomain().getCodeSource().getLocation();
        Assume.assumeNotNull(slURL);

        ClassLoader parent = Engine.class.getClassLoader().getParent();

        URLClassLoader sdkLoader = new URLClassLoader(new URL[]{sdkURL}, parent);
        boolean sdkLoaderLoadsTruffleLanguage;
        try {
            Class.forName("com.oracle.truffle.api.TruffleLanguage", false, sdkLoader);
            sdkLoaderLoadsTruffleLanguage = true;
        } catch (ClassNotFoundException cnf) {
            sdkLoaderLoadsTruffleLanguage = false;
        }
        Assume.assumeFalse(sdkLoaderLoadsTruffleLanguage);
        URLClassLoader truffleLoader = new URLClassLoader(new URL[]{truffleURL}, sdkLoader);
        URLClassLoader slLoader = new URLClassLoader(new URL[]{slURL}, truffleLoader);
        Thread.currentThread().setContextClassLoader(slLoader);

        Class<?> engineClass = sdkLoader.loadClass(Engine.class.getName());
        Object engine = engineClass.getMethod("create").invoke(null);
        assertNotNull("Engine has been created", engine);

        Map<?, ?> languages = (Map<?, ?>) engineClass.getMethod("getLanguages").invoke(engine);
        Object lang = languages.get("hashemi");
        assertNotNull("SL language found: " + languages, lang);
    }

    @After
    public void resetLoader() {
        Thread.currentThread().setContextClassLoader(loader);
    }
}
