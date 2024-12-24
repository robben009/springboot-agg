FROM openjdk:17-jdk-slim
VOLUME /tmp
ADD target/k8sdemo2.jar k8sdemo2.jar
ENTRYPOINT ["java","-jar","/k8sdemo2.jar"]

# 之后执行docker build -t k8sdemo2:1.0 .