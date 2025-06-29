FROM eclipse-temurin:17-jdk

# Установка Maven
RUN apt-get update && apt-get install -y maven ca-certificates

# Копируем проект
WORKDIR /app
COPY . .
COPY .mvn /app/.mvn

RUN ls -R /app/src

# Очистим весь кэш Maven, чтобы не мешал
RUN rm -rf /root/.m2

# Удалим только локальные зависимости
RUN mvn dependency:purge-local-repository

# Пакуем проект
RUN mvn clean package -DskipTests -X

# Запуск jar-файла
CMD ["java", "-jar", "target/smartshine-1.0.0.jar"]
