FROM openjdk:21-slim AS build

WORKDIR /app

COPY . .

RUN ./mvnw package -DskipTests

FROM build AS production

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8765

CMD ["java", "-jar", "app.jar"]