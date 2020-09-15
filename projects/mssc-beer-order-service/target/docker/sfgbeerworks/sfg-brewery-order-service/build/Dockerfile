FROM openjdk:11-jre-slim as builder
WORKDIR application
ADD maven/sfg-brewery-order-service-1.0.3-SNAPSHOT.jar sfg-brewery-order-service-1.0.3-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar sfg-brewery-order-service-1.0.3-SNAPSHOT.jar extract

FROM openjdk:11-jre-slim
LABEL PROJECT_NAME=sfg-brewery-order-service \
      PROJECT=com.github.sfg-beer-works:sfg-brewery-order-service:jar:1.0.3-SNAPSHOT

EXPOSE 8080

WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]
