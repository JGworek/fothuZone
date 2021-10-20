FROM ghcr.io/graalvm/graalvm-ce:java8
ENV SPRING_DATASOURCE_URL="jdbc:postgresql://database-1.czjizrnwviub.us-east-1.rds.amazonaws.com:5432/zone"
ENV SPRING_DATASOURCE_USERNAME="postgres"
ENV SPRING_DATASOURCE_PASSWORD="Mitsukichan1211"
COPY /target/FothuZoneService-0.0.1-SNAPSHOT.jar fothuZone.jar
EXPOSE 6969
CMD ["nohup", "java", "-jar", "fothuZone.jar", "&"]
