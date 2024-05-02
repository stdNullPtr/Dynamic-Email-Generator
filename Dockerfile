# TODO agree on a stable version
FROM eclipse-temurin:21.0.3_9-jre

VOLUME /tmp

EXPOSE 8080

COPY target/*.jar /app/app.jar

# Create run user and set ownership to /app
RUN groupadd -g 1001 javauser && useradd --home-dir /app --uid 1001 --gid 1001 --shell /bin/bash javauser
RUN chown -R javauser:javauser /app
USER javauser

ENTRYPOINT ["java","-jar","/app/app.jar"]
