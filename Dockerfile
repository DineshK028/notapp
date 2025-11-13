# --- Build Stage: compile and package WAR ---
FROM maven:3.9.1-openjdk-17 AS build
WORKDIR /build
COPY notapp/ ./notapp/
RUN mvn -f notapp/pom.xml clean package -DskipTests

# --- Run Stage: deploy WAR on Tomcat 10 ---
FROM tomcat:10.1-jdk17-temurin
WORKDIR /usr/local/tomcat/webapps/
COPY --from=build /build/notapp/target/*.war ./notapp.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
