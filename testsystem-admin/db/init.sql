/*
Navicat MySQL Data Transfer

Source Server         : 47.99.203.78
Source Server Version : 50635
Source Host           : 47.99.203.78:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2019-11-16 16:39:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', null, 'io.renren.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xEFBFBDEFBFBD0005737200156F72672E71756172747A2E4A6F62446174614D6170EFBFBDEFBFBDEFBFBDE8BFA9EFBFBDEFBFBD020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D6170EFBFBD08EFBFBDEFBFBDEFBFBDEFBFBD5D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013EFBFBD2EEFBFBD28760AEFBFBD0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507EFBFBDEFBFBDEFBFBD1660EFBFBD03000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686AEFBFBD014B597419030000787077080000016B731E40EFBFBD7874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673BEFBFBDEFBFBDCC8F23EFBFBD0200014A000576616C7565787200106A6176612E6C616E672E4E756D626572EFBFBDEFBFBDEFBFBD1D0BEFBFBDEFBFBDEFBFBD0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4EFBFBDEFBFBDEFBFBD3802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('RenrenScheduler', 'DESKTOP-I4G143G1562730205871', '1562732083796', '15000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', null, '1561545000000', '-1', '5', 'WAITING', 'CRON', '1561004785000', '0', null, '2', 0xEFBFBDEFBFBD0005737200156F72672E71756172747A2E4A6F62446174614D6170EFBFBDEFBFBDEFBFBDE8BFA9EFBFBDEFBFBD020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D6170EFBFBD08EFBFBDEFBFBDEFBFBDEFBFBD5D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013EFBFBD2EEFBFBD28760AEFBFBD0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507EFBFBDEFBFBDEFBFBD1660EFBFBD03000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686AEFBFBD014B597419030000787077080000016B731E40EFBFBD7874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673BEFBFBDEFBFBDCC8F23EFBFBD0200014A000576616C7565787200106A6176612E6C616E672E4E756D626572EFBFBDEFBFBDEFBFBD1D0BEFBFBDEFBFBDEFBFBD0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4EFBFBDEFBFBDEFBFBD3802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
INSERT INTO `schedule_job_log` VALUES ('1', '1', 'testTask', 'renren', '0', null, '2', '2019-06-20 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('2', '1', 'testTask', 'renren', '0', null, '1', '2019-06-20 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('3', '1', 'testTask', 'renren', '0', null, '1', '2019-06-20 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('4', '1', 'testTask', 'renren', '0', null, '7', '2019-06-20 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('5', '1', 'testTask', 'renren', '0', null, '3', '2019-06-20 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('6', '1', 'testTask', 'renren', '0', null, '3', '2019-06-20 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('7', '1', 'testTask', 'renren', '0', null, '1', '2019-06-21 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('8', '1', 'testTask', 'renren', '0', null, '2', '2019-06-21 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('9', '1', 'testTask', 'renren', '0', null, '1', '2019-06-22 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('10', '1', 'testTask', 'renren', '0', null, '2', '2019-06-22 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('11', '1', 'testTask', 'renren', '0', null, '4', '2019-06-22 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('12', '1', 'testTask', 'renren', '0', null, '1', '2019-06-24 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('13', '1', 'testTask', 'renren', '0', null, '2', '2019-06-25 09:00:00');
INSERT INTO `schedule_job_log` VALUES ('14', '1', 'testTask', 'renren', '0', null, '0', '2019-06-25 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('15', '1', 'testTask', 'renren', '0', null, '2', '2019-06-25 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('16', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('17', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('18', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('19', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('20', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('21', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('22', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('23', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('24', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('25', '1', 'testTask', 'renren', '0', null, '0', '2019-06-25 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('26', '1', 'testTask', 'renren', '0', null, '0', '2019-06-25 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('27', '1', 'testTask', 'renren', '0', null, '2', '2019-06-25 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('28', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('29', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('30', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('31', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('32', '1', 'testTask', 'renren', '0', null, '1', '2019-06-25 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('33', '1', 'testTask', 'renren', '0', null, '0', '2019-06-26 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('34', '1', 'testTask', 'renren', '0', null, '1', '2019-06-26 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('35', '1', 'testTask', 'renren', '0', null, '6', '2019-06-26 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('36', '1', 'testTask', 'renren', '0', null, '2', '2019-06-26 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('37', '1', 'testTask', 'renren', '0', null, '1', '2019-06-26 18:00:00');

-- ----------------------------
-- Table structure for sys_answer
-- ----------------------------
DROP TABLE IF EXISTS `sys_answer`;
CREATE TABLE `sys_answer` (
  `answer_id` bigint(50) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(50) DEFAULT NULL COMMENT '答题人id',
  `test_id` varchar(100) DEFAULT NULL COMMENT '试卷id',
  `subject_id` varchar(100) DEFAULT NULL COMMENT '试题id',
  `content` varchar(3000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '答案内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `price` bigint(100) DEFAULT NULL COMMENT '得分',
  `orders` bigint(10) DEFAULT NULL COMMENT '第几次考试此卷',
  `uuid` varchar(100) DEFAULT NULL,
  `shanchu` int(1) unsigned zerofill DEFAULT NULL COMMENT '是否删除,1删除 0;不删除',
  `falg` int(1) DEFAULT NULL COMMENT '是否错误,1:正确0：错误',
  PRIMARY KEY (`answer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=285 DEFAULT CHARSET=utf8 COMMENT='做题答案表';

-- ----------------------------
-- Records of sys_answer
-- ----------------------------

-- ----------------------------
-- Table structure for sys_book
-- ----------------------------
DROP TABLE IF EXISTS `sys_book`;
CREATE TABLE `sys_book` (
  `book_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `course_id` bigint(10) DEFAULT NULL COMMENT '科目id',
  `num` bigint(10) DEFAULT NULL COMMENT '浏览次数',
  `admin` varchar(10) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `body` text CHARACTER SET utf8mb4,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COMMENT='电子书籍表';

-- ----------------------------
-- Records of sys_book
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', '0', '云存储配置信息');

-- ----------------------------
-- Table structure for sys_course
-- ----------------------------
DROP TABLE IF EXISTS `sys_course`;
CREATE TABLE `sys_course` (
  `course_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级科目id',
  `name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '科目名称',
  `type` varchar(50) DEFAULT NULL COMMENT '题型类型',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `order_num` int(10) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `admin` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1 COMMENT='科目表';

-- ----------------------------
-- Records of sys_course
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `user_id` bigint(20) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `type` varchar(100) NOT NULL COMMENT '字典类型',
  `code` varchar(100) NOT NULL COMMENT '字典码',
  `value` varchar(1000) NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '性别', 'sex', '0', '女', '0', null, '0');
INSERT INTO `sys_dict` VALUES ('2', '性别', 'sex', '1', '男', '1', null, '0');
INSERT INTO `sys_dict` VALUES ('3', '性别', 'sex', '2', '未知', '3', null, '0');

-- ----------------------------
-- Table structure for sys_jungle
-- ----------------------------
DROP TABLE IF EXISTS `sys_jungle`;
CREATE TABLE `sys_jungle` (
  `jungle_id` bigint(100) NOT NULL AUTO_INCREMENT,
  `subject_id` bigint(100) DEFAULT NULL COMMENT '题目id',
  `content` text CHARACTER SET utf8mb4 COMMENT '内容',
  `name` varchar(10) DEFAULT NULL COMMENT '选项',
  `admin` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`jungle_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_jungle
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=299 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(1000) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'fa fa-cog', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '管理员管理', 'modules/sys/user.html', null, '1', 'fa fa-user', '3');
INSERT INTO `sys_menu` VALUES ('3', '1', '角色管理', 'modules/sys/role.html', null, '1', 'fa fa-user-secret', '2');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'modules/sys/menu.html', null, '1', 'fa fa-th-list', '3');
INSERT INTO `sys_menu` VALUES ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('19', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('20', '3', '新增', null, 'sys:role:save,sys:menu:perms', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('21', '3', '修改', null, 'sys:role:update,sys:menu:perms', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('22', '3', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('23', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('24', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('25', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('26', '4', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('29', '1', '系统日志', 'modules/sys/log.html', 'sys:log:list', '1', 'fa fa-file-text-o', '7');
INSERT INTO `sys_menu` VALUES ('31', '1', '部门管理', 'modules/sys/dept.html', null, '1', 'fa fa-file-code-o', '1');
INSERT INTO `sys_menu` VALUES ('32', '31', '查看', null, 'sys:dept:list,sys:dept:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('33', '31', '新增', null, 'sys:dept:save,sys:dept:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('34', '31', '修改', null, 'sys:dept:update,sys:dept:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('35', '31', '删除', null, 'sys:dept:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('41', '0', '账号管理', null, null, '0', 'fa fa-address-book', '1');
INSERT INTO `sys_menu` VALUES ('42', '41', '用户账号', 'modules/stu/sysstu.html', 'sys:user:list,sys:user:info,sys:user:save,sys:user:update,sys:user:delete,sys:user:saves,sys:user:import,importUsers:save,sys:user:outport', '1', 'fa fa-address-book-o', '0');
INSERT INTO `sys_menu` VALUES ('43', '0', '试题管理', null, null, '0', 'fa fa-file-text-o', '2');
INSERT INTO `sys_menu` VALUES ('44', '0', '过程管理', null, null, '0', 'fa fa-superpowers', '3');
INSERT INTO `sys_menu` VALUES ('46', '0', '个人事务', null, null, '0', 'fa fa-user-circle-o', '4');
INSERT INTO `sys_menu` VALUES ('48', '0', '题库管理', null, null, '0', 'fa fa-file-text', '6');
INSERT INTO `sys_menu` VALUES ('49', '0', '成绩管理', null, null, '0', 'fa fa-file-o', '7');
INSERT INTO `sys_menu` VALUES ('50', '0', '新闻管理', 'modules/news/sysnews.html', 'sys:sysnews:list,sys:sysnews:info,sys:sysnews:save,sys:sysnews:update,sys:sysnews:delete\r\n', '0', 'fa fa-newspaper-o', '8');
INSERT INTO `sys_menu` VALUES ('51', '50', '新闻管理', 'modules/news/sysnews.html', 'sys:sysnews:list,sys:sysnews:info,sys:sysnews:save,sys:sysnews:update,sys:sysnews:delete,uploadFile:save', '1', 'fa fa-bars', '0');
INSERT INTO `sys_menu` VALUES ('52', '46', '新闻列表', 'modules/myself/myself.html', 'sys:sysnews:list,sys:sysnews:info,sys:sysnews:save,sys:sysnews:update,sys:sysnews:delete,uploadFile:save', '1', 'fa fa-file-code-o', '0');
INSERT INTO `sys_menu` VALUES ('54', '1', '知识点设置', 'modules/course/syscourse.html', 'sys:syscourse:list,sys:syscourse:info,sys:syscourse:save,sys:syscourse:update,sys:syscourse:delete,uploadFile:save,sys:syscourse:select,sys:dept:list', '1', 'fa fa-wpexplorer', '10');
INSERT INTO `sys_menu` VALUES ('55', '1', '题型设置', 'modules/topic/systopic.html', 'sys:systopic:list,sys:systopic:info,sys:systopic:save,sys:systopic:update,sys:systopic:delete,uploadFile:save', '1', 'fa fa-check-circle-o ', '11');
INSERT INTO `sys_menu` VALUES ('56', '48', '题库列表', 'modules/subject/syssubject.html', 'sys:syssubject:list,sys:syssubject:info,sys:syssubject:save,sys:syssubject:update,sys:syssubject:delete,uploadFile:save,sys/syssubject/list,sys:syssubject:getTopics,sys:syssubject:select,importUsers:save,uploadFile:save,importTests:save,sys:syssubject:input,sys:syssubject:output,sys:syssubject:subjects,sys:syssubject:topics', '1', 'fa fa-server', '0');
INSERT INTO `sys_menu` VALUES ('57', '48', '电子书籍', 'modules/book/sysbook.html', 'sys:sysbook:list,sys:sysbook:info,sys:sysbook:save,sys:sysbook:update,sys:sysbook:delete,uploadFile:save,sys:syssubject:select,uploadFile:save', '1', 'fa fa-television', '1');
INSERT INTO `sys_menu` VALUES ('58', '48', '题库统计', 'modules/subject/syssubject2.html', 'sys:syssubject:list,sys:syssubject:info,sys:syssubject:save,sys:syssubject:update,sys:syssubject:delete,uploadFile:save,sys/syssubject/list,sys:syssubject:getTopics,sys:syssubject:select,importUsers:save,uploadFile:save,importTests:save,sys:syssubject:input,sys:syssubject:output,sys:syssubject:subjects,sys:syssubject:topics', '1', 'fa fa-window-minimize', '2');
INSERT INTO `sys_menu` VALUES ('59', '43', '试卷管理', 'modules/test/systest.html', 'sys:systest:list,sys:systest:info,sys:systest:save,sys:systest:update,sys:systest:delete,uploadFile:save,sys:user:list,sys:user:info,sys:user:save,sys:user:update,sys:user:delete,sys:user:saves,sys:user:import,importUsers:save,sys:user:outport,sys:systest:select,sys:systest:course,sys:systest:courset,sys:systest:topic,sys:systest:subject,sys:systest:courses,sys:systest:titles,sys:syssubject:list,sys:syssubject:info,sys:syssubject:save,sys:syssubject:update,sys:syssubject:delete,uploadFile:save,sys/syssubject/list,sys:syssubject:getTopics,sys:syssubject:select,importUsers:save,uploadFile:save,importTests:save,sys:syssubject:input,sys:syssubject:output,sys:syssubject:subjects,sys:syssubject:topics,sys:systest:num', '1', 'fa fa-text-height', '0');
INSERT INTO `sys_menu` VALUES ('61', '46', '参加考试', 'modules/myself/mytest.html', 'sys:systest:list,sys:systest:info,sys:systest:save,sys:systest:update,sys:systest:delete,uploadFile:save,sys:user:list,sys:user:info,sys:user:save,sys:user:update,sys:user:delete,sys:user:saves,sys:user:import,importUsers:save,sys:user:outport,sys:systest:select,sys:systest:course,sys:systest:courset,sys:systest:topic,sys:systest:subject,sys:systest:courses,sys:systest:titles,sys:systest:list2,sys:systest:titles2,sys:sysanswer:list,sys:sysanswer:info,sys:sysanswer:save,sys:sysanswer:update,sys:sysanswer:delete', '1', 'fa fa-list', '1');
INSERT INTO `sys_menu` VALUES ('62', '46', '参加作业', 'modules/myself/mytest2.html', 'sys:systitle:list,sys:systitle:info,sys:systitle:save,sys:systitle:update,sys:systitle:delete,uploadFile:save,sys:systitle:type,sys:systest:course,sys:systest:courset,sys:systest:topic,sys:systest:subject,sys:systest:courses,sys:systest:list2,sys:systest:titles2,sys:sysanswer:list,sys:sysanswer:info,sys:sysanswer:save,sys:sysanswer:update,sys:sysanswer:delete', '1', 'fa fa-text-height', '1');
INSERT INTO `sys_menu` VALUES ('63', '44', '试卷结果', 'modules/mytest/sysmytest.html', 'sys:sysmytest:list,sys:sysmytest:info,sys:sysmytest:save,sys:sysmytest:update,sys:sysmytest:delete,uploadFile:save,sys:sysmytest:mytestId,sys:sysmytest:mytestId2,sys:dept:list', '1', 'fa fa-quora', '0');
INSERT INTO `sys_menu` VALUES ('64', '49', '试卷成绩', 'modules/mytest/sysmyprice.html', 'sys:sysmytest:list,sys:sysmytest:info,sys:sysmytest:save,sys:sysmytest:update,sys:sysmytest:delete,uploadFile:save,sys:sysmytest:mytestId,sys:sysmytest:mytestId2,sys:dept:list,sys:syssubject:select', '1', 'fa fa-graduation-cap', '0');
INSERT INTO `sys_menu` VALUES ('65', '49', '作业成绩', 'modules/mytest/sysmyprice2.html', 'sys:sysmytest:list,sys:sysmytest:info,sys:sysmytest:save,sys:sysmytest:update,sys:sysmytest:delete,uploadFile:save,sys:sysmytest:mytestId,sys:sysmytest:mytestId2,sys:dept:list,sys:syssubject:select', '1', 'fa fa-globe', '1');
INSERT INTO `sys_menu` VALUES ('66', '46', '参加学习', 'modules/myself/book.html', 'sys:sysbook:list,sys:sysbook:info,sys:sysbook:save,sys:sysbook:update,sys:sysbook:delete,uploadFile:save,sys:syssubject:select,uploadFile:save', '1', 'fa fa-television', '0');
INSERT INTO `sys_menu` VALUES ('68', '46', '错题管理', 'modules/myself/cuoti.html', 'sys:systest:list,sys:systest:info,sys:systest:save,sys:systest:update,sys:systest:delete,uploadFile:save,sys:user:list,sys:user:info,sys:user:save,sys:user:update,sys:user:delete,sys:user:saves,sys:user:import,importUsers:save,sys:user:outport,sys:systest:select,sys:systest:course,sys:systest:courset,sys:systest:topic,sys:systest:subject,sys:systest:courses,sys:systest:titles,sys:systest:list2,sys:systest:titles2,sys:sysanswer:list,sys:sysanswer:info,sys:sysanswer:save,sys:sysanswer:update,sys:sysanswer:delete,sys:sysanswer:cuotis,sys:sysanswer:cuotiInfo,sys:sysanswer:delete3', '1', 'fa fa-window-close', '0');
INSERT INTO `sys_menu` VALUES ('69', '0', '个人信息', null, null, '0', 'fa fa-info', '0');
INSERT INTO `sys_menu` VALUES ('70', '69', '信息维护', 'modules/myself/info.html', 'sys:user:list,sys:user:info,sys:user:save,sys:user:update,sys:user:delete,sys:user:saves,sys:user:import,importUsers:save,sys:user:outport', '1', 'fa fa-address-book-o', '0');

-- ----------------------------
-- Table structure for sys_mytest
-- ----------------------------
DROP TABLE IF EXISTS `sys_mytest`;
CREATE TABLE `sys_mytest` (
  `mytest_id` bigint(50) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(50) DEFAULT NULL COMMENT '答题人id',
  `test_id` varchar(100) DEFAULT NULL COMMENT '试卷id',
  `admin_id` varchar(100) DEFAULT NULL COMMENT '评卷人id',
  `price` varchar(10) DEFAULT NULL COMMENT '试题得分',
  `inprice` varchar(10) DEFAULT NULL COMMENT '是否通过',
  `user_name` varchar(100) DEFAULT NULL COMMENT '评卷人昵称',
  `test_name` varchar(100) DEFAULT NULL COMMENT '试卷名称',
  `kuangkao` varchar(10) DEFAULT NULL COMMENT '是否缺席',
  `pingjuan` int(1) DEFAULT NULL COMMENT '是否评卷',
  `type` varchar(5) DEFAULT NULL COMMENT '类型',
  `orders` bigint(10) DEFAULT NULL COMMENT '第几次答此卷',
  `date` datetime DEFAULT NULL COMMENT '提交试卷时间',
  PRIMARY KEY (`mytest_id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8 COMMENT='学生考试结果表';

-- ----------------------------
-- Records of sys_mytest
-- ----------------------------

-- ----------------------------
-- Table structure for sys_news
-- ----------------------------
DROP TABLE IF EXISTS `sys_news`;
CREATE TABLE `sys_news` (
  `new_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `body` text CHARACTER SET utf8mb4 COMMENT '新闻内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '新闻标题',
  `dept_id` varchar(20) DEFAULT NULL COMMENT '部门id',
  `num` bigint(20) DEFAULT NULL COMMENT '浏览次数',
  `admin` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '发布人',
  PRIMARY KEY (`new_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_news
-- ----------------------------

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1494 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_subject
-- ----------------------------
DROP TABLE IF EXISTS `sys_subject`;
CREATE TABLE `sys_subject` (
  `subject_id` bigint(50) NOT NULL AUTO_INCREMENT,
  `course_id` bigint(50) DEFAULT NULL COMMENT '科目表id',
  `topic_id` bigint(10) DEFAULT NULL COMMENT '题型id',
  `difficulty` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '题目难度',
  `num` int(10) DEFAULT NULL COMMENT '选项数量',
  `price` int(10) DEFAULT NULL COMMENT '题目分数',
  `content` text CHARACTER SET utf8mb4 COMMENT '题目内容',
  `admin` varchar(100) DEFAULT NULL COMMENT '创建人',
  `answer` varchar(1000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '答案',
  `parse` text CHARACTER SET utf8mb4 COMMENT '解析',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `file` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '操作题文件',
  `answert` varchar(1000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '判断题答案',
  `name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '题目名称',
  `uuid` varchar(100) DEFAULT NULL,
  `answertt` varchar(1000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '选择题答案',
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_subject
-- ----------------------------

-- ----------------------------
-- Table structure for sys_test
-- ----------------------------
DROP TABLE IF EXISTS `sys_test`;
CREATE TABLE `sys_test` (
  `test_id` bigint(50) NOT NULL AUTO_INCREMENT,
  `dept_id` varchar(500) DEFAULT NULL COMMENT '参考部门id',
  `admin_id` varchar(200) DEFAULT NULL COMMENT '评卷人id',
  `name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '试题名称',
  `type` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '试题类型',
  `typet` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '出题方式',
  `times` bigint(10) DEFAULT NULL COMMENT '答题时间',
  `price` int(10) DEFAULT NULL COMMENT '试题总分',
  `inprice` int(10) DEFAULT NULL COMMENT '通过分数',
  `test_type` varchar(10) DEFAULT NULL COMMENT '考试类型（1：允许多次参加考试，2：允许填空类试题自动评卷，3：允许查看评卷结果，4：允许自动保存答卷）',
  `fix_times` bigint(10) DEFAULT NULL COMMENT '间隔时间',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `num` bigint(10) DEFAULT NULL COMMENT '题目个数',
  `admin` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `uuid` varchar(100) DEFAULT NULL,
  `subject_id` varchar(10) DEFAULT NULL COMMENT '题库试题id',
  `parent_id` varchar(100) DEFAULT NULL,
  `user_id` bigint(100) DEFAULT NULL,
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 COMMENT='考试表';

-- ----------------------------
-- Records of sys_test
-- ----------------------------

-- ----------------------------
-- Table structure for sys_testsubs
-- ----------------------------
DROP TABLE IF EXISTS `sys_testsubs`;
CREATE TABLE `sys_testsubs` (
  `testsubs_id` bigint(50) NOT NULL AUTO_INCREMENT,
  `test_id` bigint(10) DEFAULT NULL COMMENT '试卷id',
  `course_id` bigint(10) DEFAULT NULL COMMENT '科目id',
  `course_name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '科目名称',
  `topic_id` bigint(10) DEFAULT NULL COMMENT '题型id',
  `num` bigint(10) DEFAULT NULL COMMENT '题目数量',
  `admin` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`testsubs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COMMENT='试卷随机试题表';

-- ----------------------------
-- Records of sys_testsubs
-- ----------------------------

-- ----------------------------
-- Table structure for sys_title
-- ----------------------------
DROP TABLE IF EXISTS `sys_title`;
CREATE TABLE `sys_title` (
  `title_id` bigint(50) NOT NULL AUTO_INCREMENT,
  `test_id` bigint(10) DEFAULT NULL COMMENT '试卷id',
  `subject_id` varchar(100) DEFAULT NULL COMMENT '题库试题id',
  `orders` int(10) DEFAULT NULL COMMENT '题目顺序',
  `admin` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `topic_id` bigint(10) DEFAULT NULL COMMENT '试卷id',
  PRIMARY KEY (`title_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1025 DEFAULT CHARSET=utf8 COMMENT='试题表';

-- ----------------------------
-- Records of sys_title
-- ----------------------------

-- ----------------------------
-- Table structure for sys_topic
-- ----------------------------
DROP TABLE IF EXISTS `sys_topic`;
CREATE TABLE `sys_topic` (
  `topic_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '题型',
  `admin` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `name` varchar(1000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '题型名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name2` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1 COMMENT='题型表';

-- ----------------------------
-- Records of sys_topic
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(500) DEFAULT NULL COMMENT '密码',
  `passw` varchar(500) DEFAULT NULL,
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `sex` varchar(5) DEFAULT NULL COMMENT '性别',
  `type` int(1) DEFAULT NULL COMMENT '类型（1：管理员 2：学生）',
  `picture` varchar(50) DEFAULT NULL COMMENT '照片',
  `birthday` varchar(50) DEFAULT NULL COMMENT '出生年月',
  `ip` varchar(64) DEFAULT NULL COMMENT '登录IP',
  `idcard` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `idtype` varchar(10) DEFAULT NULL COMMENT '证件类型',
  `num` bigint(10) DEFAULT NULL COMMENT '批量数量',
  `admin` varchar(10) DEFAULT NULL COMMENT '创建人id',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=344 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '0bcbe1cb31eb6a752f24e72d6fc227505c9ac54da385ac1f5a2e3051762f5af8', 'admin', 'yZwRb280hsctXgemyGYY', 'cemb@163.com', '321321', '1', '0', '321', '1', null, null, null, '3213213', '321321', '0', '1', 'admin1', '2019-06-20 11:11:11');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
