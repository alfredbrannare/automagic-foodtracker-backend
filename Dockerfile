FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests -B

FROM eclipse-temurin:25-jre

WORKDIR /app

RUN groupadd --system appgroup && \
    useradd --system --no-log-init -g appgroup appuser

COPY --from=builder /app/target/*.jar app.jar

RUN chown -R appuser:appgroup /app

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]