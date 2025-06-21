FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY . /app

RUN ./mvnw package -DskipTests

CMD ["java", "-jar", "target/*.jar"]
