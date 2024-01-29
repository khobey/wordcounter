#
# Build phase
#
FROM maven:3.8.6-openjdk-11 AS build
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Run
#
FROM openjdk:11
COPY --from=build /home/app/target/wordcounter-0.0.1-SNAPSHOT.jar /usr/local/lib/wordcounter-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/wordcounter-0.0.1-SNAPSHOT.jar"]