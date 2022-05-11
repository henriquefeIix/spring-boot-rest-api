FROM maven:3.8-jdk-11 as maven
WORKDIR /usr/local/library
COPY pom.xml /usr/local/library
RUN mvn dependency:resolve dependency:resolve-plugins
COPY src /usr/local/library/src
RUN mvn package -Dmaven.test.skip

FROM tomcat:9.0-jdk11-openjdk-slim as tomcat
COPY --from=maven /usr/local/library/target/library.war /usr/local/tomcat/webapps
COPY ./wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENV TZ=America/Sao_Paulo
EXPOSE 8080
CMD ["catalina.sh", "run"]