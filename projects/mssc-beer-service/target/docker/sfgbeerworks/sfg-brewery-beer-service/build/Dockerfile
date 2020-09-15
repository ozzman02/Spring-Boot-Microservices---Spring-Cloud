FROM openjdk:11-jre-slim as builder
WORKDIR application
ADD maven/sfg-brewery-beer-service-1.0.17-SNAPSHOT.jar sfg-brewery-beer-service-1.0.17-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar sfg-brewery-beer-service-1.0.17-SNAPSHOT.jar extract

FROM openjdk:11-jre-slim
LABEL PROJECT_NAME=sfg-brewery-beer-service \
      PROJECT=com.github.sfg-beer-works:sfg-brewery-beer-service:jar:1.0.17-SNAPSHOT

EXPOSE 8080

WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
COPY truststore ./
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]
