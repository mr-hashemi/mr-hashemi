
package ninja.soroosh.hashem.lang.launcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public final class HashemiMain {

    private static final String HASHEMI = "hashemi";

    /**
     * The main entry point.
     */
    public static void main(String[] args) throws IOException {
        Source source;
        Map<String, String> options = new HashMap<>();
        String file = null;
        for (String arg : args) {
            if (parseOption(options, arg)) {
                continue;
            } else {
                if (file == null) {
                    file = arg;
                }
            }
        }

        if (file == null) {
            // @formatter:off
            source = Source.newBuilder(HASHEMI, new InputStreamReader(System.in), "<stdin>").build();
            // @formatter:on
        } else {
            source = Source.newBuilder(HASHEMI, new File(file)).build();
        }

        System.exit(executeSource(source, System.in, System.out, options));
    }

    private static int executeSource(Source source, InputStream in, PrintStream out, Map<String, String> options) {
        Context context;
        PrintStream err = System.err;
        try {
            context = Context.newBuilder(HASHEMI).in(in).out(out).options(options).build();
        } catch (IllegalArgumentException e) {
            err.println(e.getMessage());
            return 1;
        }

        try {
            Value result = context.eval(source);
            if (context.getBindings(HASHEMI).getMember("main") == null) {
                err.println("No bebin main() defined in Hashemi source file.");
                return 1;
            }
            if (!result.isNull()) {
                out.println(result.toString());
            }
            return 0;
        } catch (PolyglotException ex) {
            if (ex.isInternalError()) {
                // for internal errors we print the full stack trace
                ex.printStackTrace();
            } else {
                err.println(ex.getMessage());
            }
            return 1;
        } finally {
            context.close();
        }
    }

    private static boolean parseOption(Map<String, String> options, String arg) {
        if (arg.length() <= 2 || !arg.startsWith("--")) {
            return false;
        }
        int eqIdx = arg.indexOf('=');
        String key;
        String value;
        if (eqIdx < 0) {
            key = arg.substring(2);
            value = null;
        } else {
            key = arg.substring(2, eqIdx);
            value = arg.substring(eqIdx + 1);
        }

        if (value == null) {
            value = "true";
        }
        int index = key.indexOf('.');
        String group = key;
        if (index >= 0) {
            group = group.substring(0, index);
        }
        options.put(key, value);
        return true;
    }

}
