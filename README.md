# spring-cloud-alibaba-demo
Spring Cloud Alibaba 的练手项目，目前整合了<br>
* Spring Boot 3.0.2
* Spring Cloud Alibaba 2022.0.0.0-RC2
* Apache Dubbo 3.2.5

<br>项目代码架构使用依赖反转按分层架构分模块，严格限制跨层访问；
<br>代码逻辑方面目前已整合sentinel给dubbo接口的服务消费/服务生产做流控，通过sentinel控制台配置。