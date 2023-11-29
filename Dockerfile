#Examples:
# docker build -t firstapp .
# docker run -t -i --rm -p 8888:8888 firstapp

FROM adoptopenjdk:11-jre-hotspot

ENV FAT_JAR firstapp-1.0.0-SNAPSHOT-fat.jar
ENV APP_HOME /usr/app

EXPOSE 8888

COPY target/$FAT_JAR $APP_HOME/

WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $FAT_JAR"]
