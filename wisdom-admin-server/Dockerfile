# 使用 OpenJDK 8 的 Alpine 版本作为基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录为 /app
WORKDIR /wisdom/admin

# 复制构建目录中的 JAR 文件到工作目录中
ADD target/wisdom-admin-server-1.0.jar .

# 暴露端口（如果你的应用程序需要监听端口的话）
EXPOSE 8989

# 指定启动命令  "wisdom-admin-server-1.0.jar"  也就是打包后target目录下的 jar 的名字
# "--spring.profiles.active=prod" 是指定运行的 application-prod.yml 配置文件后续会讲到
ENTRYPOINT ["java", "-jar", "wisdom-admin-server-1.0.jar", "--spring.profiles.active=prod"]