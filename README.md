# Running Jetty servlet over GraalJS on stock JDK11+

This is a simple maven project that demonstrates how it's possible to run [Jetty](https://www.eclipse.org/jetty/)
with Servlets over [GraalJS](http://www.graalvm.org/docs/reference-manual/languages/js/) on a stock JDK11.

JS implementation contains a few files:
 - `app.js` where jetty server is created.
 - `DemoServlet.js` where Servlet is defined.
 - `Loaded.js` for an example with `load`.

## Pre requirements

- Linux or Mac OS
- [Maven](https://maven.apache.org)
- [JDK11](https://jdk.java.net/11/)

## Setup

- Clone this repository
```
git clone https://github.com/catap/graal-js-jdk11-jetty
```

- Move to the newly cloned directory
```
cd graal-js-jdk11-jetty
```

- Make sure that JAVA_HOME is pointed at a JDK11+
```
export JAVA_HOME=$(/usr/libexec/java_home -v 11) # on macOS
export JAVA_HOME=/path/to/jdk11 # on Linux
```

- Package the project using Maven
```
mvn package
```

## Execution

Run over maven
```
mvn exec:exec
```

or by hands:
```
cd src/main/js
java -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -jar ../../../target/graal-js-jdk11-jetty-1.0-SNAPSHOT-jar-with-dependencies.jar app.js
```

or as docker container
```
docker build -t graal-js-jdk11-jetty .
docker run --rm graal-js-jdk11-jetty
```
