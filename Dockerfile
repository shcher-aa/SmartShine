FROM eclipse-temurin:17-jdk-alpine

# Установка Maven
RUN apk add --no-cache maven

# Копируем проект
COPY . /app
WORKDIR /app

# Сборка проекта
RUN mvn clean package -DskipTests

# Запуск .jar — найдём его автоматически
CMD ["sh", "-c", "java -jar target/*.jar"]
