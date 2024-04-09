FROM eclipse-temurin:11-jre-focal as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar

RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:11-jre-focal
COPY --from=builder dependencies/ ./
RUN true
COPY --from=builder spring-boot-loader/ ./
RUN true
COPY --from=builder snapshot-dependencies/ ./
RUN true
COPY --from=builder application/ ./
RUN true

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher", "--spring.config.location=file:/data/mcs/reto-tecnico-max-leon/reto-tecnico-max-leon-dev.yml"]
