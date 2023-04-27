/*
 Navicat Premium Data Transfer

 Source Server         : 10.7.201.181
 Source Server Type    : PostgreSQL
 Source Server Version : 90620
 Source Host           : 10.7.201.181:5432
 Source Catalog        : fosung_workbench_1.0
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90620
 File Encoding         : 65001

 Date: 15/11/2021 19:16:40
*/


-- ----------------------------
-- Table structure for application_basic
-- ----------------------------
DROP TABLE IF EXISTS "public"."application_basic";
CREATE TABLE "public"."application_basic" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "app_code" varchar(16) COLLATE "pg_catalog"."default",
  "app_name" varchar(255) COLLATE "pg_catalog"."default",
  "app_support" varchar(255) COLLATE "pg_catalog"."default",
  "app_type" varchar(255) COLLATE "pg_catalog"."default",
  "category_code" varchar(255) COLLATE "pg_catalog"."default",
  "category_name" varchar(255) COLLATE "pg_catalog"."default",
  "creater_name" varchar(255) COLLATE "pg_catalog"."default",
  "data_source" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "examine_flag" varchar(255) COLLATE "pg_catalog"."default",
  "external_id" int8,
  "icon_url" varchar(255) COLLATE "pg_catalog"."default",
  "in_or_out" bool,
  "maintain" bool,
  "maintain_message" varchar(255) COLLATE "pg_catalog"."default",
  "maintain_url" varchar(255) COLLATE "pg_catalog"."default",
  "num" int4,
  "package_name" varchar(255) COLLATE "pg_catalog"."default",
  "project_id" int8,
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for application_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."application_category";
CREATE TABLE "public"."application_category" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "category_name" varchar(255) COLLATE "pg_catalog"."default",
  "category_type" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "num" int4
)
;

-- ----------------------------
-- Table structure for application_config_android
-- ----------------------------
DROP TABLE IF EXISTS "public"."application_config_android";
CREATE TABLE "public"."application_config_android" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "app_id" int8,
  "del" bool,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "version_name" varchar(255) COLLATE "pg_catalog"."default",
  "version_num" int4,
  "app_package_path" varchar(255) COLLATE "pg_catalog"."default",
  "app_store_id" varchar(255) COLLATE "pg_catalog"."default",
  "app_version_id" int8,
  "bundle_id" varchar(255) COLLATE "pg_catalog"."default",
  "download_path" varchar(255) COLLATE "pg_catalog"."default",
  "start_app" bool
)
;

-- ----------------------------
-- Table structure for application_config_html
-- ----------------------------
DROP TABLE IF EXISTS "public"."application_config_html";
CREATE TABLE "public"."application_config_html" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "app_id" int8,
  "del" bool,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "version_name" varchar(255) COLLATE "pg_catalog"."default",
  "version_num" int4,
  "app_package_path" varchar(255) COLLATE "pg_catalog"."default",
  "app_version_id" int8,
  "download_path" varchar(255) COLLATE "pg_catalog"."default",
  "front_url" varchar(255) COLLATE "pg_catalog"."default",
  "publish_type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for application_config_ios
-- ----------------------------
DROP TABLE IF EXISTS "public"."application_config_ios";
CREATE TABLE "public"."application_config_ios" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "app_id" int8,
  "del" bool,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "version_name" varchar(255) COLLATE "pg_catalog"."default",
  "version_num" int4,
  "app_store_id" varchar(255) COLLATE "pg_catalog"."default",
  "app_version_id" int8,
  "bundle_id" varchar(255) COLLATE "pg_catalog"."default",
  "ios_url_scemes" varchar(255) COLLATE "pg_catalog"."default",
  "start_app" bool
)
;

-- ----------------------------
-- Table structure for application_examine
-- ----------------------------
DROP TABLE IF EXISTS "public"."application_examine";
CREATE TABLE "public"."application_examine" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "app_id" int8,
  "del" bool,
  "examine_name" varchar(255) COLLATE "pg_catalog"."default",
  "examine_reason" varchar(255) COLLATE "pg_catalog"."default",
  "examine_status" varchar(255) COLLATE "pg_catalog"."default",
  "examine_time" varchar(255) COLLATE "pg_catalog"."default",
  "examine_type" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "submitter_name" varchar(255) COLLATE "pg_catalog"."default",
  "verson_id" int8
)
;

-- ----------------------------
-- Table structure for application_owner
-- ----------------------------
DROP TABLE IF EXISTS "public"."application_owner";
CREATE TABLE "public"."application_owner" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "app_config_id" int8,
  "terminal_id" int8,
  "user_id" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for application_version
-- ----------------------------
DROP TABLE IF EXISTS "public"."application_version";
CREATE TABLE "public"."application_version" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "app_id" int8,
  "app_type" varchar(255) COLLATE "pg_catalog"."default",
  "audit_remark" varchar(255) COLLATE "pg_catalog"."default",
  "audit_status" varchar(255) COLLATE "pg_catalog"."default",
  "compatible_old_version" bool,
  "config_path" varchar(255) COLLATE "pg_catalog"."default",
  "create_user_name" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "download_num" int4,
  "file_name" varchar(255) COLLATE "pg_catalog"."default",
  "is_new_version" bool,
  "package_name" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "start_name" varchar(255) COLLATE "pg_catalog"."default",
  "start_params" varchar(255) COLLATE "pg_catalog"."default",
  "version_config_id" int8,
  "version_name" varchar(255) COLLATE "pg_catalog"."default",
  "version_num" int4,
  "version_size" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for cities
-- ----------------------------
DROP TABLE IF EXISTS "public"."cities";
CREATE TABLE "public"."cities" (
  "id" int4 NOT NULL,
  "c_name" text COLLATE "pg_catalog"."default",
  "c_pinyin" text COLLATE "pg_catalog"."default",
  "c_code" text COLLATE "pg_catalog"."default",
  "c_province" text COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for config_api
-- ----------------------------
DROP TABLE IF EXISTS "public"."config_api";
CREATE TABLE "public"."config_api" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "api_address" varchar(255) COLLATE "pg_catalog"."default",
  "api_code" varchar(255) COLLATE "pg_catalog"."default",
  "api_group_id" int8,
  "api_host" varchar(255) COLLATE "pg_catalog"."default",
  "api_name" varchar(32) COLLATE "pg_catalog"."default",
  "del" bool,
  "extend_property_json" varchar(2000) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for config_api_group
-- ----------------------------
DROP TABLE IF EXISTS "public"."config_api_group";
CREATE TABLE "public"."config_api_group" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "api_category" varchar(255) COLLATE "pg_catalog"."default",
  "api_group_name" varchar(255) COLLATE "pg_catalog"."default",
  "api_type" varchar(255) COLLATE "pg_catalog"."default",
  "extend_property_json" varchar(2000) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for config_api_params
-- ----------------------------
DROP TABLE IF EXISTS "public"."config_api_params";
CREATE TABLE "public"."config_api_params" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "api_id" int8,
  "param_key" varchar(255) COLLATE "pg_catalog"."default",
  "param_value" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for config_cascade
-- ----------------------------
DROP TABLE IF EXISTS "public"."config_cascade";
CREATE TABLE "public"."config_cascade" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "config_label" varchar(255) COLLATE "pg_catalog"."default",
  "config_status" varchar(255) COLLATE "pg_catalog"."default",
  "config_type" varchar(255) COLLATE "pg_catalog"."default",
  "config_value" varchar(255) COLLATE "pg_catalog"."default",
  "num" int4,
  "parent_id" int8,
  "path" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for config_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."config_category";
CREATE TABLE "public"."config_category" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "category_addr" varchar(255) COLLATE "pg_catalog"."default",
  "category_name" varchar(32) COLLATE "pg_catalog"."default",
  "category_type" varchar(32) COLLATE "pg_catalog"."default",
  "category_type_name" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool
)
;

-- ----------------------------
-- Table structure for config_certification
-- ----------------------------
DROP TABLE IF EXISTS "public"."config_certification";
CREATE TABLE "public"."config_certification" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "authorized_addr" varchar(255) COLLATE "pg_catalog"."default",
  "authorized_ak" varchar(255) COLLATE "pg_catalog"."default",
  "authorized_name" varchar(32) COLLATE "pg_catalog"."default",
  "authorized_sk" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "exit_addr" varchar(255) COLLATE "pg_catalog"."default",
  "forgot_pwd_addr" varchar(255) COLLATE "pg_catalog"."default",
  "refresh_token_addr" varchar(255) COLLATE "pg_catalog"."default",
  "registered_addr" varchar(255) COLLATE "pg_catalog"."default",
  "verification_code_addr" varchar(255) COLLATE "pg_catalog"."default",
  "verification_img_addr" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for config_manage
-- ----------------------------
DROP TABLE IF EXISTS "public"."config_manage";
CREATE TABLE "public"."config_manage" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "config_name" varchar(255) COLLATE "pg_catalog"."default",
  "config_platform" varchar(255) COLLATE "pg_catalog"."default",
  "config_platform_name" varchar(255) COLLATE "pg_catalog"."default",
  "config_type" varchar(255) COLLATE "pg_catalog"."default",
  "config_type_name" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool
)
;

-- ----------------------------
-- Table structure for cosumer_order
-- ----------------------------
DROP TABLE IF EXISTS "public"."cosumer_order";
CREATE TABLE "public"."cosumer_order" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "consumer" varchar(255) COLLATE "pg_catalog"."default",
  "content" varchar(2000) COLLATE "pg_catalog"."default",
  "exception_msg" varchar(2000) COLLATE "pg_catalog"."default",
  "handler" varchar(255) COLLATE "pg_catalog"."default",
  "operate_time" timestamp(6),
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "tag" varchar(255) COLLATE "pg_catalog"."default",
  "topic" varchar(255) COLLATE "pg_catalog"."default",
  "unique_id" int8
)
;

-- ----------------------------
-- Table structure for manage_opt_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."manage_opt_log";
CREATE TABLE "public"."manage_opt_log" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "del" bool,
  "opt_business_id" varchar(255) COLLATE "pg_catalog"."default",
  "opt_module" varchar(255) COLLATE "pg_catalog"."default",
  "opt_name" varchar(255) COLLATE "pg_catalog"."default",
  "opt_type" varchar(255) COLLATE "pg_catalog"."default",
  "req_ip" varchar(255) COLLATE "pg_catalog"."default",
  "req_method" varchar(255) COLLATE "pg_catalog"."default",
  "req_param" text COLLATE "pg_catalog"."default",
  "req_uri" varchar(255) COLLATE "pg_catalog"."default",
  "res_param" text COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for project_basic
-- ----------------------------
DROP TABLE IF EXISTS "public"."project_basic";
CREATE TABLE "public"."project_basic" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "del" bool,
  "person" varchar(32) COLLATE "pg_catalog"."default",
  "project_code" varchar(32) COLLATE "pg_catalog"."default",
  "project_name" varchar(32) COLLATE "pg_catalog"."default",
  "remark" varchar(512) COLLATE "pg_catalog"."default",
  "status" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for project_manager
-- ----------------------------
DROP TABLE IF EXISTS "public"."project_manager";
CREATE TABLE "public"."project_manager" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "del" bool,
  "project_id" int8,
  "user_id" varchar(128) COLLATE "pg_catalog"."default",
  "user_name" varchar(100) COLLATE "pg_catalog"."default",
  "user_phone" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_attachment";
CREATE TABLE "public"."sys_attachment" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "business_id" varchar(255) COLLATE "pg_catalog"."default",
  "business_name" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "download_path" varchar(255) COLLATE "pg_catalog"."default",
  "extension" varchar(255) COLLATE "pg_catalog"."default",
  "image_height" int4,
  "image_width" int4,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "origin_name" varchar(255) COLLATE "pg_catalog"."default",
  "size" int8,
  "sort_number" int4,
  "storage_name" varchar(255) COLLATE "pg_catalog"."default",
  "storage_path" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(255) COLLATE "pg_catalog"."default",
  "url" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dictionary";
CREATE TABLE "public"."sys_dictionary" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "display" bool,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "num" int8,
  "parent_id" int8,
  "path" varchar(255) COLLATE "pg_catalog"."default",
  "value" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for sys_manager
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_manager";
CREATE TABLE "public"."sys_manager" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" int8,
  "sys_statu" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_org";
CREATE TABLE "public"."sys_org" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "biz_id" int4,
  "del" bool,
  "dt_org_id" varchar(255) COLLATE "pg_catalog"."default",
  "has_children" varchar(255) COLLATE "pg_catalog"."default",
  "level" varchar(255) COLLATE "pg_catalog"."default",
  "order_number" varchar(255) COLLATE "pg_catalog"."default",
  "org_code" varchar(255) COLLATE "pg_catalog"."default",
  "org_name" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" varchar(255) COLLATE "pg_catalog"."default",
  "sync_flag" bool,
  "dt_user_num" int4
)
;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_resource";
CREATE TABLE "public"."sys_resource" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "image" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" varchar(255) COLLATE "pg_catalog"."default",
  "path" varchar(2000) COLLATE "pg_catalog"."default",
  "sort" int4,
  "system_code" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(255) COLLATE "pg_catalog"."default",
  "url" varchar(1000) COLLATE "pg_catalog"."default",
  "visible" bool
)
;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_resource";
CREATE TABLE "public"."sys_role_resource" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "resource_id" int8,
  "role_code" varchar(255) COLLATE "pg_catalog"."default",
  "role_id" int8
)
;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user";
CREATE TABLE "public"."sys_user" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "del" bool,
  "dt_hash" varchar(255) COLLATE "pg_catalog"."default",
  "dt_name" varchar(255) COLLATE "pg_catalog"."default",
  "dt_org_id" varchar(255) COLLATE "pg_catalog"."default",
  "enable" bool,
  "headimgurl" varchar(255) COLLATE "pg_catalog"."default",
  "identify_id" varchar(255) COLLATE "pg_catalog"."default",
  "org_code" varchar(255) COLLATE "pg_catalog"."default",
  "org_id" varchar(255) COLLATE "pg_catalog"."default",
  "org_name" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "realname" varchar(255) COLLATE "pg_catalog"."default",
  "source" varchar(255) COLLATE "pg_catalog"."default",
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "telephone" varchar(255) COLLATE "pg_catalog"."default",
  "username" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_role";
CREATE TABLE "public"."sys_user_role" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "role_code" varchar(255) COLLATE "pg_catalog"."default",
  "role_id" int8,
  "user_id" int8
)
;

-- ----------------------------
-- Table structure for terminal_application_bind
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_application_bind";
CREATE TABLE "public"."terminal_application_bind" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "app_config_id" int8,
  "app_id" int8,
  "app_version_id" int8,
  "del" bool,
  "project_id" int8,
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "terminal_id" int8,
  "zone_id" int8
)
;

-- ----------------------------
-- Table structure for terminal_application_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_application_config";
CREATE TABLE "public"."terminal_application_config" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "app_icon" varchar(255) COLLATE "pg_catalog"."default",
  "app_id" int8,
  "app_name" varchar(255) COLLATE "pg_catalog"."default",
  "app_type" varchar(255) COLLATE "pg_catalog"."default",
  "app_version_id" int8,
  "data_source" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "num" int4,
  "project_id" int8,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "start_name" varchar(255) COLLATE "pg_catalog"."default",
  "start_param" varchar(255) COLLATE "pg_catalog"."default",
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "terminal_id" int8,
  "version_name" varchar(255) COLLATE "pg_catalog"."default",
  "version_num" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for terminal_application_shelves
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_application_shelves";
CREATE TABLE "public"."terminal_application_shelves" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "app_config_id" int8,
  "data_source" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "shelves_range" varchar(255) COLLATE "pg_catalog"."default",
  "shelves_range_parent" varchar(255) COLLATE "pg_catalog"."default",
  "shelves_type" varchar(255) COLLATE "pg_catalog"."default",
  "terminal_id" int8
)
;

-- ----------------------------
-- Table structure for terminal_basic
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_basic";
CREATE TABLE "public"."terminal_basic" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "authentication_id" int8,
  "del" bool,
  "package_name" varchar(255) COLLATE "pg_catalog"."default",
  "project_id" int8,
  "remark" varchar(512) COLLATE "pg_catalog"."default",
  "terminal_code" varchar(32) COLLATE "pg_catalog"."default",
  "terminal_logo" varchar(255) COLLATE "pg_catalog"."default",
  "terminal_name" varchar(32) COLLATE "pg_catalog"."default",
  "terminal_type" varchar(255) COLLATE "pg_catalog"."default",
  "zone_id" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for terminal_category_app
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_category_app";
CREATE TABLE "public"."terminal_category_app" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "app_config_id" int8,
  "category_code" varchar(255) COLLATE "pg_catalog"."default",
  "category_type" varchar(255) COLLATE "pg_catalog"."default",
  "disabled_num" bool,
  "terminal_id" int8
)
;

-- ----------------------------
-- Table structure for terminal_config_agreement
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_config_agreement";
CREATE TABLE "public"."terminal_config_agreement" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "agreement_content" text COLLATE "pg_catalog"."default",
  "agreement_name" varchar(32) COLLATE "pg_catalog"."default",
  "agreement_type" varchar(255) COLLATE "pg_catalog"."default",
  "agreement_url" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "terminal_id" int8
)
;

-- ----------------------------
-- Table structure for terminal_config_api
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_config_api";
CREATE TABLE "public"."terminal_config_api" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "bind_category" varchar(255) COLLATE "pg_catalog"."default",
  "bind_group_id" int8,
  "bind_type" varchar(255) COLLATE "pg_catalog"."default",
  "terminal_id" int8
)
;

-- ----------------------------
-- Table structure for terminal_config_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_config_category";
CREATE TABLE "public"."terminal_config_category" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "area" varchar(255) COLLATE "pg_catalog"."default",
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "logo_url" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "num" int4,
  "parent_id" int8,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "status" bool,
  "terminal_id" int8,
  "type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for terminal_config_common
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_config_common";
CREATE TABLE "public"."terminal_config_common" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "config_code" varchar(255) COLLATE "pg_catalog"."default",
  "config_type" varchar(255) COLLATE "pg_catalog"."default",
  "config_value" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "terminal_id" int8
)
;

-- ----------------------------
-- Table structure for terminal_config_manage
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_config_manage";
CREATE TABLE "public"."terminal_config_manage" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "config_manage_id" int8,
  "del" bool,
  "terminal_id" int8,
  "config_platform" varchar(255) COLLATE "pg_catalog"."default",
  "config_platform_name" varchar(255) COLLATE "pg_catalog"."default",
  "config_type" varchar(255) COLLATE "pg_catalog"."default",
  "config_type_name" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for terminal_config_menu_top
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_config_menu_top";
CREATE TABLE "public"."terminal_config_menu_top" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "area" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "img" varchar(255) COLLATE "pg_catalog"."default",
  "is_end" bool,
  "menu_type" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "navigation_btm_id" int8,
  "navigation_id" int8,
  "num" int4,
  "parent_id" int8,
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "terminal_id" int8,
  "url" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- 创建序列
create sequence terminal_config_navigation_btm_int_id_seq increment by 1 minvalue 1 no maxvalue start with 1;

-- ----------------------------
-- Table structure for terminal_config_navigation_btm
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_config_navigation_btm";
CREATE TABLE "public"."terminal_config_navigation_btm" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "area" varchar(255) COLLATE "pg_catalog"."default",
  "associated_object" varchar(255) COLLATE "pg_catalog"."default",
  "default_show" bool,
  "del" bool,
  "force_login" varchar(255) COLLATE "pg_catalog"."default",
  "navigation_logo_checked" varchar(255) COLLATE "pg_catalog"."default",
  "navigation_logo_no_checked" varchar(255) COLLATE "pg_catalog"."default",
  "navigation_name" varchar(16) COLLATE "pg_catalog"."default",
  "navigation_url" varchar(255) COLLATE "pg_catalog"."default",
  "num" int4,
  "show_style" varchar(255) COLLATE "pg_catalog"."default",
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "terminal_id" int8,
  "int_id" int4 NOT NULL DEFAULT nextval('terminal_config_navigation_btm_int_id_seq'::regclass)
)
;

-- ----------------------------
-- Table structure for terminal_config_navigation_top
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_config_navigation_top";
CREATE TABLE "public"."terminal_config_navigation_top" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "area" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "menu_type" varchar(255) COLLATE "pg_catalog"."default",
  "navigation_btm_id" int8,
  "navigation_name" varchar(255) COLLATE "pg_catalog"."default",
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "terminal_id" int8
)
;

-- ----------------------------
-- Table structure for terminal_image_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_image_config";
CREATE TABLE "public"."terminal_image_config" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "del" bool,
  "end_time" timestamp(6),
  "image_type" varchar(255) COLLATE "pg_catalog"."default",
  "img_url" varchar(255) COLLATE "pg_catalog"."default",
  "link_url" varchar(255) COLLATE "pg_catalog"."default",
  "num" int4,
  "start_time" timestamp(6),
  "terminal_id" int8
)
;

