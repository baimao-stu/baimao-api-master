# baimao-api 开发平台
> 一个丰富的API开放调用平台，为开发者提供便捷、实用、安全的API调用体验； Java + React 全栈项目，包括网站前台 + 后台
> 在线体验地址：baimao API
> 项目前端开源地址：

## 项目背景
帮助和服务更多的用户和开发者，使其更加方便快捷的获取他们想要的信息和功能。 
接口平台可以帮助开发者快速接入一些常用的服务，从而提高他们的开发效率，
比如随机头像，随机壁纸，随机动漫图片(二次元爱好者专用)等服务，他们是一些应用或者小程序常见的功能，
所以提供这些接口可以帮助开发者更加方便地实现这些功能。
这些接口也可以让用户在使用应用时获得更加全面的功能和服务，从而提高他们的用户体验。

## 技术栈
### 后端
* 主语言：Java
* 框架：SpringBoot 2.7.2、Mybatis-plus、Spring Cloud
* 数据库：Mysql8.0
* 注册中心：Nacos
* 服务调用：Dubbo
* 网关：Spring Cloud Gateway

### 前端
* 开发框架：React、Umi
* 脚手架：Ant Design Pro
* 组件库：Ant Design、Ant Design Components
* 语法扩展：TypeScript、Less
* 打包工具：Webpack
* 代码规范：ESLint、StyleLint、Prettier

## 项目模块
bmapi-common：公共模块（公共实体，公共常量，工具类，统一异常处理，使用RPC的公共接口）

bmapi-sdk：提供给开发者的接口调用 sdk

bmapi-backend：管理后台（用户管理，接口信息管理）

bmapi-interface：接口服务，提供可调用的接口

bmapi-gateway：网关服务（包括路由、统一鉴权、统一日志、接口调用统计）
