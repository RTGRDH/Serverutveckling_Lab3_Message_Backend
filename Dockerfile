FROM openjdk:8
COPY target/demo-*.jar /backend.jar
CMD ["java", "-jar", "/backend.jar"]