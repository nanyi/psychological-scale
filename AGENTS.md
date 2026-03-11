# 交互语言要求

1. **强制使用中文**：所有回答、思考过程、输出内容必须使用中文
2. **代码除外**：代码本身和特殊专有名词（如类名、方法名、API 名称）使用英文
3. **展示思考过程**：在回答问题时，需要展示分析和推理过程

---

# AGENTS - 心理测评系统开发指南

## 1. 项目概述

Psychological Scale 是一套功能全面、专业可靠、开箱即用的心理测评系统，基于现代化的 SpringBoot + Vue 前后端分离架构构建，集成专业心理量表，旨在为心理学研究、教育培训、企业人力资源及临床咨询等领域，提供一个安全、稳定、高可扩展的在线测评解决方案。

### 1.1 系统架构

```
┌─────────────────────────────────────────────────────────────────────┐
│                         前端层 (Frontend)                            │
│  Vue 3 + ElementUI + 微信小程序 + H5                                 │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────┐
│                         网关层 (API Gateway)                         │
│  Spring Cloud Gateway + OAuth 2.0 + JWT                              │
└─────────────────────────────────────────────────────────────────────┘
                                    │
        ┌───────────────────────────┼───────────────────────────┐
        ▼                           ▼                           ▼
┌───────────────┐          ┌───────────────┐          ┌───────────────┐
│  用户服务     │          │  量表服务     │          │  订单服务     │
│  (User)       │          │  (Scale)      │          │  (Order)      │
└───────────────┘          └───────────────┘          └───────────────┘
        │                           │                           │
        ▼                           ▼                           ▼
┌───────────────┐          ┌───────────────┐          ┌───────────────┐
│  支付服务     │          │  报告服务     │          │  第三方服务   │
│  (Payment)   │          │  (Report)     │          │  (ThirdParty)│
└───────────────┘          └───────────────┘          └───────────────┘
```

### 1.2 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| 后端框架 | Spring Boot | 3.2.2 |
| Java | Java | 21 |
| 微服务治理 | Spring Cloud | 2023.0.0 |
| ORM | MyBatis-Plus | 3.5.5 |
| 前端框架 | Vue | 3.4 |
| UI 组件 | ElementUI | 2.5 |
| 移动端 | 微信小程序 | - |
| 数据库 | MySQL | 8.0 |
| 缓存 | Redis | 7.0 |
| 文档生成 | Apache POI | 5.2 |

### 1.3 功能模块

| 模块ID | 模块名称 | 说明 |
|--------|----------|------|
| MOD-001 | 用户与账户管理 | 用户认证、角色权限、分组管理 |
| MOD-002 | 量表库管理与测评执行 | 量表配置、题目管理、测评执行 |
| MOD-003 | 量表订单与支付 | 量表购买、支付、退款、企业配额 |
| MOD-004 | 第三方量表服务对接 | API对接、题目获取、报告回调 |
| MOD-005 | 数据分析 | 统计看板、群体分析、常模对比 |
| MOD-006 | 报告生成与导出 | 自动生成、Word/PDF导出 |

### 1.4 后端公共模块

| 模块 | 说明 |
|------|------|
| ps-common | 公共工具类、异常定义、枚举 |
| ps-core | 核心实体、DTO、VO |
| ps-security | 安全认证、权限控制 |
| ps-api | API接口定义 |

---

## 2. 代码规范

### Java (Backend)

- **Java 版本**: 21 (Spring Boot 3.2.2)
- **命名规范**:
  - 类/方法：CamelCase
  - 常量：UPPER_SNAKE_CASE
  - 包名：全小写
- **Lombok**: `@Data`, `@Slf4j`, `@RequiredArgsConstructor`
- **导入规则**:
  - 禁止通配符导入
  - 静态导入放最后
  - 按组组织（标准库、第三方、项目内部）
- **错误处理**:
  - 统一使用 `@ControllerAdvice` + `RestResult<T>` 封装
  - 记录详细日志，返回友好错误信息
- **API 设计**:
  - RESTful 风格，与前端交互url、参数使用蛇形命名风格
  - POST/PUT 使用 `@RequestBody`
- **测试**: JUnit 5 + Mockito
- **ORM**: MyBatis-Plus (自动建库建表，无需 SQL 脚本)

### Vue 3 / TypeScript (Frontend)

- **框架**: Vue 3.4 + ElementUI 2.5 + TypeScript
- **命名规范**:
  - 组件：PascalCase
  - Hooks：camelCase, use* 前缀
  - 类型接口：PascalCase, 禁止使用 `any`
- **组件规范**:
  - 使用函数式组件 + Composition API
  - 状态管理：useState, useRef, useEffect, Pinia
- **代码格式**:
  - 单引号 (singleQuote: true)
  - 尾随逗号 (trailingComma: 'all')
  - 行宽 100 字符 (printWidth: 100)
  - LF 换行 (endOfLine: 'lf')
- **编辑器配置** (.editorconfig):
  - 缩进：2 空格
  - 字符集：UTF-8
  - 删除行尾空格
  - 文件末尾空行
