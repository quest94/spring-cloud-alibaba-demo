/**
 * DAO 层：数据访问层，与底层 MySQL、 Oracle、 Hbase 进行数据交互。
 * <p>
 *     分层领域模型规约：
 *     QUERY：数据查询对象，各层接收上层的查询请求。 注：超过 2 个参数的查询封装，禁止使用 Map 类来传输。
 *     DO（Data Object） ：与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。
 * </p>
 * <p>
 *     分层异常处理规约：
 *     在 DAO 层，产生的异常类型有很多，无法用细粒度异常进行 catch，使用 catch(Exception e)方式，并 throw new DAOException(e)，
 *     不需要打印日志，因为日志在 Manager/Service 层一定需要捕获并打到日志文件中去，如果同台服务器再打日志，浪费性能和存储。
 * </p>
 * <p>
 *     包结构划分：
 *     com.公司名.项目名.xxx.dao
 * </p>
 */
package com.quest94.demo.sca.dao;
