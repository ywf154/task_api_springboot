FROM openjdk:17-jdk

COPY . /app
WORKDIR /app

# 使用 Spring Boot Maven 插件启动应用程序
ENTRYPOINT ["mvn", "spring-boot:run"]