- **路径别名**:
  - `@/*` → `./src/*`

### 后端公共模块设计规范

#### 模块架构

| 模块 | 说明 | 依赖 |
|------|------|------|
| ps-common | 公共工具类、异常定义、枚举、常量 | 无 |
| ps-core | 核心实体、DTO、VO | ps-common |
| ps-security | 安全认证、权限控制、认证拦截器 | ps-common, ps-core |
| ps-api | API接口定义、Feign接口 | ps-common, ps-core |

#### 模块详细设计

##### ps-common 公共模块

```
ps-common/
├── src/main/java/com/iotsic/ps/common/
│   ├── constant/          # 常量定义
│   │   ├── SystemConstant  # 系统常量
│   │   └── BusinessConstant # 业务常量
│   ├── exception/          # 异常定义
│   │   ├── BaseException  # 基础异常
│   │   ├── BusinessException # 业务异常
│   │   └── GlobalExceptionHandler # 全局异常处理
│   ├── enums/             # 枚举定义
│   │   ├── ErrorCodeEnum  # 错误码枚举
│   │   └── StatusEnum    # 状态枚举
│   ├── utils/             # 工具类
│   │   ├── JsonUtils     # JSON工具
│   │   ├── DateUtils     # 日期工具
│   │   ├── EncryptUtils   # 加密工具
│   │   └── ValidationUtils # 校验工具
│   └── config/            # 公共配置
│       ├── JacksonConfig # Jackson配置
│       └── RedisConfig   # Redis配置
```

##### ps-core 核心模块

```
ps-core/
├── src/main/java/com/iotsic/ps/core/
│   ├── entity/           # 实体类
│   │   ├── User          # 用户实体
│   │   ├── Scale         # 量表实体
│   │   └── BaseEntity    # 基础实体
│   ├── dto/              # 数据传输对象
│   │   ├── request/      # 请求DTO
│   │   └── response/     # 响应DTO
│   ├── vo/               # 视图对象
│   │   └── RestResult    # 响应结果
│   └── enums/            # 核心枚举
│       ├── ScaleCategoryEnum # 量表分类
│       └── OrderTypeEnum  # 订单类型
```

##### ps-security 安全模块

```
ps-security/
├── src/main/java/com/iotsic/ps/security/
│   ├── aspect/           # 安全切面
│   │   └── PermissionAspect # 权限切面
│   ├── annotation/       # 安全注解
│   │   ├── RequireLogin  # 登录要求
│   │   └── RequirePermission # 权限要求
│   ├── filter/           # 安全过滤器
│   │   └── JwtFilter    # JWT过滤器
│   ├── handler/          # 安全处理器
│   │   └── AccessDeniedHandler # 权限不足处理
│   └── service/          # 安全服务
│       ├── JwtService   # JWT服务
│       └── PermissionService # 权限服务
```

##### ps-api 接口模块

```
ps-api/
├── src/main/java/com/iotsic/ps/api/
│   ├── user/             # 用户服务API
│   │   └── UserApi      # 用户Feign接口
│   ├── scale/            # 量表服务API
│   │   └── ScaleApi    # 量表Feign接口
│   ├── order/            # 订单服务API
│   │   └── OrderApi    # 订单Feign接口
│   └── config/          # API配置
│       └── FeignConfig # Feign配置
```

#### 依赖管理规范

- **公共模块版本统一管理**: 在父pom中统一管理依赖版本
- **接口依赖原则**: 
  - API模块只依赖common和core
  - 业务服务依赖API模块，不直接依赖其他业务服务
  - 使用Feign进行服务间调用

#### 公共类设计规范

- **统一响应**: 使用`RestResult<T>`封装响应结果
- **分页对象**: 使用`PageRequest`、`SortablePageRequest`和`PageResult`。`SortablePageRequest`继承`PageRequest`，并添加多条件排序功能
- **基础实体**: 所有实体类继承`BaseEntity`
- **DTO规范**: 请求DTO以`Request`结尾，响应DTO以`Response`结尾

#### 服务间调用规范

- **同步调用**: 使用OpenFeign
- **异步调用**: 使用CompletableFuture
- **超时控制**: 默认3秒超时
- **熔断降级**: 使用Sentinel进行熔断降级

### 数据库设计规范

- **表命名**: 小写字母 + 下划线（snake_case）
- **主键**: BIGINT自增
- **审计字段**（业务表必须包含）:
  | 字段名 | 类型 | 说明 |
  |--------|------|------|
  | create_time | DATETIME | 创建时间 |
  | create_by | BIGINT | 创建人（关联用户ID） |
  | update_time | DATETIME | 更新时间 |
  | update_by | BIGINT | 更新人（关联用户ID） |
  | deleted | TINYINT | 逻辑删除（0-正常，1-删除） |
- **索引**: 针对查询字段建立适当索引
- **ORM**: 使用MyBatis-Plus，自动建库建表

### API 设计规范

