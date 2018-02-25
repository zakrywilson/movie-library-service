FROM openjdk:8-jdk-alpine
ARG JAR_FILE
ARG DIR=/usr/share/wilson/movie-library-service
ADD target/${JAR_FILE} ${DIR}/${JAR_FILE}
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/share/wilson/movie-library-service/movie-library-service.jar"]
