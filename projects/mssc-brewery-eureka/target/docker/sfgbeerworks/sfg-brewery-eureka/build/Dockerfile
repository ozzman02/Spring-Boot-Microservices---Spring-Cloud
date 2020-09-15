FROM openjdk:11-jre-slim as builder
WORKDIR application
ADD maven/sfg-brewery-eureka-0.0.4-SNAPSHOT.jar sfg-brewery-eureka-0.0.4-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar sfg-brewery-eureka-0.0.4-SNAPSHOT.jar extract

FROM openjdk:11-jre-slim
LABEL PROJECT_NAME=sfg-brewery-eureka \
      PROJECT=com.github.sfg-beer-works:sfg-brewery-eureka:jar:0.0.4-SNAPSHOT

EXPOSE 8080

WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]
