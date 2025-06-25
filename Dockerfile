FROM eclipse-temurin:17-jdk-alpine

# Установка Maven
RUN apk add --no-cache maven

# Копируем проект
WORKDIR /app
COPY . .

RUN mvn dependency:purge-local-repository

# Сборка проекта
RUN mvn clean package -DskipTests

# Запуск jar-файла
CMD ["java", "-jar", "target/smartshine-1.0.0.jar"]
