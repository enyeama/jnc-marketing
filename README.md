# jnc-marketing-poc

# 总览
   在剑南春JavaWeb项目开发中为了便于后期的维护，我们使用多模块的Maven配置。可以帮助项目划分模块，鼓励重用，防止POM变得过于庞大，方便某个模块的构建，而不用每次都构建整个项目，并且使得针对某个模块的特殊控制更为方便。
各个基础模块已经由开发组搭建如下：

# 概述
## 检查模型（Model）和资源库（Repository）
所有Model和Repository实体都在项目工程jnc-marketing-persistence。
#### Model是Java JPA实体，用于数据库表映射。
* 路径为：com.sap.jnc.marketing.persistence.model<br>
 
#### Repository模块继承Spring Data方便快捷的访问数据库。
* 路径为：com.sap.jnc.marketing.persistence.repository

## 创建数据传输对象(DTO)
* 路径为：/jnc-marketing-dto/com.sap.jnc.marketing.dto<br>
  表现层与应用层之间通过数据传输对象（DTO）进行交互。
  
  包含但不必须以下三类DTO：
  - Request：用于表现层请求数据封装。
    例如，某一页面为员工信息补充而设计，用于再次收集员工特殊信息字段，则有DTO如下：
    com.sap.jnc.marketing.dto.request.employee.EmployeeMergeRequest
  - Response：用于应用层返回数据对象封装。
    例如，某一页面查询员工关键联系方式信息并展示，则有DTO如下：
    com.sap.jnc.marketing.dto.response.employee.EmployeeInfoResponse
  - Shared：用于存放公共DTO。建议添加sub-package区分不同业务场景。
    例如，创建微信相关公用DTO，则有sub-package：
    com.sap.jnc.marketing.dto.shared.wechat
    
## 编写业务逻辑服务(Service)
* 路径为：/jnc-marketing-service/com.sap.jnc.marketing.service<br>
  具体业务逻辑的实现。
  例如，有业务需求：更新员工补充信息。则创建服务接口：
  com.sap.jnc.marketing.service.employee.EmployeeService
  以及具体实现类：
  com.sap.jnc.marketing.service.employee.impl.EmployeeServiceImpl
  
## 编写控制器(Controller)
 * Spring Controller负责分发请求。通过开发不同的业务方法，调用业务逻辑服务处理来自表现层的请求数据，并返回对应处理结果。<br>
   本项目中，有四个控制器模块：
   - 后台管理模块控制器：系统管理员或关键用户业务处理
     /jnc-marketing-api-admin/com.sap.jnc.marketing.api.admin.web.controller
   - 微信模块控制器：微信相关业务处理
     /jnc-marketing-api-wechat/com.sap.jnc.marketing.api.wechat.web.controller
   - 集成模块控制器：第三方系统集成业务处理
     /jnc-marketing-api-integration/com.sap.jnc.marketing.api.integration.web.controller
   - 后台任务模块控制器：后台定时自动任务处理
     /jnc-marketing-api-job/com.sap.jnc.marketing.api.job.web.controller <br>
   例如，有业务需求：员工信息维护业务。则创建控制器：
   com.sap.jnc.marketing.api.admin.web.controller.EmployeeController

## 编写表现层页面(UI)
 * 表现层负责向业务层传递参数，获取业务层返回的信息并展示，从而达到和用户互动的目的。
   本项目中，有三个控制器模块：
   - 后台管理模块界面：
     /jnc-marketing-ui-admin
   - 微信模块界面：
     /jnc-marketing-ui-wechat
   - 后台任务模块界面：
     /jnc-marketing-ui-job 
   例如，有业务需求：员工信息维护业务。则创建页面：
   /jnc-marketing-ui-admin/src/main/webapp/addEmployee.html
   
   
