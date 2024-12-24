FROM openjdk:17-jdk-slim
VOLUME /tmp
ADD target/k8sdemo.jar k8sdemo.jar
ENTRYPOINT ["java","-jar","/k8sdemo.jar"]

# 之后执行docker build -t k8sdemo:1.0 .