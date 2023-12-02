/**
 * Manager 层：通用业务处理层，它有如下特征：
 * 1. 对第三方平台封装的层，预处理返回结果及转化异常信息（适配上层接口）；
 * 2. 对 Service 层通用能力的下沉，如缓存方案、中间件通用处理；
 * 3. 与 DAO 层交互，对多个 DAO 的组合复用。
 * <p>
 *     分层领域模型规约：
 *     QUERY：数据查询对象，各层接收上层的查询请求。 注：超过 2 个参数的查询封装，禁止使用 Map 类来传输。
 *     DTO（Data Transfer Object） ：数据传输对象， Service 和 Manager 向外传输的对象。
 * </p>
 * <p>
 *     分层异常处理规约：
 *     如果 Manager 层与 Service 同机部署，日志方式与 DAO 层处理一致，如果是单独部署，则采用与 Service 一致的处理方式。
 * </p>
 * <p>
 *     包结构划分：
 *     com.公司名.项目名.xxx.manager.wuzhong
 *     com.公司名.项目名.xxx.manager.cache
 *     com.公司名.项目名.xxx.manager.dao
 * </p>
 */
package com.quest94.demo.sca.manager;

