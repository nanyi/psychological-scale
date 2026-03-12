# Psychological Scale 后端服务

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21-blue)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-green)](https://spring.io/projects/spring-boot)

## 项目介绍

Psychological Scale 后端服务，基于 Spring Boot 3.2.2 + Java 21 构建，提供心理测评系统的核心业务能力，包括用户管理、量表管理、订单支付、第三方对接、报告生成和数据分析等功能。

## 技术架构

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.2.2 | 后端框架 |
| Java | 21 | 编程语言 |
| Spring Cloud | 2023.0.0 | 微服务治理 |
| MyBatis-Plus | 3.5.5 | ORM框架 |
| MySQL | 8.0 | 关系型数据库 |
| Redis | 7.0 | 缓存数据库 |

## 模块结构

```
backend/
├── ps-common/                  # 公共模块
│   ├── constant/              # 常量定义
│   ├── exception/             # 异常定义
│   ├── enums/                 # 枚举定义
│   ├── utils/                 # 工具类
│   └── config/                # 公共配置
├── ps-core/                   # 核心模块
│   ├── entity/                # 实体类
│   ├── dto/                   # 数据传输对象
│   └── enums/                 # 核心枚举
├── ps-security/               # 安全模块
│   ├── aspect/                # 安全切面
│   ├── annotation/            # 安全注解
│   ├── filter/               # 安全过滤器
│   └── service/               # 安全服务
├── ps-api/                   # API接口模块
│   ├── user/                  # 用户服务API
│   ├── scale/                 # 量表服务API
│   └── order/                 # 订单服务API
├── ps-user/                   # 用户服务
├── ps-scale/                  # 量表服务
├── ps-order/                  # 订单服务
├── ps-payment/                # 支付服务
├── ps-report/                 # 报告服务
├── ps-thirdparty/             # 第三方服务
└── ps-analysis/               # 分析服务
```

## 快速开始

### 环境要求

- JDK 21+
- Maven 3.8+
- MySQL 8.0+
- Redis 7.0+

### 构建项目

```bash
# 进入后端目录
cd backend

# 编译项目
mvn compile

# 构建打包（跳过测试）
mvn clean package -DskipTests

# 运行测试
mvn test
```

### 核心API

#### 用户模块
- `POST /api/user/register` - 用户注册
- `POST /api/user/login` - 用户登录
- `GET /api/user/info/{id}` - 获取用户信息

#### 量表模块
- `POST /api/scale/create` - 创建量表
- `GET /api/scale/list` - 量表列表
- `POST /api/exam/start` - 开始测评
- `POST /api/exam/submit/{recordId}` - 提交测评

#### 订单模块
- `POST /api/order/create` - 创建订单
- `POST /api/order/pay` - 支付订单
- `POST /api/order/refund` - 申请退款

#### 报告模块
- `POST /api/report/generate` - 生成报告
- `POST /api/report/export/word` - 导出Word
- `POST /api/report/export/pdf` - 导出PDF

详细API文档请参考 [API设计文档](../docs/design/api-design.md)。

## 开发规范

本项目遵循以下开发规范，详见 [AGENTS.md](../AGENTS.md)：

- Java代码规范（命名、注释、异常处理）
- 接口设计规范（RESTful、版本控制）
- 数据库设计规范（命名、审计字段）
- Git提交规范

## 文档

- [需求文档](../docs/requirements/) - 各模块需求说明
- [设计文档](../docs/design/) - 技术设计文档
- [实施计划](../docs/plans/) - 开发计划

## 许可证

本项目基于 MIT 许可证开源，详见 [LICENSE](LICENSE) 文件。

---

*Psychological Scale 后端服务 - 专业的心理测评解决方案*
