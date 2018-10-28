/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2018-10-28 16:56:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dept`
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', 'IT');
INSERT INTO `dept` VALUES ('2', 'EE');
INSERT INTO `dept` VALUES ('3', 'AC');

-- ----------------------------
-- Table structure for `emp`
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `dept_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_c8wcapxjl4d2caenhkeob1w7f` (`dept_id`),
  CONSTRAINT `FK_c8wcapxjl4d2caenhkeob1w7f` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of emp
-- ----------------------------
INSERT INTO `emp` VALUES ('1', 'Alice', '1');
INSERT INTO `emp` VALUES ('2', 'Bob', '2');
INSERT INTO `emp` VALUES ('3', 'Celly', '3');
INSERT INTO `emp` VALUES ('4', 'Derry', '3');
INSERT INTO `emp` VALUES ('5', 'Easy', '3');
INSERT INTO `emp` VALUES ('6', 'Gold', '2');
INSERT INTO `emp` VALUES ('7', 'Henry', '2');
