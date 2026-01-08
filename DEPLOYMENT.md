# 🚀 部署指南

## 📋 部署前准备

### 环境要求
- **JDK**: Java 17 或更高版本
- **数据库**: MySQL 8.0 或更高版本
- **构建工具**: Maven 3.6 或更高版本
- **Git**: 用于代码管理

## 🔧 本地开发环境搭建

### 1. 克隆项目
```bash
git clone https://github.com/your-username/personal-accounting-system.git
cd personal-accounting-system
```

### 2. 数据库配置
```sql
-- 创建数据库
CREATE DATABASE billdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 导入数据库结构和初始数据
mysql -u root -p billdb < billdb_utf8.sql
```

### 3. 环境变量配置
```bash
# 复制环境变量模板
cp .env.example .env

# 编辑 .env 文件，填入实际的数据库配置
DB_PASSWORD=your_actual_password
```

### 4. 运行项目
```bash
# 清理编译
mvn clean compile

# 运行项目
mvn spring-boot:run

# 或者打包运行
mvn clean package
java -jar target/demo1-0.0.1-SNAPSHOT.jar
```

### 5. 访问系统
- 系统首页: http://localhost:8089
- 用户注册: http://localhost:8089/user/toRegister
- 用户登录: http://localhost:8089/user/toLogin

## 🌐 生产环境部署

### 1. 服务器环境准备
```bash
# 安装 JDK 17
sudo apt update
sudo apt install openjdk-17-jdk

# 安装 MySQL
sudo apt install mysql-server

# 安装 Maven (可选，也可以使用项目自带的 mvnw)
sudo apt install maven
```

### 2. 数据库配置
```sql
-- 创建生产数据库
CREATE DATABASE billdb_prod CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建专用数据库用户
CREATE USER 'billdb_user'@'localhost' IDENTIFIED BY 'strong_password';
GRANT ALL PRIVILEGES ON billdb_prod.* TO 'billdb_user'@'localhost';
FLUSH PRIVILEGES;

-- 导入数据库结构
mysql -u billdb_user -p billdb_prod < billdb_utf8.sql
```

### 3. 应用配置
创建生产环境配置文件 `application-prod.yml`:
```yaml
server:
  port: 8089

spring:
  application:
    name: personal-accounting-system
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/billdb_prod?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=Asia/Shanghai
    username: billdb_user
    password: ${DB_PASSWORD}
  thymeleaf:
    cache: true

mybatis:
  mapper-locations: classpath:mapper/*.xml
  configuration:
    map-underscore-to-camel-case: true
  type-aliases-package: com.example.demo.entity

logging:
  level:
    com.example.*: info
    root: warn
  file:
    name: logs/application.log
```

### 4. 构建和部署
```bash
# 克隆项目
git clone https://github.com/your-username/personal-accounting-system.git
cd personal-accounting-system

# 设置环境变量
export DB_PASSWORD=your_production_password
export SPRING_PROFILES_ACTIVE=prod

# 构建项目
mvn clean package -DskipTests

# 运行应用
nohup java -jar target/demo1-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod > logs/app.log 2>&1 &
```

### 5. 使用 systemd 管理服务 (推荐)
创建服务文件 `/etc/systemd/system/accounting-system.service`:
```ini
[Unit]
Description=Personal Accounting System
After=network.target

[Service]
Type=simple
User=ubuntu
WorkingDirectory=/opt/accounting-system
ExecStart=/usr/bin/java -jar /opt/accounting-system/demo1-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
Environment=DB_PASSWORD=your_production_password
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

启动服务:
```bash
sudo systemctl daemon-reload
sudo systemctl enable accounting-system
sudo systemctl start accounting-system
sudo systemctl status accounting-system
```

## 🐳 Docker 部署

### 1. 创建 Dockerfile
```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/demo1-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8089

CMD ["java", "-jar", "app.jar"]
```

### 2. 创建 docker-compose.yml
```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8089:8089"
    environment:
      - DB_PASSWORD=${DB_PASSWORD}
      - SPRING_PROFILES_ACTIVE=prod
    depends_on:
      - mysql
    networks:
      - accounting-network

  mysql:
    image: mysql:8.0
    environment:
      - MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}
      - MYSQL_DATABASE=billdb
      - MYSQL_USER=billdb_user
      - MYSQL_PASSWORD=${DB_PASSWORD}
    volumes:
      - mysql_data:/var/lib/mysql
      - ./billdb_utf8.sql:/docker-entrypoint-initdb.d/init.sql
    networks:
      - accounting-network

volumes:
  mysql_data:

networks:
  accounting-network:
    driver: bridge
```

### 3. 部署命令
```bash
# 构建项目
mvn clean package -DskipTests

# 使用 Docker Compose 部署
docker-compose up -d
```

## 🔒 安全配置

### 1. 数据库安全
- 使用强密码
- 限制数据库用户权限
- 定期备份数据库

### 2. 应用安全
- 使用 HTTPS (配置 SSL 证书)
- 设置防火墙规则
- 定期更新依赖包

### 3. 服务器安全
- 定期更新系统
- 配置 fail2ban
- 使用非 root 用户运行应用

## 📊 监控和维护

### 1. 日志监控
```bash
# 查看应用日志
tail -f logs/application.log

# 查看系统服务日志
sudo journalctl -u accounting-system -f
```

### 2. 性能监控
- 使用 Spring Boot Actuator
- 配置 Prometheus + Grafana
- 监控数据库性能

### 3. 备份策略
```bash
# 数据库备份脚本
#!/bin/bash
DATE=$(date +%Y%m%d_%H%M%S)
mysqldump -u billdb_user -p billdb_prod > backup_$DATE.sql
```

## 🚨 故障排除

### 常见问题
1. **端口被占用**: 检查端口 8089 是否被其他程序占用
2. **数据库连接失败**: 检查数据库服务状态和连接配置
3. **内存不足**: 调整 JVM 内存参数 `-Xmx512m`
4. **权限问题**: 确保应用有读写日志文件的权限

### 日志分析
```bash
# 查看错误日志
grep -i error logs/application.log

# 查看数据库连接日志
grep -i "database\|connection" logs/application.log
```

## 📞 技术支持

如遇到部署问题，请：
1. 查看项目 Issues
2. 检查日志文件
3. 参考故障排除指南
4. 联系技术支持

---

**部署成功后，记得修改默认配置和密码！**