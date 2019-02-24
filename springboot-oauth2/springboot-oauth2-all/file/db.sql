create database uc ;
use uc;

drop table  if exists t_oauth_client  ;
create table t_oauth_client(
  id bigint(11) primary key auto_increment comment '主键',
  client_id varchar(30) not null comment '客户端id',
  client_secret varchar(30) not null comment '客户端秘钥',
  oauth_type tinyint(2) comment '授权类型：0-client（客户端模式）；1-password（密码模式）；2-authorization code（授权码模式）；3-implicit（简化模式）',
  type tinyint(2) comment '登录类型：0-用户名登录；1-工号登录；2-手机号登录',
  redirect_uri varchar(100) comment '跳转url',
  org_id bigint(19) comment '机构id',
  create_time bigint(16) default 0 comment '创建时间',
  update_time bigint(16) default 0 comment '更新时间',
  version int default 0 comment '乐观锁版本号',
  is_del tinyint(1) not null comment '删除标识：0-启用；1-禁用'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

alter table t_oauth_client add column resource_ids varchar(50) comment '资源id' after type;
alter table t_oauth_client add column authorized_grant_types varchar(50) comment '此客户端被授予的授权类型';
alter table t_oauth_client add column scope varchar(50) comment 'scope' ;
alter table t_oauth_client add column authorities varchar(50) comment '权限名称';
alter table t_oauth_client add column access_token_validity_seconds int(5) comment 'token过期时间';
alter table t_oauth_client add column refresh_token_validity_seconds int(5) comment 'token刷新过期时间';

