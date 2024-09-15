FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY build/libs/compliance-tracker-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
RUN addgroup --system apprunner && adduser -S -s /bin/false -G apprunner apprunner
RUN chown -R apprunner:apprunner /app.jar
RUN chown -R apprunner:apprunner /tmp
USER apprunner
ENTRYPOINT ["java", "-jar", "app.jar"]