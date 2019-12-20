# The hashemi language native image

Truffle language implementations can be AOT compiled using the GraalVM
[native-image](https://www.graalvm.org/docs/reference-manual/aot-compilation/)
tool.  Running `mvn package` in the hashemilang folder also builds a
`hashemi` executable This executable is the full Simple Language
implementation as a native application, and has no need for a JVM to run.
