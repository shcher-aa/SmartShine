FROM eclipse-temurin:17-jdk-alpine

# Установим Maven
RUN apk add --no-cache maven

WORKDIR /app
COPY . /app

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/*.jar"]
