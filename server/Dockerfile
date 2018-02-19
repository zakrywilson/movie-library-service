FROM openjdk:8-jdk-alpine
ARG JAR_FILE
ARG DIR=/usr/share/wilson/dvd-library-service
ADD target/${JAR_FILE} ${DIR}/${JAR_FILE}
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/share/wilson/dvd-library-service/dvd-library-service.jar"]
#ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/wilson/dvd-library-service/dvd-library-service.jar"]
#ARG JAR_FILE
#ADD target/${JAR_FILE} /usr/share/wilson/dvd-library-service/wilson/dvd-library-service.jar
