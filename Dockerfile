
FROM maven:3.9-amazoncorretto-25 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM public.ecr.aws/amazoncorretto/amazoncorretto:25 AS runner
WORKDIR /app
ENV PORT=8080

COPY --from=builder /app/target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]