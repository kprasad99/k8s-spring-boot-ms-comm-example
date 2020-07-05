FROM maven:3-jdk-11 as builder

COPY ./src /app-build/src
COPY ./pom.xml /app-build/

WORKDIR /app-build

RUN mvn clean package -DskipTests && \
    java -Djarmode=layertools -jar target/*.jar extract --destination /app


FROM alpine:latest as jreBuilder

RUN apk --no-cache add openjdk11-jdk openjdk11-jmods

ENV JAVA_MINIMAL="/opt/java-minimal"

# build minimal JRE
RUN /usr/lib/jvm/java-11-openjdk/bin/jlink \
    --verbose \
    --add-modules \
        java.base,java.desktop,java.instrument,\
java.management,java.management.rmi,java.naming,java.net.http,java.prefs,\
java.sql,java.security.jgss,java.security.sasl,java.sql,jdk.management.agent,jdk.management,\
jdk.crypto.ec,jdk.unsupported \
    --compress 2 --strip-debug --no-header-files --no-man-pages \
    --release-info="add:IMPLEMENTOR=Karthik:IMPLEMENTOR_VERSION=jre-11" \
    --output "$JAVA_MINIMAL"

FROM alpine:latest

ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk
ENV PATH="$PATH:$JAVA_HOME/bin"

COPY --from=jreBuilder /opt/java-minimal "$JAVA_HOME"

ARG DEPENDENCY=/app

WORKDIR /app
COPY --from=builder ${DEPENDENCY}/dependencies/ ./
COPY --from=builder ${DEPENDENCY}/spring-boot-loader/ ./
COPY --from=builder ${DEPENDENCY}/snapshot-dependencies/ ./
COPY --from=builder ${DEPENDENCY}/application/ ./

ENV JAVA_TOOLS_OPTS=$JAVA_TOOLS_OPTS

VOLUME /tmp
	
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]