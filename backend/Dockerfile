FROM maven:3.8.5-openjdk-17-slim as build
COPY ./src /home/app/src
COPY ./pom.xml /home/app/pom.xml
RUN mvn -DskipTests=true -f /home/app/pom.xml clean package

FROM openjdk:17-jdk-slim
COPY --from=build /home/app/target/jjmp-0.0.1.jar /home/app/app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "/home/app/app.jar"]