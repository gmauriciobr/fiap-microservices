# Build
FROM gradle:7.4.2-jdk11 AS build
COPY under-vendas /opt/app
WORKDIR /opt/app
RUN gradle -Dskip.tests clean bootJar

# Package
FROM openjdk:11
COPY --from=build /opt/app/build/libs/vendas.jar /opt/app/vendas.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opt/app/vendas.jar"]