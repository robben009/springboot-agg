FROM openjdk:17-jdk-slim
VOLUME /tmp
ADD target/k8sDemo-0.0.1-SNAPSHOT.jar k8sDemo2.jar
ENTRYPOINT ["java","-jar","/k8sDemo2.jar"]

# 之后执行docker build -t my-node-app .