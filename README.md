# 四六级考试管理系统

### 技术栈&运行环境  

```
maven
Java 1.8
Spring boot
Mysql
Redis
Vue
```

### 目录结构
```
|-- exam-service  （业务层）
|-- exam-core  (独立的业务领域核心)
    |-- user        (用户领域核心)
    |-- message    (消息领域核心)
    |-- common   	(通用领域核心)
    |-- exam      (考试领域核心)
|-- exam-security  (服务安全层)    
|-- exam-web   (接口交互层)
|-- util  (通用工具模块 -- 来自Beta的二方库)

```