- **响应格式**: 统一使用 `RestResult<T>` 封装
- **错误码**: 统一错误码体系
- **分页**: 使用 `PageRequest`、`SortablePageRequest` 和 `PageResult`。`SortablePageRequest`继承`PageRequest`，并添加多条件排序功能
- **认证**: JWT Token Bearer 认证

---

## 3. Git 工作规范

### 代码提交要求

**每次代码修改完成后，必须进行 Git 提交**。

- 完成一个功能模块或修复一个问题后，应立即提交代码
- 提交信息应清晰描述本次修改的内容
- 遵循 Git 提交规范（见下文）
- 提交前确保代码编译通过且无严重问题

### 禁止回滚规则

**严禁使用以下命令回滚任何代码和文件**：

- `git checkout`（用于文件恢复）
- `git revert`
- `git reset`（含 --hard, --soft 等参数）
- 任何其他回滚命令

**原因**：
- 回滚会丢失代码修改历史，影响团队协作
- 导致代码审查和问题追踪困难
- 破坏代码完整性

**正确的处理方式**：
- 如果需要撤销修改，直接删除或重新编辑相关代码
- 如果提交了错误的代码，应该创建一个新的提交来修复
- 遇到问题时，先分析原因再决定如何处理，而不是盲目回滚

### Git 提交规范

```
提交类型(模块): 提交说明

提交类型：
- feat: 新功能
- fix: Bug 修复
- docs: 文档更新
- style: 代码格式调整
- refactor: 重构
- test: 测试相关
- chore: 构建/工具链
- perf: 性能优化

示例：
- feat(scale): 添加量表维度管理功能
- fix(order): 修复订单退款金额计算错误
- docs: 更新量表模板设计文档
```

---

## 4. 文档规范

### 文档结构

```
documents/
├── requirements/          # 需求文档
│   ├── MOD-001-{模块名称}/
│   │   └── {模块名称}-需求文档.md
│   └── ...
├── design/                # 技术设计文档
│   ├── architecture-design.md    # 总体架构设计
│   ├── database-design.md       # 数据库设计
│   ├── api-design.md           # API接口设计
│   ├── security-design.md      # 安全合规设计
│   ├── MOD-XXX-{模块名称}/
│   │   └── {模块名称}-设计文档.md
│   └── ...
├── test/                # 测试文档
│   ├── 系统测试计划.md    # 系统测试计划
│   ├── MOD-XXX-{模块名称}/
│   │   └── {模块名称}-测试文档.md
│   └── ...
├── assets/                # 文档资源
└── status.md              # 文档状态跟踪表
```

### 需求文档模板

参考路径: `docs/requirements/需求文档模板.md`

### 技术设计文档模板

参考路径: `docs/design/模块设计文档模板.md`

### 文档命名规范

- 需求文档: `{模块编号}-{模块名称}/{模块名称}-需求文档.md`
- 设计文档: `{模块编号}-{模块名称}/{模块名称}-设计文档.md`
- 模块编号格式: MOD-001, MOD-002, ...

### 文档状态

- 草稿：初稿编写中
- 评审中：待评审
- 已完成：已通过评审

---

## 5. 测试规范

### 测试数据要求

- **禁止使用 Mock 数据**：所有测试必须使用真实数据
- **真实环境测试**：测试流程必须连接真实数据库和服务
- **数据准备**：测试前应准备充分的测试数据
- **数据清理**：测试后应清理测试数据，保持环境干净
- **集成测试优先**：优先编写集成测试而非单元测试

### 原因说明

1. Mock 测试无法验证真实业务逻辑
2. Mock 测试容易遗漏边界条件
3. 真实数据测试能发现集成问题
4. 确保代码在生产环境中正常工作

### 强制要求：禁止 Mock 测试

**严禁在测试中使用 Mock 数据或模拟实现！**

- 如果数据不足：**跳过测试**（使用 `Assumptions.assumeTrue()`）
- 如果服务不可用：**测试失败**（抛出异常）
- 如果环境未就绪：**跳过测试**（记录原因）

**不要写无意义的模拟测试！宁愿测试失败或跳过，也不要用假数据欺骗自己。**

```java
// 正确做法：数据不足时跳过测试
Assumptions.assumeTrue(dataAvailable, "跳过测试：数据库中没有足够的测试数据");

// 错误做法：使用Mock数据
@Mock
private ScaleRepository mockRepository; // 禁止！
```

### 例外情况

**无例外！** 所有测试都必须使用真实数据和服务。

### 强制提交场景

以下情况**必须**提交代码：
- 完成任何功能模块的实现
- 修复任何 Bug
- 更新文档
- 修改配置文件
- 进行任何代码修改后

### 提交检查清单

在提交前请确认：
- [ ] 代码编译通过
- [ ] 相关功能已测试（如适用）

### 代码推送要求

**每次提交代码后，必须推送到远程仓库**。

- 保持本地和远程代码同步
- 确保团队成员可以获取最新代码
- 避免因本地提交未推送导致的代码丢失风险

