FROM openjdk:8-jre
MAINTAINER Evgeniy Cheban <mister.cheban@gmail.com>

ADD ./target/user-service.jar /app/
RUN mkdir /app/images
CMD ["java", "-Xmx200m", "-jar", "/app/user-service.jar"]

EXPOSE 6000
