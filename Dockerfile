FROM tomcat:alpine

CMD ["catalina.sh", "run"]

EXPOSE 8080
ENV TZ=Asia/Omsk
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY target/ccardApi-1.0.war /tmp/ccardApi.war
RUN mkdir -p /usr/local/tomcat/webapps/ccardApi && unzip /tmp/ccardApi.war -d /usr/local/tomcat/webapps/ccardApi