-- ----------------------------
-- Table structure for terminal_navigation_strategy
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_navigation_strategy";
CREATE TABLE "public"."terminal_navigation_strategy" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "del" bool,
  "navigation_btm_id" int8,
  "strategy_area" varchar(255) COLLATE "pg_catalog"."default",
  "strategy_name" varchar(255) COLLATE "pg_catalog"."default",
  "strategy_url" varchar(255) COLLATE "pg_catalog"."default",
  "terminal_id" int8
)
;

-- ----------------------------
-- Table structure for terminal_navigation_turn_url
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_navigation_turn_url";
CREATE TABLE "public"."terminal_navigation_turn_url" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "association_type" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "navigation_turn_url" varchar(255) COLLATE "pg_catalog"."default",
  "num" int4,
  "terminal_id" int8,
  "turn_name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for terminal_update_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_update_log";
CREATE TABLE "public"."terminal_update_log" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "del" bool,
  "terminal_id" int8,
  "terminal_version_id" int8,
  "update_content" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for terminal_version
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_version";
CREATE TABLE "public"."terminal_version" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "app_size" varchar(255) COLLATE "pg_catalog"."default",
  "del" bool,
  "download_number" int4,
  "file_name" varchar(255) COLLATE "pg_catalog"."default",
  "force_upgrade" bool,
  "installation_package" varchar(255) COLLATE "pg_catalog"."default",
  "installation_package_md5" varchar(255) COLLATE "pg_catalog"."default",
  "installations_number" int4,
  "project_id" int8,
  "remark" varchar(512) COLLATE "pg_catalog"."default",
  "status" varchar(255) COLLATE "pg_catalog"."default",
  "terminal_id" int8,
  "terminal_logo_url" varchar(255) COLLATE "pg_catalog"."default",
  "terminal_type" varchar(255) COLLATE "pg_catalog"."default",
  "terminal_version" varchar(32) COLLATE "pg_catalog"."default",
  "terminal_version_num" int4,
  "upgrade_back_img" varchar(255) COLLATE "pg_catalog"."default",
  "upgrade_prompt" bool,
  "upgrade_title" varchar(255) COLLATE "pg_catalog"."default",
  "version_name" varchar(32) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for terminal_version_upgrade_range
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminal_version_upgrade_range";
CREATE TABLE "public"."terminal_version_upgrade_range" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6),
  "create_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "last_update_time" timestamp(6),
  "last_update_user_id" varchar(128) COLLATE "pg_catalog"."default",
  "del" bool,
  "terminal_version_id" int8,
  "upgrade_range_name" varchar(255) COLLATE "pg_catalog"."default",
  "upgrade_range_type" varchar(255) COLLATE "pg_catalog"."default",
  "upgrade_range_value" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Primary Key structure for table application_basic
