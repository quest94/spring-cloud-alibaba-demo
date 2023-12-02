/**
 * 开放接口层：可直接封装 Service 接口暴露成 RPC 接口； 通过 Web 封装成 http 接口； 网关控制层等。
 * <p>
 *      分层领域模型规约：
 *      QUERY：数据查询对象，各层接收上层的查询请求。 注：超过 2 个参数的查询封装，禁止使用 Map 类来传输。
 * </p>
 * <p>
 *      分层异常处理规约：
 *      开放接口层要将异常处理成错误码和错误信息方式返回。
 * </p>
 * <p>
 *     包结构划分：
 *     com.公司名.项目名.xxx.openapi
 * </p>
 */
package com.quest94.demo.sca.openapi;