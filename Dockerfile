FROM openjdk:21
WORKDIR /java
COPY target/bookmanager-0.0.1-SNAPSHOT.jar .
RUN groupadd dev && \
    useradd -m -g dev -s /bin/bash gustavo && \
    mkdir -p /home/gustavo/exports && \
    chown -R gustavo:dev /home/gustavo
USER gustavo
EXPOSE 8080
CMD ["java", "-jar", "bookmanager-0.0.1-SNAPSHOT.jar"]