-- ----------------------------
ALTER TABLE "public"."application_basic" ADD CONSTRAINT "application_basic_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table application_category
-- ----------------------------
ALTER TABLE "public"."application_category" ADD CONSTRAINT "application_category_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table application_config_android
-- ----------------------------
CREATE INDEX "idx_android_app_id" ON "public"."application_config_android" USING btree (
  "app_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_android_version_id" ON "public"."application_config_android" USING btree (
  "app_version_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table application_config_android
-- ----------------------------
ALTER TABLE "public"."application_config_android" ADD CONSTRAINT "application_config_android_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table application_config_html
-- ----------------------------
CREATE INDEX "idx_html_app_id" ON "public"."application_config_html" USING btree (
  "app_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_html_version_id" ON "public"."application_config_html" USING btree (
  "app_version_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table application_config_html
-- ----------------------------
ALTER TABLE "public"."application_config_html" ADD CONSTRAINT "application_config_html_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table application_config_ios
-- ----------------------------
CREATE INDEX "idx_ios_app_id" ON "public"."application_config_ios" USING btree (
  "app_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_ios_version_id" ON "public"."application_config_ios" USING btree (
  "app_version_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table application_config_ios
-- ----------------------------
ALTER TABLE "public"."application_config_ios" ADD CONSTRAINT "application_config_ios_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table application_examine
-- ----------------------------
ALTER TABLE "public"."application_examine" ADD CONSTRAINT "application_examine_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table application_owner
-- ----------------------------
ALTER TABLE "public"."application_owner" ADD CONSTRAINT "application_owner_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table application_version
-- ----------------------------
CREATE INDEX "idx_version_app_id" ON "public"."application_version" USING btree (
  "app_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_version_config_version_id" ON "public"."application_version" USING btree (
  "version_config_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table application_version
-- ----------------------------
ALTER TABLE "public"."application_version" ADD CONSTRAINT "application_version_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table cities
-- ----------------------------
ALTER TABLE "public"."cities" ADD CONSTRAINT "cities_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table config_api
-- ----------------------------
ALTER TABLE "public"."config_api" ADD CONSTRAINT "config_api_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table config_api_group
-- ----------------------------
ALTER TABLE "public"."config_api_group" ADD CONSTRAINT "config_api_group_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table config_api_params
-- ----------------------------
ALTER TABLE "public"."config_api_params" ADD CONSTRAINT "config_api_params_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table config_cascade
-- ----------------------------
ALTER TABLE "public"."config_cascade" ADD CONSTRAINT "config_cascade_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table config_category
-- ----------------------------
ALTER TABLE "public"."config_category" ADD CONSTRAINT "config_category_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table config_certification
-- ----------------------------
ALTER TABLE "public"."config_certification" ADD CONSTRAINT "config_certification_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table config_manage
-- ----------------------------
ALTER TABLE "public"."config_manage" ADD CONSTRAINT "config_manage_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table cosumer_order
-- ----------------------------
ALTER TABLE "public"."cosumer_order" ADD CONSTRAINT "cosumer_order_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table manage_opt_log
-- ----------------------------
ALTER TABLE "public"."manage_opt_log" ADD CONSTRAINT "manage_opt_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table project_basic
-- ----------------------------
ALTER TABLE "public"."project_basic" ADD CONSTRAINT "project_basic_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table project_manager
-- ----------------------------
ALTER TABLE "public"."project_manager" ADD CONSTRAINT "project_manager_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_attachment
-- ----------------------------
ALTER TABLE "public"."sys_attachment" ADD CONSTRAINT "sys_attachment_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_dictionary
-- ----------------------------
ALTER TABLE "public"."sys_dictionary" ADD CONSTRAINT "sys_dictionary_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_manager
-- ----------------------------
ALTER TABLE "public"."sys_manager" ADD CONSTRAINT "sys_manager_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_org
-- ----------------------------
ALTER TABLE "public"."sys_org" ADD CONSTRAINT "sys_org_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_resource
-- ----------------------------
ALTER TABLE "public"."sys_resource" ADD CONSTRAINT "sys_resource_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role_resource
-- ----------------------------
ALTER TABLE "public"."sys_role_resource" ADD CONSTRAINT "sys_role_resource_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_user
-- ----------------------------
CREATE INDEX "idx51bvuyvihefoh4kp5syh2jpi4" ON "public"."sys_user" USING btree (
  "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idxaeliq6as9d08489ymhiv74ao4" ON "public"."sys_user" USING btree (
  "dt_org_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idxfphxf8f8kefy4i0l0sowo7k8j" ON "public"."sys_user" USING btree (
  "org_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idxntlpf2jbluajs0p8pd3u3hkg0" ON "public"."sys_user" USING btree (
  "identify_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idxo5a0h8as4uj8264mcn1wj3u28" ON "public"."sys_user" USING btree (
  "dt_hash" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user_role
-- ----------------------------
ALTER TABLE "public"."sys_user_role" ADD CONSTRAINT "sys_user_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table terminal_application_bind
-- ----------------------------
CREATE INDEX "idx_bind_app_id" ON "public"."terminal_application_bind" USING btree (
  "app_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_bind_app_version_id" ON "public"."terminal_application_bind" USING btree (
  "app_version_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_bind_terminal_id" ON "public"."terminal_application_bind" USING btree (
  "terminal_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table terminal_application_bind
-- ----------------------------
ALTER TABLE "public"."terminal_application_bind" ADD CONSTRAINT "terminal_application_bind_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table terminal_application_config
-- ----------------------------
CREATE INDEX "idx_config_app_id" ON "public"."terminal_application_config" USING btree (
  "app_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_config_terminal_id" ON "public"."terminal_application_config" USING btree (
  "terminal_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table terminal_application_config
-- ----------------------------
ALTER TABLE "public"."terminal_application_config" ADD CONSTRAINT "terminal_application_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table terminal_application_shelves
-- ----------------------------
CREATE INDEX "idx_shelves_app_config_id" ON "public"."terminal_application_shelves" USING btree (
  "app_config_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_shelves_terminal_id" ON "public"."terminal_application_shelves" USING btree (
  "terminal_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table terminal_application_shelves
-- ----------------------------
ALTER TABLE "public"."terminal_application_shelves" ADD CONSTRAINT "terminal_application_shelves_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table terminal_basic
-- ----------------------------
ALTER TABLE "public"."terminal_basic" ADD CONSTRAINT "terminal_basic_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table terminal_category_app
-- ----------------------------
CREATE INDEX "idx_category_category_code" ON "public"."terminal_category_app" USING btree (
  "category_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_category_terminal_id" ON "public"."terminal_category_app" USING btree (
  "terminal_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table terminal_category_app
-- ----------------------------
ALTER TABLE "public"."terminal_category_app" ADD CONSTRAINT "terminal_category_app_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table terminal_config_agreement
-- ----------------------------
ALTER TABLE "public"."terminal_config_agreement" ADD CONSTRAINT "terminal_config_agreement_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table terminal_config_api
-- ----------------------------
ALTER TABLE "public"."terminal_config_api" ADD CONSTRAINT "terminal_config_api_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table terminal_config_category
-- ----------------------------
ALTER TABLE "public"."terminal_config_category" ADD CONSTRAINT "terminal_config_category_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table terminal_config_common
-- ----------------------------
CREATE INDEX "idx_common_terminal_id" ON "public"."terminal_config_common" USING btree (
  "terminal_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table terminal_config_common
-- ----------------------------
ALTER TABLE "public"."terminal_config_common" ADD CONSTRAINT "terminal_config_common_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table terminal_config_manage
-- ----------------------------
CREATE INDEX "idx_manage_terminal_id" ON "public"."terminal_config_manage" USING btree (
  "terminal_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table terminal_config_manage
-- ----------------------------
ALTER TABLE "public"."terminal_config_manage" ADD CONSTRAINT "terminal_config_manage_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table terminal_config_menu_top
-- ----------------------------
ALTER TABLE "public"."terminal_config_menu_top" ADD CONSTRAINT "terminal_config_menu_top_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table terminal_config_navigation_btm
-- ----------------------------
ALTER TABLE "public"."terminal_config_navigation_btm" ADD CONSTRAINT "terminal_config_navigation_btm_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table terminal_config_navigation_top
-- ----------------------------
ALTER TABLE "public"."terminal_config_navigation_top" ADD CONSTRAINT "terminal_config_navigation_top_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table terminal_image_config
-- ----------------------------
ALTER TABLE "public"."terminal_image_config" ADD CONSTRAINT "terminal_image_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table terminal_navigation_strategy
-- ----------------------------
ALTER TABLE "public"."terminal_navigation_strategy" ADD CONSTRAINT "terminal_navigation_strategy_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table terminal_navigation_turn_url
-- ----------------------------
ALTER TABLE "public"."terminal_navigation_turn_url" ADD CONSTRAINT "terminal_navigation_turn_url_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table terminal_update_log
-- ----------------------------
ALTER TABLE "public"."terminal_update_log" ADD CONSTRAINT "terminal_update_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table terminal_version
-- ----------------------------
CREATE INDEX "idx_terminal_version_id" ON "public"."terminal_version" USING btree (
  "terminal_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table terminal_version
-- ----------------------------
ALTER TABLE "public"."terminal_version" ADD CONSTRAINT "terminal_version_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table terminal_version_upgrade_range
-- ----------------------------
ALTER TABLE "public"."terminal_version_upgrade_range" ADD CONSTRAINT "terminal_version_upgrade_range_pkey" PRIMARY KEY ("id");



-- ----------------------------
-- Records of terminal_navigation_turn_url
-- ----------------------------
INSERT INTO "public"."terminal_navigation_turn_url" VALUES (1, NULL, NULL, NULL, NULL, 'local', 'f', 'local', 1, 123456789, '默认值');
INSERT INTO "public"."terminal_navigation_turn_url" VALUES (2, NULL, NULL, NULL, NULL, 'remote', 'f', 'remote', 1, 123456789, '默认值');

INSERT INTO "public"."cities" VALUES (1, '北京', 'beijing', '101010100', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2, '海淀', 'haidian', '101010200', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (3, '朝阳', 'chaoyang', '101010300', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (4, '顺义', 'shunyi', '101010400', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (5, '怀柔', 'huairou', '101010500', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (6, '通州', 'tongzhou', '101010600', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (7, '昌平', 'changping', '101010700', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (8, '延庆', 'yanqing', '101010800', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (9, '丰台', 'fengtai', '101010900', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (10, '石景山', 'shijingshan', '101011000', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (11, '大兴', 'daxing', '101011100', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (12, '房山', 'fangshan', '101011200', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (13, '密云', 'miyun', '101011300', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (14, '门头沟', 'mentougou', '101011400', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (15, '平谷', 'pinggu', '101011500', '北京', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (16, '上海', 'shanghai', '101020100', '上海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (17, '闵行', 'minhang', '101020200', '上海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (18, '宝山', 'baoshan', '101020300', '上海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (19, '嘉定', 'jiading', '101020500', '上海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (20, '南汇', 'nanhui', '101020600', '上海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (21, '金山', 'jinshan', '101020700', '上海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (22, '青浦', 'qingpu', '101020800', '上海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (23, '松江', 'songjiang', '101020900', '上海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (24, '奉贤', 'fengxian', '101021000', '上海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (25, '崇明', 'chongming', '101021100', '上海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (26, '徐家汇', 'xujiahui', '101021200', '上海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (27, '浦东', 'pudong', '101021300', '上海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (28, '天津', 'tianjin', '101030100', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (29, '武清', 'wuqing', '101030200', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (30, '宝坻', 'baodi', '101030300', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (31, '东丽', 'dongli', '101030400', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (32, '西青', 'xiqing', '101030500', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (33, '北辰', 'beichen', '101030600', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (34, '宁河', 'ninghe', '101030700', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (35, '汉沽', 'hangu', '101030800', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (36, '静海', 'jinghai', '101030900', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (37, '津南', 'jinnan', '101031000', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (38, '塘沽', 'tanggu', '101031100', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (39, '大港', 'dagang', '101031200', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (40, '蓟县', 'jixian', '101031400', '天津', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (41, '重庆', 'chongqing', '101040100', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (42, '永川', 'yongchuan', '101040200', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (43, '合川', 'hechuan', '101040300', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (44, '南川', 'nanchuan', '101040400', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (45, '江津', 'jiangjin', '101040500', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (46, '万盛', 'wansheng', '101040600', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (47, '渝北', 'yubei', '101040700', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (48, '北碚', 'beibei', '101040800', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (49, '巴南', 'banan', '101040900', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (50, '长寿', 'changshou', '101041000', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (51, '黔江', 'qianjiang', '101041100', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (52, '万州', 'wanzhou', '101041300', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (53, '涪陵', 'fuling', '101041400', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (54, '开县', 'kaixian', '101041500', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (55, '城口', 'chengkou', '101041600', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (56, '云阳', 'yunyang', '101041700', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (57, '巫溪', 'wuxi', '101041800', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (58, '奉节', 'fengjie', '101041900', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (59, '巫山', 'wushan', '101042000', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (60, '潼南', 'tongnan', '101042100', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (61, '垫江', 'dianjiang', '101042200', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (62, '梁平', 'liangping', '101042300', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (63, '忠县', 'zhongxian', '101042400', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (64, '石柱', 'shizhu', '101042500', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (65, '大足', 'dazu', '101042600', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (66, '荣昌', 'rongchang', '101042700', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (67, '铜梁', 'tongliang', '101042800', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (68, '璧山', 'bishan', '101042900', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (69, '丰都', 'fengdu', '101043000', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (70, '武隆', 'wulong', '101043100', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (71, '彭水', 'pengshui', '101043200', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (72, '綦江', 'qijiang', '101043300', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (73, '酉阳', 'youyang', '101043400', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (74, '秀山', 'xiushan', '101043600', '重庆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (75, '哈尔滨', 'haerbin', '101050101', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (76, '双城', 'shuangcheng', '101050102', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (77, '呼兰', 'hulan', '101050103', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (78, '阿城', 'acheng', '101050104', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (79, '宾县', 'binxian', '101050105', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (80, '依兰', 'yilan', '101050106', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (81, '巴彦', 'bayan', '101050107', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (82, '通河', 'tonghe', '101050108', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (83, '方正', 'fangzheng', '101050109', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (84, '延寿', 'yanshou', '101050110', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (85, '尚志', 'shangzhi', '101050111', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (86, '五常', 'wuchang', '101050112', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (87, '木兰', 'mulan', '101050113', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (88, '齐齐哈尔', 'qiqihaer', '101050201', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (89, '讷河', 'nehe', '101050202', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (90, '龙江', 'longjiang', '101050203', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (91, '甘南', 'gannan', '101050204', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (92, '富裕', 'fuyu', '101050205', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (93, '依安', 'yian', '101050206', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (94, '拜泉', 'baiquan', '101050207', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (95, '克山', 'keshan', '101050208', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (96, '克东', 'kedong', '101050209', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (97, '泰来', 'tailai', '101050210', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (98, '牡丹江', 'mudanjiang', '101050301', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (99, '海林', 'hailin', '101050302', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (100, '穆棱', 'muling', '101050303', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (101, '林口', 'linkou', '101050304', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (102, '绥芬河', 'suifenhe', '101050305', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (103, '宁安', 'ningan', '101050306', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (104, '东宁', 'dongning', '101050307', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (105, '佳木斯', 'jiamusi', '101050401', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (106, '汤原', 'tangyuan', '101050402', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (107, '抚远', 'fuyuan', '101050403', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (108, '桦川', 'huachuan', '101050404', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (109, '桦南', 'huanan', '101050405', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (110, '同江', 'tongjiang', '101050406', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (111, '富锦', 'fujin', '101050407', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (112, '绥化', 'suihua', '101050501', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (113, '肇东', 'zhaodong', '101050502', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (114, '安达', 'anda', '101050503', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (115, '海伦', 'hailun', '101050504', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (116, '明水', 'mingshui', '101050505', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (117, '望奎', 'wangkui', '101050506', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (118, '兰西', 'lanxi', '101050507', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (119, '青冈', 'qinggang', '101050508', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (120, '庆安', 'qingan', '101050509', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (121, '绥棱', 'suiling', '101050510', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (122, '黑河', 'heihe', '101050601', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (123, '嫩江', 'nenjiang', '101050602', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (124, '孙吴', 'sunwu', '101050603', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (125, '逊克', 'xunke', '101050604', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (126, '五大连池', 'wudalianchi', '101050605', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (127, '北安', 'beian', '101050606', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (128, '大兴安岭', 'daxinganling', '101050701', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (129, '塔河', 'tahe', '101050702', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (130, '漠河', 'mohe', '101050703', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (131, '呼玛', 'huma', '101050704', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (132, '呼中', 'huzhong', '101050705', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (133, '新林', 'xinlin', '101050706', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (134, '加格达奇', 'jiagedaqi', '101050708', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (135, '伊春', 'yichun', '101050801', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (136, '乌伊岭', 'wuyiling', '101050802', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (137, '五营', 'wuying', '101050803', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (138, '铁力', 'tieli', '101050804', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (139, '嘉荫', 'jiayin', '101050805', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (140, '大庆', 'daqing', '101050901', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (141, '林甸', 'lindian', '101050902', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (142, '肇州', 'zhaozhou', '101050903', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (143, '肇源', 'zhaoyuan', '101050904', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (144, '杜尔伯特', 'duerbote', '101050905', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (145, '七台河', 'qitaihe', '101051002', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (146, '勃利', 'boli', '101051003', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (147, '鸡西', 'jixi', '101051101', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (148, '虎林', 'hulin', '101051102', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (149, '密山', 'mishan', '101051103', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (150, '鸡东', 'jidong', '101051104', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (151, '鹤岗', 'hegang', '101051201', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (152, '绥滨', 'suibin', '101051202', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (153, '萝北', 'luobei', '101051203', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (154, '双鸭山', 'shuangyashan', '101051301', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (155, '集贤', 'jixian', '101051302', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (156, '宝清', 'baoqing', '101051303', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (157, '饶河', 'raohe', '101051304', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (158, '友谊', 'youyi', '101051305', '黑龙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (159, '长春', 'changchun', '101060101', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (160, '农安', 'nongan', '101060102', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (161, '德惠', 'dehui', '101060103', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (162, '九台', 'jiutai', '101060104', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (163, '榆树', 'yushu', '101060105', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (164, '双阳', 'shuangyang', '101060106', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (165, '吉林', 'jilin', '101060201', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (166, '舒兰', 'shulan', '101060202', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (167, '永吉', 'yongji', '101060203', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (168, '蛟河', 'jiaohe', '101060204', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (169, '磐石', 'panshi', '101060205', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (170, '桦甸', 'huadian', '101060206', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (171, '延吉', 'yanji', '101060301', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (172, '敦化', 'dunhua', '101060302', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (173, '安图', 'antu', '101060303', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (174, '汪清', 'wangqing', '101060304', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (175, '和龙', 'helong', '101060305', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (176, '龙井', 'longjing', '101060307', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (177, '珲春', 'hunchun', '101060308', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (178, '图们', 'tumen', '101060309', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (179, '四平', 'siping', '101060401', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (180, '双辽', 'shuangliao', '101060402', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (181, '梨树', 'lishu', '101060403', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (182, '公主岭', 'gongzhuling', '101060404', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (183, '伊通', 'yitong', '101060405', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (184, '通化', 'tonghua', '101060501', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (185, '梅河口', 'meihekou', '101060502', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (186, '柳河', 'liuhe', '101060503', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (187, '辉南', 'huinan', '101060504', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (188, '集安', 'jian', '101060505', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (189, '通化县', 'tonghuaxian', '101060506', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (190, '白城', 'baicheng', '101060601', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (191, '洮南', 'taonan', '101060602', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (192, '大安', 'daan', '101060603', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (193, '镇赉', 'zhenlai', '101060604', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (194, '通榆', 'tongyu', '101060605', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (195, '辽源', 'liaoyuan', '101060701', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (196, '东丰', 'dongfeng', '101060702', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (197, '东辽', 'dongliao', '101060703', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (198, '松原', 'songyuan', '101060801', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (199, '乾安', 'qianan', '101060802', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (200, '前郭', 'qianguo', '101060803', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (201, '长岭', 'changling', '101060804', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (202, '扶余', 'fuyu', '101060805', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (203, '白山', 'baishan', '101060901', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (204, '靖宇', 'jingyu', '101060902', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (205, '临江', 'linjiang', '101060903', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (206, '东岗', 'donggang', '101060904', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (207, '长白', 'changbai', '101060905', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (208, '抚松', 'fusong', '101060906', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (209, '江源', 'jiangyuan', '101060907', '吉林', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (210, '沈阳', 'shenyang', '101070101', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (211, '辽中', 'liaozhong', '101070103', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (212, '康平', 'kangping', '101070104', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (213, '法库', 'faku', '101070105', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (214, '新民', 'xinming', '101070106', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (215, '大连', 'dalian', '101070201', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (216, '瓦房店', 'wafangdian', '101070202', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (217, '金州', 'jinzhou', '101070203', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (218, '普兰店', 'pulandian', '101070204', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (219, '旅顺', 'lvshun', '101070205', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (220, '长海', 'changhai', '101070206', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (221, '庄河', 'zhuanghe', '101070207', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (222, '鞍山', 'anshan', '101070301', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (223, '台安', 'taian', '101070302', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (224, '岫岩', 'xiuyan', '101070303', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (225, '海城', 'haicheng', '101070304', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (226, '抚顺', 'fushun', '101070401', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (227, '新宾', 'xinbin', '101070402', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (228, '清原', 'qingyuan', '101070403', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (229, '章党', 'zhangdang', '101070404', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (230, '本溪', 'benxi', '101070501', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (231, '本溪县', 'benxixian', '101070502', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (232, '桓仁', 'huanren', '101070504', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (233, '丹东', 'dandong', '101070601', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (234, '凤城', 'fengcheng', '101070602', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (235, '宽甸', 'kuandian', '101070603', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (236, '东港', 'donggang', '101070604', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (237, '锦州', 'jinzhou', '101070701', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (238, '凌海', 'linghai', '101070702', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (239, '义县', 'yixian', '101070704', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (240, '黑山', 'heishan', '101070705', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (241, '北镇', 'beizhen', '101070706', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (242, '营口', 'yingkou', '101070801', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (243, '大石桥', 'dashiqiao', '101070802', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (244, '盖州', 'gaizhou', '101070803', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (245, '阜新', 'fuxin', '101070901', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (246, '彰武', 'zhangwu', '101070902', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (247, '辽阳', 'liaoyang', '101071001', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (248, '辽阳县', 'liaoyangxian', '101071002', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (249, '灯塔', 'dengta', '101071003', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (250, '弓长岭', 'gongchangling', '101071004', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (251, '铁岭', 'tieling', '101071101', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (252, '开原', 'kaiyuan', '101071102', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (253, '昌图', 'changtu', '101071103', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (254, '西丰', 'xifeng', '101071104', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (255, '调兵山', 'tiefa', '101071105', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (256, '朝阳', 'chaoyang', '101071201', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (257, '凌源', 'lingyuan', '101071203', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (258, '喀左', 'kazuo', '101071204', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (259, '北票', 'beipiao', '101071205', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (260, '建平县', 'jianpingxian', '101071207', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (261, '盘锦', 'panjin', '101071301', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (262, '大洼', 'dawa', '101071302', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (263, '盘山', 'panshan', '101071303', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (264, '葫芦岛', 'huludao', '101071401', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (265, '建昌', 'jianchang', '101071402', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (266, '绥中', 'suizhong', '101071403', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (267, '兴城', 'xingcheng', '101071404', '辽宁', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (268, '呼和浩特', 'huhehaote', '101080101', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (269, '土左旗', 'tuzuoqi', '101080102', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (270, '托县', 'tuoxian', '101080103', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (271, '和林', 'helin', '101080104', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (272, '清水河', 'qingshuihe', '101080105', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (273, '呼市郊区', 'hushijiaoqu', '101080106', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (274, '武川', 'wuchuan', '101080107', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (275, '包头', 'baotou', '101080201', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (276, '白云鄂博', 'baiyunebo', '101080202', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (277, '满都拉', 'mandula', '101080203', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (278, '土右旗', 'tuyouqi', '101080204', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (279, '固阳', 'guyang', '101080205', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (280, '达茂旗', 'damaoqi', '101080206', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (281, '希拉穆仁', 'xilamuren', '101080207', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (282, '乌海', 'wuhai', '101080301', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (283, '集宁', 'jining', '101080401', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (284, '卓资', 'zhuozi', '101080402', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (285, '化德', 'huade', '101080403', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (286, '商都', 'shangdu', '101080404', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (287, '兴和', 'xinghe', '101080406', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (288, '凉城', 'liangcheng', '101080407', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (289, '察右前旗', 'chayouqianqi', '101080408', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (290, '察右中旗', 'chayouzhongqi', '101080409', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (291, '察右后旗', 'chayouhouqi', '101080410', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (292, '四子王旗', 'siziwangqi', '101080411', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (293, '丰镇', 'fengzhen', '101080412', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (294, '通辽', 'tongliao', '101080501', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (295, '舍伯吐', 'shebotu', '101080502', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (296, '科左中旗', 'kezuozhongqi', '101080503', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (297, '科左后旗', 'kezuohouqi', '101080504', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (298, '青龙山', 'qinglongshan', '101080505', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (299, '开鲁', 'kailu', '101080506', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (300, '库伦', 'kulun', '101080507', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (301, '奈曼', 'naiman', '101080508', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (302, '扎鲁特', 'zhalute', '101080509', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (303, '高力板', 'gaoliban', '101080510', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (304, '巴雅尔吐胡硕', 'bayaertuhushuo', '101080511', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (305, '赤峰', 'chifeng', '101080601', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (306, '阿鲁旗', 'aluqi', '101080603', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (307, '浩尔吐', 'haoertu', '101080604', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (308, '巴林左旗', 'balinzuoqi', '101080605', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (309, '巴林右旗', 'balinyouqi', '101080606', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (310, '林西', 'linxi', '101080607', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (311, '克什克腾', 'keshiketeng', '101080608', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (312, '翁牛特', 'wengniute', '101080609', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (313, '岗子', 'gangzi', '101080610', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (314, '喀喇沁', 'kalaqin', '101080611', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (315, '八里罕', 'balihan', '101080612', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (316, '宁城', 'ningcheng', '101080613', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (317, '敖汉', 'aohan', '101080614', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (318, '宝国吐', 'baoguotu', '101080615', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (319, '鄂尔多斯', 'eerduosi', '101080701', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (320, '达拉特', 'dalate', '101080703', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (321, '准格尔', 'zhungeer', '101080704', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (322, '鄂前旗', 'eqianqi', '101080705', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (323, '河南', 'henan', '101080706', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (324, '伊克乌素', 'yikewusu', '101080707', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (325, '鄂托克', 'etuoke', '101080708', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (326, '杭锦旗', 'hangjinqi', '101080709', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (327, '乌审旗', 'wushenqi', '101080710', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (328, '伊金霍洛', 'yijinhuoluo', '101080711', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (329, '乌审召', 'wushenzhao', '101080712', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (330, '东胜', 'dongsheng', '101080713', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (331, '临河', 'linhe', '101080801', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (332, '五原', 'wuyuan', '101080802', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (333, '磴口', 'dengkou', '101080803', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (334, '乌前旗', 'wuqianqi', '101080804', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (335, '大佘太', 'dashetai', '101080805', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (336, '乌中旗', 'wuzhongqi', '101080806', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (337, '乌后旗', 'wuhouqi', '101080807', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (338, '海力素', 'hailisu', '101080808', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (339, '那仁宝力格', 'narenbaolige', '101080809', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (340, '杭锦后旗', 'hangjinhouqi', '101080810', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (341, '锡林浩特', 'xilinhaote', '101080901', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (342, '二连浩特', 'erlianhaote', '101080903', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (343, '阿巴嘎', 'abaga', '101080904', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (344, '苏左旗', 'suzuoqi', '101080906', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (345, '苏右旗', 'suyouqi', '101080907', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (346, '朱日和', 'zhurihe', '101080908', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (347, '东乌旗', 'dongwuqi', '101080909', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (348, '西乌旗', 'xiwuqi', '101080910', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (349, '太仆寺', 'taibusiqi', '101080911', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (350, '镶黄旗', 'xianghuang', '101080912', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (351, '正镶白旗', 'zhengxiangbaiqi', '101080913', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (352, '正兰旗', 'zhenglanqi', '101080914', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (353, '多伦', 'duolun', '101080915', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (354, '博克图', 'boketu', '101080916', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (355, '乌拉盖', 'wulagai', '101080917', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (356, '呼伦贝尔', 'hulunbeier', '101081000', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (357, '海拉尔', 'hailaer', '101081001', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (358, '小二沟', 'xiaoergou', '101081002', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (359, '阿荣旗', 'arongqi', '101081003', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (360, '莫力达瓦', 'molidawa', '101081004', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (361, '鄂伦春旗', 'elunchunqi', '101081005', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (362, '鄂温克旗', 'ewenkeqi', '101081006', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (363, '陈旗', 'chenqi', '101081007', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (364, '新左旗', 'xinzuoqi', '101081008', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (365, '新右旗', 'xinyouqi', '101081009', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (366, '满洲里', 'manzhouli', '101081010', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (367, '牙克石', 'yakeshi', '101081011', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (368, '扎兰屯', 'zhalantun', '101081012', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (369, '额尔古纳', 'eerguna', '101081014', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (370, '根河', 'genhe', '101081015', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (371, '图里河', 'tulihe', '101081016', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (372, '乌兰浩特', 'wulanhaote', '101081101', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (373, '阿尔山', 'aershan', '101081102', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (374, '科右中旗', 'keyouzhongqi', '101081103', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (375, '胡尔勒', 'huerle', '101081104', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (376, '扎赉特', 'zhanlaite', '101081105', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (377, '索伦', 'suolun', '101081106', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (378, '突泉', 'tuquan', '101081107', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (379, '霍林郭勒', 'huolinguole', '101081108', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (380, '科右前旗', 'keyouqianqi', '101081109', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (381, '阿左旗', 'azuoqi', '101081201', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (382, '阿右旗', 'ayouqi', '101081202', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (383, '额济纳', 'ejina', '101081203', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (384, '拐子湖', 'guanzihu', '101081204', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (385, '吉兰太', 'jilantai', '101081205', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (386, '锡林高勒', 'xilingaole', '101081206', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (387, '头道湖', 'toudaohu', '101081207', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (388, '中泉子', 'zhongquanzi', '101081208', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (389, '诺尔公', 'nuoergong', '101081209', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (390, '雅布赖', 'yabulai', '101081210', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (391, '乌斯泰', 'wusitai', '101081211', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (392, '孪井滩', 'luanjingtan', '101081212', '内蒙古', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (393, '石家庄', 'shijiazhuang', '101090101', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (394, '井陉', 'jingxing', '101090102', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (395, '正定', 'zhengding', '101090103', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (396, '栾城', 'luancheng', '101090104', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (397, '行唐', 'xingtang', '101090105', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (398, '灵寿', 'lingshou', '101090106', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (399, '高邑', 'gaoyi', '101090107', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (400, '深泽', 'shenze', '101090108', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (401, '赞皇', 'zanhuang', '101090109', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (402, '无极', 'wuji', '101090110', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (403, '平山', 'pingshan', '101090111', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (404, '元氏', 'yuanshi', '101090112', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (405, '赵县', 'zhaoxian', '101090113', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (406, '辛集', 'xinji', '101090114', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (407, '藁城', 'gaocheng', '101090115', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (408, '晋州', 'jinzhou', '101090116', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (409, '新乐', 'xinle', '101090117', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (410, '鹿泉', 'luquan', '101090118', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (411, '保定', 'baoding', '101090201', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (412, '满城', 'mancheng', '101090202', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (413, '阜平', 'fuping', '101090203', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (414, '徐水', 'xushui', '101090204', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (415, '唐县', 'tangxian', '101090205', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (416, '高阳', 'gaoyang', '101090206', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (417, '容城', 'rongcheng', '101090207', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (418, '涞源', 'laiyuan', '101090209', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (419, '望都', 'wangdu', '101090210', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (420, '安新', 'anxin', '101090211', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (421, '易县', 'yixian', '101090212', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (422, '曲阳', 'quyang', '101090214', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (423, '蠡县', 'lixian', '101090215', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (424, '顺平', 'shunping', '101090216', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (425, '雄县', 'xiongxian', '101090217', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (426, '涿州', 'zhuozhou', '101090218', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (427, '定州', 'dingzhou', '101090219', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (428, '安国', 'anguo', '101090220', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (429, '高碑店', 'gaobeidian', '101090221', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (430, '涞水', 'laishui', '101090222', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (431, '定兴', 'dingxing', '101090223', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (432, '清苑', 'qingyuan', '101090224', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (433, '博野', 'boye', '101090225', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (434, '张家口', 'zhangjiakou', '101090301', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (435, '宣化', 'xuanhua', '101090302', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (436, '张北', 'zhangbei', '101090303', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (437, '康保', 'kangbao', '101090304', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (438, '沽源', 'guyuan', '101090305', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (439, '尚义', 'shangyi', '101090306', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (440, '蔚县', 'yuxian', '101090307', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (441, '阳原', 'yangyuan', '101090308', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (442, '怀安', 'huaian', '101090309', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (443, '万全', 'wanquan', '101090310', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (444, '怀来', 'huailai', '101090311', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (445, '涿鹿', 'zhuolu', '101090312', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (446, '赤城', 'chicheng', '101090313', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (447, '崇礼', 'chongli', '101090314', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (448, '承德', 'chengde', '101090402', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (449, '承德县', 'chengdexian', '101090403', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (450, '兴隆', 'xinglong', '101090404', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (451, '平泉', 'pingquan', '101090405', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (452, '滦平', 'luanping', '101090406', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (453, '隆化', 'longhua', '101090407', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (454, '丰宁', 'fengning', '101090408', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (455, '宽城', 'kuancheng', '101090409', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (456, '围场', 'weichang', '101090410', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (457, '唐山', 'tangshan', '101090501', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (458, '丰南', 'fengnan', '101090502', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (459, '丰润', 'fengrun', '101090503', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (460, '滦县', 'luanxian', '101090504', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (461, '滦南', 'luannan', '101090505', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (462, '乐亭', 'leting', '101090506', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (463, '迁西', 'qianxi', '101090507', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (464, '玉田', 'yutian', '101090508', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (465, '唐海', 'tanghai', '101090509', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (466, '遵化', 'zunhua', '101090510', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (467, '迁安', 'qianan', '101090511', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (468, '曹妃甸', 'caofeidian', '101090512', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (469, '廊坊', 'langfang', '101090601', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (470, '固安', 'guan', '101090602', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (471, '永清', 'yongqing', '101090603', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (472, '香河', 'xianghe', '101090604', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (473, '大城', 'dacheng', '101090605', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (474, '文安', 'wenan', '101090606', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (475, '大厂', 'dachang', '101090607', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (476, '霸州', 'bazhou', '101090608', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (477, '三河', 'sanhe', '101090609', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (478, '沧州', 'cangzhou', '101090701', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (479, '青县', 'qingxian', '101090702', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (480, '东光', 'dongguang', '101090703', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (481, '海兴', 'haixing', '101090704', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (482, '盐山', 'yanshan', '101090705', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (483, '肃宁', 'suning', '101090706', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (484, '南皮', 'nanpi', '101090707', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (485, '吴桥', 'wuqiao', '101090708', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (486, '献县', 'xianxian', '101090709', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (487, '孟村', 'mengcun', '101090710', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (488, '泊头', 'botou', '101090711', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (489, '任丘', 'renqiu', '101090712', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (490, '黄骅', 'huanghua', '101090713', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (491, '河间', 'hejian', '101090714', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (492, '沧县', 'cangxian', '101090716', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (493, '衡水', 'hengshui', '101090801', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (494, '枣强', 'zaoqiang', '101090802', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (495, '武邑', 'wuyi', '101090803', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (496, '武强', 'wuqiang', '101090804', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (497, '饶阳', 'raoyang', '101090805', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (498, '安平', 'anping', '101090806', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (499, '故城', 'gucheng', '101090807', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (500, '景县', 'jingxian', '101090808', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (501, '阜城', 'fucheng', '101090809', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (502, '冀州', 'jizhou', '101090810', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (503, '深州', 'shenzhou', '101090811', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (504, '邢台', 'xingtai', '101090901', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (505, '临城', 'lincheng', '101090902', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (506, '内丘', 'neiqiu', '101090904', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (507, '柏乡', 'baixiang', '101090905', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (508, '隆尧', 'longyao', '101090906', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (509, '南和', 'nanhe', '101090907', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (510, '宁晋', 'ningjin', '101090908', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (511, '巨鹿', 'julu', '101090909', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (512, '新河', 'xinhe', '101090910', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (513, '广宗', 'guangzong', '101090911', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (514, '平乡', 'pingxiang', '101090912', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (515, '威县', 'weixian', '101090913', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (516, '清河', 'qinghe', '101090914', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (517, '临西', 'linxi', '101090915', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (518, '南宫', 'nangong', '101090916', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (519, '沙河', 'shahe', '101090917', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (520, '任县', 'renxian', '101090918', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (521, '邯郸', 'handan', '101091001', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (522, '峰峰', 'fengfeng', '101091002', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (523, '临漳', 'linzhang', '101091003', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (524, '成安', 'chengan', '101091004', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (525, '大名', 'daming', '101091005', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (526, '涉县', 'shexian', '101091006', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (527, '磁县', 'cixian', '101091007', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (528, '肥乡', 'feixiang', '101091008', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (529, '永年', 'yongnian', '101091009', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (530, '邱县', 'qiuxian', '101091010', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (531, '鸡泽', 'jize', '101091011', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (532, '广平', 'guangping', '101091012', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (533, '馆陶', 'guantao', '101091013', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (534, '魏县', 'weixian', '101091014', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (535, '曲周', 'quzhou', '101091015', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (536, '武安', 'wuan', '101091016', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (537, '秦皇岛', 'qinhuangdao', '101091101', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (538, '青龙', 'qinglong', '101091102', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (539, '昌黎', 'changli', '101091103', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (540, '抚宁', 'funing', '101091104', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (541, '卢龙', 'lulong', '101091105', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (542, '北戴河', 'beidaihe', '101091106', '河北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (543, '太原', 'taiyuan', '101100101', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (544, '清徐', 'qingxu', '101100102', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (545, '阳曲', 'yangqu', '101100103', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (546, '娄烦', 'loufan', '101100104', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (547, '古交', 'gujiao', '101100105', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (548, '尖草坪区', 'jiancaopingqu', '101100106', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (549, '小店区', 'xiaodianqu', '101100107', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (550, '大同', 'datong', '101100201', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (551, '阳高', 'yanggao', '101100202', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (552, '大同县', 'datongxian', '101100203', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (553, '天镇', 'tianzhen', '101100204', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (554, '广灵', 'guangling', '101100205', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (555, '灵丘', 'lingqiu', '101100206', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (556, '浑源', 'hunyuan', '101100207', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (557, '左云', 'zuoyun', '101100208', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (558, '阳泉', 'yangquan', '101100301', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (559, '盂县', 'yuxian', '101100302', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (560, '平定', 'pingding', '101100303', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (561, '晋中', 'jinzhong', '101100401', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (562, '榆次', 'yuci', '101100402', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (563, '榆社', 'yushe', '101100403', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (564, '左权', 'zuoquan', '101100404', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (565, '和顺', 'heshun', '101100405', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (566, '昔阳', 'xiyang', '101100406', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (567, '寿阳', 'shouyang', '101100407', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (568, '太谷', 'taigu', '101100408', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (569, '祁县', 'qixian', '101100409', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (570, '平遥', 'pingyao', '101100410', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (571, '灵石', 'lingshi', '101100411', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (572, '介休', 'jiexiu', '101100412', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (573, '长治', 'changzhi', '101100501', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (574, '黎城', 'licheng', '101100502', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (575, '屯留', 'tunliu', '101100503', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (576, '潞城', 'lucheng', '101100504', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (577, '襄垣', 'xiangyuan', '101100505', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (578, '平顺', 'pingshun', '101100506', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (579, '武乡', 'wuxiang', '101100507', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (580, '沁县', 'qinxian', '101100508', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (581, '长子', 'zhangzi', '101100509', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (582, '沁源', 'qinyuan', '101100510', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (583, '壶关', 'huguan', '101100511', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (584, '晋城', 'jincheng', '101100601', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (585, '沁水', 'qinshui', '101100602', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (586, '阳城', 'yangcheng', '101100603', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (587, '陵川', 'lingchuan', '101100604', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (588, '高平', 'gaoping', '101100605', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (589, '泽州', 'zezhou', '101100606', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (590, '临汾', 'linfen', '101100701', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (591, '曲沃', 'quwo', '101100702', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (592, '永和', 'yonghe', '101100703', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (593, '隰县', 'xixian', '101100704', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (594, '大宁', 'daning', '101100705', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (595, '吉县', 'jixian', '101100706', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (596, '襄汾', 'xiangfen', '101100707', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (597, '蒲县', 'puxian', '101100708', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (598, '汾西', 'fenxi', '101100709', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (599, '洪洞', 'hongtong', '101100710', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (600, '霍州', 'huozhou', '101100711', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (601, '乡宁', 'xiangning', '101100712', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (602, '翼城', 'yicheng', '101100713', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (603, '侯马', 'houma', '101100714', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (604, '浮山', 'fushan', '101100715', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (605, '安泽', 'anze', '101100716', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (606, '古县', 'guxian', '101100717', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (607, '运城', 'yuncheng', '101100801', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (608, '临猗', 'linyi', '101100802', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (609, '稷山', 'jishan', '101100803', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (610, '万荣', 'wanrong', '101100804', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (611, '河津', 'hejin', '101100805', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (612, '新绛', 'xinjiang', '101100806', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (613, '绛县', 'jiangxian', '101100807', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (614, '闻喜', 'wenxi', '101100808', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (615, '垣曲', 'yuanqu', '101100809', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (616, '永济', 'yongji', '101100810', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (617, '芮城', 'ruicheng', '101100811', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (618, '夏县', 'xiaxian', '101100812', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (619, '平陆', 'pinglu', '101100813', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (620, '朔州', 'shuozhou', '101100901', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (621, '平鲁', 'pinglu', '101100902', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (622, '山阴', 'shanyin', '101100903', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (623, '右玉', 'youyu', '101100904', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (624, '应县', 'yingxian', '101100905', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (625, '怀仁', 'huairen', '101100906', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (626, '忻州', 'xinzhou', '101101001', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (627, '定襄', 'dingxiang', '101101002', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (628, '五台县', 'wutaixian', '101101003', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (629, '河曲', 'hequ', '101101004', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (630, '偏关', 'pianguan', '101101005', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (631, '神池', 'shenchi', '101101006', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (632, '宁武', 'ningwu', '101101007', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (633, '代县', 'daixian', '101101008', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (634, '繁峙', 'fanshi', '101101009', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (635, '五台山', 'wutaishan', '101101010', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (636, '保德', 'bode', '101101011', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (637, '静乐', 'jingle', '101101012', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (638, '岢岚', 'kelan', '101101013', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (639, '五寨', 'wuzhai', '101101014', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (640, '原平', 'yuanping', '101101015', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (641, '吕梁', 'lvliang', '101101100', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (642, '离石', 'lishi', '101101101', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (643, '临县', 'linxian', '101101102', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (644, '兴县', 'xingxian', '101101103', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (645, '岚县', 'lanxian', '101101104', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (646, '柳林', 'liulin', '101101105', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (647, '石楼', 'shilou', '101101106', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (648, '方山', 'fangshan', '101101107', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (649, '交口', 'jiaokou', '101101108', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (650, '中阳', 'zhongyang', '101101109', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (651, '孝义', 'xiaoyi', '101101110', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (652, '汾阳', 'fenyang', '101101111', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (653, '文水', 'wenshui', '101101112', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (654, '交城', 'jiaocheng', '101101113', '山西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (655, '西安', 'xian', '101110101', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (656, '长安', 'changan', '101110102', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (657, '临潼', 'lintong', '101110103', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (658, '蓝田', 'lantian', '101110104', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (659, '周至', 'zhouzhi', '101110105', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (660, '户县', 'huxian', '101110106', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (661, '高陵', 'gaoling', '101110107', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (662, '咸阳', 'xianyang', '101110200', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (663, '三原', 'sanyuan', '101110201', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (664, '礼泉', 'liquan', '101110202', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (665, '永寿', 'yongshou', '101110203', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (666, '淳化', 'chunhua', '101110204', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (667, '泾阳', 'jingyang', '101110205', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (668, '武功', 'wugong', '101110206', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (669, '乾县', 'qianxian', '101110207', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (670, '彬县', 'binxian', '101110208', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (671, '长武', 'changwu', '101110209', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (672, '旬邑', 'xunyi', '101110210', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (673, '兴平', 'xingping', '101110211', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (674, '延安', 'yanan', '101110300', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (675, '延长', 'yanchang', '101110301', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (676, '延川', 'yanchuan', '101110302', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (677, '子长', 'zichang', '101110303', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (678, '宜川', 'yichuan', '101110304', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (679, '富县', 'fuxian', '101110305', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (680, '志丹', 'zhidan', '101110306', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (681, '安塞', 'ansai', '101110307', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (682, '甘泉', 'ganquan', '101110308', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (683, '洛川', 'luochuan', '101110309', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (684, '黄陵', 'huangling', '101110310', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (685, '黄龙', 'huanglong', '101110311', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (686, '吴起', 'wuqi', '101110312', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (687, '榆林', 'yulin', '101110401', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (688, '府谷', 'fugu', '101110402', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (689, '神木', 'shenmu', '101110403', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (690, '佳县', 'jiaxian', '101110404', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (691, '定边', 'dingbian', '101110405', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (692, '靖边', 'jingbian', '101110406', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (693, '横山', 'hengshan', '101110407', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (694, '米脂', 'mizhi', '101110408', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (695, '子洲', 'zizhou', '101110409', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (696, '绥德', 'suide', '101110410', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (697, '吴堡', 'wubu', '101110411', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (698, '清涧', 'jingjian', '101110412', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (699, '榆阳', 'yuyang', '101110413', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (700, '渭南', 'weinan', '101110501', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (701, '华县', 'huaxian', '101110502', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (702, '潼关', 'tongguan', '101110503', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (703, '大荔', 'dali', '101110504', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (704, '白水', 'baishui', '101110505', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (705, '富平', 'fuping', '101110506', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (706, '蒲城', 'pucheng', '101110507', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (707, '澄城', 'chengcheng', '101110508', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (708, '合阳', 'heyang', '101110509', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (709, '韩城', 'hancheng', '101110510', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (710, '华阴', 'huayin', '101110511', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (711, '商洛', 'shangluo', '101110601', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (712, '洛南', 'luonan', '101110602', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (713, '柞水', 'zhashui', '101110603', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (714, '商州', 'shangxian', '101110604', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (715, '镇安', 'zhenan', '101110605', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (716, '丹凤', 'danfeng', '101110606', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (717, '商南', 'shangnan', '101110607', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (718, '山阳', 'shanyang', '101110608', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (719, '安康', 'ankang', '101110701', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (720, '紫阳', 'ziyang', '101110702', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (721, '石泉', 'shiquan', '101110703', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (722, '汉阴', 'hanyin', '101110704', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (723, '旬阳', 'xunyang', '101110705', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (724, '岚皋', 'langao', '101110706', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (725, '平利', 'pingli', '101110707', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (726, '白河', 'baihe', '101110708', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (727, '镇坪', 'zhenping', '101110709', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (728, '宁陕', 'ningshan', '101110710', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (729, '汉中', 'hanzhong', '101110801', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (730, '略阳', 'lueyang', '101110802', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (731, '勉县', 'mianxian', '101110803', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (732, '留坝', 'liuba', '101110804', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (733, '洋县', 'yangxian', '101110805', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (734, '城固', 'chenggu', '101110806', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (735, '西乡', 'xixiang', '101110807', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (736, '佛坪', 'fuoping', '101110808', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (737, '宁强', 'ningqiang', '101110809', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (738, '南郑', 'nanzheng', '101110810', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (739, '镇巴', 'zhenba', '101110811', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (740, '宝鸡', 'baoji', '101110901', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (741, '千阳', 'qianyang', '101110903', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (742, '麟游', 'linyou', '101110904', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (743, '岐山', 'qishan', '101110905', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (744, '凤翔', 'fengxiang', '101110906', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (745, '扶风', 'fufeng', '101110907', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (746, '眉县', 'meixian', '101110908', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (747, '太白', 'taibai', '101110909', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (748, '凤县', 'fengxian', '101110910', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (749, '陇县', 'longxian', '101110911', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (750, '陈仓', 'chencang', '101110912', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (751, '铜川', 'tongchuan', '101111001', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (752, '耀县', 'yaoxian', '101111002', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (753, '宜君', 'yijun', '101111003', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (754, '耀州', 'yaozhou', '101111004', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (755, '杨凌', 'yangling', '101111101', '陕西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (756, '济南', 'jinan', '101120101', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (757, '长清', 'changqing', '101120102', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (758, '商河', 'shanghe', '101120103', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (759, '章丘', 'zhangqiu', '101120104', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (760, '平阴', 'pingyin', '101120105', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (761, '济阳', 'jiyang', '101120106', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (762, '天桥', 'tianqiao', '101120107', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (763, '青岛', 'qingdao', '101120201', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (764, '崂山', 'laoshan', '101120202', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (765, '即墨', 'jimo', '101120204', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (766, '胶州', 'jiaozhou', '101120205', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (767, '胶南', 'jiaonan', '101120206', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (768, '莱西', 'laixi', '101120207', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (769, '平度', 'pingdu', '101120208', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (770, '淄博', 'zibo', '101120301', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (771, '淄川', 'zichuan', '101120302', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (772, '博山', 'boshan', '101120303', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (773, '高青', 'gaoqing', '101120304', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (774, '周村', 'zhoucun', '101120305', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (775, '沂源', 'yiyuan', '101120306', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (776, '桓台', 'huantai', '101120307', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (777, '临淄', 'linzi', '101120308', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (778, '德州', 'dezhou', '101120401', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (779, '武城', 'wucheng', '101120402', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (780, '临邑', 'linyi', '101120403', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (781, '陵县', 'lingxian', '101120404', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (782, '齐河', 'qihe', '101120405', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (783, '乐陵', 'leling', '101120406', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (784, '庆云', 'qingyun', '101120407', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (785, '平原', 'pingyuan', '101120408', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (786, '宁津', 'ningjin', '101120409', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (787, '夏津', 'xiajin', '101120410', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (788, '禹城', 'yucheng', '101120411', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (789, '烟台', 'yantai', '101120501', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (790, '莱州', 'laizhou', '101120502', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (791, '长岛', 'changdao', '101120503', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (792, '蓬莱', 'penglai', '101120504', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (793, '龙口', 'longkou', '101120505', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (794, '招远', 'zhaoyuan', '101120506', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (795, '栖霞', 'qixia', '101120507', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (796, '福山', 'fushan', '101120508', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (797, '牟平', 'moup', '101120509', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (798, '莱阳', 'laiyang', '101120510', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (799, '海阳', 'haiyang', '101120511', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (800, '潍坊', 'weifang', '101120601', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (801, '青州', 'qingzhou', '101120602', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (802, '寿光', 'shouguang', '101120603', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (803, '临朐', 'linqu', '101120604', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (804, '昌乐', 'changle', '101120605', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (805, '昌邑', 'changyi', '101120606', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (806, '安丘', 'anqiu', '101120607', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (807, '高密', 'gaomi', '101120608', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (808, '诸城', 'zhucheng', '101120609', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (809, '济宁', 'jining', '101120701', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (810, '嘉祥', 'jiaxiang', '101120702', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (811, '微山', 'weishan', '101120703', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (812, '鱼台', 'yutai', '101120704', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (813, '兖州', 'yanzhou', '101120705', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (814, '金乡', 'jinxiang', '101120706', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (815, '汶上', 'wenshang', '101120707', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (816, '泗水', 'sishui', '101120708', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (817, '梁山', 'liangshan', '101120709', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (818, '曲阜', 'qufu', '101120710', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (819, '邹城', 'zoucheng', '101120711', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (820, '泰安', 'taian', '101120801', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (821, '新泰', 'xintai', '101120802', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (822, '肥城', 'feicheng', '101120804', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (823, '东平', 'dongping', '101120805', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (824, '宁阳', 'ningyang', '101120806', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (825, '临沂', 'linyi', '101120901', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (826, '莒南', 'junan', '101120902', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (827, '沂南', 'yinan', '101120903', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (828, '苍山', 'cangshan', '101120904', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (829, '临沭', 'linshu', '101120905', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (830, '郯城', 'tancheng', '101120906', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (831, '蒙阴', 'mengyin', '101120907', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (832, '平邑', 'pingyi', '101120908', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (833, '费县', 'feixian', '101120909', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (834, '沂水', 'yishui', '101120910', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (835, '菏泽', 'heze', '101121001', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (836, '鄄城', 'juancheng', '101121002', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (837, '郓城', 'yuncheng', '101121003', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (838, '东明', 'dongming', '101121004', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (839, '定陶', 'dingtao', '101121005', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (840, '巨野', 'juye', '101121006', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (841, '曹县', 'caoxian', '101121007', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (842, '成武', 'chengwu', '101121008', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (843, '单县', 'shanxian', '101121009', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (844, '滨州', 'binzhou', '101121101', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (845, '博兴', 'boxing', '101121102', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (846, '无棣', 'wudi', '101121103', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (847, '阳信', 'yangxin', '101121104', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (848, '惠民', 'huimin', '101121105', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (849, '沾化', 'zhanhua', '101121106', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (850, '邹平', 'zouping', '101121107', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (851, '东营', 'dongying', '101121201', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (852, '河口', 'hekou', '101121202', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (853, '垦利', 'kenli', '101121203', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (854, '利津', 'lijin', '101121204', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (855, '广饶', 'guangrao', '101121205', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (856, '威海', 'weihai', '101121301', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (857, '文登', 'wendeng', '101121302', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (858, '荣成', 'rongcheng', '101121303', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (859, '乳山', 'rushan', '101121304', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (860, '成山头', 'chengshantou', '101121305', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (861, '石岛', 'shidao', '101121306', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (862, '枣庄', 'zaozhuang', '101121401', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (863, '薛城', 'xuecheng', '101121402', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (864, '峄城', 'yicheng', '101121403', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (865, '台儿庄', 'taierzhuang', '101121404', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (866, '滕州', 'tengzhou', '101121405', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (867, '日照', 'rizhao', '101121501', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (868, '五莲', 'wulian', '101121502', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (869, '莒县', 'juxian', '101121503', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (870, '莱芜', 'laiwu', '101121601', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (871, '聊城', 'liaocheng', '101121701', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (872, '冠县', 'guanxian', '101121702', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (873, '阳谷', 'yanggu', '101121703', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (874, '高唐', 'gaotang', '101121704', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (875, '茌平', 'chiping', '101121705', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (876, '东阿', 'donge', '101121706', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (877, '临清', 'linqing', '101121707', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (878, '莘县', 'shenxian', '101121709', '山东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (879, '乌鲁木齐', 'wulumuqi', '101130101', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (880, '小渠子', 'xiaoquzi', '101130103', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (881, '巴仑台', 'baluntai', '101130104', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (882, '达坂城', 'dabancheng', '101130105', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (883, '乌鲁木齐牧试站', 'wulumuqimushizhan', '101130108', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (884, '天池', 'tianchi', '101130109', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (885, '白杨沟', 'baiyanggou', '101130110', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (886, '克拉玛依', 'kelamayi', '101130201', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (887, '乌尔禾', 'wuerhe', '101130202', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (888, '白碱滩', 'baijiantan', '101130203', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (889, '石河子', 'shihezi', '101130301', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (890, '炮台', 'paotai', '101130302', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (891, '莫索湾', 'mosuowan', '101130303', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (892, '昌吉', 'changji', '101130401', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (893, '呼图壁', 'hutubi', '101130402', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (894, '米泉', 'miquan', '101130403', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (895, '阜康', 'fukang', '101130404', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (896, '吉木萨尔', 'jimusaer', '101130405', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (897, '奇台', 'qitai', '101130406', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (898, '玛纳斯', 'manasi', '101130407', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (899, '木垒', 'mulei', '101130408', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (900, '蔡家湖', 'caijiahu', '101130409', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (901, '吐鲁番', 'tulufan', '101130501', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (902, '托克逊', 'tuokexun', '101130502', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (903, '克孜勒', 'kezile', '101130503', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (904, '鄯善', 'shanshan', '101130504', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (905, '库尔勒', 'kuerle', '101130601', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (906, '轮台', 'luntai', '101130602', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (907, '尉犁', 'weili', '101130603', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (908, '若羌', 'ruoqiang', '101130604', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (909, '且末', 'qiemo', '101130605', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (910, '和静', 'hejing', '101130606', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (911, '焉耆', 'yanqi', '101130607', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (912, '和硕', 'shuo', '101130608', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (913, '巴音布鲁克', 'bayinbuluke', '101130610', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (914, '铁干里克', 'tieganlike', '101130611', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (915, '博湖', 'bohu', '101130612', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (916, '塔中', 'tazhong', '101130613', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (917, '阿拉尔', 'alaer', '101130701', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (918, '阿克苏', 'akesu', '101130801', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (919, '乌什', 'wushi', '101130802', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (920, '温宿', 'wensu', '101130803', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (921, '拜城', 'baicheng', '101130804', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (922, '新和', 'xinhe', '101130805', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (923, '沙雅', 'shaya', '101130806', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (924, '库车', 'kuche', '101130807', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (925, '柯坪', 'keping', '101130808', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (926, '阿瓦提', 'awati', '101130809', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (927, '喀什', 'kashi', '101130901', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (928, '英吉沙', 'yingjisha', '101130902', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (929, '塔什库尔干', 'tashikuergan', '101130903', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (930, '麦盖提', 'maigaiti', '101130904', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (931, '莎车', 'shache', '101130905', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (932, '叶城', 'yecheng', '101130906', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (933, '泽普', 'zepu', '101130907', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (934, '巴楚', 'bachu', '101130908', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (935, '岳普湖', 'yuepuhu', '101130909', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (936, '伽师', 'jiashi', '101130910', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (937, '疏附', 'shufu', '101130911', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (938, '疏勒', 'shule', '101130912', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (939, '伊宁', 'yining', '101131001', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (940, '察布查尔', 'chabuchaer', '101131002', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (941, '尼勒克', 'nileke', '101131003', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (942, '伊宁县', 'yiningxian', '101131004', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (943, '巩留', 'gongliu', '101131005', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (944, '新源', 'xinyuan', '101131006', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (945, '昭苏', 'zhaosu', '101131007', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (946, '特克斯', 'tekesi', '101131008', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (947, '霍城', 'huocheng', '101131009', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (948, '霍尔果斯', 'huoerguosi', '101131010', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (949, '奎屯', 'kuitunshi', '101131011', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (950, '塔城', 'tacheng', '101131101', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (951, '裕民', 'yumin', '101131102', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (952, '额敏', 'emin', '101131103', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (953, '和布克赛尔', 'hebukesaier', '101131104', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (954, '托里', 'tuoli', '101131105', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (955, '乌苏', 'wusu', '101131106', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (956, '沙湾', 'shawan', '101131107', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (957, '和丰', 'hefeng', '101131108', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (958, '哈密', 'hami', '101131201', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (959, '巴里坤', 'balikun', '101131203', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (960, '伊吾', 'yiwu', '101131204', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (961, '和田', 'hetian', '101131301', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (962, '皮山', 'pishan', '101131302', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (963, '策勒', 'cele', '101131303', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (964, '墨玉', 'moyu', '101131304', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (965, '洛浦', 'luopu', '101131305', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (966, '民丰', 'minfeng', '101131306', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (967, '于田', 'yutian', '101131307', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (968, '阿勒泰', 'aletai', '101131401', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (969, '哈巴河', 'habahe', '101131402', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (970, '吉木乃', 'jimunai', '101131405', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (971, '布尔津', 'buerjin', '101131406', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (972, '福海', 'fuhai', '101131407', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (973, '富蕴', 'fuyun', '101131408', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (974, '青河', 'qinghe', '101131409', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (975, '阿图什', 'atushi', '101131501', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (976, '乌恰', 'wuqia', '101131502', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (977, '阿克陶', 'aketao', '101131503', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (978, '阿合奇', 'aheqi', '101131504', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (979, '博乐', 'bole', '101131601', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (980, '温泉', 'wenquan', '101131602', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (981, '精河', 'jinghe', '101131603', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (982, '阿拉山口', 'alashankou', '101131606', '新疆', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (983, '拉萨', 'lasa', '101140101', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (984, '当雄', 'dangxiong', '101140102', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (985, '尼木', 'nimu', '101140103', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (986, '林周', 'linzhou', '101140104', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (987, '堆龙德庆', 'duilongdeqing', '101140105', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (988, '曲水', 'qushui', '101140106', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (989, '达孜', 'dazi', '101140107', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (990, '墨竹工卡', 'mozhugongka', '101140108', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (991, '日喀则', 'rikaze', '101140201', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (992, '拉孜', 'lazi', '101140202', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (993, '南木林', 'nanmulin', '101140203', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (994, '聂拉木', 'nielamu', '101140204', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (995, '定日', 'anri', '101140205', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (996, '江孜', 'jiangzi', '101140206', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (997, '帕里', 'pali', '101140207', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (998, '仲巴', 'zhongba', '101140208', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (999, '萨嘎', 'saga', '101140209', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1000, '吉隆', 'jilong', '101140210', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1001, '昂仁', 'angren', '101140211', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1002, '定结', 'dingjie', '101140212', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1003, '萨迦', 'sajia', '101140213', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1004, '谢通门', 'xietongmen', '101140214', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1005, '岗巴', 'gangba', '101140216', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1006, '白朗', 'bailang', '101140217', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1007, '亚东', 'yadong', '101140218', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1008, '康马', 'kangma', '101140219', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1009, '仁布', 'renbu', '101140220', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1010, '山南', 'shannan', '101140301', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1011, '贡嘎', 'gongga', '101140302', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1012, '札囊', 'zhanan', '101140303', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1013, '加查', 'jiacha', '101140304', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1014, '浪卡子', 'langkazi', '101140305', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1015, '错那', 'cuona', '101140306', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1016, '隆子', 'longzi', '101140307', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1017, '泽当', 'zedang', '101140308', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1018, '乃东', 'naidong', '101140309', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1019, '桑日', 'sangri', '101140310', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1020, '洛扎', 'luozha', '101140311', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1021, '措美', 'cuomei', '101140312', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1022, '琼结', 'qiongjie', '101140313', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1023, '曲松', 'qusong', '101140314', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1024, '林芝', 'linzhi', '101140401', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1025, '波密', 'bomi', '101140402', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1026, '米林', 'milin', '101140403', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1027, '察隅', 'chayu', '101140404', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1028, '工布江达', 'gongbujiangda', '101140405', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1029, '朗县', 'langxian', '101140406', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1030, '墨脱', 'motuo', '101140407', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1031, '昌都', 'changdu', '101140501', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1032, '丁青', 'dingqing', '101140502', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1033, '边坝', 'bianba', '101140503', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1034, '洛隆', 'luolong', '101140504', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1035, '左贡', 'zuogong', '101140505', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1036, '芒康', 'mangkang', '101140506', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1037, '类乌齐', 'leiwuqi', '101140507', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1038, '八宿', 'basu', '101140508', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1039, '江达', 'jiangda', '101140509', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1040, '察雅', 'chaya', '101140510', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1041, '贡觉', 'gongjue', '101140511', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1042, '那曲', 'naqu', '101140601', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1043, '尼玛', 'nima', '101140602', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1044, '嘉黎', 'jiali', '101140603', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1045, '班戈', 'bange', '101140604', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1046, '安多', 'anduo', '101140605', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1047, '索县', 'suoxian', '101140606', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1048, '聂荣', 'nierong', '101140607', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1049, '巴青', 'baqing', '101140608', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1050, '比如', 'biru', '101140609', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1051, '阿里', 'ali', '101140701', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1052, '改则', 'gaize', '101140702', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1053, '申扎', 'shenzha', '101140703', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1054, '狮泉河', 'shiquanhe', '101140704', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1055, '普兰', 'pulan', '101140705', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1056, '札达', 'zhada', '101140706', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1057, '噶尔', 'gaer', '101140707', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1058, '日土', 'ritu', '101140708', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1059, '革吉', 'geji', '101140709', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1060, '措勤', 'cuoqin', '101140710', '西藏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1061, '西宁', 'xining', '101150101', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1062, '大通', 'datong', '101150102', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1063, '湟源', 'huangyuan', '101150103', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1064, '湟中', 'huangzhong', '101150104', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1065, '海东', 'haidong', '101150201', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1066, '乐都', 'ledu', '101150202', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1067, '民和', 'minhe', '101150203', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1068, '互助', 'huzhu', '101150204', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1069, '化隆', 'hualong', '101150205', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1070, '循化', 'xunhua', '101150206', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1071, '冷湖', 'lenghu', '101150207', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1072, '平安', 'pingan', '101150208', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1073, '黄南', 'huangnan', '101150301', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1074, '尖扎', 'jianzha', '101150302', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1075, '泽库', 'zeku', '101150303', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1076, '河南', 'henan', '101150304', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1077, '同仁', 'tongren', '101150305', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1078, '海南', 'hainan', '101150401', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1079, '贵德', 'guide', '101150404', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1080, '兴海', 'xinghai', '101150406', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1081, '贵南', 'guinan', '101150407', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1082, '同德', 'tongde', '101150408', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1083, '共和', 'gonghe', '101150409', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1084, '果洛', 'guoluo', '101150501', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1085, '班玛', 'banma', '101150502', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1086, '甘德', 'gande', '101150503', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1087, '达日', 'dari', '101150504', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1088, '久治', 'jiuzhi', '101150505', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1089, '玛多', 'madu', '101150506', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1090, '多县', 'duoxian', '101150507', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1091, '玛沁', 'maqin', '101150508', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1092, '玉树', 'yushu', '101150601', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1093, '称多', 'chenduo', '101150602', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1094, '治多', 'zhiduo', '101150603', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1095, '杂多', 'zaduo', '101150604', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1096, '囊谦', 'nangqian', '101150605', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1097, '曲麻莱', 'qumacai', '101150606', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1098, '海西', 'haixi', '101150701', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1099, '天峻', 'tianjun', '101150708', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1100, '乌兰', 'wulan', '101150709', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1101, '茫崖', 'mangai', '101150712', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1102, '大柴旦', 'dachaidan', '101150713', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1103, '德令哈', 'delingha', '101150716', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1104, '海北', 'haibei', '101150801', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1105, '门源', 'menyuan', '101150802', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1106, '祁连', 'qilian', '101150803', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1107, '海晏', 'haiman', '101150804', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1108, '刚察', 'gangcha', '101150806', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1109, '格尔木', 'geermu', '101150901', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1110, '都兰', 'dulan', '101150902', '青海', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1111, '兰州', 'lanzhou', '101160101', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1112, '皋兰', 'gaolan', '101160102', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1113, '永登', 'yongdeng', '101160103', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1114, '榆中', 'yuzhong', '101160104', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1115, '定西', 'dingxi', '101160201', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1116, '通渭', 'tongwei', '101160202', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1117, '陇西', 'longxi', '101160203', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1118, '渭源', 'weiyuan', '101160204', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1119, '临洮', 'lintao', '101160205', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1120, '漳县', 'zhangxian', '101160206', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1121, '岷县', 'minxian', '101160207', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1122, '安定', 'anding', '101160208', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1123, '平凉', 'pingliang', '101160301', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1124, '泾川', 'jingchuan', '101160302', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1125, '灵台', 'lingtai', '101160303', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1126, '崇信', 'chongxin', '101160304', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1127, '华亭', 'huating', '101160305', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1128, '庄浪', 'zhuanglang', '101160306', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1129, '静宁', 'jingning', '101160307', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1130, '崆峒', 'kongtong', '101160308', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1131, '庆阳', 'qingyang', '101160401', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1132, '西峰', 'xifeng', '101160402', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1133, '环县', 'huanxian', '101160403', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1134, '华池', 'huachi', '101160404', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1135, '合水', 'heshui', '101160405', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1136, '正宁', 'zhengning', '101160406', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1137, '宁县', 'ningxian', '101160407', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1138, '镇原', 'zhenyuan', '101160408', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1139, '庆城', 'qingcheng', '101160409', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1140, '武威', 'wuwei', '101160501', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1141, '民勤', 'minqin', '101160502', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1142, '古浪', 'gulang', '101160503', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1143, '天祝', 'tianzhu', '101160505', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1144, '金昌', 'jinchang', '101160601', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1145, '永昌', 'yongchang', '101160602', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1146, '张掖', 'zhangye', '101160701', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1147, '肃南', 'sunan', '101160702', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1148, '民乐', 'minle', '101160703', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1149, '临泽', 'linze', '101160704', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1150, '高台', 'gaotai', '101160705', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1151, '山丹', 'shandan', '101160706', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1152, '酒泉', 'jiuquan', '101160801', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1153, '金塔', 'jinta', '101160803', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1154, '阿克塞', 'akesai', '101160804', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1155, '瓜州', 'guazhou', '101160805', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1156, '肃北', 'subei', '101160806', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1157, '玉门', 'yumen', '101160807', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1158, '敦煌', 'dunhuang', '101160808', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1159, '天水', 'tianshui', '101160901', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1160, '清水', 'qingshui', '101160903', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1161, '秦安', 'qinan', '101160904', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1162, '甘谷', 'gangu', '101160905', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1163, '武山', 'wushan', '101160906', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1164, '张家川', 'zhangjiachuan', '101160907', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1165, '麦积', 'maiji', '101160908', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1166, '武都', 'wudu', '101161001', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1167, '成县', 'chengxian', '101161002', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1168, '文县', 'wenxian', '101161003', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1169, '宕昌', 'dangchang', '101161004', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1170, '康县', 'kangxian', '101161005', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1171, '西和', 'xihe', '101161006', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1172, '礼县', 'lixian', '101161007', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1173, '徽县', 'huixian', '101161008', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1174, '两当', 'liangdang', '101161009', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1175, '临夏', 'linxia', '101161101', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1176, '康乐', 'kangle', '101161102', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1177, '永靖', 'yongjing', '101161103', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1178, '广河', 'guanghe', '101161104', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1179, '和政', 'hezheng', '101161105', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1180, '东乡', 'dongxiang', '101161106', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1181, '积石山', 'jishishan', '101161107', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1182, '合作', 'hezuo', '101161201', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1183, '临潭', 'lintan', '101161202', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1184, '卓尼', 'zhuoni', '101161203', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1185, '舟曲', 'zhouqu', '101161204', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1186, '迭部', 'diebu', '101161205', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1187, '玛曲', 'maqu', '101161206', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1188, '碌曲', 'luqu', '101161207', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1189, '夏河', 'xiahe', '101161208', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1190, '白银', 'baiyin', '101161301', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1191, '靖远', 'jingyuan', '101161302', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1192, '会宁', 'huining', '101161303', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1193, '平川', 'pingchuan', '101161304', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1194, '景泰', 'jingtai', '101161305', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1195, '嘉峪关', 'jiayuguan', '101161401', '甘肃', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1196, '银川', 'yinchuan', '101170101', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1197, '永宁', 'yongning', '101170102', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1198, '灵武', 'lingwu', '101170103', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1199, '贺兰', 'helan', '101170104', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1200, '石嘴山', 'shizuishan', '101170201', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1201, '惠农', 'huinong', '101170202', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1202, '平罗', 'pingluo', '101170203', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1203, '陶乐', 'taole', '101170204', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1204, '大武口', 'dawukou', '101170206', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1205, '吴忠', 'wuzhong', '101170301', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1206, '同心', 'tongxin', '101170302', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1207, '盐池', 'yanchi', '101170303', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1208, '青铜峡', 'qingtongxia', '101170306', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1209, '固原', 'guyuan', '101170401', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1210, '西吉', 'xiji', '101170402', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1211, '隆德', 'longde', '101170403', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1212, '泾源', 'jinyuan', '101170404', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1213, '彭阳', 'pengyang', '101170406', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1214, '中卫', 'zhongwei', '101170501', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1215, '中宁', 'zhongning', '101170502', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1216, '海原', 'haiyuan', '101170504', '宁夏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1217, '郑州', 'zhengzhou', '101180101', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1218, '巩义', 'gongyi', '101180102', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1219, '荥阳', 'xingyang', '101180103', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1220, '登封', 'dengfeng', '101180104', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1221, '新密', 'xinmi', '101180105', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1222, '新郑', 'xinzheng', '101180106', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1223, '中牟', 'zhongmou', '101180107', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1224, '上街', 'shangjie', '101180108', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1225, '安阳', 'anyang', '101180201', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1226, '汤阴', 'tangyin', '101180202', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1227, '滑县', 'huaxian', '101180203', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1228, '内黄', 'neihuang', '101180204', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1229, '林州', 'linzhou', '101180205', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1230, '新乡', 'xinxiang', '101180301', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1231, '获嘉', 'huojia', '101180302', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1232, '原阳', 'yuanyang', '101180303', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1233, '辉县', 'huixian', '101180304', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1234, '卫辉', 'weihui', '101180305', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1235, '延津', 'yanjin', '101180306', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1236, '封丘', 'fengqiu', '101180307', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1237, '长垣', 'changyuan', '101180308', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1238, '许昌', 'xuchang', '101180401', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1239, '鄢陵', 'yanling', '101180402', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1240, '襄城', 'xiangcheng', '101180403', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1241, '长葛', 'changge', '101180404', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1242, '禹州', 'yuzhou', '101180405', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1243, '平顶山', 'pingdingshan', '101180501', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1244, '郏县', 'jiaxian', '101180502', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1245, '宝丰', 'baofeng', '101180503', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1246, '汝州', 'ruzhou', '101180504', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1247, '叶县', 'yexian', '101180505', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1248, '舞钢', 'wugang', '101180506', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1249, '鲁山', 'lushan', '101180507', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1250, '石龙', 'shilong', '101180508', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1251, '信阳', 'xinyang', '101180601', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1252, '息县', 'xixian', '101180602', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1253, '罗山', 'luoshan', '101180603', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1254, '光山', 'guangshan', '101180604', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1255, '新县', 'xinxian', '101180605', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1256, '淮滨', 'huaibin', '101180606', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1257, '潢川', 'huangchuan', '101180607', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1258, '固始', 'gushi', '101180608', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1259, '商城', 'shangcheng', '101180609', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1260, '南阳', 'nanyang', '101180701', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1261, '南召', 'nanzhao', '101180702', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1262, '方城', 'fangcheng', '101180703', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1263, '社旗', 'sheqi', '101180704', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1264, '西峡', 'xixia', '101180705', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1265, '内乡', 'neixiang', '101180706', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1266, '镇平', 'zhenping', '101180707', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1267, '淅川', 'xichuan', '101180708', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1268, '新野', 'xinye', '101180709', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1269, '唐河', 'tanghe', '101180710', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1270, '邓州', 'dengzhou', '101180711', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1271, '桐柏', 'tongbai', '101180712', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1272, '开封', 'kaifeng', '101180801', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1273, '杞县', 'qixian', '101180802', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1274, '尉氏', 'weishi', '101180803', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1275, '通许', 'tongxu', '101180804', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1276, '兰考', 'lankao', '101180805', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1277, '洛阳', 'luoyang', '101180901', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1278, '新安', 'xinan', '101180902', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1279, '孟津', 'mengjin', '101180903', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1280, '宜阳', 'yiyang', '101180904', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1281, '洛宁', 'luoning', '101180905', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1282, '伊川', 'yichuan', '101180906', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1283, '嵩县', 'songxian', '101180907', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1284, '偃师', 'yanshi', '101180908', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1285, '栾川', 'luanchuan', '101180909', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1286, '汝阳', 'ruyang', '101180910', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1287, '吉利', 'jili', '101180911', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1288, '商丘', 'shangqiu', '101181001', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1289, '睢县', 'suixian', '101181003', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1290, '民权', 'minquan', '101181004', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1291, '虞城', 'yucheng', '101181005', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1292, '柘城', 'zhecheng', '101181006', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1293, '宁陵', 'ningling', '101181007', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1294, '夏邑', 'xiayi', '101181008', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1295, '永城', 'yongcheng', '101181009', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1296, '焦作', 'jiaozuo', '101181101', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1297, '修武', 'xiuwu', '101181102', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1298, '武陟', 'wuzhi', '101181103', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1299, '沁阳', 'qinyang', '101181104', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1300, '博爱', 'boai', '101181106', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1301, '温县', 'wenxian', '101181107', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1302, '孟州', 'mengzhou', '101181108', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1303, '鹤壁', 'hebi', '101181201', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1304, '浚县', 'xunxian', '101181202', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1305, '淇县', 'qixian', '101181203', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1306, '濮阳', 'puyang', '101181301', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1307, '台前', 'taiqian', '101181302', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1308, '南乐', 'nanle', '101181303', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1309, '清丰', 'qingfeng', '101181304', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1310, '范县', 'fanxian', '101181305', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1311, '周口', 'zhoukou', '101181401', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1312, '扶沟', 'fugou', '101181402', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1313, '太康', 'taikang', '101181403', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1314, '淮阳', 'huaiyang', '101181404', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1315, '西华', 'xihua', '101181405', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1316, '商水', 'shangshui', '101181406', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1317, '项城', 'xiangcheng', '101181407', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1318, '郸城', 'dancheng', '101181408', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1319, '鹿邑', 'luyi', '101181409', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1320, '沈丘', 'shenqiu', '101181410', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1321, '漯河', 'luohe', '101181501', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1322, '临颍', 'linying', '101181502', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1323, '舞阳', 'wuyang', '101181503', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1324, '驻马店', 'zhumadian', '101181601', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1325, '西平', 'xiping', '101181602', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1326, '遂平', 'suiping', '101181603', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1327, '上蔡', 'shangcai', '101181604', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1328, '汝南', 'runan', '101181605', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1329, '泌阳', 'biyang', '101181606', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1330, '平舆', 'pingyu', '101181607', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1331, '新蔡', 'xincai', '101181608', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1332, '确山', 'queshan', '101181609', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1333, '正阳', 'zhengyang', '101181610', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1334, '三门峡', 'sanmenxia', '101181701', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1335, '灵宝', 'lingbao', '101181702', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1336, '渑池', 'mianchi', '101181703', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1337, '卢氏', 'lushi', '101181704', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1338, '义马', 'yima', '101181705', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1339, '陕县', 'shanxian', '101181706', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1340, '济源', 'jiyuan', '101181801', '河南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1341, '南京', 'nanjing', '101190101', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1342, '溧水', 'lishui', '101190102', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1343, '高淳', 'gaochun', '101190103', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1344, '江宁', 'jiangning', '101190104', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1345, '六合', 'luhe', '101190105', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1346, '江浦', 'jiangpu', '101190106', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1347, '浦口', 'pukou', '101190107', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1348, '无锡', 'wuxi', '101190201', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1349, '江阴', 'jiangyin', '101190202', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1350, '宜兴', 'yixing', '101190203', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1351, '锡山', 'xishan', '101190204', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1352, '镇江', 'zhenjiang', '101190301', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1353, '丹阳', 'danyang', '101190302', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1354, '扬中', 'yangzhong', '101190303', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1355, '句容', 'jurong', '101190304', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1356, '丹徒', 'dantu', '101190305', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1357, '苏州', 'suzhou', '101190401', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1358, '常熟', 'changshu', '101190402', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1359, '张家港', 'zhangjiagang', '101190403', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1360, '昆山', 'kunshan', '101190404', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1361, '吴中', 'wuzhong', '101190405', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1362, '吴江', 'wujiang', '101190407', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1363, '太仓', 'taicang', '101190408', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1364, '南通', 'nantong', '101190501', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1365, '海安', 'haian', '101190502', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1366, '如皋', 'rugao', '101190503', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1367, '如东', 'rudong', '101190504', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1368, '启东', 'qidong', '101190507', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1369, '海门', 'haimen', '101190508', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1370, '通州', 'tongzhou', '101190509', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1371, '扬州', 'yangzhou', '101190601', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1372, '宝应', 'baoying', '101190602', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1373, '仪征', 'yizheng', '101190603', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1374, '高邮', 'gaoyou', '101190604', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1375, '江都', 'jiangdu', '101190605', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1376, '邗江', 'hanjiang', '101190606', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1377, '盐城', 'yancheng', '101190701', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1378, '响水', 'xiangshui', '101190702', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1379, '滨海', 'binhai', '101190703', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1380, '阜宁', 'funing', '101190704', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1381, '射阳', 'sheyang', '101190705', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1382, '建湖', 'jianhu', '101190706', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1383, '东台', 'dongtai', '101190707', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1384, '大丰', 'dafeng', '101190708', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1385, '盐都', 'yandu', '101190709', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1386, '徐州', 'xuzhou', '101190801', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1387, '铜山', 'tongshan', '101190802', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1388, '丰县', 'fengxian', '101190803', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1389, '沛县', 'peixian', '101190804', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1390, '邳州', 'pizhou', '101190805', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1391, '睢宁', 'suining', '101190806', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1392, '新沂', 'xinyi', '101190807', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1393, '淮安', 'huaian', '101190901', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1394, '金湖', 'jinhu', '101190902', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1395, '盱眙', 'xuyi', '101190903', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1396, '洪泽', 'hongze', '101190904', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1397, '涟水', 'lianshui', '101190905', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1398, '淮阴区', 'huaiyinqu', '101190906', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1399, '淮安区', 'huaianqu', '101190908', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1400, '连云港', 'lianyungang', '101191001', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1401, '东海', 'donghai', '101191002', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1402, '赣榆', 'ganyu', '101191003', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1403, '灌云', 'guanyun', '101191004', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1404, '灌南', 'guannan', '101191005', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1405, '常州', 'changzhou', '101191101', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1406, '溧阳', 'liyang', '101191102', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1407, '金坛', 'jintan', '101191103', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1408, '武进', 'wujin', '101191104', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1409, '泰州', 'taizhou', '101191201', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1410, '兴化', 'xinghua', '101191202', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1411, '泰兴', 'taixing', '101191203', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1412, '姜堰', 'jiangyan', '101191204', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1413, '靖江', 'jingjiang', '101191205', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1414, '宿迁', 'suqian', '101191301', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1415, '沭阳', 'shuyang', '101191302', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1416, '泗阳', 'siyang', '101191303', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1417, '泗洪', 'sihong', '101191304', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1418, '宿豫', 'suyu', '101191305', '江苏', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1419, '武汉', 'wuhan', '101200101', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1420, '蔡甸', 'caidian', '101200102', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1421, '黄陂', 'huangpi', '101200103', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1422, '新洲', 'xinzhou', '101200104', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1423, '江夏', 'jiangxia', '101200105', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1424, '东西湖', 'dongxihu', '101200106', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1425, '襄阳', 'xiangyang', '101200201', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1426, '襄州', 'xiangzhou', '101200202', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1427, '保康', 'baokang', '101200203', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1428, '南漳', 'nanzhang', '101200204', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1429, '宜城', 'yicheng', '101200205', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1430, '老河口', 'laohekou', '101200206', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1431, '谷城', 'gucheng', '101200207', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1432, '枣阳', 'zaoyang', '101200208', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1433, '鄂州', 'ezhou', '101200301', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1434, '梁子湖', 'liangzihu', '101200302', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1435, '孝感', 'xiaogan', '101200401', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1436, '安陆', 'anlu', '101200402', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1437, '云梦', 'yunmeng', '101200403', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1438, '大悟', 'dawu', '101200404', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1439, '应城', 'yingcheng', '101200405', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1440, '汉川', 'hanchuan', '101200406', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1441, '孝昌', 'xiaochang', '101200407', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1442, '黄冈', 'huanggang', '101200501', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1443, '红安', 'hongan', '101200502', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1444, '麻城', 'macheng', '101200503', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1445, '罗田', 'luotian', '101200504', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1446, '英山', 'yingshan', '101200505', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1447, '浠水', 'xishui', '101200506', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1448, '蕲春', 'qichun', '101200507', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1449, '黄梅', 'huangmei', '101200508', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1450, '武穴', 'wuxue', '101200509', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1451, '团风', 'tuanfeng', '101200510', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1452, '黄石', 'huangshi', '101200601', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1453, '大冶', 'daye', '101200602', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1454, '阳新', 'yangxin', '101200603', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1455, '铁山', 'tieshan', '101200604', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1456, '下陆', 'xialu', '101200605', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1457, '西塞山', 'xisaishan', '101200606', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1458, '咸宁', 'xianning', '101200701', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1459, '赤壁', 'chibi', '101200702', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1460, '嘉鱼', 'jiayu', '101200703', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1461, '崇阳', 'chongyang', '101200704', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1462, '通城', 'tongcheng', '101200705', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1463, '通山', 'tongshan', '101200706', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1464, '荆州', 'jingzhou', '101200801', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1465, '江陵', 'jiangling', '101200802', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1466, '公安', 'gongan', '101200803', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1467, '石首', 'shishou', '101200804', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1468, '监利', 'jianli', '101200805', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1469, '洪湖', 'honghu', '101200806', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1470, '松滋', 'songzi', '101200807', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1471, '宜昌', 'yichang', '101200901', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1472, '远安', 'yuanan', '101200902', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1473, '秭归', 'zigui', '101200903', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1474, '兴山', 'xingshan', '101200904', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1475, '五峰', 'wufeng', '101200906', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1476, '当阳', 'dangyang', '101200907', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1477, '长阳', 'changyang', '101200908', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1478, '宜都', 'yidu', '101200909', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1479, '枝江', 'zhijiang', '101200910', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1480, '三峡', 'sanxia', '101200911', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1481, '夷陵', 'yiling', '101200912', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1482, '恩施', 'enshi', '101201001', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1483, '利川', 'lichuan', '101201002', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1484, '建始', 'jianshi', '101201003', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1485, '咸丰', 'xianfeng', '101201004', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1486, '宣恩', 'xuanen', '101201005', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1487, '鹤峰', 'hefeng', '101201006', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1488, '来凤', 'laifeng', '101201007', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1489, '巴东', 'badong', '101201008', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1490, '十堰', 'shiyan', '101201101', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1491, '竹溪', 'zhuxi', '101201102', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1492, '郧西', 'yunxi', '101201103', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1493, '郧县', 'yunxian', '101201104', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1494, '竹山', 'zhushan', '101201105', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1495, '房县', 'fangxian', '101201106', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1496, '丹江口', 'danjiangkou', '101201107', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1497, '茅箭', 'maojian', '101201108', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1498, '张湾', 'zhangwan', '101201109', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1499, '神农架', 'shennongjia', '101201201', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1500, '随州', 'suizhou', '101201301', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1501, '广水', 'guangshui', '101201302', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1502, '荆门', 'jingmen', '101201401', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1503, '钟祥', 'zhongxiang', '101201402', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1504, '京山', 'jingshan', '101201403', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1505, '掇刀', 'duodao', '101201404', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1506, '沙洋', 'shayang', '101201405', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1507, '沙市', 'shashi', '101201406', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1508, '天门', 'tianmen', '101201501', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1509, '仙桃', 'xiantao', '101201601', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1510, '潜江', 'qianjiang', '101201701', '湖北', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1511, '杭州', 'hangzhou', '101210101', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1512, '萧山', 'xiaoshan', '101210102', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1513, '桐庐', 'tonglu', '101210103', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1514, '淳安', 'chunan', '101210104', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1515, '建德', 'jiande', '101210105', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1516, '余杭', 'yuhang', '101210106', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1517, '临安', 'linan', '101210107', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1518, '富阳', 'fuyang', '101210108', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1519, '湖州', 'huzhou', '101210201', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1520, '长兴', 'changxing', '101210202', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1521, '安吉', 'anji', '101210203', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1522, '德清', 'deqing', '101210204', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1523, '嘉兴', 'jiaxing', '101210301', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1524, '嘉善', 'jiashan', '101210302', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1525, '海宁', 'haining', '101210303', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1526, '桐乡', 'tongxiang', '101210304', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1527, '平湖', 'pinghu', '101210305', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1528, '海盐', 'haiyan', '101210306', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1529, '宁波', 'ningbo', '101210401', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1530, '慈溪', 'cixi', '101210403', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1531, '余姚', 'yuyao', '101210404', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1532, '奉化', 'fenghua', '101210405', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1533, '象山', 'xiangshan', '101210406', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1534, '宁海', 'ninghai', '101210408', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1535, '北仑', 'beilun', '101210410', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1536, '鄞州', 'yinzhou', '101210411', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1537, '镇海', 'zhenhai', '101210412', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1538, '绍兴', 'shaoxing', '101210501', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1539, '诸暨', 'zhuji', '101210502', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1540, '上虞', 'shangyu', '101210503', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1541, '新昌', 'xinchang', '101210504', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1542, '嵊州', 'shengzhou', '101210505', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1543, '台州', 'taizhou', '101210601', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1544, '玉环', 'yuhuan', '101210603', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1545, '三门', 'sanmen', '101210604', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1546, '天台', 'tiantai', '101210605', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1547, '仙居', 'xianju', '101210606', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1548, '温岭', 'wenling', '101210607', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1549, '洪家', 'hongjia', '101210609', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1550, '临海', 'linhai', '101210610', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1551, '椒江', 'jiaojiang', '101210611', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1552, '黄岩', 'huangyan', '101210612', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1553, '路桥', 'luqiao', '101210613', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1554, '温州', 'wenzhou', '101210701', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1555, '泰顺', 'taishun', '101210702', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1556, '文成', 'wencheng', '101210703', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1557, '平阳', 'pingyang', '101210704', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1558, '瑞安', 'ruian', '101210705', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1559, '洞头', 'dongtou', '101210706', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1560, '乐清', 'yueqing', '101210707', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1561, '永嘉', 'yongjia', '101210708', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1562, '苍南', 'cangnan', '101210709', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1563, '丽水', 'lishui', '101210801', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1564, '遂昌', 'suichang', '101210802', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1565, '龙泉', 'longquan', '101210803', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1566, '缙云', 'jinyun', '101210804', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1567, '青田', 'qingtian', '101210805', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1568, '云和', 'yunhe', '101210806', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1569, '庆元', 'qingyuan', '101210807', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1570, '松阳', 'songyang', '101210808', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1571, '景宁', 'jingning', '101210809', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1572, '金华', 'jinhua', '101210901', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1573, '浦江', 'pujiang', '101210902', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1574, '兰溪', 'lanxi', '101210903', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1575, '义乌', 'yiwu', '101210904', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1576, '东阳', 'dongyang', '101210905', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1577, '武义', 'wuyi', '101210906', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1578, '永康', 'yongkang', '101210907', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1579, '磐安', 'panan', '101210908', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1580, '衢州', 'quzhou', '101211001', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1581, '常山', 'changshan', '101211002', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1582, '开化', 'kaihua', '101211003', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1583, '龙游', 'longyou', '101211004', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1584, '江山', 'jiangshan', '101211005', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1585, '衢江', 'qujiang', '101211006', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1586, '舟山', 'zhoushan', '101211101', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1587, '嵊泗', 'shengsi', '101211102', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1588, '岱山', 'daishan', '101211104', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1589, '普陀', 'putuo', '101211105', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1590, '定海', 'dinghai', '101211106', '浙江', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1591, '合肥', 'hefei', '101220101', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1592, '长丰', 'changfeng', '101220102', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1593, '肥东', 'feidong', '101220103', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1594, '肥西', 'feixi', '101220104', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1595, '蚌埠', 'bengbu', '101220201', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1596, '怀远', 'huaiyuan', '101220202', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1597, '固镇', 'guzhen', '101220203', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1598, '五河', 'wuhe', '101220204', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1599, '芜湖', 'wuhu', '101220301', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1600, '繁昌', 'fanyang', '101220302', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1601, '芜湖县', 'wuhuxian', '101220303', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1602, '南陵', 'nanling', '101220304', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1603, '淮南', 'huainan', '101220401', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1604, '凤台', 'fengtai', '101220402', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1605, '潘集', 'panji', '101220403', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1606, '马鞍山', 'maanshan', '101220501', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1607, '当涂', 'dangtu', '101220502', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1608, '安庆', 'anqing', '101220601', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1609, '枞阳', 'zongyang', '101220602', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1610, '太湖', 'taihu', '101220603', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1611, '潜山', 'qianshan', '101220604', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1612, '怀宁', 'huaining', '101220605', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1613, '宿松', 'susong', '101220606', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1614, '望江', 'wangjiang', '101220607', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1615, '岳西', 'yuexi', '101220608', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1616, '桐城', 'tongcheng', '101220609', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1617, '宿州', 'suzhou', '101220701', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1618, '砀山', 'dangshan', '101220702', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1619, '灵璧', 'lingbi', '101220703', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1620, '泗县', 'sixian', '101220704', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1621, '萧县', 'xiaoxian', '101220705', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1622, '阜阳', 'fuyang', '101220801', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1623, '阜南', 'funan', '101220802', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1624, '颍上', 'yingshang', '101220803', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1625, '临泉', 'linquan', '101220804', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1626, '界首', 'jieshou', '101220805', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1627, '太和', 'taihe', '101220806', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1628, '亳州', 'bozhou', '101220901', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1629, '涡阳', 'guoyang', '101220902', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1630, '利辛', 'lixin', '101220903', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1631, '蒙城', 'mengcheng', '101220904', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1632, '黄山', 'huangshan', '101221001', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1633, '黄山区', 'huangshanqu', '101221002', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1634, '屯溪', 'tunxi', '101221003', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1635, '祁门', 'qimen', '101221004', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1636, '黟县', 'yixian', '101221005', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1637, '歙县', 'shexian', '101221006', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1638, '休宁', 'xiuning', '101221007', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1639, '黄山风景区', 'huangshanfengjingqu', '101221008', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1640, '滁州', 'chuzhou', '101221101', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1641, '凤阳', 'fengyang', '101221102', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1642, '明光', 'mingguang', '101221103', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1643, '定远', 'dingyuan', '101221104', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1644, '全椒', 'quanjiao', '101221105', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1645, '来安', 'laian', '101221106', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1646, '天长', 'tianchang', '101221107', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1647, '淮北', 'huaibei', '101221201', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1648, '濉溪', 'suixi', '101221202', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1649, '铜陵', 'tongling', '101221301', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1650, '宣城', 'xuancheng', '101221401', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1651, '泾县', 'jingxian', '101221402', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1652, '旌德', 'jingde', '101221403', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1653, '宁国', 'ningguo', '101221404', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1654, '绩溪', 'jixi', '101221405', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1655, '广德', 'guangde', '101221406', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1656, '郎溪', 'langxi', '101221407', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1657, '六安', 'luan', '101221501', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1658, '霍邱', 'huoqiu', '101221502', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1659, '寿县', 'shouxian', '101221503', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1660, '金寨', 'jinzhai', '101221505', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1661, '霍山', 'huoshan', '101221506', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1662, '舒城', 'shucheng', '101221507', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1663, '巢湖', 'chaohu', '101221601', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1664, '庐江', 'lujiang', '101221602', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1665, '无为', 'wuwei', '101221603', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1666, '含山', 'hanshan', '101221604', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1667, '和县', 'hexian', '101221605', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1668, '池州', 'chizhou', '101221701', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1669, '东至', 'dongzhi', '101221702', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1670, '青阳', 'qingyang', '101221703', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1671, '九华山', 'jiuhuashan', '101221704', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1672, '石台', 'shitai', '101221705', '安徽', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1673, '福州', 'fuzhou', '101230101', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1674, '闽清', 'minqing', '101230102', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1675, '闽侯', 'minhou', '101230103', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1676, '罗源', 'luoyuan', '101230104', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1677, '连江', 'lianjiang', '101230105', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1678, '永泰', 'yongtai', '101230107', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1679, '平潭', 'pingtan', '101230108', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1680, '长乐', 'changle', '101230110', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1681, '福清', 'fuqing', '101230111', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1682, '厦门', 'xiamen', '101230201', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1683, '同安', 'tongan', '101230202', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1684, '宁德', 'ningde', '101230301', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1685, '古田', 'gutian', '101230302', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1686, '霞浦', 'xiapu', '101230303', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1687, '寿宁', 'shouning', '101230304', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1688, '周宁', 'zhouning', '101230305', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1689, '福安', 'fuan', '101230306', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1690, '柘荣', 'zherong', '101230307', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1691, '福鼎', 'fuding', '101230308', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1692, '屏南', 'pingnan', '101230309', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1693, '莆田', 'putian', '101230401', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1694, '仙游', 'xianyou', '101230402', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1695, '秀屿港', 'xiuyugang', '101230403', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1696, '涵江', 'hanjiang', '101230404', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1697, '秀屿', 'xiuyu', '101230405', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1698, '荔城', 'licheng', '101230406', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1699, '城厢', 'chengxiang', '101230407', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1700, '泉州', 'quanzhou', '101230501', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1701, '安溪', 'anxi', '101230502', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1702, '永春', 'yongchun', '101230504', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1703, '德化', 'dehua', '101230505', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1704, '南安', 'nanan', '101230506', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1705, '崇武', 'chongwu', '101230507', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1706, '惠安', 'huian', '101230508', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1707, '晋江', 'jinjiang', '101230509', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1708, '石狮', 'shishi', '101230510', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1709, '漳州', 'zhangzhou', '101230601', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1710, '长泰', 'changtai', '101230602', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1711, '南靖', 'nanjing', '101230603', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1712, '平和', 'pinghe', '101230604', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1713, '龙海', 'longhai', '101230605', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1714, '漳浦', 'zhangpu', '101230606', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1715, '诏安', 'zhaoan', '101230607', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1716, '东山', 'dongshan', '101230608', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1717, '云霄', 'yunxiao', '101230609', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1718, '华安', 'huaan', '101230610', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1719, '龙岩', 'longyan', '101230701', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1720, '长汀', 'changting', '101230702', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1721, '连城', 'liancheng', '101230703', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1722, '武平', 'wuping', '101230704', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1723, '上杭', 'shanghang', '101230705', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1724, '永定', 'yongding', '101230706', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1725, '漳平', 'zhangping', '101230707', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1726, '三明', 'sanming', '101230801', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1727, '宁化', 'ninghua', '101230802', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1728, '清流', 'qingliu', '101230803', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1729, '泰宁', 'taining', '101230804', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1730, '将乐', 'jiangle', '101230805', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1731, '建宁', 'jianning', '101230806', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1732, '明溪', 'mingxi', '101230807', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1733, '沙县', 'shaxian', '101230808', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1734, '尤溪', 'youxi', '101230809', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1735, '永安', 'yongan', '101230810', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1736, '大田', 'datian', '101230811', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1737, '南平', 'nanping', '101230901', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1738, '顺昌', 'shunchang', '101230902', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1739, '光泽', 'guangze', '101230903', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1740, '邵武', 'shaowu', '101230904', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1741, '武夷山', 'wuyishan', '101230905', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1742, '浦城', 'pucheng', '101230906', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1743, '建阳', 'jianyang', '101230907', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1744, '松溪', 'songxi', '101230908', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1745, '政和', 'zhenghe', '101230909', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1746, '建瓯', 'jianou', '101230910', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1747, '钓鱼岛', 'diaoyudao', '101231001', '福建', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1748, '南昌', 'nanchang', '101240101', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1749, '新建', 'xinjian', '101240102', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1750, '南昌县', 'nanchangxian', '101240103', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1751, '安义', 'anyi', '101240104', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1752, '进贤', 'jinxian', '101240105', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1753, '莲塘', 'liantang', '101240106', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1754, '九江', 'jiujiang', '101240201', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1755, '瑞昌', 'ruichang', '101240202', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1756, '庐山', 'lushan', '101240203', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1757, '武宁', 'wuning', '101240204', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1758, '德安', 'dean', '101240205', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1759, '永修', 'yongxiu', '101240206', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1760, '湖口', 'hukou', '101240207', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1761, '彭泽', 'pengze', '101240208', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1762, '星子', 'xingzi', '101240209', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1763, '都昌', 'duchang', '101240210', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1764, '修水', 'xiushui', '101240212', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1765, '澎泽', 'pengze', '101240213', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1766, '上饶', 'shangrao', '101240301', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1767, '鄱阳', 'poyang', '101240302', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1768, '婺源', 'wuyuan', '101240303', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1769, '余干', 'yugan', '101240305', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1770, '万年', 'wannian', '101240306', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1771, '德兴', 'dexing', '101240307', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1772, '上饶县', 'shangraoxian', '101240308', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1773, '弋阳', 'yiyang', '101240309', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1774, '横峰', 'hengfeng', '101240310', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1775, '铅山', 'yanshan', '101240311', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1776, '玉山', 'yushan', '101240312', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1777, '广丰', 'guangfeng', '101240313', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1778, '抚州', 'fuzhou', '101240401', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1779, '广昌', 'guangchang', '101240402', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1780, '乐安', 'anle', '101240403', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1781, '崇仁', 'chongren', '101240404', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1782, '金溪', 'jinxi', '101240405', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1783, '资溪', 'zixi', '101240406', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1784, '宜黄', 'yihuang', '101240407', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1785, '南城', 'nancheng', '101240408', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1786, '南丰', 'nanfeng', '101240409', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1787, '黎川', 'lichuan', '101240410', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1788, '东乡', 'dongxiang', '101240411', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1789, '宜春', 'yichun', '101240501', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1790, '铜鼓', 'tonggu', '101240502', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1791, '宜丰', 'yifeng', '101240503', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1792, '万载', 'wanzai', '101240504', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1793, '上高', 'shanggao', '101240505', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1794, '靖安', 'jingan', '101240506', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1795, '奉新', 'fengxin', '101240507', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1796, '高安', 'gaoan', '101240508', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1797, '樟树', 'zhangshu', '101240509', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1798, '丰城', 'fengcheng', '101240510', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1799, '吉安', 'jian', '101240601', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1800, '吉安县', 'jianxian', '101240602', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1801, '吉水', 'jishui', '101240603', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1802, '新干', 'xingan', '101240604', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1803, '峡江', 'xiajiang', '101240605', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1804, '永丰', 'yongfeng', '101240606', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1805, '永新', 'yongxin', '101240607', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1806, '井冈山', 'jinggangshan', '101240608', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1807, '万安', 'wanan', '101240609', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1808, '遂川', 'suichuan', '101240610', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1809, '泰和', 'taihe', '101240611', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1810, '安福', 'anfu', '101240612', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1811, '宁冈', 'ninggang', '101240613', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1812, '赣州', 'ganzhou', '101240701', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1813, '崇义', 'chongyi', '101240702', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1814, '上犹', 'shangyou', '101240703', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1815, '南康', 'nankang', '101240704', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1816, '大余', 'dayu', '101240705', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1817, '信丰', 'xinfeng', '101240706', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1818, '宁都', 'ningdu', '101240707', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1819, '石城', 'shicheng', '101240708', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1820, '瑞金', 'ruijin', '101240709', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1821, '于都', 'yudu', '101240710', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1822, '会昌', 'huichang', '101240711', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1823, '安远', 'anyuan', '101240712', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1824, '全南', 'quannan', '101240713', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1825, '龙南', 'longnan', '101240714', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1826, '定南', 'dingnan', '101240715', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1827, '寻乌', 'xunwu', '101240716', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1828, '兴国', 'xingguo', '101240717', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1829, '赣县', 'ganxian', '101240718', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1830, '景德镇', 'jingdezhen', '101240801', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1831, '乐平', 'leping', '101240802', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1832, '浮梁', 'fuliang', '101240803', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1833, '萍乡', 'pingxiang', '101240901', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1834, '莲花', 'lianhua', '101240902', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1835, '上栗', 'shangli', '101240903', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1836, '安源', 'anyuan', '101240904', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1837, '芦溪', 'luxi', '101240905', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1838, '湘东', 'xiangdong', '101240906', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1839, '新余', 'xinyu', '101241001', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1840, '分宜', 'fenyi', '101241002', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1841, '鹰潭', 'yingtan', '101241101', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1842, '余江', 'yujiang', '101241102', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1843, '贵溪', 'guixi', '101241103', '江西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1844, '长沙', 'changsha', '101250101', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1845, '宁乡', 'ningxiang', '101250102', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1846, '浏阳', 'liuyang', '101250103', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1847, '马坡岭', 'mapoling', '101250104', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1848, '望城', 'wangcheng', '101250105', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1849, '湘潭', 'xiangtan', '101250201', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1850, '韶山', 'shaoshan', '101250202', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1851, '湘乡', 'xiangxiang', '101250203', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1852, '株洲', 'zhuzhou', '101250301', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1853, '攸县', 'youxian', '101250302', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1854, '醴陵', 'liling', '101250303', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1855, '茶陵', 'chaling', '101250305', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1856, '炎陵', 'yanling', '101250306', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1857, '衡阳', 'hengyang', '101250401', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1858, '衡山', 'hengshan', '101250402', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1859, '衡东', 'hengdong', '101250403', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1860, '祁东', 'qidong', '101250404', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1861, '衡阳县', 'hengyangxian', '101250405', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1862, '常宁', 'changning', '101250406', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1863, '衡南', 'hengnan', '101250407', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1864, '耒阳', 'leiyang', '101250408', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1865, '南岳', 'nanyue', '101250409', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1866, '郴州', 'chenzhou', '101250501', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1867, '桂阳', 'guiyang', '101250502', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1868, '嘉禾', 'jiahe', '101250503', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1869, '宜章', 'yizhang', '101250504', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1870, '临武', 'linwu', '101250505', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1871, '资兴', 'zixing', '101250507', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1872, '汝城', 'rucheng', '101250508', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1873, '安仁', 'anren', '101250509', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1874, '永兴', 'yongxing', '101250510', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1875, '桂东', 'guidong', '101250511', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1876, '苏仙', 'suxian', '101250512', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1877, '常德', 'changde', '101250601', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1878, '安乡', 'anxiang', '101250602', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1879, '桃源', 'taoyuan', '101250603', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1880, '汉寿', 'hanshou', '101250604', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1881, '澧县', 'lixian', '101250605', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1882, '临澧', 'linli', '101250606', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1883, '石门', 'shimen', '101250607', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1884, '津市', 'jinshi', '101250608', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1885, '益阳', 'yiyang', '101250700', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1886, '赫山区', 'heshanqu', '101250701', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1887, '南县', 'nanxian', '101250702', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1888, '桃江', 'taojiang', '101250703', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1889, '安化', 'anhua', '101250704', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1890, '沅江', 'yuanjiang', '101250705', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1891, '娄底', 'loudi', '101250801', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1892, '双峰', 'shuangfeng', '101250802', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1893, '冷水江', 'lengshuijiang', '101250803', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1894, '新化', 'xinhua', '101250805', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1895, '涟源', 'lianyuan', '101250806', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1896, '邵阳', 'shaoyang', '101250901', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1897, '隆回', 'longhui', '101250902', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1898, '洞口', 'dongkou', '101250903', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1899, '新邵', 'xinshao', '101250904', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1900, '邵东', 'shaodong', '101250905', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1901, '绥宁', 'suining', '101250906', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1902, '新宁', 'xinning', '101250907', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1903, '武冈', 'wugang', '101250908', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1904, '城步', 'chengbu', '101250909', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1905, '邵阳县', 'shaoyangxian', '101250910', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1906, '岳阳', 'yueyang', '101251001', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1907, '华容', 'huarong', '101251002', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1908, '湘阴', 'xiangyin', '101251003', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1909, '汨罗', 'miluo', '101251004', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1910, '平江', 'pingjiang', '101251005', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1911, '临湘', 'linxiang', '101251006', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1912, '张家界', 'zhangjiajie', '101251101', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1913, '桑植', 'sangzhi', '101251102', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1914, '慈利', 'cili', '101251103', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1915, '武陵源', 'wulingyuan', '101251104', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1916, '怀化', 'huaihua', '101251201', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1917, '沅陵', 'yuanling', '101251203', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1918, '辰溪', 'chenxi', '101251204', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1919, '靖州', 'jingzhou', '101251205', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1920, '会同', 'huitong', '101251206', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1921, '通道', 'tongdao', '101251207', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1922, '麻阳', 'mayang', '101251208', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1923, '新晃', 'xinhuang', '101251209', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1924, '芷江', 'zhijiang', '101251210', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1925, '溆浦', 'xupu', '101251211', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1926, '中方', 'zhongfang', '101251212', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1927, '洪江', 'hongjiang', '101251213', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1928, '永州', 'yongzhou', '101251401', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1929, '祁阳', 'qiyang', '101251402', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1930, '东安', 'dongan', '101251403', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1931, '双牌', 'shuangpai', '101251404', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1932, '道县', 'daoxian', '101251405', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1933, '宁远', 'ningyuan', '101251406', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1934, '江永', 'jiangyong', '101251407', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1935, '蓝山', 'lanshan', '101251408', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1936, '新田', 'xintian', '101251409', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1937, '江华', 'jianghua', '101251410', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1938, '冷水滩', 'lengshuitan', '101251411', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1939, '吉首', 'jishou', '101251501', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1940, '保靖', 'baojing', '101251502', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1941, '永顺', 'yongshun', '101251503', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1942, '古丈', 'guzhang', '101251504', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1943, '凤凰', 'fenghuang', '101251505', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1944, '泸溪', 'luxi', '101251506', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1945, '龙山', 'longshan', '101251507', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1946, '花垣', 'huayuan', '101251508', '湖南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1947, '贵阳', 'guiyang', '101260101', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1948, '白云', 'baiyun', '101260102', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1949, '花溪', 'huaxi', '101260103', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1950, '乌当', 'wudang', '101260104', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1951, '息烽', 'xifeng', '101260105', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1952, '开阳', 'kaiyang', '101260106', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1953, '修文', 'xiuwen', '101260107', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1954, '清镇', 'qingzhen', '101260108', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1955, '小河', 'xiaohe', '101260109', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1956, '云岩', 'yunyan', '101260110', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1957, '南明', 'nanming', '101260111', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1958, '遵义', 'zunyi', '101260201', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1959, '遵义县', 'zunyixian', '101260202', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1960, '仁怀', 'renhuai', '101260203', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1961, '绥阳', 'suiyang', '101260204', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1962, '湄潭', 'meitan', '101260205', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1963, '凤冈', 'fenggang', '101260206', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1964, '桐梓', 'tongzi', '101260207', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1965, '赤水', 'chishui', '101260208', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1966, '习水', 'xishui', '101260209', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1967, '道真', 'daozhen', '101260210', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1968, '正安', 'zhengan', '101260211', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1969, '务川', 'wuchuan', '101260212', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1970, '余庆', 'yuqing', '101260213', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1971, '汇川', 'huichuan', '101260214', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1972, '红花岗', 'honghuagang', '101260215', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1973, '安顺', 'anshun', '101260301', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1974, '普定', 'puding', '101260302', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1975, '镇宁', 'zhenning', '101260303', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1976, '平坝', 'pingba', '101260304', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1977, '紫云', 'ziyun', '101260305', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1978, '关岭', 'guanling', '101260306', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1979, '都匀', 'duyun', '101260401', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1980, '贵定', 'guiding', '101260402', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1981, '瓮安', 'wengan', '101260403', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1982, '长顺', 'changshun', '101260404', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1983, '福泉', 'fuquan', '101260405', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1984, '惠水', 'huishui', '101260406', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1985, '龙里', 'longli', '101260407', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1986, '罗甸', 'luodian', '101260408', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1987, '平塘', 'pingtang', '101260409', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1988, '独山', 'dushan', '101260410', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1989, '三都', 'sandu', '101260411', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1990, '荔波', 'libo', '101260412', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1991, '凯里', 'kaili', '101260501', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1992, '岑巩', 'cengong', '101260502', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1993, '施秉', 'shibing', '101260503', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1994, '镇远', 'zhenyuan', '101260504', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1995, '黄平', 'huangping', '101260505', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1996, '麻江', 'majiang', '101260507', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1997, '丹寨', 'danzhai', '101260508', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1998, '三穗', 'sansui', '101260509', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (1999, '台江', 'taijiang', '101260510', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2000, '剑河', 'jianhe', '101260511', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2001, '雷山', 'leishan', '101260512', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2002, '黎平', 'liping', '101260513', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2003, '天柱', 'tianzhu', '101260514', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2004, '锦屏', 'jinping', '101260515', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2005, '榕江', 'rongjiang', '101260516', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2006, '从江', 'congjiang', '101260517', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2007, '铜仁', 'tongren', '101260601', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2008, '江口', 'jiangkou', '101260602', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2009, '玉屏', 'yuping', '101260603', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2010, '万山', 'wanshan', '101260604', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2011, '思南', 'sinan', '101260605', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2012, '印江', 'yinjiang', '101260607', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2013, '石阡', 'shiqian', '101260608', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2014, '沿河', 'yanhe', '101260609', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2015, '德江', 'dejiang', '101260610', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2016, '松桃', 'songtao', '101260611', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2017, '毕节', 'bijie', '101260701', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2018, '赫章', 'hezhang', '101260702', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2019, '金沙', 'jinsha', '101260703', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2020, '威宁', 'weining', '101260704', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2021, '大方', 'dafang', '101260705', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2022, '纳雍', 'nayong', '101260706', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2023, '织金', 'zhijin', '101260707', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2024, '黔西', 'qianxi', '101260708', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2025, '水城', 'shuicheng', '101260801', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2026, '六枝', 'liuzhi', '101260802', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2027, '盘县', 'panxian', '101260804', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2028, '兴义', 'xingyi', '101260901', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2029, '晴隆', 'qinglong', '101260902', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2030, '兴仁', 'xingren', '101260903', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2031, '贞丰', 'zhenfeng', '101260904', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2032, '望谟', 'wangmo', '101260905', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2033, '安龙', 'anlong', '101260907', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2034, '册亨', 'ceheng', '101260908', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2035, '普安', 'puan', '101260909', '贵州', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2036, '成都', 'chengdu', '101270101', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2037, '龙泉驿', 'longquanyi', '101270102', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2038, '新都', 'xindu', '101270103', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2039, '温江', 'wenjiang', '101270104', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2040, '金堂', 'jintang', '101270105', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2041, '双流', 'shuangliu', '101270106', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2042, '郫县', 'pixian', '101270107', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2043, '大邑', 'dayi', '101270108', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2044, '蒲江', 'pujiang', '101270109', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2045, '新津', 'xinjin', '101270110', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2046, '都江堰', 'dujiangyan', '101270111', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2047, '彭州', 'pengzhou', '101270112', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2048, '邛崃', 'qionglai', '101270113', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2049, '崇州', 'chongzhou', '101270114', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2050, '攀枝花', 'panzhihua', '101270201', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2051, '仁和', 'renhe', '101270202', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2052, '米易', 'miyi', '101270203', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2053, '盐边', 'yanbian', '101270204', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2054, '自贡', 'zigong', '101270301', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2055, '富顺', 'fushun', '101270302', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2056, '荣县', 'rongxian', '101270303', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2057, '绵阳', 'mianyang', '101270401', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2058, '三台', 'santai', '101270402', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2059, '盐亭', 'yanting', '101270403', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2060, '安县', 'anxian', '101270404', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2061, '梓潼', 'zitong', '101270405', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2062, '北川', 'beichuan', '101270406', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2063, '平武', 'pingwu', '101270407', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2064, '江油', 'jiangyou', '101270408', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2065, '南充', 'nanchong', '101270501', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2066, '南部', 'nanbu', '101270502', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2067, '营山', 'yingshan', '101270503', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2068, '蓬安', 'pengan', '101270504', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2069, '仪陇', 'yilong', '101270505', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2070, '西充', 'xichong', '101270506', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2071, '阆中', 'langzhong', '101270507', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2072, '达州', 'dazhou', '101270601', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2073, '宣汉', 'xuanhan', '101270602', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2074, '开江', 'kaijiang', '101270603', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2075, '大竹', 'dazhu', '101270604', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2076, '渠县', 'quxian', '101270605', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2077, '万源', 'wanyuan', '101270606', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2078, '通川', 'tongchuan', '101270607', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2079, '达县', 'daxian', '101270608', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2080, '遂宁', 'suining', '101270701', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2081, '蓬溪', 'pengxi', '101270702', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2082, '射洪', 'shehong', '101270703', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2083, '广安', 'guangan', '101270801', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2084, '岳池', 'yuechi', '101270802', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2085, '武胜', 'wusheng', '101270803', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2086, '邻水', 'linshui', '101270804', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2087, '华蓥', 'huaying', '101270805', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2088, '巴中', 'bazhong', '101270901', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2089, '通江', 'tongjiang', '101270902', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2090, '南江', 'nanjiang', '101270903', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2091, '平昌', 'pingchang', '101270904', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2092, '泸州', 'luzhou', '101271001', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2093, '泸县', 'luxian', '101271003', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2094, '合江', 'hejiang', '101271004', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2095, '叙永', 'xuyong', '101271005', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2096, '古蔺', 'gulin', '101271006', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2097, '纳溪', 'naxi', '101271007', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2098, '宜宾', 'yibin', '101271101', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2099, '宜宾县', 'yibinxian', '101271103', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2100, '南溪', 'nanxi', '101271104', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2101, '江安', 'jiangan', '101271105', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2102, '长宁', 'changning', '101271106', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2103, '高县', 'gaoxian', '101271107', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2104, '珙县', 'gongxian', '101271108', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2105, '筠连', 'junlian', '101271109', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2106, '兴文', 'xingwen', '101271110', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2107, '屏山', 'pingshan', '101271111', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2108, '内江', 'neijiang', '101271201', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2109, '东兴', 'dongxing', '101271202', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2110, '威远', 'weiyuan', '101271203', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2111, '资中', 'zizhong', '101271204', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2112, '隆昌', 'longchang', '101271205', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2113, '资阳', 'ziyang', '101271301', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2114, '安岳', 'anyue', '101271302', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2115, '乐至', 'lezhi', '101271303', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2116, '简阳', 'jianyang', '101271304', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2117, '乐山', 'leshan', '101271401', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2118, '犍为', 'qianwei', '101271402', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2119, '井研', 'jingyan', '101271403', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2120, '夹江', 'jiajiang', '101271404', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2121, '沐川', 'muchuan', '101271405', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2122, '峨边', 'ebian', '101271406', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2123, '马边', 'mabian', '101271407', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2124, '峨眉', 'emei', '101271408', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2125, '峨眉山', 'emeishan', '101271409', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2126, '眉山', 'meishan', '101271501', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2127, '仁寿', 'renshou', '101271502', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2128, '彭山', 'pengshan', '101271503', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2129, '洪雅', 'hongya', '101271504', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2130, '丹棱', 'danleng', '101271505', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2131, '青神', 'qingshen', '101271506', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2132, '凉山', 'liangshan', '101271601', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2133, '木里', 'muli', '101271603', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2134, '盐源', 'yanyuan', '101271604', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2135, '德昌', 'dechang', '101271605', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2136, '会理', 'huili', '101271606', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2137, '会东', 'huidong', '101271607', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2138, '宁南', 'ningnan', '101271608', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2139, '普格', 'puge', '101271609', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2140, '西昌', 'xichang', '101271610', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2141, '金阳', 'jinyang', '101271611', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2142, '昭觉', 'zhaojue', '101271612', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2143, '喜德', 'xide', '101271613', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2144, '冕宁', 'mianning', '101271614', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2145, '越西', 'yuexi', '101271615', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2146, '甘洛', 'ganluo', '101271616', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2147, '雷波', 'leibo', '101271617', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2148, '美姑', 'meigu', '101271618', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2149, '布拖', 'butuo', '101271619', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2150, '雅安', 'yaan', '101271701', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2151, '名山', 'mingshan', '101271702', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2152, '荥经', 'yingjing', '101271703', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2153, '汉源', 'hanyuan', '101271704', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2154, '石棉', 'shimian', '101271705', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2155, '天全', 'tianquan', '101271706', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2156, '芦山', 'lushan', '101271707', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2157, '宝兴', 'baoxing', '101271708', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2158, '甘孜', 'ganzi', '101271801', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2159, '康定', 'kangding', '101271802', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2160, '泸定', 'luding', '101271803', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2161, '丹巴', 'danba', '101271804', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2162, '九龙', 'jiulong', '101271805', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2163, '雅江', 'yajiang', '101271806', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2164, '道孚', 'daofu', '101271807', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2165, '炉霍', 'luhuo', '101271808', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2166, '新龙', 'xinlong', '101271809', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2167, '德格', 'dege', '101271810', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2168, '白玉', 'baiyu', '101271811', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2169, '石渠', 'shiqu', '101271812', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2170, '色达', 'seda', '101271813', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2171, '理塘', 'litang', '101271814', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2172, '巴塘', 'batang', '101271815', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2173, '乡城', 'xiangcheng', '101271816', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2174, '稻城', 'daocheng', '101271817', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2175, '得荣', 'derong', '101271818', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2176, '阿坝', 'aba', '101271901', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2177, '汶川', 'wenchuan', '101271902', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2178, '理县', 'lixian', '101271903', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2179, '茂县', 'maoxian', '101271904', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2180, '松潘', 'songfan', '101271905', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2181, '九寨沟', 'jiuzhaigou', '101271906', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2182, '金川', 'jinchuan', '101271907', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2183, '小金', 'xiaojin', '101271908', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2184, '黑水', 'heishui', '101271909', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2185, '马尔康', 'maerkang', '101271910', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2186, '壤塘', 'rangtang', '101271911', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2187, '若尔盖', 'nuoergai', '101271912', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2188, '红原', 'hongyuan', '101271913', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2189, '南坪', 'nanping', '101271914', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2190, '德阳', 'deyang', '101272001', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2191, '中江', 'zhongjiang', '101272002', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2192, '广汉', 'guanghan', '101272003', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2193, '什邡', 'shifang', '101272004', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2194, '绵竹', 'mianzhu', '101272005', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2195, '罗江', 'luojiang', '101272006', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2196, '广元', 'guangyuan', '101272101', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2197, '旺苍', 'wangcang', '101272102', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2198, '青川', 'qingchuan', '101272103', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2199, '剑阁', 'jiange', '101272104', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2200, '苍溪', 'cangxi', '101272105', '四川', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2201, '广州', 'guangzhou', '101280101', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2202, '番禺', 'panyu', '101280102', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2203, '从化', 'conghua', '101280103', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2204, '增城', 'zengcheng', '101280104', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2205, '花都', 'huadu', '101280105', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2206, '韶关', 'shaoguan', '101280201', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2207, '乳源', 'ruyuan', '101280202', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2208, '始兴', 'shixing', '101280203', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2209, '翁源', 'wengyuan', '101280204', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2210, '乐昌', 'lechang', '101280205', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2211, '仁化', 'renhua', '101280206', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2212, '南雄', 'nanxiong', '101280207', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2213, '新丰', 'xinfeng', '101280208', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2214, '曲江', 'qujiang', '101280209', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2215, '浈江', 'chengjiang', '101280210', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2216, '武江', 'wujiang', '101280211', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2217, '惠州', 'huizhou', '101280301', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2218, '博罗', 'boluo', '101280302', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2219, '惠阳', 'huiyang', '101280303', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2220, '惠东', 'huidong', '101280304', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2221, '龙门', 'longmen', '101280305', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2222, '梅州', 'meizhou', '101280401', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2223, '兴宁', 'xingning', '101280402', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2224, '蕉岭', 'jiaoling', '101280403', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2225, '大埔', 'dabu', '101280404', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2226, '丰顺', 'fengshun', '101280406', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2227, '平远', 'pingyuan', '101280407', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2228, '五华', 'wuhua', '101280408', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2229, '梅县', 'meixian', '101280409', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2230, '汕头', 'shantou', '101280501', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2231, '潮阳', 'chaoyang', '101280502', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2232, '澄海', 'chenghai', '101280503', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2233, '南澳', 'nanao', '101280504', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2234, '深圳', 'shenzhen', '101280601', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2235, '珠海', 'zhuhai', '101280701', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2236, '斗门', 'doumen', '101280702', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2237, '金湾', 'jinwan', '101280703', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2238, '佛山', 'foshan', '101280800', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2239, '顺德', 'shunde', '101280801', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2240, '三水', 'sanshui', '101280802', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2241, '南海', 'nanhai', '101280803', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2242, '高明', 'gaoming', '101280804', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2243, '肇庆', 'zhaoqing', '101280901', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2244, '广宁', 'guangning', '101280902', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2245, '四会', 'sihui', '101280903', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2246, '德庆', 'deqing', '101280905', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2247, '怀集', 'huaiji', '101280906', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2248, '封开', 'fengkai', '101280907', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2249, '高要', 'gaoyao', '101280908', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2250, '湛江', 'zhanjiang', '101281001', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2251, '吴川', 'wuchuan', '101281002', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2252, '雷州', 'leizhou', '101281003', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2253, '徐闻', 'xuwen', '101281004', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2254, '廉江', 'lianjiang', '101281005', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2255, '赤坎', 'chikan', '101281006', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2256, '遂溪', 'suixi', '101281007', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2257, '坡头', 'potou', '101281008', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2258, '霞山', 'xiashan', '101281009', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2259, '麻章', 'mazhang', '101281010', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2260, '江门', 'jiangmen', '101281101', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2261, '开平', 'kaiping', '101281103', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2262, '新会', 'xinhui', '101281104', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2263, '恩平', 'enping', '101281105', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2264, '台山', 'taishan', '101281106', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2265, '蓬江', 'pengjiang', '101281107', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2266, '鹤山', 'heshan', '101281108', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2267, '江海', 'jianghai', '101281109', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2268, '河源', 'heyuan', '101281201', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2269, '紫金', 'zijin', '101281202', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2270, '连平', 'lianping', '101281203', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2271, '和平', 'heping', '101281204', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2272, '龙川', 'longchuan', '101281205', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2273, '东源', 'dongyuan', '101281206', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2274, '清远', 'qingyuan', '101281301', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2275, '连南', 'liannan', '101281302', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2276, '连州', 'lianzhou', '101281303', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2277, '连山', 'lianshan', '101281304', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2278, '阳山', 'yangshan', '101281305', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2279, '佛冈', 'fogang', '101281306', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2280, '英德', 'yingde', '101281307', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2281, '清新', 'qingxin', '101281308', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2282, '云浮', 'yunfu', '101281401', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2283, '罗定', 'luoding', '101281402', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2284, '新兴', 'xinxing', '101281403', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2285, '郁南', 'yunan', '101281404', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2286, '云安', 'yunan', '101281406', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2287, '潮州', 'chaozhou', '101281501', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2288, '饶平', 'raoping', '101281502', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2289, '潮安', 'chaoan', '101281503', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2290, '东莞', 'dongguan', '101281601', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2291, '中山', 'zhongshan', '101281701', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2292, '阳江', 'yangjiang', '101281801', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2293, '阳春', 'yangchun', '101281802', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2294, '阳东', 'yangdong', '101281803', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2295, '阳西', 'yangxi', '101281804', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2296, '揭阳', 'jieyang', '101281901', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2297, '揭西', 'jiexi', '101281902', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2298, '普宁', 'puning', '101281903', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2299, '惠来', 'huilai', '101281904', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2300, '揭东', 'jiedong', '101281905', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2301, '茂名', 'maoming', '101282001', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2302, '高州', 'gaozhou', '101282002', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2303, '化州', 'huazhou', '101282003', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2304, '电白', 'dianbai', '101282004', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2305, '信宜', 'xinyi', '101282005', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2306, '茂港', 'maogang', '101282006', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2307, '汕尾', 'shanwei', '101282101', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2308, '海丰', 'haifeng', '101282102', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2309, '陆丰', 'lufeng', '101282103', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2310, '陆河', 'luhe', '101282104', '广东', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2311, '昆明', 'kunming', '101290101', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2312, '东川', 'dongchuan', '101290103', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2313, '寻甸', 'xundian', '101290104', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2314, '晋宁', 'jinning', '101290105', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2315, '宜良', 'yiliang', '101290106', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2316, '石林', 'shilin', '101290107', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2317, '呈贡', 'chenggong', '101290108', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2318, '富民', 'fumin', '101290109', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2319, '嵩明', 'songming', '101290110', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2320, '禄劝', 'luquan', '101290111', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2321, '安宁', 'anning', '101290112', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2322, '太华山', 'taihuashan', '101290113', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2323, '大理', 'dali', '101290201', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2324, '云龙', 'yunlong', '101290202', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2325, '漾濞', 'yangbi', '101290203', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2326, '永平', 'yongping', '101290204', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2327, '宾川', 'binchuan', '101290205', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2328, '弥渡', 'midu', '101290206', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2329, '祥云', 'xiangyun', '101290207', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2330, '巍山', 'weishan', '101290208', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2331, '剑川', 'jianchuan', '101290209', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2332, '洱源', 'eryuan', '101290210', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2333, '鹤庆', 'heqing', '101290211', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2334, '南涧', 'nanjian', '101290212', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2335, '红河', 'honghe', '101290301', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2336, '石屏', 'shiping', '101290302', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2337, '建水', 'jianshui', '101290303', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2338, '弥勒', 'mile', '101290304', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2339, '元阳', 'yuanyang', '101290305', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2340, '绿春', 'lvchun', '101290306', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2341, '开远', 'kaiyuan', '101290307', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2342, '个旧', 'gejiu', '101290308', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2343, '蒙自', 'mengzi', '101290309', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2344, '屏边', 'pingbian', '101290310', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2345, '泸西', 'luxi', '101290311', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2346, '金平', 'jinping', '101290312', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2347, '河口', 'hekou', '101290313', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2348, '曲靖', 'qujing', '101290401', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2349, '沾益', 'zhanyi', '101290402', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2350, '陆良', 'luliang', '101290403', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2351, '富源', 'fuyuan', '101290404', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2352, '马龙', 'malong', '101290405', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2353, '师宗', 'shizong', '101290406', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2354, '罗平', 'luoping', '101290407', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2355, '会泽', 'huize', '101290408', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2356, '宣威', 'xuanwei', '101290409', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2357, '保山', 'baoshan', '101290501', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2358, '龙陵', 'longling', '101290503', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2359, '施甸', 'sidian', '101290504', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2360, '昌宁', 'changning', '101290505', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2361, '腾冲', 'tengchong', '101290506', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2362, '文山', 'wenshan', '101290601', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2363, '西畴', 'xichou', '101290602', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2364, '马关', 'maguan', '101290603', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2365, '麻栗坡', 'malipo', '101290604', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2366, '砚山', 'yanshan', '101290605', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2367, '丘北', 'qiubei', '101290606', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2368, '广南', 'guangnan', '101290607', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2369, '富宁', 'funing', '101290608', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2370, '玉溪', 'yuxi', '101290701', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2371, '澄江', 'chengjiang', '101290702', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2372, '江川', 'jiangchuan', '101290703', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2373, '通海', 'tonghai', '101290704', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2374, '华宁', 'huaning', '101290705', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2375, '新平', 'xinping', '101290706', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2376, '易门', 'yimen', '101290707', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2377, '峨山', 'eshan', '101290708', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2378, '元江', 'yuanjiang', '101290709', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2379, '楚雄', 'chuxiong', '101290801', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2380, '大姚', 'dayao', '101290802', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2381, '元谋', 'yuanmou', '101290803', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2382, '姚安', 'yaoan', '101290804', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2383, '牟定', 'mouding', '101290805', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2384, '南华', 'nanhua', '101290806', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2385, '武定', 'wuding', '101290807', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2386, '禄丰', 'lufeng', '101290808', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2387, '双柏', 'shuangbai', '101290809', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2388, '永仁', 'yongren', '101290810', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2389, '普洱', 'puer', '101290901', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2390, '景谷', 'jinggu', '101290902', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2391, '景东', 'jingdong', '101290903', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2392, '澜沧', 'lancang', '101290904', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2393, '墨江', 'mojiang', '101290906', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2394, '江城', 'jiangcheng', '101290907', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2395, '孟连', 'menglian', '101290908', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2396, '西盟', 'ximeng', '101290909', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2397, '镇沅', 'zhenyuan', '101290911', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2398, '宁洱', 'ninger', '101290912', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2399, '昭通', 'zhaotong', '101291001', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2400, '鲁甸', 'ludian', '101291002', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2401, '彝良', 'yiliang', '101291003', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2402, '镇雄', 'zhenxiong', '101291004', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2403, '威信', 'weixin', '101291005', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2404, '巧家', 'qiaojia', '101291006', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2405, '绥江', 'suijiang', '101291007', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2406, '永善', 'yongshan', '101291008', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2407, '盐津', 'yanjin', '101291009', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2408, '大关', 'daguan', '101291010', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2409, '水富', 'shuifu', '101291011', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2410, '临沧', 'lincang', '101291101', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2411, '沧源', 'cangyuan', '101291102', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2412, '耿马', 'gengma', '101291103', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2413, '双江', 'shuangjiang', '101291104', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2414, '凤庆', 'fengqing', '101291105', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2415, '永德', 'yongde', '101291106', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2416, '云县', 'yunxian', '101291107', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2417, '镇康', 'zhenkang', '101291108', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2418, '怒江', 'nujiang', '101291201', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2419, '福贡', 'fugong', '101291203', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2420, '兰坪', 'lanping', '101291204', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2421, '泸水', 'lushui', '101291205', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2422, '六库', 'liuku', '101291206', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2423, '贡山', 'gongshan', '101291207', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2424, '香格里拉', 'xianggelila', '101291301', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2425, '德钦', 'deqin', '101291302', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2426, '维西', 'weixi', '101291303', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2427, '中甸', 'zhongdian', '101291304', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2428, '丽江', 'lijiang', '101291401', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2429, '永胜', 'yongsheng', '101291402', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2430, '华坪', 'huaping', '101291403', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2431, '宁蒗', 'ninglang', '101291404', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2432, '德宏', 'dehong', '101291501', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2433, '陇川', 'longchuan', '101291503', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2434, '盈江', 'yingjiang', '101291504', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2435, '瑞丽', 'ruili', '101291506', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2436, '梁河', 'lianghe', '101291507', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2437, '潞西', 'luxi', '101291508', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2438, '景洪', 'jinghong', '101291601', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2439, '勐海', 'menghai', '101291603', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2440, '勐腊', 'mengla', '101291605', '云南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2441, '南宁', 'nanning', '101300101', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2442, '邕宁', 'yongning', '101300103', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2443, '横县', 'hengxian', '101300104', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2444, '隆安', 'longan', '101300105', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2445, '马山', 'mashan', '101300106', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2446, '上林', 'shanglin', '101300107', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2447, '武鸣', 'wuming', '101300108', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2448, '宾阳', 'binyang', '101300109', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2449, '崇左', 'chongzuo', '101300201', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2450, '天等', 'tiandeng', '101300202', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2451, '龙州', 'longzhou', '101300203', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2452, '凭祥', 'pingxiang', '101300204', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2453, '大新', 'daxin', '101300205', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2454, '扶绥', 'fusui', '101300206', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2455, '宁明', 'ningming', '101300207', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2456, '柳州', 'liuzhou', '101300301', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2457, '柳城', 'liucheng', '101300302', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2458, '鹿寨', 'luzhai', '101300304', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2459, '柳江', 'liujiang', '101300305', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2460, '融安', 'rongan', '101300306', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2461, '融水', 'rongshui', '101300307', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2462, '三江', 'sanjiang', '101300308', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2463, '来宾', 'laibin', '101300401', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2464, '忻城', 'xicheng', '101300402', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2465, '金秀', 'jinxiu', '101300403', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2466, '象州', 'xiangzhou', '101300404', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2467, '武宣', 'wuxuan', '101300405', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2468, '合山', 'heshan', '101300406', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2469, '桂林', 'guilin', '101300501', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2470, '龙胜', 'longsheng', '101300503', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2471, '永福', 'yongfu', '101300504', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2472, '临桂', 'lingui', '101300505', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2473, '兴安', 'xingan', '101300506', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2474, '灵川', 'lingchuan', '101300507', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2475, '全州', 'quanzhou', '101300508', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2476, '灌阳', 'guanyang', '101300509', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2477, '阳朔', 'yangshuo', '101300510', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2478, '恭城', 'gongcheng', '101300511', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2479, '平乐', 'pingle', '101300512', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2480, '荔浦', 'lipu', '101300513', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2481, '资源', 'ziyuan', '101300514', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2482, '梧州', 'wuzhou', '101300601', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2483, '藤县', 'tengxian', '101300602', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2484, '苍梧', 'cangwu', '101300604', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2485, '蒙山', 'mengshan', '101300605', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2486, '岑溪', 'cenxi', '101300606', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2487, '长洲', 'changzhou', '101300607', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2488, '贺州', 'hezhou', '101300701', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2489, '昭平', 'zhaoping', '101300702', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2490, '富川', 'fuchuan', '101300703', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2491, '钟山', 'zhongshan', '101300704', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2492, '贵港', 'guigang', '101300801', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2493, '桂平', 'guiping', '101300802', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2494, '平南', 'pingnan', '101300803', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2495, '玉林', 'yulin', '101300901', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2496, '博白', 'bobai', '101300902', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2497, '北流', 'beiliu', '101300903', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2498, '容县', 'rongxian', '101300904', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2499, '陆川', 'luchuan', '101300905', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2500, '兴业', 'xingye', '101300906', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2501, '百色', 'baise', '101301001', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2502, '那坡', 'napo', '101301002', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2503, '田阳', 'tianyang', '101301003', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2504, '德保', 'debao', '101301004', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2505, '靖西', 'jingxi', '101301005', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2506, '田东', 'tiandong', '101301006', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2507, '平果', 'pingguo', '101301007', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2508, '隆林', 'longlin', '101301008', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2509, '西林', 'xilin', '101301009', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2510, '乐业', 'leye', '101301010', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2511, '凌云', 'lingyun', '101301011', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2512, '田林', 'tianlin', '101301012', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2513, '钦州', 'qinzhou', '101301101', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2514, '浦北', 'pubei', '101301102', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2515, '灵山', 'lingshan', '101301103', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2516, '河池', 'hechi', '101301201', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2517, '天峨', 'tiane', '101301202', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2518, '东兰', 'donglan', '101301203', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2519, '巴马', 'bama', '101301204', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2520, '环江', 'huanjiang', '101301205', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2521, '罗城', 'luocheng', '101301206', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2522, '宜州', 'yizhou', '101301207', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2523, '凤山', 'fengshan', '101301208', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2524, '南丹', 'nandan', '101301209', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2525, '都安', 'andu', '101301210', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2526, '大化', 'dahua', '101301211', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2527, '北海', 'beihai', '101301301', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2528, '合浦', 'hepu', '101301302', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2529, '涠洲岛', 'weizhoudao', '101301303', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2530, '防城港', 'fangchenggang', '101301401', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2531, '上思', 'shangsi', '101301402', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2532, '东兴', 'dongxing', '101301403', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2533, '防城', 'fangcheng', '101301405', '广西', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2534, '海口', 'haikou', '101310101', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2535, '三亚', 'sanya', '101310201', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2536, '东方', 'dongfang', '101310202', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2537, '临高', 'lingao', '101310203', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2538, '澄迈', 'chengmai', '101310204', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2539, '儋州', 'danzhou', '101310205', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2540, '昌江', 'changjiang', '101310206', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2541, '白沙', 'baisha', '101310207', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2542, '琼中', 'qiongzhong', '101310208', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2543, '定安', 'dingan', '101310209', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2544, '屯昌', 'tunchang', '101310210', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2545, '琼海', 'qionghai', '101310211', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2546, '文昌', 'wenchang', '101310212', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2547, '保亭', 'baoting', '101310214', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2548, '万宁', 'wanning', '101310215', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2549, '陵水', 'lingshui', '101310216', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2550, '西沙', 'xisha', '101310217', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2551, '南沙', 'nansha', '101310220', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2552, '乐东', 'ledong', '101310221', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2553, '五指山', 'wuzhishan', '101310222', '海南', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2554, '香港', 'hongkong', '101320101', '香港', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2555, '新界', 'xinjie', '101320103', '香港', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2556, '澳门', 'macao', '101330101', '澳门', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2557, '氹仔岛', 'dangzidao', '101330102', '澳门', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2558, '路环岛', 'luhuandao', '101330103', '澳门', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2559, '台北', 'taibeixian', '101340101', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2560, '桃园', 'taoyuan', '101340102', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2561, '新竹', 'xinzhu', '101340103', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2562, '宜兰', 'yilan', '101340104', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2563, '高雄', 'gaoxiong', '101340201', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2564, '嘉义', 'jiayi', '101340202', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2565, '台南', 'tainan', '101340203', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2566, '台东', 'taidong', '101340204', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2567, '屏东', 'pingdong', '101340205', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2568, '台中', 'taizhong', '101340401', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2569, '苗栗', 'miaoli', '101340402', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2570, '彰化', 'zhanghua', '101340403', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2571, '南投', 'nantou', '101340404', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2572, '花莲', 'hualian', '101340405', '台湾', NULL, NULL, NULL, NULL);
INSERT INTO "public"."cities" VALUES (2573, '云林', 'yunlin', '101340406', '台湾', NULL, NULL, NULL, NULL);


-- ----------------------------
-- Records of config_cascade
-- ----------------------------
INSERT INTO "public"."config_cascade" VALUES (4, NULL, NULL, NULL, NULL, '统一存储配置', '1', 'config', 'oss', 4, 0, 'oss');
INSERT INTO "public"."config_cascade" VALUES (1, NULL, NULL, NULL, NULL, '认证配置', '1', 'config', 'authorized', 1, 0, 'authorized');
INSERT INTO "public"."config_cascade" VALUES (3, NULL, NULL, NULL, NULL, '资源配置', '1', 'config', 'resource', 2, 0, 'resource');
INSERT INTO "public"."config_cascade"("id", "create_time", "create_user_id", "last_update_time", "last_update_user_id", "config_label", "config_status", "config_value", "parent_id", "num", "config_type", "path") VALUES (5, NULL, NULL, NULL, NULL, '消息配置', '1', 'ums', 0, 5, 'config', 'ums');
INSERT INTO "public"."config_cascade"("id", "create_time", "create_user_id", "last_update_time", "last_update_user_id", "config_label", "config_status", "config_value", "parent_id", "num", "config_type", "path") VALUES (6, NULL, NULL, NULL, NULL, '统一搜索配置', '1', 'unisearch', 0, 6, 'config', 'unisearch');
