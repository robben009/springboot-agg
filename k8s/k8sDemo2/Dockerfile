FROM openjdk:17-jdk-slim
VOLUME /tmp
ADD target/k8sDemo2-0.0.1-SNAPSHOT.jar k8sDemo.jar
ENTRYPOINT ["java","-jar","/k8sDemo.jar"]
