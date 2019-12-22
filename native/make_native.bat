%JAVA_HOME%\bin\native-image --help
%JAVA_HOME%\bin\native-image --macro:truffle --no-fallback --initialize-at-build-time  -cp ..\language\target\hashem.jar;..\launcher\target\hashem-launcher.jar ninja.soroosh.hashem.lang.launcher.HashemiMain hashem-win

