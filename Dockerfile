FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

COPY pom.xml pom.xml
COPY src src/
COPY mvnw mvnw
COPY .mvn .mvn

RUN chmod +x ./mvnw && ./mvnw clean package -DskipTests -DskipITs

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=builder /app/target/med-assurance-0.0.1-SNAPSHOT.jar ./med-assurance-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "med-assurance-0.0.1-SNAPSHOT.jar"]