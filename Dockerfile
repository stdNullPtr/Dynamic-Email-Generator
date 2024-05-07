# TODO agree on a stable version
FROM eclipse-temurin:latest

WORKDIR /app

COPY target/email-generator.jar /app/app.jar

# Create run user and set ownership to /app
RUN groupadd -g 1001 javauser && useradd --home-dir /app --uid 1001 --gid 1001 --shell /bin/bash javauser
RUN chown -R javauser:javauser /app
USER javauser

ENTRYPOINT ["java","-jar","/app/app.jar"]
