FROM ghcr.io/graalvm/graalvm-ce:java8
COPY /target/FothuZoneService-0.0.1-SNAPSHOT.jar fothuZone.jar
EXPOSE 6969
CMD ["nohup", "java", "-jar", "fothuZone.jar", "&"]
