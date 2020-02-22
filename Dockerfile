FROM fedora:31
RUN set -x \
	&& yum install -y java-13-openjdk java-13-openjdk-devel \
	&& yum clean all

EXPOSE 12345

COPY target/graal-js-jdk11-jetty-1.0-SNAPSHOT-jar-with-dependencies.jar /graal-js-jdk11-jetty-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY src/main/js /js
WORKDIR /js

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+EnableJVMCI", "-jar", "/graal-js-jdk11-jetty-1.0-SNAPSHOT-jar-with-dependencies.jar", "app.js"]
