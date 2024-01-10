FROM openjdk:8-jdk-alpine

# 指点的工作目录
WORKDIR /wisdom/userApplet

#将

#将jar 包添加到工作目录，比如 target/wisdom-admin-service-0.0.1-SNAPSHOT.jar
ADD target/wisdom-proprietor-server-1.0.jar .
# 暴露端口
EXPOSE 7777
# 启动命令
ENTRYPOINT ["java","-jar","wisdom-proprietor-server-1.0.jar","--spring.profites.active=prod"]
