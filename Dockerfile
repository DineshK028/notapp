# Use Maven with JDK 11 to build the WAR
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

# Use Tomcat to run the WAR
FROM tomcat:10.1-jdk11
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/noteapp.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
