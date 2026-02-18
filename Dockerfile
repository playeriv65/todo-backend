# 原生
FROM --platform=$BUILDPLATFORM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# 缓存依赖
COPY pom.xml .
RUN mvn dependency:go-offline

# 编译代码
COPY src ./src
RUN mvn clean package -DskipTests

# 第二阶段：生产运行 (精简镜像，适配多架构)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 从编译阶段复制生成的 jar 包
COPY --from=build /app/target/*.jar app.jar

# 暴露 Spring Boot 默认端口
EXPOSE 8080

# 启动指令
ENTRYPOINT ["java", "-jar", "app.jar"]