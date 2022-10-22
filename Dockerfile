FROM java:8
VOLUME /tmp
ADD target/springbootdemo-0.0.1-SNAPSHOT.jar springbootdemo.jar
ENTRYPOINT ["java","-jar","/springbootdemo.jar"]