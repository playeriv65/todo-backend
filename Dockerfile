# 使用 Maven + JDK 21 镜像进行构建
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# 先只复制 pom.xml 以利用 Docker 缓存层，加快后续构建速度
COPY pom.xml .
RUN mvn dependency:go-offline

# 复制源码并打包
COPY src ./src
RUN mvn clean package -DskipTests

# 使用更小的 JRE 镜像，减少线上镜像体积
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 从编译阶段复制生成的 jar 包
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]