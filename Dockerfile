FROM maven:3.8.6-eclipse-temurin-17-focal AS build
COPY src /home/app/budjetointi-harjoitustyo/src
COPY pom.xml /home/budjetointi-harjoitustyo/app
RUN mvn -f /home/app/budjetointi-harjoitustyo/pom.xml clean package
FROM eclipse-temurin:17-jre-focal
COPY --from=build /home/app/budjetointi-harjoitustyo/target/budjetointi-harjoitustyo-0.0.1-SNAPSHOT.jar /usr/local/lib/pkg.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/pkg.jar"]