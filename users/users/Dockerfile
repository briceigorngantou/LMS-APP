FROM eclipse-temurin:21-jammy as base
#RUN addgroup demogroup; adduser --ingroup demogroup --disabled-password demo
#USER demo
# Stage 1: Build the application with JDK 21
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src


# Stage 2: Create a development-ready image
FROM base as development
CMD ["./mvnw", "spring-boot:run"]

FROM base as test
RUN ["./mvnw", "test"]

#Stage 3: building the application using maven
FROM base as build
RUN ./mvnw clean package -DskipTests

#stage 4: Create a production-ready image
FROM eclipse-temurin:21-jammy as production
COPY --from=build /app/target/users*.jar ./app.jar
CMD ["java", "-jar", "app.jar"]

