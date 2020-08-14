/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-07-30 11:02:09
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
INSERT INTO `qrtz_scheduler_state` VALUES ('RenrenScheduler', 'DESKTOP-I4G143G1564455336045', '1564455728991', '15000');

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
-- Table structure for sys_book
-- ----------------------------
DROP TABLE IF EXISTS `sys_book`;
CREATE TABLE `sys_book` (
  `book_id` bigint(10) NOT NULL,
  `course_id` bigint(10) DEFAULT NULL COMMENT '科目id',
  `num` int(10) DEFAULT NULL COMMENT '浏览次数',
  `admin` bigint(10) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='电子书籍表';

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
  `admin` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COMMENT='科目表';

-- ----------------------------
-- Records of sys_course
-- ----------------------------
INSERT INTO `sys_course` VALUES ('1', '0', '生物', null, '1', '0', '-1', '1', '2019-07-16 10:12:29');
INSERT INTO `sys_course` VALUES ('2', '1', '生物2', null, '1', '0', '-1', '1', '2019-07-16 10:12:31');
INSERT INTO `sys_course` VALUES ('3', '0', '通信', null, '1', '0', '0', '1', '2019-07-12 15:43:58');
INSERT INTO `sys_course` VALUES ('4', '3', '物理', null, '1', '0', '0', '1', '2019-07-12 15:44:10');
INSERT INTO `sys_course` VALUES ('5', '0', '计算机', null, '1', '1', '0', '1', '2019-07-12 15:44:26');
INSERT INTO `sys_course` VALUES ('6', '5', 'C语言', null, '1', '0', '0', '1', '2019-07-12 15:44:36');
INSERT INTO `sys_course` VALUES ('7', '0', '物理', null, '1', '0', '0', '1', '2019-07-16 10:11:19');
INSERT INTO `sys_course` VALUES ('8', '0', '111', null, '1', '0', '0', '1', '2019-07-21 19:57:34');
INSERT INTO `sys_course` VALUES ('9', '0', '222', null, '0', '0', '0', '1', '2019-07-21 19:58:36');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '6', '计算机学院', '0', '0');
INSERT INTO `sys_dept` VALUES ('6', '7', '太原理工大学', '0', '0');
INSERT INTO `sys_dept` VALUES ('7', '0', '在线考试系统', '0', '0');
INSERT INTO `sys_dept` VALUES ('8', '1', '物联网工程', '0', '0');
INSERT INTO `sys_dept` VALUES ('9', '8', '1班', '0', '0');
INSERT INTO `sys_dept` VALUES ('10', '7', '四川大学', '1', '0');
INSERT INTO `sys_dept` VALUES ('11', '10', '通信学院', '0', '0');
INSERT INTO `sys_dept` VALUES ('12', '0', '111', '0', '0');
INSERT INTO `sys_dept` VALUES ('13', '12', '222', '0', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_jungle
-- ----------------------------
INSERT INTO `sys_jungle` VALUES ('1', null, '2', 'A', '1', '2019-07-20 19:46:13');
INSERT INTO `sys_jungle` VALUES ('2', null, '3', 'B', '1', '2019-07-20 19:46:15');
INSERT INTO `sys_jungle` VALUES ('3', null, '2', 'A', '1', '2019-07-21 10:12:23');
INSERT INTO `sys_jungle` VALUES ('4', null, '3', 'B', '1', '2019-07-21 10:12:23');
INSERT INTO `sys_jungle` VALUES ('5', '8', '2', 'A', '1', '2019-07-21 10:25:00');
INSERT INTO `sys_jungle` VALUES ('6', '8', '3', 'B', '1', '2019-07-21 10:25:00');
INSERT INTO `sys_jungle` VALUES ('7', '9', '3', 'A', '1', '2019-07-21 10:38:15');
INSERT INTO `sys_jungle` VALUES ('8', '9', '4', 'B', '1', '2019-07-21 10:38:15');
INSERT INTO `sys_jungle` VALUES ('9', '9', '5', 'C', '1', '2019-07-21 10:38:15');
INSERT INTO `sys_jungle` VALUES ('10', '10', '2', 'A', '1', '2019-07-21 10:45:14');
INSERT INTO `sys_jungle` VALUES ('11', '10', '3', 'B', '1', '2019-07-21 10:45:14');
INSERT INTO `sys_jungle` VALUES ('12', '11', '2', 'A', '1', '2019-07-21 10:51:17');
INSERT INTO `sys_jungle` VALUES ('13', '11', '3', 'B', '1', '2019-07-21 10:51:17');
INSERT INTO `sys_jungle` VALUES ('14', '19', null, 'A', '1', '2019-07-21 20:28:56');
INSERT INTO `sys_jungle` VALUES ('15', '19', null, 'B', '1', '2019-07-21 20:28:56');

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
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":1,\"username\":\"admin\",\"password\":\"e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b\",\"salt\":\"YzcmCZNvbXocrsz9dm8e\",\"email\":\"root@renren.io\",\"mobile\":\"13612345678\",\"status\":1,\"roleIdList\":[],\"createTime\":\"Nov 11, 2016 11:11:11 AM\",\"deptId\":7,\"deptName\":\"在线考试系统\"}', '199', '0:0:0:0:0:0:0:1', '2019-06-20 19:20:26');
INSERT INTO `sys_log` VALUES ('2', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":1,\"username\":\"admin\",\"salt\":\"YzcmCZNvbXocrsz9dm8e\",\"email\":\"cemb@163.com\",\"mobile\":\"\",\"status\":1,\"roleIdList\":[],\"createTime\":\"Nov 11, 2016 11:11:11 AM\",\"deptId\":7,\"deptName\":\"在线考试系统\"}', '14', '0:0:0:0:0:0:0:1', '2019-06-20 19:20:59');
INSERT INTO `sys_log` VALUES ('3', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":2,\"username\":\"tylg\",\"password\":\"24e8c731be850d1a4c6db2df372cc6488bc4d080942ac6d4226d5995ec9bc528\",\"salt\":\"Rz4ZaliFQfRMSeSi9UXA\",\"email\":\"c@qq.com\",\"status\":1,\"roleIdList\":[],\"createTime\":\"Jun 20, 2019 7:22:56 PM\",\"deptId\":6,\"deptName\":\"太原理工大学\"}', '31', '0:0:0:0:0:0:0:1', '2019-06-20 19:22:56');
INSERT INTO `sys_log` VALUES ('4', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":2,\"username\":\"tylg\",\"salt\":\"Rz4ZaliFQfRMSeSi9UXA\",\"email\":\"c@qq.com\",\"status\":1,\"roleIdList\":[],\"createTime\":\"Jun 20, 2019 7:22:56 PM\",\"deptId\":9,\"deptName\":\"1班\"}', '13', '0:0:0:0:0:0:0:1', '2019-06-20 19:23:23');
INSERT INTO `sys_log` VALUES ('5', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":1,\"roleName\":\"太原理工大学\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,31,32,33,34,35],\"deptIdList\":[],\"createTime\":\"Jun 20, 2019 7:28:18 PM\"}', '59', '0:0:0:0:0:0:0:1', '2019-06-20 19:28:19');
INSERT INTO `sys_log` VALUES ('6', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":2,\"username\":\"tylg\",\"salt\":\"Rz4ZaliFQfRMSeSi9UXA\",\"email\":\"c@qq.com\",\"status\":1,\"roleIdList\":[1],\"createTime\":\"Jun 20, 2019 7:22:56 PM\",\"deptId\":9,\"deptName\":\"1班\"}', '17', '0:0:0:0:0:0:0:1', '2019-06-20 19:28:31');
INSERT INTO `sys_log` VALUES ('7', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":2,\"roleName\":\"四川大学\",\"deptId\":10,\"deptName\":\"四川大学\",\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22],\"deptIdList\":[10],\"createTime\":\"Jun 20, 2019 7:35:31 PM\"}', '124', '0:0:0:0:0:0:0:1', '2019-06-20 19:35:31');
INSERT INTO `sys_log` VALUES ('8', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '{\"roleId\":1,\"roleName\":\"太原理工大学\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,31,32,33,34,35],\"deptIdList\":[6],\"createTime\":\"Jun 20, 2019 7:28:19 PM\"}', '32', '0:0:0:0:0:0:0:1', '2019-06-20 19:35:37');
INSERT INTO `sys_log` VALUES ('9', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":3,\"username\":\"scdx\",\"password\":\"56aff7441f64341b26d71f6b6313f0794c8a8281003db7b932de774c231492e6\",\"salt\":\"I2xT5i3JLbjd1ZwYhanl\",\"email\":\"scdx@qq.com\",\"status\":1,\"roleIdList\":[2],\"createTime\":\"Jun 20, 2019 7:36:02 PM\",\"deptId\":10,\"deptName\":\"四川大学\"}', '43', '0:0:0:0:0:0:0:1', '2019-06-20 19:36:02');
INSERT INTO `sys_log` VALUES ('10', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":41,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"账号管理\",\"type\":0,\"icon\":\"fa-address-book\",\"orderNum\":1}', '12', '0:0:0:0:0:0:0:1', '2019-06-20 19:38:48');
INSERT INTO `sys_log` VALUES ('11', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":41,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"账号管理\",\"type\":0,\"icon\":\"fa fa-address-book\",\"orderNum\":1}', '12', '0:0:0:0:0:0:0:1', '2019-06-20 19:39:10');
INSERT INTO `sys_log` VALUES ('12', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"账号管理\",\"name\":\"用户账号\",\"url\":\"1\",\"type\":1,\"orderNum\":0}', '11', '0:0:0:0:0:0:0:1', '2019-06-20 19:44:17');
INSERT INTO `sys_log` VALUES ('13', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"账号管理\",\"name\":\"用户账号\",\"url\":\"modules/stu/sysstu.html\",\"perms\":\"sys:sysstu:list,sys:sysstu:info,sys:sysstu:save,sys:sysstu:update,sys:sysstu:delete\",\"type\":1,\"icon\":\"fa fa-address-book-o\",\"orderNum\":0}', '11', '0:0:0:0:0:0:0:1', '2019-06-20 20:18:08');
INSERT INTO `sys_log` VALUES ('14', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"账号管理\",\"name\":\"用户账号\",\"url\":\"modules/stu/sysstu.html\",\"perms\":\"sys:sysstu:list,sys:sysstu:info,sys:sysstu:save,sys:sysstu:update,sys:sysstu:delete\",\"type\":1,\"icon\":\"fa fa-address-book-o\",\"orderNum\":0}', '14', '0:0:0:0:0:0:0:1', '2019-06-22 14:56:50');
INSERT INTO `sys_log` VALUES ('15', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"账号管理\",\"name\":\"用户账号\",\"url\":\"modules/stu/sysstu.html\",\"perms\":\"sys:user:list,sys:user:info,sys:user:save,sys:user:update,sys:user:delete\",\"type\":1,\"icon\":\"fa fa-address-book-o\",\"orderNum\":0}', '20', '0:0:0:0:0:0:0:1', '2019-06-22 15:38:55');
INSERT INTO `sys_log` VALUES ('16', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":3,\"roleName\":\"四川大学通信物联网工程01班学生\",\"deptId\":11,\"deptName\":\"通信学院\",\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,41,42],\"deptIdList\":[],\"createTime\":\"Jun 22, 2019 4:09:21 PM\"}', '190', '0:0:0:0:0:0:0:1', '2019-06-22 16:09:22');
INSERT INTO `sys_log` VALUES ('17', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":4,\"username\":\"cemb\",\"password\":\"45737d6eb5b84a2846f28f23c599a32309d42ea82cd2ffae49f4285fb7065e26\",\"salt\":\"FPFW2TJEKTHcptaTVvWV\",\"email\":\"q@qq.com\",\"mobile\":\"1\",\"status\":1,\"roleIdList\":[3],\"createTime\":\"Jun 22, 2019 4:51:13 PM\",\"deptId\":11,\"deptName\":\"通信学院\",\"sex\":\"男\",\"birthday\":\"1\",\"idcard\":\"3\",\"ip\":\"0:0:0:0:0:0:0:1\",\"name\":\"3\",\"type\":2}', '2376', '0:0:0:0:0:0:0:1', '2019-06-22 16:51:14');
INSERT INTO `sys_log` VALUES ('18', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":4,\"username\":\"cemb\",\"salt\":\"FPFW2TJEKTHcptaTVvWV\",\"email\":\"q@qq.com\",\"mobile\":\"1\",\"status\":1,\"roleIdList\":[3],\"createTime\":\"Jun 22, 2019 4:51:14 PM\",\"deptId\":11,\"deptName\":\"通信学院\",\"sex\":\"男\",\"birthday\":\"1\",\"idcard\":\"3\",\"ip\":\"0:0:0:0:0:0:0:1\",\"name\":\"你\",\"type\":2}', '18', '0:0:0:0:0:0:0:1', '2019-06-22 16:55:47');
INSERT INTO `sys_log` VALUES ('19', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":43,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"试题管理\",\"type\":0,\"icon\":\"fa fa-file-text-o\",\"orderNum\":2}', '9', '0:0:0:0:0:0:0:1', '2019-06-25 09:13:31');
INSERT INTO `sys_log` VALUES ('20', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":44,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"过程管理\",\"type\":0,\"icon\":\"fa fa-superpowers\",\"orderNum\":3}', '4', '0:0:0:0:0:0:0:1', '2019-06-25 09:14:35');
INSERT INTO `sys_log` VALUES ('21', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":45,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"过程管理\",\"type\":0,\"icon\":\"fa fa-superpowers\",\"orderNum\":3}', '6', '0:0:0:0:0:0:0:1', '2019-06-25 09:14:36');
INSERT INTO `sys_log` VALUES ('22', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '45', '23', '0:0:0:0:0:0:0:1', '2019-06-25 09:15:17');
INSERT INTO `sys_log` VALUES ('23', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":46,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"个人事务\",\"type\":0,\"icon\":\"fa fa-user-circle-o\",\"orderNum\":4}', '5', '0:0:0:0:0:0:0:1', '2019-06-25 09:15:53');
INSERT INTO `sys_log` VALUES ('24', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":47,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"系统设置\",\"type\":0,\"icon\":\"fa fa-sun-o\",\"orderNum\":5}', '8', '0:0:0:0:0:0:0:1', '2019-06-25 10:05:55');
INSERT INTO `sys_log` VALUES ('25', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '47', '14', '0:0:0:0:0:0:0:1', '2019-06-25 10:08:31');
INSERT INTO `sys_log` VALUES ('26', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '5', '1', '0:0:0:0:0:0:0:1', '2019-06-25 10:08:49');
INSERT INTO `sys_log` VALUES ('27', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":48,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"题库管理\",\"type\":0,\"icon\":\"fa fa-file-text\",\"orderNum\":6}', '2', '0:0:0:0:0:0:0:1', '2019-06-25 10:11:09');
INSERT INTO `sys_log` VALUES ('28', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":49,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"成绩管理\",\"type\":0,\"icon\":\"fa fa-file-o\",\"orderNum\":7}', '3', '0:0:0:0:0:0:0:1', '2019-06-25 10:22:17');
INSERT INTO `sys_log` VALUES ('29', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":50,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"新闻管理\",\"type\":0,\"icon\":\"fa fa-newspaper-o\",\"orderNum\":8}', '5', '0:0:0:0:0:0:0:1', '2019-06-25 10:23:03');
INSERT INTO `sys_log` VALUES ('30', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":51,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"新闻管理\",\"type\":0,\"icon\":\"fa fa-newspaper-o\",\"orderNum\":8}', '5', '0:0:0:0:0:0:0:1', '2019-06-25 10:23:04');
INSERT INTO `sys_log` VALUES ('31', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '51', '12', '0:0:0:0:0:0:0:1', '2019-06-25 10:23:09');
INSERT INTO `sys_log` VALUES ('32', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":50,\"parentId\":50,\"parentName\":\"新闻管理\",\"name\":\"新闻管理\",\"url\":\"modules/news/sysnews.html\",\"perms\":\"sys:sysnews:list,sys:sysnews:info,sys:sysnews:save,sys:sysnews:update,sys:sysnews:delete\\r\\n\",\"type\":1,\"icon\":\"fa fa-newspaper-t\",\"orderNum\":8}', '16', '0:0:0:0:0:0:0:1', '2019-06-26 15:52:24');
INSERT INTO `sys_log` VALUES ('33', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":50,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"新闻管理\",\"url\":\"modules/news/sysnews.html\",\"perms\":\"sys:sysnews:list,sys:sysnews:info,sys:sysnews:save,sys:sysnews:update,sys:sysnews:delete\\r\\n\",\"type\":0,\"icon\":\"fa fa-newspaper-o\",\"orderNum\":8}', '4', '0:0:0:0:0:0:0:1', '2019-06-26 15:52:46');
INSERT INTO `sys_log` VALUES ('34', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":51,\"parentId\":50,\"parentName\":\"新闻管理\",\"name\":\"新闻管理\",\"url\":\"modules/news/sysnews.html\",\"perms\":\"sys:sysnews:list,sys:sysnews:info,sys:sysnews:save,sys:sysnews:update,sys:sysnews:delete\",\"type\":1,\"icon\":\"fa fa-newspaper-t\",\"orderNum\":0}', '10', '0:0:0:0:0:0:0:1', '2019-06-26 15:53:24');
INSERT INTO `sys_log` VALUES ('35', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":4,\"roleName\":\"111\",\"remark\":\"222\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"menuIdList\":[],\"deptIdList\":[7,10],\"createTime\":\"Jul 6, 2019 12:18:58 PM\"}', '167', '0:0:0:0:0:0:0:1', '2019-07-06 12:18:58');
INSERT INTO `sys_log` VALUES ('36', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":52,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"新闻列表\",\"url\":\"modules/news/myself.html\",\"type\":1,\"icon\":\"fa fa-file-text-o\",\"orderNum\":0}', '16', '0:0:0:0:0:0:0:1', '2019-07-06 16:09:43');
INSERT INTO `sys_log` VALUES ('37', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":52,\"parentId\":46,\"parentName\":\"个人事务\",\"name\":\"新闻列表\",\"url\":\"modules/news/myself.html\",\"type\":1,\"icon\":\"fa fa-file-text-o\",\"orderNum\":0}', '24', '0:0:0:0:0:0:0:1', '2019-07-06 16:10:09');
INSERT INTO `sys_log` VALUES ('38', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":51,\"parentId\":50,\"parentName\":\"新闻管理\",\"name\":\"新闻管理\",\"url\":\"modules/news/sysnews.html\",\"perms\":\"sys:sysnews:list,sys:sysnews:info,sys:sysnews:save,sys:sysnews:update,sys:sysnews:delete,uploadFile:save\",\"type\":1,\"icon\":\"fa fa-bars\",\"orderNum\":0}', '13', '0:0:0:0:0:0:0:1', '2019-07-06 16:13:03');
INSERT INTO `sys_log` VALUES ('39', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":52,\"parentId\":46,\"parentName\":\"个人事务\",\"name\":\"新闻列表\",\"url\":\"modules/myself/myself.html\",\"perms\":\"sys:sysnews:list,sys:sysnews:info,sys:sysnews:save,sys:sysnews:update,sys:sysnews:delete,uploadFile:save\",\"type\":1,\"icon\":\"fa fa-file-code-o\",\"orderNum\":0}', '19', '0:0:0:0:0:0:0:1', '2019-07-06 19:52:56');
INSERT INTO `sys_log` VALUES ('40', 'admin', '删除角色', 'io.renren.modules.sys.controller.SysRoleController.delete()', '[1,2,3,4]', '51', '0:0:0:0:0:0:0:1', '2019-07-07 09:43:44');
INSERT INTO `sys_log` VALUES ('41', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":5,\"roleName\":\"太原理工大学\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"menuIdList\":[1,2,15,16,17,18,31,32,33,34,35,41,42,43,44,48,49,50,51],\"deptIdList\":[6],\"createTime\":\"Jul 7, 2019 9:47:02 AM\"}', '148', '0:0:0:0:0:0:0:1', '2019-07-07 09:47:02');
INSERT INTO `sys_log` VALUES ('42', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":6,\"roleName\":\"计算机学院\",\"deptId\":1,\"deptName\":\"计算机学院\",\"menuIdList\":[1,2,15,16,17,18,31,32,33,34,35,41,42,46,52,48,49,50,51],\"deptIdList\":[1],\"createTime\":\"Jul 7, 2019 9:48:02 AM\"}', '38', '0:0:0:0:0:0:0:1', '2019-07-07 09:48:03');
INSERT INTO `sys_log` VALUES ('43', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":1,\"username\":\"admin\",\"salt\":\"YzcmCZNvbXocrsz9dm8e\",\"email\":\"cemb@163.com\",\"mobile\":\"\",\"status\":1,\"roleIdList\":[],\"createTime\":\"Jun 20, 2019 11:11:11 AM\",\"deptId\":7,\"deptName\":\"在线考试系统\",\"type\":1}', '59', '0:0:0:0:0:0:0:1', '2019-07-07 09:49:48');
INSERT INTO `sys_log` VALUES ('44', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":2,\"username\":\"tylg\",\"salt\":\"Rz4ZaliFQfRMSeSi9UXA\",\"email\":\"c@qq.com\",\"status\":1,\"roleIdList\":[6],\"createTime\":\"Jun 20, 2019 7:22:56 PM\",\"deptId\":1,\"deptName\":\"计算机学院\",\"type\":1}', '19', '0:0:0:0:0:0:0:1', '2019-07-07 09:50:16');
INSERT INTO `sys_log` VALUES ('45', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":1,\"username\":\"admin\",\"salt\":\"YzcmCZNvbXocrsz9dm8e\",\"email\":\"cemb@163.com\",\"mobile\":\"\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jun 20, 2019 11:11:11 AM\",\"deptId\":7,\"deptName\":\"在线考试系统\",\"type\":1}', '19', '0:0:0:0:0:0:0:1', '2019-07-07 09:50:23');
INSERT INTO `sys_log` VALUES ('46', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":2,\"username\":\"tylg\",\"password\":\"24e8c731be850d1a4c6db2df372cc6488bc4d080942ac6d4226d5995ec9bc528\",\"salt\":\"Rz4ZaliFQfRMSeSi9UXA\",\"email\":\"c@qq.com\",\"status\":1,\"roleIdList\":[6],\"createTime\":\"Jun 20, 2019 7:22:56 PM\",\"deptId\":1,\"deptName\":\"计算机学院\",\"type\":1}', '19', '0:0:0:0:0:0:0:1', '2019-07-07 09:50:37');
INSERT INTO `sys_log` VALUES ('47', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":1,\"username\":\"admin\",\"salt\":\"YzcmCZNvbXocrsz9dm8e\",\"email\":\"cemb@163.com\",\"mobile\":\"\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jun 20, 2019 11:11:11 AM\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"type\":1}', '152', '0:0:0:0:0:0:0:1', '2019-07-07 10:27:30');
INSERT INTO `sys_log` VALUES ('48', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":54,\"parentId\":1,\"parentName\":\"系统管理\",\"name\":\"科目设置\",\"url\":\"modules/course/syscourse.html\",\"perms\":\"sys:syscourse:list,sys:syscourse:info,sys:syscourse:save,sys:syscourse:update,sys:syscourse:delete,uploadFile:save\",\"type\":1,\"icon\":\"fa fa-wpexplorer\",\"orderNum\":0}', '18', '0:0:0:0:0:0:0:1', '2019-07-07 11:36:38');
INSERT INTO `sys_log` VALUES ('49', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":55,\"parentId\":1,\"parentName\":\"系统管理\",\"name\":\"题型设置\",\"url\":\"modules/topic/systopic.html\",\"perms\":\"sys:systopic:list,sys:systopic:info,sys:systopic:save,sys:systopic:update,sys:systopic:delete,uploadFile:save\",\"type\":1,\"icon\":\"fa fa-check-circle-o \",\"orderNum\":0}', '16', '0:0:0:0:0:0:0:1', '2019-07-07 11:38:28');
INSERT INTO `sys_log` VALUES ('50', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":54,\"parentId\":1,\"parentName\":\"系统管理\",\"name\":\"科目设置\",\"url\":\"modules/course/syscourse.html\",\"perms\":\"sys:syscourse:list,sys:syscourse:info,sys:syscourse:save,sys:syscourse:update,sys:syscourse:delete,uploadFile:save\",\"type\":1,\"icon\":\"fa fa-wpexplorer\",\"orderNum\":10}', '21', '0:0:0:0:0:0:0:1', '2019-07-07 11:39:02');
INSERT INTO `sys_log` VALUES ('51', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":55,\"parentId\":1,\"parentName\":\"系统管理\",\"name\":\"题型设置\",\"url\":\"modules/topic/systopic.html\",\"perms\":\"sys:systopic:list,sys:systopic:info,sys:systopic:save,sys:systopic:update,sys:systopic:delete,uploadFile:save\",\"type\":1,\"icon\":\"fa fa-check-circle-o \",\"orderNum\":11}', '12', '0:0:0:0:0:0:0:1', '2019-07-07 11:39:09');
INSERT INTO `sys_log` VALUES ('52', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":56,\"parentId\":48,\"parentName\":\"题库管理\",\"name\":\"题库列表\",\"url\":\"modules/subject/syssubject.html\",\"perms\":\"sys:syssubject:list,sys:syssubject:info,sys:syssubject:save,sys:syssubject:update,sys:syssubject:delete,uploadFile:save\",\"type\":1,\"icon\":\"fa fa-server\",\"orderNum\":0}', '24', '0:0:0:0:0:0:0:1', '2019-07-07 11:41:23');
INSERT INTO `sys_log` VALUES ('53', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":57,\"parentId\":48,\"parentName\":\"题库管理\",\"name\":\"电子书籍\",\"url\":\"modules/book/sysbook.html\",\"perms\":\"sys:sysbook:list,sys:sysbook:info,sys:sysbook:save,sys:sysbook:update,sys:sysbook:delete,uploadFile:save\",\"type\":1,\"icon\":\"fa fa-television\",\"orderNum\":1}', '11', '0:0:0:0:0:0:0:1', '2019-07-07 11:43:26');
INSERT INTO `sys_log` VALUES ('54', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":58,\"parentId\":48,\"parentName\":\"题库管理\",\"name\":\"题库统计\",\"url\":\"model\",\"type\":1,\"icon\":\"fa fa-window-minimize\",\"orderNum\":2}', '12', '0:0:0:0:0:0:0:1', '2019-07-07 11:44:02');
INSERT INTO `sys_log` VALUES ('55', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"账号管理\",\"name\":\"用户账号\",\"url\":\"modules/stu/sysstu.html\",\"perms\":\"sys:user:list,sys:user:info,sys:user:save,sys:user:update,sys:user:delete,sys:user:saves\",\"type\":1,\"icon\":\"fa fa-address-book-o\",\"orderNum\":0}', '17', '0:0:0:0:0:0:0:1', '2019-07-08 09:39:32');
INSERT INTO `sys_log` VALUES ('56', 'admin', '批量保存用户', 'io.renren.modules.sys.controller.SysUserController.saves()', '{\"userId\":14,\"username\":\"scdx10\",\"password\":\"cc4b7206e4ef29d27e0725b8f94cf37458e1e21c08156d09883d153c9783e1e2\",\"salt\":\"q9ynPjxhrOFlvtzT2La2\",\"status\":1,\"roleIdList\":[6],\"createTime\":\"Jul 8, 2019 9:52:19 AM\",\"deptId\":10,\"deptName\":\"四川大学\",\"ip\":\"0:0:0:0:0:0:0:1\",\"type\":2,\"num\":10}', '136', '0:0:0:0:0:0:0:1', '2019-07-08 09:52:19');
INSERT INTO `sys_log` VALUES ('57', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":15,\"username\":\"111\",\"password\":\"36b421384a70001acf189cb5b1937b6220261019e8b3fce3a4d348f680708020\",\"salt\":\"sTdPAlpCLhgH157XswbA\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jul 8, 2019 10:02:18 AM\",\"deptId\":7,\"deptName\":\"在线考试系统\",\"ip\":\"0:0:0:0:0:0:0:1\",\"type\":2,\"num\":0}', '43', '0:0:0:0:0:0:0:1', '2019-07-08 10:02:18');
INSERT INTO `sys_log` VALUES ('58', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"账号管理\",\"name\":\"用户账号\",\"url\":\"modules/stu/sysstu.html\",\"perms\":\"sys:user:list,sys:user:info,sys:user:save,sys:user:update,sys:user:delete,sys:user:saves,sys:user:import\",\"type\":1,\"icon\":\"fa fa-address-book-o\",\"orderNum\":0}', '29', '0:0:0:0:0:0:0:1', '2019-07-08 16:03:48');
INSERT INTO `sys_log` VALUES ('59', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', null, '12081', '0:0:0:0:0:0:0:1', '2019-07-08 17:29:20');
INSERT INTO `sys_log` VALUES ('60', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', '{\"password\":\"ImportUser.xls1\",\"status\":1,\"roleIdList\":[5],\"deptId\":6,\"deptName\":\"太原理工大学\",\"type\":2,\"num\":0}', '13285', '0:0:0:0:0:0:0:1', '2019-07-09 20:13:20');
INSERT INTO `sys_log` VALUES ('61', 'admin', '删除用户', 'io.renren.modules.sys.controller.SysUserController.delete()', '[5,6,7,8,9,10,11,12,13,14,15,16,17,18]', '16', '0:0:0:0:0:0:0:1', '2019-07-09 21:58:07');
INSERT INTO `sys_log` VALUES ('62', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '19340', '0:0:0:0:0:0:0:1', '2019-07-10 09:41:08');
INSERT INTO `sys_log` VALUES ('63', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '6430', '0:0:0:0:0:0:0:1', '2019-07-10 09:41:20');
INSERT INTO `sys_log` VALUES ('64', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '110', '0:0:0:0:0:0:0:1', '2019-07-10 09:43:45');
INSERT INTO `sys_log` VALUES ('65', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '4', '0:0:0:0:0:0:0:1', '2019-07-10 09:43:48');
INSERT INTO `sys_log` VALUES ('66', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '4', '0:0:0:0:0:0:0:1', '2019-07-10 09:43:52');
INSERT INTO `sys_log` VALUES ('67', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '27362', '0:0:0:0:0:0:0:1', '2019-07-10 10:08:39');
INSERT INTO `sys_log` VALUES ('68', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '6942', '0:0:0:0:0:0:0:1', '2019-07-10 10:11:20');
INSERT INTO `sys_log` VALUES ('69', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '10', '0:0:0:0:0:0:0:1', '2019-07-10 10:11:41');
INSERT INTO `sys_log` VALUES ('70', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '4', '0:0:0:0:0:0:0:1', '2019-07-10 10:11:50');
INSERT INTO `sys_log` VALUES ('71', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '3168', '0:0:0:0:0:0:0:1', '2019-07-10 10:12:05');
INSERT INTO `sys_log` VALUES ('72', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '103', '0:0:0:0:0:0:0:1', '2019-07-10 10:42:20');
INSERT INTO `sys_log` VALUES ('73', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '5', '0:0:0:0:0:0:0:1', '2019-07-10 10:43:26');
INSERT INTO `sys_log` VALUES ('74', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '5', '0:0:0:0:0:0:0:1', '2019-07-10 10:43:46');
INSERT INTO `sys_log` VALUES ('75', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '106', '0:0:0:0:0:0:0:1', '2019-07-10 10:47:27');
INSERT INTO `sys_log` VALUES ('76', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '109', '0:0:0:0:0:0:0:1', '2019-07-10 11:02:30');
INSERT INTO `sys_log` VALUES ('77', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '110', '0:0:0:0:0:0:0:1', '2019-07-10 11:04:15');
INSERT INTO `sys_log` VALUES ('78', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '3', '0:0:0:0:0:0:0:1', '2019-07-10 11:04:36');
INSERT INTO `sys_log` VALUES ('79', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '96', '0:0:0:0:0:0:0:1', '2019-07-10 11:09:46');
INSERT INTO `sys_log` VALUES ('80', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '71', '0:0:0:0:0:0:0:1', '2019-07-10 11:16:02');
INSERT INTO `sys_log` VALUES ('81', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', '{\"password\":\"用户账号表.xls1\",\"status\":1,\"roleIdList\":[5],\"deptId\":6,\"deptName\":\"太原理工大学\",\"type\":2,\"num\":0}', '1', '0:0:0:0:0:0:0:1', '2019-07-10 11:24:25');
INSERT INTO `sys_log` VALUES ('82', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', '{\"password\":\"用户账号表.xls1\",\"status\":1,\"roleIdList\":[5],\"deptId\":6,\"deptName\":\"太原理工大学\",\"type\":2,\"num\":0}', '10095', '0:0:0:0:0:0:0:1', '2019-07-10 11:25:13');
INSERT INTO `sys_log` VALUES ('83', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', '{\"password\":\"用户账号表.xls1\",\"status\":1,\"roleIdList\":[],\"type\":2,\"num\":0}', '1691', '0:0:0:0:0:0:0:1', '2019-07-10 11:26:03');
INSERT INTO `sys_log` VALUES ('84', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', '{\"password\":\"ImportUser.xls1\",\"status\":1,\"roleIdList\":[],\"type\":2,\"num\":0}', '72', '0:0:0:0:0:0:0:1', '2019-07-10 11:27:43');
INSERT INTO `sys_log` VALUES ('85', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', '{\"password\":\"用户账号表.xls1\",\"status\":1,\"roleIdList\":[5],\"deptId\":6,\"deptName\":\"太原理工大学\",\"type\":2,\"num\":0}', '46', '0:0:0:0:0:0:0:1', '2019-07-10 11:31:18');
INSERT INTO `sys_log` VALUES ('86', 'admin', '删除用户', 'io.renren.modules.sys.controller.SysUserController.delete()', '[20]', '15', '0:0:0:0:0:0:0:1', '2019-07-10 11:31:42');
INSERT INTO `sys_log` VALUES ('87', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', '{\"password\":\"用户账号表.xls1\",\"status\":1,\"roleIdList\":[5],\"deptId\":6,\"deptName\":\"太原理工大学\",\"type\":2,\"num\":0}', '0', '127.0.0.1', '2019-07-10 11:33:25');
INSERT INTO `sys_log` VALUES ('88', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', '{\"password\":\"用户账号表.xls1\",\"status\":1,\"roleIdList\":[],\"type\":2,\"num\":0}', '0', '0:0:0:0:0:0:0:1', '2019-07-10 11:35:22');
INSERT INTO `sys_log` VALUES ('89', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', '{\"password\":\"用户账号表.xls1\",\"status\":1,\"roleIdList\":[],\"type\":2,\"num\":0}', '48', '0:0:0:0:0:0:0:1', '2019-07-10 11:36:59');
INSERT INTO `sys_log` VALUES ('90', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', '{\"password\":\"用户账号表.xls1\",\"status\":1,\"roleIdList\":[5],\"deptId\":6,\"deptName\":\"太原理工大学\",\"type\":2,\"num\":0}', '12', '0:0:0:0:0:0:0:1', '2019-07-10 11:37:38');
INSERT INTO `sys_log` VALUES ('91', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[22]', '22', '0:0:0:0:0:0:0:1', '2019-07-10 11:38:07');
INSERT INTO `sys_log` VALUES ('92', 'admin', '删除用户', 'io.renren.modules.sys.controller.SysUserController.delete()', '[22]', '17', '0:0:0:0:0:0:0:1', '2019-07-10 11:51:35');
INSERT INTO `sys_log` VALUES ('93', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":23,\"username\":\"cb\",\"password\":\"123456lFP7kH6yTJC682u9WUV9\",\"salt\":\"lFP7kH6yTJC682u9WUV9\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jul 11, 2019 4:03:49 PM\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"ip\":\"0:0:0:0:0:0:0:1\",\"type\":1,\"num\":0,\"admin\":\"1\"}', '78', '0:0:0:0:0:0:0:1', '2019-07-11 16:03:49');
INSERT INTO `sys_log` VALUES ('94', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":23,\"username\":\"cb\",\"password\":\"adminlFP7kH6yTJC682u9WUV9\",\"salt\":\"lFP7kH6yTJC682u9WUV9\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jul 11, 2019 4:03:49 PM\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"ip\":\"0:0:0:0:0:0:0:1\",\"type\":1,\"num\":0,\"admin\":\"1\"}', '33', '0:0:0:0:0:0:0:1', '2019-07-11 16:07:36');
INSERT INTO `sys_log` VALUES ('95', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":23,\"username\":\"cb11\",\"password\":\"adminlFP7kH6yTJC682u9WUV9\",\"salt\":\"lFP7kH6yTJC682u9WUV9\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jul 11, 2019 4:03:49 PM\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"ip\":\"0:0:0:0:0:0:0:1\",\"type\":1,\"num\":0,\"admin\":\"1\"}', '18', '0:0:0:0:0:0:0:1', '2019-07-11 16:08:02');
INSERT INTO `sys_log` VALUES ('96', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[27]', '115', '0:0:0:0:0:0:0:1', '2019-07-11 17:33:51');
INSERT INTO `sys_log` VALUES ('97', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '18', '0:0:0:0:0:0:0:1', '2019-07-11 17:34:13');
INSERT INTO `sys_log` VALUES ('98', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '18', '0:0:0:0:0:0:0:1', '2019-07-11 17:34:37');
INSERT INTO `sys_log` VALUES ('99', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '25169', '0:0:0:0:0:0:0:1', '2019-07-11 17:39:28');
INSERT INTO `sys_log` VALUES ('100', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '7', '0:0:0:0:0:0:0:1', '2019-07-11 17:39:53');
INSERT INTO `sys_log` VALUES ('101', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '28496', '0:0:0:0:0:0:0:1', '2019-07-11 17:43:39');
INSERT INTO `sys_log` VALUES ('102', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '30085', '0:0:0:0:0:0:0:1', '2019-07-11 17:44:17');
INSERT INTO `sys_log` VALUES ('103', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '21229', '0:0:0:0:0:0:0:1', '2019-07-11 17:47:26');
INSERT INTO `sys_log` VALUES ('104', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '51067', '0:0:0:0:0:0:0:1', '2019-07-11 17:49:05');
INSERT INTO `sys_log` VALUES ('105', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '107', '0:0:0:0:0:0:0:1', '2019-07-11 17:59:09');
INSERT INTO `sys_log` VALUES ('106', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '1789423', '0:0:0:0:0:0:0:1', '2019-07-11 18:29:18');
INSERT INTO `sys_log` VALUES ('107', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '37806', '0:0:0:0:0:0:0:1', '2019-07-11 18:33:46');
INSERT INTO `sys_log` VALUES ('108', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[26]', '22812', '0:0:0:0:0:0:0:1', '2019-07-11 18:35:17');
INSERT INTO `sys_log` VALUES ('109', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[26]', '32284', '0:0:0:0:0:0:0:1', '2019-07-11 18:44:08');
INSERT INTO `sys_log` VALUES ('110', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '9902', '0:0:0:0:0:0:0:1', '2019-07-11 18:44:23');
INSERT INTO `sys_log` VALUES ('111', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '710211', '0:0:0:0:0:0:0:1', '2019-07-11 18:58:59');
INSERT INTO `sys_log` VALUES ('112', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '204581', '0:0:0:0:0:0:0:1', '2019-07-11 19:03:11');
INSERT INTO `sys_log` VALUES ('113', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '71599', '0:0:0:0:0:0:0:1', '2019-07-11 19:04:27');
INSERT INTO `sys_log` VALUES ('114', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '24176', '0:0:0:0:0:0:0:1', '2019-07-11 19:04:56');
INSERT INTO `sys_log` VALUES ('115', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '539485', '0:0:0:0:0:0:0:1', '2019-07-11 19:13:59');
INSERT INTO `sys_log` VALUES ('116', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '103', '0:0:0:0:0:0:0:1', '2019-07-11 19:31:47');
INSERT INTO `sys_log` VALUES ('117', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '38', '0:0:0:0:0:0:0:1', '2019-07-11 19:32:04');
INSERT INTO `sys_log` VALUES ('118', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '41015', '0:0:0:0:0:0:0:1', '2019-07-11 19:33:54');
INSERT INTO `sys_log` VALUES ('119', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[24]', '88182', '0:0:0:0:0:0:0:1', '2019-07-11 19:45:00');
INSERT INTO `sys_log` VALUES ('120', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '86822', '0:0:0:0:0:0:0:1', '2019-07-11 19:46:33');
INSERT INTO `sys_log` VALUES ('121', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '101', '0:0:0:0:0:0:0:1', '2019-07-11 19:48:17');
INSERT INTO `sys_log` VALUES ('122', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '11291', '0:0:0:0:0:0:0:1', '2019-07-11 19:48:48');
INSERT INTO `sys_log` VALUES ('123', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '55302', '0:0:0:0:0:0:0:1', '2019-07-11 19:53:47');
INSERT INTO `sys_log` VALUES ('124', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '28899', '0:0:0:0:0:0:0:1', '2019-07-11 19:54:24');
INSERT INTO `sys_log` VALUES ('125', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '18391', '0:0:0:0:0:0:0:1', '2019-07-11 19:55:06');
INSERT INTO `sys_log` VALUES ('126', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '103841', '0:0:0:0:0:0:0:1', '2019-07-11 19:57:08');
INSERT INTO `sys_log` VALUES ('127', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '96', '0:0:0:0:0:0:0:1', '2019-07-11 19:59:50');
INSERT INTO `sys_log` VALUES ('128', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '16', '0:0:0:0:0:0:0:1', '2019-07-11 20:00:07');
INSERT INTO `sys_log` VALUES ('129', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":24,\"username\":\"cb1\",\"salt\":\"AZwaRFa1LFg9VNQwi0sq\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jul 11, 2019 4:19:09 PM\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"sex\":\"女\",\"ip\":\"0:0:0:0:0:0:0:1\",\"name\":\"带我去多无群\",\"type\":2,\"num\":20,\"admin\":\"1\"}', '46', '0:0:0:0:0:0:0:1', '2019-07-11 20:01:25');
INSERT INTO `sys_log` VALUES ('130', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":26,\"username\":\"cb3\",\"salt\":\"VZkULPipFh2ELqEPZhXQ\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jul 11, 2019 4:19:09 PM\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"sex\":\"喃\",\"ip\":\"0:0:0:0:0:0:0:1\",\"name\":\"而我却二额无热无热无\",\"type\":2,\"num\":20,\"admin\":\"1\"}', '24', '0:0:0:0:0:0:0:1', '2019-07-11 20:01:37');
INSERT INTO `sys_log` VALUES ('131', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '12', '0:0:0:0:0:0:0:1', '2019-07-11 20:01:40');
INSERT INTO `sys_log` VALUES ('132', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4]', '99', '0:0:0:0:0:0:0:1', '2019-07-11 20:03:42');
INSERT INTO `sys_log` VALUES ('133', 'admin', '导出用户', 'io.renren.modules.sys.controller.SysUserController.outportUsers()', '[4,24,25,26,27]', '17', '0:0:0:0:0:0:0:1', '2019-07-11 20:03:56');
INSERT INTO `sys_log` VALUES ('134', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', '{\"password\":\"用户账.xls1\",\"status\":1,\"roleIdList\":[5],\"deptId\":10,\"deptName\":\"四川大学\",\"type\":2,\"num\":0}', '60', '0:0:0:0:0:0:0:1', '2019-07-11 20:07:35');
INSERT INTO `sys_log` VALUES ('135', 'admin', '导入用户', 'io.renren.modules.sys.controller.SysUserController.importUsers()', '{\"password\":\"用户账.xls1\",\"status\":1,\"roleIdList\":[5],\"deptId\":6,\"deptName\":\"太原理工大学\",\"type\":2,\"num\":0}', '41', '0:0:0:0:0:0:0:1', '2019-07-11 20:08:14');
INSERT INTO `sys_log` VALUES ('136', 'admin', '批量保存用户', 'io.renren.modules.sys.controller.SysUserController.saves()', '{\"userId\":63,\"username\":\"cbb20\",\"password\":\"adminlG4xbp0Z2bC53aJHMBC0yBGF4zk2mb7z0kF8bl4CPL7P6GYCSiwEWrwIbrabiS7ow01kma1WYcZP7v1NVV9swHpnKnLseBkfH58bVah0n8ht4Qni22iyLcJc3PWzAzxn1Ezc2oTGiMz8Pt8BtBaRGzops9TAc6Hjmob1CFJoAblLmuAzUDqn0YYbVzn4BGdiWqAiMa0rcoXFp56nXHybZTw6PYiRYJrrBnX3pl9cxRnz2CZmuhWUqLeLge2gDFFLYhEmOvbCcZIuDrXQs2IrWFteiOcoUX635meVnAJnJyBawGamL84D6uqpkWkTGyrth8bnpgfRIncRrIcshm80qSBEVj8j6FRKIqEiQpiexphYbd6W5pYcP1GYsnzVlcIzi130qoP2rACR20OH\",\"salt\":\"lcIzi130qoP2rACR20OH\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jul 11, 2019 8:11:27 PM\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"ip\":\"0:0:0:0:0:0:0:1\",\"type\":2,\"num\":20,\"admin\":\"1\"}', '160', '0:0:0:0:0:0:0:1', '2019-07-11 20:11:28');
INSERT INTO `sys_log` VALUES ('137', 'admin', '批量保存用户', 'io.renren.modules.sys.controller.SysUserController.saves()', '{\"userId\":93,\"username\":\"cb30\",\"password\":\"e8eb8a4377eced7b4fcdd9d761a3a453d21ed4f2a5bdd687e93430ec0d1cb6bc\",\"passw\":\"admin\",\"salt\":\"DgyXwFy9c2kfVMK4Y5At\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jul 11, 2019 8:15:51 PM\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"ip\":\"0:0:0:0:0:0:0:1\",\"type\":2,\"num\":30,\"admin\":\"1\"}', '323', '0:0:0:0:0:0:0:1', '2019-07-11 20:15:51');
INSERT INTO `sys_log` VALUES ('138', 'cb1', '删除用户', 'io.renren.modules.sys.controller.SysUserController.delete()', '[64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83]', '2', '0:0:0:0:0:0:0:1', '2019-07-11 20:16:28');
INSERT INTO `sys_log` VALUES ('139', 'cb1', '删除用户', 'io.renren.modules.sys.controller.SysUserController.delete()', '[65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83]', '6', '0:0:0:0:0:0:0:1', '2019-07-11 20:16:36');
INSERT INTO `sys_log` VALUES ('140', 'cb1', '删除用户', 'io.renren.modules.sys.controller.SysUserController.delete()', '[84,85,86,87,88,89,90,91,92,93]', '11', '0:0:0:0:0:0:0:1', '2019-07-11 20:16:41');
INSERT INTO `sys_log` VALUES ('141', 'cb1', '批量保存用户', 'io.renren.modules.sys.controller.SysUserController.saves()', '{\"userId\":98,\"username\":\"zz5\",\"password\":\"9ec391231ae2d5e36876850e85b3a3b055dee5928e3b33952c13328eeb592ad8\",\"passw\":\"111\",\"salt\":\"lUA2MhwqA4JEKE26l6fr\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jul 11, 2019 8:17:02 PM\",\"deptId\":1,\"deptName\":\"计算机学院\",\"ip\":\"0:0:0:0:0:0:0:1\",\"type\":2,\"num\":5,\"admin\":\"64\"}', '50', '0:0:0:0:0:0:0:1', '2019-07-11 20:17:02');
INSERT INTO `sys_log` VALUES ('142', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":5,\"username\":\"12321321\",\"password\":\"5d8269a6e06a8d842928d1f48661a8d911b83fc077de27b27370efcc41502ced\",\"passw\":\"admin\",\"salt\":\"zqPGPPYUCWLq0LwUIsBB\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jul 13, 2019 11:14:41 AM\",\"deptId\":13,\"deptName\":\"222\",\"ip\":\"0:0:0:0:0:0:0:1\",\"type\":2,\"num\":0,\"admin\":\"1\"}', '80', '0:0:0:0:0:0:0:1', '2019-07-13 11:14:42');
INSERT INTO `sys_log` VALUES ('143', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":6,\"username\":\"1\",\"password\":\"06e608f30861a2bcc635b034827d5be595aef0aec28aa2187b020ad9f816e0f9\",\"passw\":\"admin\",\"salt\":\"xPWa5leAgDqjlwFSXAve\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Jul 21, 2019 7:36:51 PM\",\"deptId\":12,\"deptName\":\"111\",\"ip\":\"0:0:0:0:0:0:0:1\",\"type\":1,\"num\":0,\"admin\":\"1\"}', '53', '0:0:0:0:0:0:0:1', '2019-07-21 19:36:51');
INSERT INTO `sys_log` VALUES ('144', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '{\"userId\":1,\"username\":\"admin\",\"salt\":\"YzcmCZNvbXocrsz9dm8e\",\"email\":\"cemb@163.com\",\"mobile\":\"\",\"status\":0,\"roleIdList\":[5],\"createTime\":\"Jun 20, 2019 11:11:11 AM\",\"deptId\":6,\"deptName\":\"太原理工大学\",\"name\":\"你带我\",\"type\":1,\"num\":0,\"admin\":\"1\"}', '19', '0:0:0:0:0:0:0:1', '2019-07-21 19:59:22');
INSERT INTO `sys_log` VALUES ('145', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":59,\"parentId\":43,\"parentName\":\"试题管理\",\"name\":\"考试试卷\",\"url\":\"modules/test/systest.html\",\"type\":1,\"orderNum\":0}', '25', '0:0:0:0:0:0:0:1', '2019-07-25 11:09:18');
INSERT INTO `sys_log` VALUES ('146', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '14', '0', '0:0:0:0:0:0:0:1', '2019-07-25 11:13:08');
INSERT INTO `sys_log` VALUES ('147', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":59,\"parentId\":43,\"parentName\":\"试题管理\",\"name\":\"考试试卷\",\"url\":\"modules/test/systest.html\",\"perms\":\"sys:systest:list,sys:systest:info,sys:systest:save,sys:systest:update,sys:systest:delete,uploadFile:save\",\"type\":1,\"icon\":\"fa fa-text-height\",\"orderNum\":0}', '23', '0:0:0:0:0:0:0:1', '2019-07-25 11:33:51');
INSERT INTO `sys_log` VALUES ('148', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":60,\"parentId\":43,\"parentName\":\"试题管理\",\"name\":\"试题管理\",\"url\":\"modules/title/systitle.html\",\"perms\":\"sys:systitle:list,sys:systitle:info,sys:systitle:save,sys:systitle:update,sys:systitle:delete,uploadFile:save\",\"type\":1,\"icon\":\"fa fa-list\",\"orderNum\":1}', '14', '0:0:0:0:0:0:0:1', '2019-07-25 11:36:14');
INSERT INTO `sys_log` VALUES ('149', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":59,\"parentId\":43,\"parentName\":\"试题管理\",\"name\":\"考试试卷\",\"url\":\"modules/test/systest.html\",\"perms\":\"sys:systest:list,sys:systest:info,sys:systest:save,sys:systest:update,sys:systest:delete,uploadFile:save,sys:user:list,sys:user:info,sys:user:save,sys:user:update,sys:user:delete,sys:user:saves,sys:user:import,importUsers:save,sys:user:outport\",\"type\":1,\"icon\":\"fa fa-text-height\",\"orderNum\":0}', '17', '0:0:0:0:0:0:0:1', '2019-07-25 11:56:22');
INSERT INTO `sys_log` VALUES ('150', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":8,\"username\":\"3\",\"password\":\"a6ab157f9fd7fb9c415b12448422e6a06fcb9e84248f86e8faeac62a16ba6138\",\"passw\":\"111\",\"salt\":\"WzAYN8YR1aMibF8brOzC\",\"status\":1,\"roleIdList\":[5,6],\"createTime\":\"Jul 26, 2019 11:01:19 AM\",\"deptId\":12,\"deptName\":\"111\",\"ip\":\"0:0:0:0:0:0:0:1\",\"type\":1,\"num\":0,\"admin\":\"1\"}', '37', '0:0:0:0:0:0:0:1', '2019-07-26 11:01:20');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'fa fa-cog', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '管理员管理', 'modules/sys/user.html', null, '1', 'fa fa-user', '1');
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
INSERT INTO `sys_menu` VALUES ('54', '1', '科目设置', 'modules/course/syscourse.html', 'sys:syscourse:list,sys:syscourse:info,sys:syscourse:save,sys:syscourse:update,sys:syscourse:delete,uploadFile:save,sys:syscourse:select', '1', 'fa fa-wpexplorer', '10');
INSERT INTO `sys_menu` VALUES ('55', '1', '题型设置', 'modules/topic/systopic.html', 'sys:systopic:list,sys:systopic:info,sys:systopic:save,sys:systopic:update,sys:systopic:delete,uploadFile:save', '1', 'fa fa-check-circle-o ', '11');
INSERT INTO `sys_menu` VALUES ('56', '48', '题库列表', 'modules/subject/syssubject.html', 'sys:syssubject:list,sys:syssubject:info,sys:syssubject:save,sys:syssubject:update,sys:syssubject:delete,uploadFile:save,sys/syssubject/list,sys:syssubject:getTopics,sys:syssubject:select,importUsers:save,uploadFile:save,importTests:save,sys:syssubject:input,sys:syssubject:output', '1', 'fa fa-server', '0');
INSERT INTO `sys_menu` VALUES ('57', '48', '电子书籍', 'modules/book/sysbook.html', 'sys:sysbook:list,sys:sysbook:info,sys:sysbook:save,sys:sysbook:update,sys:sysbook:delete,uploadFile:save', '1', 'fa fa-television', '1');
INSERT INTO `sys_menu` VALUES ('58', '48', '题库统计', 'model', null, '1', 'fa fa-window-minimize', '2');
INSERT INTO `sys_menu` VALUES ('59', '43', '考试试卷', 'modules/test/systest.html', 'sys:systest:list,sys:systest:info,sys:systest:save,sys:systest:update,sys:systest:delete,uploadFile:save,sys:user:list,sys:user:info,sys:user:save,sys:user:update,sys:user:delete,sys:user:saves,sys:user:import,importUsers:save,sys:user:outport,sys:systest:select,sys:systest:course,sys:systest:courset,sys:systest:topic,sys:systest:subject,sys:systest:courses,sys:systest:titles', '1', 'fa fa-text-height', '0');
INSERT INTO `sys_menu` VALUES ('60', '43', '试题管理', 'modules/title/systitle.html', 'sys:systitle:list,sys:systitle:info,sys:systitle:save,sys:systitle:update,sys:systitle:delete,uploadFile:save,sys:systitle:type,sys:systest:course,sys:systest:courset,sys:systest:topic,sys:systest:subject,sys:systest:courses', '1', 'fa fa-list', '1');

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
  `admin` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '发布人',
  PRIMARY KEY (`new_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_news
-- ----------------------------
INSERT INTO `sys_news` VALUES ('10', '<p>欢迎使用 <b>wangEditor</b> 富文本编辑器</p><p><img src=\"/testSystem/statics/file/20190707/03572e10-7c93-428e-8485-9eb6aae4d55f.jpg\" style=\"max-width:100%;\"><br></p>', '2019-07-07 09:35:20', 'dwdwqdwqdwdwqdw', '6,', '6', '1');
INSERT INTO `sys_news` VALUES ('11', '<p><img src=\"/testSystem/statics/file/20190707/3ba6bdea-b59f-490b-b8b9-e7198fd0de3e.jpg\" style=\"max-width:100%;\"><br></p>', '2019-07-07 09:53:21', '321312321', '1,', '3', '2');
INSERT INTO `sys_news` VALUES ('12', '<p>321321321</p>', '2019-07-25 11:39:02', '32132', '', '1', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('5', '太原理工大学', null, '6', '2019-07-07 09:47:02');
INSERT INTO `sys_role` VALUES ('6', '计算机学院', null, '1', '2019-07-07 09:48:03');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES ('5', '5', '6');
INSERT INTO `sys_role_dept` VALUES ('6', '6', '1');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('57', '5', '1');
INSERT INTO `sys_role_menu` VALUES ('58', '5', '2');
INSERT INTO `sys_role_menu` VALUES ('59', '5', '15');
INSERT INTO `sys_role_menu` VALUES ('60', '5', '16');
INSERT INTO `sys_role_menu` VALUES ('61', '5', '17');
INSERT INTO `sys_role_menu` VALUES ('62', '5', '18');
INSERT INTO `sys_role_menu` VALUES ('63', '5', '31');
INSERT INTO `sys_role_menu` VALUES ('64', '5', '32');
INSERT INTO `sys_role_menu` VALUES ('65', '5', '33');
INSERT INTO `sys_role_menu` VALUES ('66', '5', '34');
INSERT INTO `sys_role_menu` VALUES ('67', '5', '35');
INSERT INTO `sys_role_menu` VALUES ('68', '5', '41');
INSERT INTO `sys_role_menu` VALUES ('69', '5', '42');
INSERT INTO `sys_role_menu` VALUES ('70', '5', '43');
INSERT INTO `sys_role_menu` VALUES ('71', '5', '44');
INSERT INTO `sys_role_menu` VALUES ('72', '5', '48');
INSERT INTO `sys_role_menu` VALUES ('73', '5', '49');
INSERT INTO `sys_role_menu` VALUES ('74', '5', '50');
INSERT INTO `sys_role_menu` VALUES ('75', '5', '51');
INSERT INTO `sys_role_menu` VALUES ('76', '6', '1');
INSERT INTO `sys_role_menu` VALUES ('77', '6', '2');
INSERT INTO `sys_role_menu` VALUES ('78', '6', '15');
INSERT INTO `sys_role_menu` VALUES ('79', '6', '16');
INSERT INTO `sys_role_menu` VALUES ('80', '6', '17');
INSERT INTO `sys_role_menu` VALUES ('81', '6', '18');
INSERT INTO `sys_role_menu` VALUES ('82', '6', '31');
INSERT INTO `sys_role_menu` VALUES ('83', '6', '32');
INSERT INTO `sys_role_menu` VALUES ('84', '6', '33');
INSERT INTO `sys_role_menu` VALUES ('85', '6', '34');
INSERT INTO `sys_role_menu` VALUES ('86', '6', '35');
INSERT INTO `sys_role_menu` VALUES ('87', '6', '41');
INSERT INTO `sys_role_menu` VALUES ('88', '6', '42');
INSERT INTO `sys_role_menu` VALUES ('89', '6', '46');
INSERT INTO `sys_role_menu` VALUES ('90', '6', '52');
INSERT INTO `sys_role_menu` VALUES ('91', '6', '48');
INSERT INTO `sys_role_menu` VALUES ('92', '6', '49');
INSERT INTO `sys_role_menu` VALUES ('93', '6', '50');
INSERT INTO `sys_role_menu` VALUES ('94', '6', '51');

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_subject
-- ----------------------------
INSERT INTO `sys_subject` VALUES ('10', '7', '2', '易', '2', '100', '1', '1', null, '4', '2019-07-21 10:45:14', null, null, '单位的无多无1', '46c5993b00ff4cf3a5dca3d2440e303e', 'true');
INSERT INTO `sys_subject` VALUES ('11', '7', '3', '易', '2', '1', '11', '1', null, '4', '2019-07-21 10:51:17', null, null, '单位的无多无2', '2f4314cd19dc4609b120ccc5f92322d7', 'true');
INSERT INTO `sys_subject` VALUES ('13', '5', '5', '易', '2', '100', '111', '1', '333', '444', '2019-07-21 11:14:06', null, null, '单位的无多无3', '4551fb02a78f463b9562e2370c73b155', null);
INSERT INTO `sys_subject` VALUES ('14', '3', '8', '难', '2', '111', '1', '1', '3', '444', '2019-07-21 11:18:39', null, null, '单位的无多无4', '20b2ffc587a44d0b84ca4d2d044aadeb', null);
INSERT INTO `sys_subject` VALUES ('15', '4', '7', '易', '2', '111', '44', '1', '555', '666', '2019-07-21 11:18:59', null, null, '单位的无多无5', '4f4f26334a7a4718901a8465f854e3d0', null);
INSERT INTO `sys_subject` VALUES ('17', '7', '4', '易', '2', '11', '111', '1', null, '33', '2019-07-21 11:19:41', null, 'zq', '单位的无多无6', '7a09dcae25d448828652bb8db5e19e5f', null);
INSERT INTO `sys_subject` VALUES ('18', '7', '9', '易', '2', '1', '1', '1', null, '2', '2019-07-21 12:19:26', '/testSystem/statics/file/20190721/29b627a2-fda3-47c1-849a-7870585d780b.xls', null, '单位的无多无', 'e70eb88d717e47368cfd6c2123a0614c', null);

-- ----------------------------
-- Table structure for sys_test
-- ----------------------------
DROP TABLE IF EXISTS `sys_test`;
CREATE TABLE `sys_test` (
  `test_id` bigint(50) NOT NULL AUTO_INCREMENT,
  `dept_id` bigint(10) DEFAULT NULL COMMENT '参考部门id',
  `admin_id` varchar(10) DEFAULT NULL COMMENT '评卷人id',
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
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='考试表';

-- ----------------------------
-- Records of sys_test
-- ----------------------------
INSERT INTO `sys_test` VALUES ('11', '12', '1', '1', '考试', '手工组卷', '11', '111', '11', '1,2,3', '11', '2019-07-29 01:01:00', '2019-07-09 02:02:00', '3', '1', '2019-07-29 11:06:03', 'ec402ff8f77c46c68ea51a0513c5a579', '10');
INSERT INTO `sys_test` VALUES ('12', null, '1', '2', '考试', '手工组卷', null, null, null, '1,3', null, null, null, null, '1', '2019-07-29 12:04:41', '25641d0376514ecb973a2efb8f0d72ad', null);
INSERT INTO `sys_test` VALUES ('14', '13', '1', '3', '考试', '随机组卷', '2', '2', '2', '1,3,2,4', '11', null, null, '3', '1', '2019-07-29 17:39:52', '10a04854a4d049f4b768d6281d6c2c64', '10');

-- ----------------------------
-- Table structure for sys_title
-- ----------------------------
DROP TABLE IF EXISTS `sys_title`;
CREATE TABLE `sys_title` (
  `title_id` bigint(50) NOT NULL AUTO_INCREMENT,
  `test_id` bigint(10) DEFAULT NULL COMMENT '试卷id',
  `subject_id` varchar(10) DEFAULT NULL COMMENT '题库试题id',
  `orders` int(10) DEFAULT NULL COMMENT '题目顺序',
  `admin` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`title_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='试题表';

-- ----------------------------
-- Records of sys_title
-- ----------------------------
INSERT INTO `sys_title` VALUES ('22', '14', '11', '0', '1', '2019-07-29 17:39:52');
INSERT INTO `sys_title` VALUES ('23', '14', '10', '1', '1', '2019-07-29 17:39:52');
INSERT INTO `sys_title` VALUES ('24', '14', '17', '2', '1', '2019-07-29 17:39:52');
INSERT INTO `sys_title` VALUES ('25', '14', '18', '3', '1', '2019-07-29 17:39:52');

-- ----------------------------
-- Table structure for sys_topic
-- ----------------------------
DROP TABLE IF EXISTS `sys_topic`;
CREATE TABLE `sys_topic` (
  `topic_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '题型',
  `admin` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `name` varchar(1000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '题型名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COMMENT='题型表';

-- ----------------------------
-- Records of sys_topic
-- ----------------------------
INSERT INTO `sys_topic` VALUES ('2', '单选类', '1', '0', '单选题', '2019-07-10 17:15:38');
INSERT INTO `sys_topic` VALUES ('3', '多选类', '1', '1', '多选题', '2019-07-10 17:17:43');
INSERT INTO `sys_topic` VALUES ('4', '判断类', '1', '1', '判断题', '2019-07-10 17:17:51');
INSERT INTO `sys_topic` VALUES ('5', '填空类', '1', '1', '填空题', '2019-07-10 17:18:02');
INSERT INTO `sys_topic` VALUES ('6', '问答类', '1', '1', '问答题', '2019-07-10 17:18:09');
INSERT INTO `sys_topic` VALUES ('7', '作文类', '1', '1', '作文题', '2019-07-10 17:18:16');
INSERT INTO `sys_topic` VALUES ('8', '打字类', '1', '1', '打字题', '2019-07-10 17:18:28');
INSERT INTO `sys_topic` VALUES ('9', '操作类', '1', '1', '操作题', '2019-07-10 17:18:34');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(500) DEFAULT NULL COMMENT '密码',
  `passw` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL,
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
  `id_type` varchar(10) DEFAULT NULL COMMENT '证件类型',
  `num` bigint(10) DEFAULT NULL COMMENT '批量数量',
  `admin` varchar(10) DEFAULT NULL COMMENT '创建人id',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'admin', 'YzcmCZNvbXocrsz9dm8e', 'cemb@163.com', '', '1', '6', null, '1', null, null, null, null, null, '0', '1', '你带我', '2019-06-20 11:11:11');
INSERT INTO `sys_user` VALUES ('2', 'tylg', '24e8c731be850d1a4c6db2df372cc6488bc4d080942ac6d4226d5995ec9bc528', 'admin', 'Rz4ZaliFQfRMSeSi9UXA', 'c@qq.com', null, '1', '1', null, '1', null, null, null, null, null, null, '1', '你带我', '2019-06-20 19:22:56');
INSERT INTO `sys_user` VALUES ('3', 'scdx', '56aff7441f64341b26d71f6b6313f0794c8a8281003db7b932de774c231492e6', 'admin', 'I2xT5i3JLbjd1ZwYhanl', 'scdx@qq.com', null, '1', '10', null, '1', null, null, null, null, null, null, '1', '你带我', '2019-06-20 19:36:02');
INSERT INTO `sys_user` VALUES ('4', 'cemb', '45737d6eb5b84a2846f28f23c599a32309d42ea82cd2ffae49f4285fb7065e26', 'admin', 'FPFW2TJEKTHcptaTVvWV', 'q@qq.com', '1', '1', '11', '男', '2', null, '1', '0:0:0:0:0:0:0:1', '3', null, null, '1', '你带我', '2019-06-22 16:51:14');
INSERT INTO `sys_user` VALUES ('5', '12321321', '5d8269a6e06a8d842928d1f48661a8d911b83fc077de27b27370efcc41502ced', 'admin', 'zqPGPPYUCWLq0LwUIsBB', null, null, '1', '13', null, '2', null, null, '0:0:0:0:0:0:0:1', null, null, '0', '1', null, '2019-07-13 11:14:42');
INSERT INTO `sys_user` VALUES ('6', '1', '06e608f30861a2bcc635b034827d5be595aef0aec28aa2187b020ad9f816e0f9', 'admin', 'xPWa5leAgDqjlwFSXAve', null, null, '1', '12', null, '1', null, null, '0:0:0:0:0:0:0:1', null, null, '0', '1', null, '2019-07-21 19:36:51');
INSERT INTO `sys_user` VALUES ('8', '3', 'a6ab157f9fd7fb9c415b12448422e6a06fcb9e84248f86e8faeac62a16ba6138', '111', 'WzAYN8YR1aMibF8brOzC', null, null, '1', '12', null, '1', null, null, '0:0:0:0:0:0:0:1', null, null, '0', '1', null, '2019-07-26 11:01:20');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('7', '2', '6');
INSERT INTO `sys_user_role` VALUES ('11', '7', '6');
INSERT INTO `sys_user_role` VALUES ('13', '9', '6');
INSERT INTO `sys_user_role` VALUES ('14', '10', '6');
INSERT INTO `sys_user_role` VALUES ('15', '11', '6');
INSERT INTO `sys_user_role` VALUES ('16', '12', '6');
INSERT INTO `sys_user_role` VALUES ('17', '13', '6');
INSERT INTO `sys_user_role` VALUES ('18', '14', '6');
INSERT INTO `sys_user_role` VALUES ('19', '15', '5');
INSERT INTO `sys_user_role` VALUES ('20', '16', '5');
INSERT INTO `sys_user_role` VALUES ('21', '17', '5');
INSERT INTO `sys_user_role` VALUES ('22', '18', '5');
INSERT INTO `sys_user_role` VALUES ('23', '20', '5');
INSERT INTO `sys_user_role` VALUES ('24', '22', '5');
INSERT INTO `sys_user_role` VALUES ('27', '23', '5');
INSERT INTO `sys_user_role` VALUES ('29', '25', '5');
INSERT INTO `sys_user_role` VALUES ('31', '27', '5');
INSERT INTO `sys_user_role` VALUES ('32', '24', '5');
INSERT INTO `sys_user_role` VALUES ('33', '26', '5');
INSERT INTO `sys_user_role` VALUES ('34', '28', '5');
INSERT INTO `sys_user_role` VALUES ('35', '30', '5');
INSERT INTO `sys_user_role` VALUES ('36', '32', '5');
INSERT INTO `sys_user_role` VALUES ('37', '34', '5');
INSERT INTO `sys_user_role` VALUES ('38', '35', '5');
INSERT INTO `sys_user_role` VALUES ('39', '36', '5');
INSERT INTO `sys_user_role` VALUES ('40', '37', '5');
INSERT INTO `sys_user_role` VALUES ('41', '38', '5');
INSERT INTO `sys_user_role` VALUES ('42', '39', '5');
INSERT INTO `sys_user_role` VALUES ('43', '40', '5');
INSERT INTO `sys_user_role` VALUES ('44', '41', '5');
INSERT INTO `sys_user_role` VALUES ('45', '42', '5');
INSERT INTO `sys_user_role` VALUES ('46', '43', '5');
INSERT INTO `sys_user_role` VALUES ('47', '44', '5');
INSERT INTO `sys_user_role` VALUES ('48', '45', '5');
INSERT INTO `sys_user_role` VALUES ('49', '46', '5');
INSERT INTO `sys_user_role` VALUES ('50', '47', '5');
INSERT INTO `sys_user_role` VALUES ('51', '48', '5');
INSERT INTO `sys_user_role` VALUES ('52', '49', '5');
INSERT INTO `sys_user_role` VALUES ('53', '50', '5');
INSERT INTO `sys_user_role` VALUES ('54', '51', '5');
INSERT INTO `sys_user_role` VALUES ('55', '52', '5');
INSERT INTO `sys_user_role` VALUES ('56', '53', '5');
INSERT INTO `sys_user_role` VALUES ('57', '54', '5');
INSERT INTO `sys_user_role` VALUES ('58', '55', '5');
INSERT INTO `sys_user_role` VALUES ('59', '56', '5');
INSERT INTO `sys_user_role` VALUES ('60', '57', '5');
INSERT INTO `sys_user_role` VALUES ('61', '58', '5');
INSERT INTO `sys_user_role` VALUES ('62', '59', '5');
INSERT INTO `sys_user_role` VALUES ('63', '60', '5');
INSERT INTO `sys_user_role` VALUES ('64', '61', '5');
INSERT INTO `sys_user_role` VALUES ('65', '62', '5');
INSERT INTO `sys_user_role` VALUES ('66', '63', '5');
INSERT INTO `sys_user_role` VALUES ('67', '64', '5');
INSERT INTO `sys_user_role` VALUES ('68', '65', '5');
INSERT INTO `sys_user_role` VALUES ('69', '66', '5');
INSERT INTO `sys_user_role` VALUES ('70', '67', '5');
INSERT INTO `sys_user_role` VALUES ('71', '68', '5');
INSERT INTO `sys_user_role` VALUES ('72', '69', '5');
INSERT INTO `sys_user_role` VALUES ('73', '70', '5');
INSERT INTO `sys_user_role` VALUES ('74', '71', '5');
INSERT INTO `sys_user_role` VALUES ('75', '72', '5');
INSERT INTO `sys_user_role` VALUES ('76', '73', '5');
INSERT INTO `sys_user_role` VALUES ('77', '74', '5');
INSERT INTO `sys_user_role` VALUES ('78', '75', '5');
INSERT INTO `sys_user_role` VALUES ('79', '76', '5');
INSERT INTO `sys_user_role` VALUES ('80', '77', '5');
INSERT INTO `sys_user_role` VALUES ('81', '78', '5');
INSERT INTO `sys_user_role` VALUES ('82', '79', '5');
INSERT INTO `sys_user_role` VALUES ('83', '80', '5');
INSERT INTO `sys_user_role` VALUES ('84', '81', '5');
INSERT INTO `sys_user_role` VALUES ('85', '82', '5');
INSERT INTO `sys_user_role` VALUES ('86', '83', '5');
INSERT INTO `sys_user_role` VALUES ('87', '84', '5');
INSERT INTO `sys_user_role` VALUES ('88', '85', '5');
INSERT INTO `sys_user_role` VALUES ('89', '86', '5');
INSERT INTO `sys_user_role` VALUES ('90', '87', '5');
INSERT INTO `sys_user_role` VALUES ('91', '88', '5');
INSERT INTO `sys_user_role` VALUES ('92', '89', '5');
INSERT INTO `sys_user_role` VALUES ('93', '90', '5');
INSERT INTO `sys_user_role` VALUES ('94', '91', '5');
INSERT INTO `sys_user_role` VALUES ('95', '92', '5');
INSERT INTO `sys_user_role` VALUES ('96', '93', '5');
INSERT INTO `sys_user_role` VALUES ('97', '94', '5');
INSERT INTO `sys_user_role` VALUES ('98', '95', '5');
INSERT INTO `sys_user_role` VALUES ('99', '96', '5');
INSERT INTO `sys_user_role` VALUES ('100', '97', '5');
INSERT INTO `sys_user_role` VALUES ('101', '98', '5');
INSERT INTO `sys_user_role` VALUES ('102', '5', '5');
INSERT INTO `sys_user_role` VALUES ('103', '6', '5');
INSERT INTO `sys_user_role` VALUES ('104', '1', '5');
INSERT INTO `sys_user_role` VALUES ('105', '8', '5');
INSERT INTO `sys_user_role` VALUES ('106', '8', '6');
