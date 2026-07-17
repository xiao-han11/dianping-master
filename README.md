# 生活优选——本地生活点评与推荐平台

## 项目简介

生活优选是一个基于 SpringBoot 开发的前后端分离的本地生活点评与推荐平台，功能类似于大众点评。项目使用 Redis 集群、Tomcat 集群、MySQL 集群提升服务性能，实现了短信登录、商户查询缓存、优惠券秒杀、附近的商户、UV 统计、用户签到、好友关注、达人探店等核心功能。

## 技术栈

- **后端框架**: SpringBoot 2.3.12
- **数据库**: MySQL 8.0
- **缓存**: Redis（Lettuce + Redisson）
- **消息队列**: RabbitMQ
- **ORM**: MyBatis-Plus
- **工具库**: Hutool、Lombok
- **构建工具**: Maven
- **语言**: Java 8

## 功能模块

### 1. 用户登录与权限管理
- 基于 Redis 的 Session 共享，解决集群模式下的登录状态同步问题
- 双层拦截器设计：全局 Token 刷新 + 路径级登录校验

### 2. 商户查询与缓存
- 基于 Cache Aside 模式，保证数据库与缓存的一致性
- 使用 Redis 缓存高频访问数据，降低数据库查询压力
- 解决缓存穿透、缓存雪崩、缓存击穿问题

### 3. 优惠券秒杀
- 基于 Redis + Lua 脚本实现秒杀资格原子性预检
- 使用乐观锁解决秒杀超卖问题
- 基于消息队列实现异步下单，提升系统吞吐量

### 4. 分布式锁
- 基于 Redisson 实现分布式锁，解决集群模式下一人一单的线程安全问题
- 支持可重入、可重试、Watch Dog 自动续期

### 5. 附近的商户
- 使用 Redis GEO 数据结构存储商户地理坐标
- 支持按距离范围搜索和排序

### 6. 达人探店与点赞
- 使用 Redis ZSet 数据结构实现点赞排行榜
- 使用 Set 集合实现关注、共同关注功能

### 7. 用户签到
- 基于 Redis BitMap 实现用户签到记录
- 支持连续签到天数统计

### 8. UV 统计
- 基于 Redis HyperLogLog 实现独立访客统计
- 内存占用极低，适合高并发场景

## 项目结构

```
src/
├── main/java/com/hmdp/
│   ├── config/          # 配置类（MVC、MyBatis、Redisson、RabbitMQ）
│   ├── controller/      # 控制器层
│   ├── dto/             # 数据传输对象
│   ├── entity/          # 实体类
│   ├── interceptor/     # 拦截器（登录校验、Token刷新）
│   ├── listener/        # 消息队列监听器
│   ├── mapper/          # 数据访问层
│   ├── service/         # 业务逻辑层
│   └── utils/           # 工具类
├── main/resources/
│   ├── application.yaml # 应用配置
│   ├── db/hmdp.sql      # 数据库初始化脚本
│   ├── mapper/          # MyBatis XML 映射文件
│   ├── seckill.lua      # 秒杀 Lua 脚本
│   └── unLock.lua       # 分布式锁释放 Lua 脚本
└── test/                # 测试代码
```

## 快速开始

### 环境要求
- JDK 1.8+
- MySQL 8.0+
- Redis 6.0+
- RabbitMQ 3.8+
- Maven 3.6+

### 配置与运行

1. 克隆项目，修改 `application.yaml` 中的数据库、Redis、RabbitMQ 连接信息
2. 执行 `src/main/resources/db/hmdp.sql` 初始化数据库
3. 启动项目：`mvn spring-boot:run`
4. 访问 http://localhost:8081

## License

本项目仅用于学习交流。
