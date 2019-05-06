SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `t_audit_log`;
CREATE TABLE `t_audit_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `c_creator` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `c_request_start_time` datetime NOT NULL COMMENT '请求开始时间',
  `c_request_method` text COLLATE utf8_bin NOT NULL COMMENT '请求方式 get post',
  `c_ip` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '操作IP地址',
  `c_request_url` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '请求地址',
  `c_class_method_path` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '具体的请求方法路径',
  `c_class_method_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `c_user_agent` varchar(300) COLLATE utf8_bin NOT NULL COMMENT '客户端信息',
  `c_params` text COLLATE utf8_bin,
  `c_request_finsh_time` int(11) DEFAULT NULL COMMENT '请求耗时 ms',
  `c_return_time` datetime DEFAULT NULL COMMENT '接口返回时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='日志表';
