FROM java:8

WORKDIR /home/ubuntu/app

COPY target/meepo.jar /home/ubuntu/app

CMD ["java","-Dspring.profiles.active=dev", "-Duser.timezone=Asia/Shanghai","-Dfile.encoding=utf8", "-Xms64m","-Xmx125m","-jar","meepo.jar"]