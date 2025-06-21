FROM eclipse-temurin:17-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app
COPY . /app

WORKDIR /app
RUN mvn clean package -DskipTests

CMD ["java", "-jar", "/app/main/target/*.jar"]
