FROM eclipse-temurin:17-jdk

# Установка Maven
RUN apt-get update && apt-get install -y maven

# Копируем проект
WORKDIR /app
COPY . .
COPY .mvn /app/.mvn

RUN mvn dependency:purge-local-repository
RUN mvn clean package -DskipTests -U --settings .mvn/settings.xml

# Сборка проекта
RUN mvn clean package -DskipTests

# Запуск jar-файла
CMD ["java", "-jar", "target/smartshine-1.0.0.jar"]
