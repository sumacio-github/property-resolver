FROM openjdk:14-alpine

COPY property-resolver/target/property-resolver-*.jar /opt/java/property-resolver.jar

ENTRYPOINT ["jshell", "--class-path", "/opt/java/property-resolver.jar", "--enable-preview"]
