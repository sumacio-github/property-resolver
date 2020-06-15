FROM openjdk:14-alpine

COPY property-utils/target/property-utils-*-jar-with-dependencies.jar /opt/java/property-utils.jar

ENTRYPOINT ["jshell", "--class-path", "/opt/java/property-utils.jar", "--enable-preview"]
