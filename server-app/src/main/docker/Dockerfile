FROM java:8
MAINTAINER Wei Yang <wei@weizilla.com>

VOLUME /tmp
ADD workout-logger-web-app.jar workout-logger-web-app.jar
RUN bash -c 'touch /workout-logger-web-app.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "workout-logger-web-app.jar", "--server.port=80"]
