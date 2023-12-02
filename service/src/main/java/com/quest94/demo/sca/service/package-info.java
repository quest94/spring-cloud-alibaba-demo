/**
 * Service 层：相对具体的业务逻辑服务层。
 * <p>
 *     分层领域模型规约：
 *     QUERY：数据查询对象，各层接收上层的查询请求。 注：超过 2 个参数的查询封装，禁止使用 Map 类来传输。
 *     DTO（Data Transfer Object） ：数据传输对象， Service 和 Manager 向外传输的对象。
 *     BO（Business Object） ：业务对象。 可以由 Service 层输出的封装业务逻辑的对象。
 * </p>
 * <p>
 *     分层异常处理规约：
 *     在 Service 层出现异常时，必须记录日志信息到磁盘，尽可能带上参数信息，相当于保护案发现场。
 * </p>
 * <p>
 *     包结构划分：
 *     com.公司名.项目名.xxx.service
 *     一般来说,该层也会比较大
 *     com.公司名.项目名.xxx.service.admin
 *     com.公司名.项目名.xxx.service.bill
 * </p>
 */
package com.quest94.demo.sca.service;
